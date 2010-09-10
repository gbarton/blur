package com.nearinfinity.blur.manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexDeletionPolicy;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

import com.nearinfinity.blur.lucene.index.SuperDocument;
import com.nearinfinity.blur.thrift.generated.BlurException;
import com.nearinfinity.blur.thrift.generated.Column;
import com.nearinfinity.blur.thrift.generated.Row;
import com.nearinfinity.blur.thrift.generated.SuperColumn;
import com.nearinfinity.blur.thrift.generated.SuperColumnFamily;
import com.nearinfinity.mele.Mele;

public class IndexManager {

	private static final Log LOG = LogFactory.getLog(IndexManager.class);
	private Mele mele;
	private Map<String, Map<String, IndexWriter>> indexWriters = new ConcurrentHashMap<String, Map<String, IndexWriter>>();
	private Map<String, Analyzer> analyzers = new ConcurrentHashMap<String, Analyzer>();
	private Map<String, Partitioner> partitioners = new ConcurrentHashMap<String, Partitioner>();

	public IndexManager() throws IOException {
		setupMele();
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				close();
			}
		}));
	}

	public Map<String, IndexReader> getIndexReaders(String table)
			throws IOException {
		Map<String, IndexWriter> map = indexWriters.get(table);
		Map<String, IndexReader> reader = new HashMap<String, IndexReader>();
		for (Entry<String, IndexWriter> writer : map.entrySet()) {
			reader.put(writer.getKey(), writer.getValue().getReader());
		}
		return reader;
	}
	
	public void replace(String table, Row row) throws BlurException {
		IndexWriter indexWriter = getIndexWriter(table,row.id);
		try {
			replace(indexWriter,row);
		} catch (IOException e) {
			LOG.error("Unknown error",e);
			throw new BlurException("Unknown error [" + e.getMessage() + "]");
		}
	}
	
	public static void replace(IndexWriter indexWriter, Row row) throws IOException {
		replace(indexWriter,createSuperDocument(row));
	}
	
	public static void replace(IndexWriter indexWriter, SuperDocument document) throws IOException {
		indexWriter.deleteDocuments(new Term(SuperDocument.ID, document.getId()));
		if (!replaceInternal(indexWriter,document)) {
			indexWriter.deleteDocuments(new Term(SuperDocument.ID, document.getId()));
			if (!replaceInternal(indexWriter,document)) {
				throw new IOException("SuperDocument too large, try increasing ram buffer size.");
			}
		}
	}

	public void close() {
		for (Entry<String, Map<String, IndexWriter>> writers : indexWriters.entrySet()) {
			for (Entry<String, IndexWriter> writer : writers.getValue().entrySet()) {
				try {
					writer.getValue().close();
				} catch (IOException e) {
					LOG.error("Erroring trying to close [" + writers.getKey()
							+ "] [" + writer.getKey() + "]", e);
				}
			}
		}
	}

	public long appendRow(String table, Row row) {
		return 0;
	}

	public long replaceRow(String table, Row row) {
		return 0;
	}

	public long removeSuperColumn(String table, String id, String superColumnId) {
		return 0;
	}

	public long removeRow(String table, String id) {
		return 0;
	}

	public SuperColumn fetchSuperColumn(String table, String id, String superColumnFamilyName, String superColumnId) {
		return null;
	}

	public Row fetchRow(String table, String id) {
		return null;
	}

	private void setupMele() throws IOException {
		mele = Mele.getMele();
		List<String> listClusters = mele.listClusters();
		for (String cluster : listClusters) {
			setupPartitioner(cluster);
			Map<String, IndexWriter> map = indexWriters.get(cluster);
			if (map == null) {
				map = new ConcurrentHashMap<String, IndexWriter>();
				indexWriters.put(cluster, map);
			}
			List<String> localDirectories = mele.listLocalDirectories(cluster);
			openForWriting(cluster, localDirectories, map);
		}

		// @todo once all are brought online, look for shards that are not
		// online yet...
		for (String cluster : listClusters) {
			Map<String, IndexWriter> map = indexWriters.get(cluster);
			if (map == null) {
				map = new ConcurrentHashMap<String, IndexWriter>();
				indexWriters.put(cluster, map);
			}
			List<String> listDirectories = mele.listDirectories(cluster);
			openForWriting(cluster, listDirectories, map);
		}
	}

	private void setupPartitioner(String cluster) throws IOException {
		partitioners.put(cluster, new Partitioner(mele.listDirectories(cluster)));
	}

	private void openForWriting(String cluster, List<String> dirs, Map<String, IndexWriter> writersMap) throws IOException {
		for (String local : dirs) {
			// @todo get zk lock here....??? maybe if the index writer cannot
			// get a lock that is good enough???
			// also maybe send this into a loop so that if a node goes down it
			// will picks the pieces...
			// need to think about what happens when a node comes back online.
			Directory directory = mele.open(cluster, local);
			IndexDeletionPolicy deletionPolicy = mele.getIndexDeletionPolicy(cluster, local);
			Analyzer analyzer = analyzers.get(cluster);
			if (analyzer == null) {
				analyzer = new StandardAnalyzer(Version.LUCENE_30);
			}
			try {
				if (!IndexWriter.isLocked(directory)) {
					IndexWriter indexWriter = new IndexWriter(directory, analyzer, deletionPolicy, MaxFieldLength.UNLIMITED);
					writersMap.put(local, indexWriter);
				}
			} catch (LockObtainFailedException e) {
				LOG.info("Cluster [" + cluster + "] shard [" + local + "] is locked by another shard.");
			}
		}
	}
	
	private IndexWriter getIndexWriter(String table, String id) throws BlurException {
		Partitioner partitioner = partitioners.get(table);
		if (id == null) {
			throw new BlurException("null mutation id");
		}
		String shard = partitioner.getShard(id);
		Map<String, IndexWriter> tableWriters = indexWriters.get(table);
		if (tableWriters == null) {
			LOG.error("Table [" + table + "] not online in this server.");
			throw new BlurException("Table [" + table
					+ "] not online in this server.");
		}
		IndexWriter indexWriter = tableWriters.get(shard);
		if (indexWriter == null) {
			LOG.error("Shard [" + shard + "] from table [" + table
					+ "] not online in this server.");
			throw new BlurException("Shard [" + shard + "] from table ["
					+ table + "] not online in this server.");
		}
		return indexWriter;
	}
	

	private static boolean replaceInternal(IndexWriter indexWriter, SuperDocument document) throws IOException {
		long oldRamSize = indexWriter.ramSizeInBytes();
		for (Document doc : document.getAllDocumentsForIndexing()) {
			long newRamSize = indexWriter.ramSizeInBytes();
			if (newRamSize < oldRamSize) {
				LOG.info("Flush occur during writing of super document, start over.");
				return false;
			}
			oldRamSize = newRamSize;
			indexWriter.addDocument(doc);
		}
		return true;
	}

	public static SuperDocument createSuperDocument(Row row) {
		SuperDocument document = new SuperDocument(row.id);
		for (Entry<String, SuperColumnFamily> superColumnFamilyEntry : row.superColumnFamilies.entrySet()) {
			String superColumnFamilyName = superColumnFamilyEntry.getKey();
			SuperColumnFamily superColumnFamily = superColumnFamilyEntry.getValue();
			for (Entry<String, SuperColumn> superColumnEntry : superColumnFamily.superColumns.entrySet()) {
				String superColumnId = superColumnEntry.getKey();
				SuperColumn superColumn = superColumnEntry.getValue();
				for (Entry<String, Column> columnEntry : superColumn.columns.entrySet()) {
					String columnName = columnEntry.getKey();
					Column column = columnEntry.getValue();
					for (String value : column.values) {
						document.addFieldStoreAnalyzedNoNorms(superColumnFamilyName, superColumnId, columnName, value);
					}
				}
			}
		}
		return document;
	}
	
}