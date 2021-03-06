/**
 * Autogenerated by Thrift Compiler (0.7.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.nearinfinity.blur.thrift.generated;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The SimpleQuery object holds the query string (normal Lucene syntax), filters and type of scoring (used when super query is on).
 */
public class SimpleQuery implements org.apache.thrift.TBase<SimpleQuery, SimpleQuery._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SimpleQuery");

  private static final org.apache.thrift.protocol.TField QUERY_STR_FIELD_DESC = new org.apache.thrift.protocol.TField("queryStr", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField SUPER_QUERY_ON_FIELD_DESC = new org.apache.thrift.protocol.TField("superQueryOn", org.apache.thrift.protocol.TType.BOOL, (short)2);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField POST_SUPER_FILTER_FIELD_DESC = new org.apache.thrift.protocol.TField("postSuperFilter", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField PRE_SUPER_FILTER_FIELD_DESC = new org.apache.thrift.protocol.TField("preSuperFilter", org.apache.thrift.protocol.TType.STRING, (short)5);

  /**
   * A Lucene syntax based query.
   */
  public String queryStr; // required
  /**
   * If the super query is on, meaning the query will be perform against all the records (joining records in some cases) and the result will be Rows (groupings of Record).
   */
  public boolean superQueryOn; // required
  /**
   * The scoring type, see the document on ScoreType for explanation of each score type.
   * 
   * @see ScoreType
   */
  public ScoreType type; // required
  /**
   * The post super filter (normal Lucene syntax), is a filter performed after the join to filter out entire rows from the results.
   */
  public String postSuperFilter; // required
  /**
   * The pre super filter (normal Lucene syntax), is a filter performed before the join to filter out records from the results.
   */
  public String preSuperFilter; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * A Lucene syntax based query.
     */
    QUERY_STR((short)1, "queryStr"),
    /**
     * If the super query is on, meaning the query will be perform against all the records (joining records in some cases) and the result will be Rows (groupings of Record).
     */
    SUPER_QUERY_ON((short)2, "superQueryOn"),
    /**
     * The scoring type, see the document on ScoreType for explanation of each score type.
     * 
     * @see ScoreType
     */
    TYPE((short)3, "type"),
    /**
     * The post super filter (normal Lucene syntax), is a filter performed after the join to filter out entire rows from the results.
     */
    POST_SUPER_FILTER((short)4, "postSuperFilter"),
    /**
     * The pre super filter (normal Lucene syntax), is a filter performed before the join to filter out records from the results.
     */
    PRE_SUPER_FILTER((short)5, "preSuperFilter");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // QUERY_STR
          return QUERY_STR;
        case 2: // SUPER_QUERY_ON
          return SUPER_QUERY_ON;
        case 3: // TYPE
          return TYPE;
        case 4: // POST_SUPER_FILTER
          return POST_SUPER_FILTER;
        case 5: // PRE_SUPER_FILTER
          return PRE_SUPER_FILTER;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __SUPERQUERYON_ISSET_ID = 0;
  private BitSet __isset_bit_vector = new BitSet(1);

  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.QUERY_STR, new org.apache.thrift.meta_data.FieldMetaData("queryStr", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SUPER_QUERY_ON, new org.apache.thrift.meta_data.FieldMetaData("superQueryOn", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, ScoreType.class)));
    tmpMap.put(_Fields.POST_SUPER_FILTER, new org.apache.thrift.meta_data.FieldMetaData("postSuperFilter", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PRE_SUPER_FILTER, new org.apache.thrift.meta_data.FieldMetaData("preSuperFilter", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SimpleQuery.class, metaDataMap);
  }

  public SimpleQuery() {
    this.superQueryOn = true;

    this.type = com.nearinfinity.blur.thrift.generated.ScoreType.SUPER;

  }

  public SimpleQuery(
    String queryStr,
    boolean superQueryOn,
    ScoreType type,
    String postSuperFilter,
    String preSuperFilter)
  {
    this();
    this.queryStr = queryStr;
    this.superQueryOn = superQueryOn;
    setSuperQueryOnIsSet(true);
    this.type = type;
    this.postSuperFilter = postSuperFilter;
    this.preSuperFilter = preSuperFilter;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SimpleQuery(SimpleQuery other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    if (other.isSetQueryStr()) {
      this.queryStr = other.queryStr;
    }
    this.superQueryOn = other.superQueryOn;
    if (other.isSetType()) {
      this.type = other.type;
    }
    if (other.isSetPostSuperFilter()) {
      this.postSuperFilter = other.postSuperFilter;
    }
    if (other.isSetPreSuperFilter()) {
      this.preSuperFilter = other.preSuperFilter;
    }
  }

  public SimpleQuery deepCopy() {
    return new SimpleQuery(this);
  }

  @Override
  public void clear() {
    this.queryStr = null;
    this.superQueryOn = true;

    this.type = com.nearinfinity.blur.thrift.generated.ScoreType.SUPER;

    this.postSuperFilter = null;
    this.preSuperFilter = null;
  }

  /**
   * A Lucene syntax based query.
   */
  public String getQueryStr() {
    return this.queryStr;
  }

  /**
   * A Lucene syntax based query.
   */
  public SimpleQuery setQueryStr(String queryStr) {
    this.queryStr = queryStr;
    return this;
  }

  public void unsetQueryStr() {
    this.queryStr = null;
  }

  /** Returns true if field queryStr is set (has been assigned a value) and false otherwise */
  public boolean isSetQueryStr() {
    return this.queryStr != null;
  }

  public void setQueryStrIsSet(boolean value) {
    if (!value) {
      this.queryStr = null;
    }
  }

  /**
   * If the super query is on, meaning the query will be perform against all the records (joining records in some cases) and the result will be Rows (groupings of Record).
   */
  public boolean isSuperQueryOn() {
    return this.superQueryOn;
  }

  /**
   * If the super query is on, meaning the query will be perform against all the records (joining records in some cases) and the result will be Rows (groupings of Record).
   */
  public SimpleQuery setSuperQueryOn(boolean superQueryOn) {
    this.superQueryOn = superQueryOn;
    setSuperQueryOnIsSet(true);
    return this;
  }

  public void unsetSuperQueryOn() {
    __isset_bit_vector.clear(__SUPERQUERYON_ISSET_ID);
  }

  /** Returns true if field superQueryOn is set (has been assigned a value) and false otherwise */
  public boolean isSetSuperQueryOn() {
    return __isset_bit_vector.get(__SUPERQUERYON_ISSET_ID);
  }

  public void setSuperQueryOnIsSet(boolean value) {
    __isset_bit_vector.set(__SUPERQUERYON_ISSET_ID, value);
  }

  /**
   * The scoring type, see the document on ScoreType for explanation of each score type.
   * 
   * @see ScoreType
   */
  public ScoreType getType() {
    return this.type;
  }

  /**
   * The scoring type, see the document on ScoreType for explanation of each score type.
   * 
   * @see ScoreType
   */
  public SimpleQuery setType(ScoreType type) {
    this.type = type;
    return this;
  }

  public void unsetType() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  /**
   * The post super filter (normal Lucene syntax), is a filter performed after the join to filter out entire rows from the results.
   */
  public String getPostSuperFilter() {
    return this.postSuperFilter;
  }

  /**
   * The post super filter (normal Lucene syntax), is a filter performed after the join to filter out entire rows from the results.
   */
  public SimpleQuery setPostSuperFilter(String postSuperFilter) {
    this.postSuperFilter = postSuperFilter;
    return this;
  }

  public void unsetPostSuperFilter() {
    this.postSuperFilter = null;
  }

  /** Returns true if field postSuperFilter is set (has been assigned a value) and false otherwise */
  public boolean isSetPostSuperFilter() {
    return this.postSuperFilter != null;
  }

  public void setPostSuperFilterIsSet(boolean value) {
    if (!value) {
      this.postSuperFilter = null;
    }
  }

  /**
   * The pre super filter (normal Lucene syntax), is a filter performed before the join to filter out records from the results.
   */
  public String getPreSuperFilter() {
    return this.preSuperFilter;
  }

  /**
   * The pre super filter (normal Lucene syntax), is a filter performed before the join to filter out records from the results.
   */
  public SimpleQuery setPreSuperFilter(String preSuperFilter) {
    this.preSuperFilter = preSuperFilter;
    return this;
  }

  public void unsetPreSuperFilter() {
    this.preSuperFilter = null;
  }

  /** Returns true if field preSuperFilter is set (has been assigned a value) and false otherwise */
  public boolean isSetPreSuperFilter() {
    return this.preSuperFilter != null;
  }

  public void setPreSuperFilterIsSet(boolean value) {
    if (!value) {
      this.preSuperFilter = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case QUERY_STR:
      if (value == null) {
        unsetQueryStr();
      } else {
        setQueryStr((String)value);
      }
      break;

    case SUPER_QUERY_ON:
      if (value == null) {
        unsetSuperQueryOn();
      } else {
        setSuperQueryOn((Boolean)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((ScoreType)value);
      }
      break;

    case POST_SUPER_FILTER:
      if (value == null) {
        unsetPostSuperFilter();
      } else {
        setPostSuperFilter((String)value);
      }
      break;

    case PRE_SUPER_FILTER:
      if (value == null) {
        unsetPreSuperFilter();
      } else {
        setPreSuperFilter((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case QUERY_STR:
      return getQueryStr();

    case SUPER_QUERY_ON:
      return Boolean.valueOf(isSuperQueryOn());

    case TYPE:
      return getType();

    case POST_SUPER_FILTER:
      return getPostSuperFilter();

    case PRE_SUPER_FILTER:
      return getPreSuperFilter();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case QUERY_STR:
      return isSetQueryStr();
    case SUPER_QUERY_ON:
      return isSetSuperQueryOn();
    case TYPE:
      return isSetType();
    case POST_SUPER_FILTER:
      return isSetPostSuperFilter();
    case PRE_SUPER_FILTER:
      return isSetPreSuperFilter();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SimpleQuery)
      return this.equals((SimpleQuery)that);
    return false;
  }

  public boolean equals(SimpleQuery that) {
    if (that == null)
      return false;

    boolean this_present_queryStr = true && this.isSetQueryStr();
    boolean that_present_queryStr = true && that.isSetQueryStr();
    if (this_present_queryStr || that_present_queryStr) {
      if (!(this_present_queryStr && that_present_queryStr))
        return false;
      if (!this.queryStr.equals(that.queryStr))
        return false;
    }

    boolean this_present_superQueryOn = true;
    boolean that_present_superQueryOn = true;
    if (this_present_superQueryOn || that_present_superQueryOn) {
      if (!(this_present_superQueryOn && that_present_superQueryOn))
        return false;
      if (this.superQueryOn != that.superQueryOn)
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_postSuperFilter = true && this.isSetPostSuperFilter();
    boolean that_present_postSuperFilter = true && that.isSetPostSuperFilter();
    if (this_present_postSuperFilter || that_present_postSuperFilter) {
      if (!(this_present_postSuperFilter && that_present_postSuperFilter))
        return false;
      if (!this.postSuperFilter.equals(that.postSuperFilter))
        return false;
    }

    boolean this_present_preSuperFilter = true && this.isSetPreSuperFilter();
    boolean that_present_preSuperFilter = true && that.isSetPreSuperFilter();
    if (this_present_preSuperFilter || that_present_preSuperFilter) {
      if (!(this_present_preSuperFilter && that_present_preSuperFilter))
        return false;
      if (!this.preSuperFilter.equals(that.preSuperFilter))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(SimpleQuery other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    SimpleQuery typedOther = (SimpleQuery)other;

    lastComparison = Boolean.valueOf(isSetQueryStr()).compareTo(typedOther.isSetQueryStr());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetQueryStr()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.queryStr, typedOther.queryStr);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSuperQueryOn()).compareTo(typedOther.isSetSuperQueryOn());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSuperQueryOn()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.superQueryOn, typedOther.superQueryOn);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetType()).compareTo(typedOther.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, typedOther.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPostSuperFilter()).compareTo(typedOther.isSetPostSuperFilter());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPostSuperFilter()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.postSuperFilter, typedOther.postSuperFilter);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPreSuperFilter()).compareTo(typedOther.isSetPreSuperFilter());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPreSuperFilter()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.preSuperFilter, typedOther.preSuperFilter);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    org.apache.thrift.protocol.TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == org.apache.thrift.protocol.TType.STOP) { 
        break;
      }
      switch (field.id) {
        case 1: // QUERY_STR
          if (field.type == org.apache.thrift.protocol.TType.STRING) {
            this.queryStr = iprot.readString();
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // SUPER_QUERY_ON
          if (field.type == org.apache.thrift.protocol.TType.BOOL) {
            this.superQueryOn = iprot.readBool();
            setSuperQueryOnIsSet(true);
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // TYPE
          if (field.type == org.apache.thrift.protocol.TType.I32) {
            this.type = ScoreType.findByValue(iprot.readI32());
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 4: // POST_SUPER_FILTER
          if (field.type == org.apache.thrift.protocol.TType.STRING) {
            this.postSuperFilter = iprot.readString();
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 5: // PRE_SUPER_FILTER
          if (field.type == org.apache.thrift.protocol.TType.STRING) {
            this.preSuperFilter = iprot.readString();
          } else { 
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          org.apache.thrift.protocol.TProtocolUtil.skip(iprot, field.type);
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();

    // check for required fields of primitive type, which can't be checked in the validate method
    validate();
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    validate();

    oprot.writeStructBegin(STRUCT_DESC);
    if (this.queryStr != null) {
      oprot.writeFieldBegin(QUERY_STR_FIELD_DESC);
      oprot.writeString(this.queryStr);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldBegin(SUPER_QUERY_ON_FIELD_DESC);
    oprot.writeBool(this.superQueryOn);
    oprot.writeFieldEnd();
    if (this.type != null) {
      oprot.writeFieldBegin(TYPE_FIELD_DESC);
      oprot.writeI32(this.type.getValue());
      oprot.writeFieldEnd();
    }
    if (this.postSuperFilter != null) {
      oprot.writeFieldBegin(POST_SUPER_FILTER_FIELD_DESC);
      oprot.writeString(this.postSuperFilter);
      oprot.writeFieldEnd();
    }
    if (this.preSuperFilter != null) {
      oprot.writeFieldBegin(PRE_SUPER_FILTER_FIELD_DESC);
      oprot.writeString(this.preSuperFilter);
      oprot.writeFieldEnd();
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("SimpleQuery(");
    boolean first = true;

    sb.append("queryStr:");
    if (this.queryStr == null) {
      sb.append("null");
    } else {
      sb.append(this.queryStr);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("superQueryOn:");
    sb.append(this.superQueryOn);
    first = false;
    if (!first) sb.append(", ");
    sb.append("type:");
    if (this.type == null) {
      sb.append("null");
    } else {
      sb.append(this.type);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("postSuperFilter:");
    if (this.postSuperFilter == null) {
      sb.append("null");
    } else {
      sb.append(this.postSuperFilter);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("preSuperFilter:");
    if (this.preSuperFilter == null) {
      sb.append("null");
    } else {
      sb.append(this.preSuperFilter);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bit_vector = new BitSet(1);
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

}

