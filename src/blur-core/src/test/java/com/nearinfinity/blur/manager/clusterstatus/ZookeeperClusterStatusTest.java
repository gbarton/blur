package com.nearinfinity.blur.manager.clusterstatus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig.ConfigException;
import org.apache.zookeeper.server.quorum.QuorumPeerMain;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nearinfinity.blur.MiniCluster;
import com.nearinfinity.blur.log.Log;
import com.nearinfinity.blur.log.LogFactory;
import com.nearinfinity.blur.thrift.generated.AnalyzerDefinition;
import com.nearinfinity.blur.thrift.generated.TableDescriptor;
import com.nearinfinity.blur.utils.BlurUtil;

public class ZookeeperClusterStatusTest {

  private static final String TEST = "test";
  private static final String DEFAULT = "default";

  private static final Log LOG = LogFactory.getLog(ZookeeperClusterStatusTest.class);
  private ZooKeeper zooKeeper;
  private ZookeeperClusterStatus clusterStatus;

  public static class QuorumPeerMainRun extends QuorumPeerMain {
    @Override
    public void initializeAndRun(String[] args) throws ConfigException, IOException {
      super.initializeAndRun(args);
    }
  }

  @BeforeClass
  public static void setupOnce() throws InterruptedException, IOException, KeeperException {
    MiniCluster.startZooKeeper("./tmp/zk_test");
  }

  @AfterClass
  public static void teardownOnce() {
    MiniCluster.shutdownZooKeeper();
  }

  @Before
  public void setup() throws KeeperException, InterruptedException, IOException {
    zooKeeper = new ZooKeeper(MiniCluster.getZkConnectionString(), 30000, new Watcher() {
      @Override
      public void process(WatchedEvent event) {

      }
    });
    BlurUtil.setupZookeeper(zooKeeper, DEFAULT);
    clusterStatus = new ZookeeperClusterStatus(zooKeeper);
  }

  @After
  public void teardown() throws InterruptedException {
    clusterStatus.close();
    zooKeeper.close();
  }

  @Test
  public void testGetClusterList() {
    LOG.warn("testGetClusterList");
    List<String> clusterList = clusterStatus.getClusterList(false);
    assertEquals(Arrays.asList(DEFAULT), clusterList);
  }

  @Test
  public void testSafeModeNotSet() throws KeeperException, InterruptedException {
    LOG.warn("testSafeModeNotSet");
    assertFalse(clusterStatus.isInSafeMode(false, DEFAULT));
    new WaitForAnswerToBeCorrect(20L) {
      @Override
      public Object run() {
        return clusterStatus.isInSafeMode(true, DEFAULT);
      }
    }.test(false);
  }

  @Test
  public void testSafeModeSetInPast() throws KeeperException, InterruptedException {
    LOG.warn("testSafeModeSetInPast");
    setSafeModeInPast();
    assertFalse(clusterStatus.isInSafeMode(false, DEFAULT));
    new WaitForAnswerToBeCorrect(20L) {
      @Override
      public Object run() {
        return clusterStatus.isInSafeMode(true, DEFAULT);
      }
    }.test(false);
  }

  @Test
  public void testSafeModeSetInFuture() throws KeeperException, InterruptedException {
    LOG.warn("testSafeModeSetInFuture");
    setSafeModeInFuture();
    assertTrue(clusterStatus.isInSafeMode(false, DEFAULT));
    new WaitForAnswerToBeCorrect(20L) {
      @Override
      public Object run() {
        return clusterStatus.isInSafeMode(true, DEFAULT);
      }
    }.test(true);
  }

  @Test
  public void testGetClusterNoTable() {
    LOG.warn("testGetCluster");
    assertNull(clusterStatus.getCluster(false, TEST));
    assertNull(clusterStatus.getCluster(true, TEST));
  }

  @Test
  public void testGetClusterTable() throws KeeperException, InterruptedException {
    LOG.warn("testGetCluster");
    createTable(TEST);
    assertEquals(DEFAULT, clusterStatus.getCluster(false, TEST));
    new WaitForAnswerToBeCorrect(20L) {
      @Override
      public Object run() {
        return clusterStatus.getCluster(true, TEST);
      }
    }.test(DEFAULT);
  }

  @Test
  public void testGetTableList() {
    assertEquals(Arrays.asList(TEST), clusterStatus.getTableList(false));
  }

  @Test
  public void testIsEnabledNoTable() {
    assertFalse(clusterStatus.isEnabled(false, DEFAULT, "notable"));
    assertFalse(clusterStatus.isEnabled(true, DEFAULT, "notable"));
  }

  @Test
  public void testIsEnabledDisabledTable() throws KeeperException, InterruptedException {
    createTable("disabledtable", false);
    assertFalse(clusterStatus.isEnabled(false, DEFAULT, "disabledtable"));
    assertFalse(clusterStatus.isEnabled(true, DEFAULT, "disabledtable"));
  }
  
  @Test
  public void testIsEnabledEnabledTable() throws KeeperException, InterruptedException {
    createTable("enabledtable", true);
    assertTrue(clusterStatus.isEnabled(false, DEFAULT, "enabledtable"));
    assertTrue(clusterStatus.isEnabled(true, DEFAULT, "enabledtable"));
  }

  private void createTable(String name) throws KeeperException, InterruptedException {
    createTable(name, true);
  }

  private void createTable(String name, boolean enabled) throws KeeperException, InterruptedException {
    TableDescriptor tableDescriptor = new TableDescriptor();
    tableDescriptor.setName(name);
    tableDescriptor.setAnalyzerDefinition(new AnalyzerDefinition());
    tableDescriptor.setTableUri("./tmp/zk_test_hdfs");
    tableDescriptor.setIsEnabled(enabled);
    clusterStatus.createTable(tableDescriptor);
    if (enabled) {
      clusterStatus.enableTable(tableDescriptor.getCluster(), name);
    }
  }

  public abstract class WaitForAnswerToBeCorrect {

    private long totalWaitTimeNanos;

    public WaitForAnswerToBeCorrect(long totalWaitTimeMs) {
      this.totalWaitTimeNanos = TimeUnit.MILLISECONDS.toNanos(totalWaitTimeMs);
    }

    public abstract Object run();

    public void test(Object o) {
      long start = System.nanoTime();
      while (true) {
        Object object = run();
        if (object.equals(o) || object == o) {
          return;
        }
        long now = System.nanoTime();
        if (now - start > totalWaitTimeNanos) {
          fail();
        }
        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          fail(e.getMessage());
        }
      }
    }
  }

  private void setSafeModeInPast() throws KeeperException, InterruptedException {
    String blurSafemodePath = ZookeeperPathConstants.getSafemodePath(DEFAULT);
    Stat stat = zooKeeper.exists(blurSafemodePath, false);
    byte[] data = Long.toString(System.currentTimeMillis() - 60000).getBytes();
    if (stat == null) {
      zooKeeper.create(blurSafemodePath, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }
    zooKeeper.setData(blurSafemodePath, data, -1);
  }

  private void setSafeModeInFuture() throws KeeperException, InterruptedException {
    String blurSafemodePath = ZookeeperPathConstants.getSafemodePath(DEFAULT);
    Stat stat = zooKeeper.exists(blurSafemodePath, false);
    byte[] data = Long.toString(System.currentTimeMillis() + 60000).getBytes();
    if (stat == null) {
      zooKeeper.create(blurSafemodePath, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }
    zooKeeper.setData(blurSafemodePath, data, -1);
  }

}
