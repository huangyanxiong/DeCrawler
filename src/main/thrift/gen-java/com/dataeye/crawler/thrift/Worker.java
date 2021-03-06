/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.dataeye.crawler.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
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
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2017-03-09")
public class Worker implements org.apache.thrift.TBase<Worker, Worker._Fields>, java.io.Serializable, Cloneable, Comparable<Worker> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Worker");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField BIZ_FIELD_DESC = new org.apache.thrift.protocol.TField("biz", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField URL_FIELD_DESC = new org.apache.thrift.protocol.TField("url", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField CRON_FIELD_DESC = new org.apache.thrift.protocol.TField("cron", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new WorkerStandardSchemeFactory());
    schemes.put(TupleScheme.class, new WorkerTupleSchemeFactory());
  }

  public String id; // required
  public String biz; // required
  public String url; // required
  public String cron; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    BIZ((short)2, "biz"),
    URL((short)3, "url"),
    CRON((short)4, "cron");

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
        case 1: // ID
          return ID;
        case 2: // BIZ
          return BIZ;
        case 3: // URL
          return URL;
        case 4: // CRON
          return CRON;
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
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BIZ, new org.apache.thrift.meta_data.FieldMetaData("biz", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.URL, new org.apache.thrift.meta_data.FieldMetaData("url", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CRON, new org.apache.thrift.meta_data.FieldMetaData("cron", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Worker.class, metaDataMap);
  }

  public Worker() {
  }

  public Worker(
    String id,
    String biz,
    String url,
    String cron)
  {
    this();
    this.id = id;
    this.biz = biz;
    this.url = url;
    this.cron = cron;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Worker(Worker other) {
    if (other.isSetId()) {
      this.id = other.id;
    }
    if (other.isSetBiz()) {
      this.biz = other.biz;
    }
    if (other.isSetUrl()) {
      this.url = other.url;
    }
    if (other.isSetCron()) {
      this.cron = other.cron;
    }
  }

  public Worker deepCopy() {
    return new Worker(this);
  }

  @Override
  public void clear() {
    this.id = null;
    this.biz = null;
    this.url = null;
    this.cron = null;
  }

  public String getId() {
    return this.id;
  }

  public Worker setId(String id) {
    this.id = id;
    return this;
  }

  public void unsetId() {
    this.id = null;
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return this.id != null;
  }

  public void setIdIsSet(boolean value) {
    if (!value) {
      this.id = null;
    }
  }

  public String getBiz() {
    return this.biz;
  }

  public Worker setBiz(String biz) {
    this.biz = biz;
    return this;
  }

  public void unsetBiz() {
    this.biz = null;
  }

  /** Returns true if field biz is set (has been assigned a value) and false otherwise */
  public boolean isSetBiz() {
    return this.biz != null;
  }

  public void setBizIsSet(boolean value) {
    if (!value) {
      this.biz = null;
    }
  }

  public String getUrl() {
    return this.url;
  }

  public Worker setUrl(String url) {
    this.url = url;
    return this;
  }

  public void unsetUrl() {
    this.url = null;
  }

  /** Returns true if field url is set (has been assigned a value) and false otherwise */
  public boolean isSetUrl() {
    return this.url != null;
  }

  public void setUrlIsSet(boolean value) {
    if (!value) {
      this.url = null;
    }
  }

  public String getCron() {
    return this.cron;
  }

  public Worker setCron(String cron) {
    this.cron = cron;
    return this;
  }

  public void unsetCron() {
    this.cron = null;
  }

  /** Returns true if field cron is set (has been assigned a value) and false otherwise */
  public boolean isSetCron() {
    return this.cron != null;
  }

  public void setCronIsSet(boolean value) {
    if (!value) {
      this.cron = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((String)value);
      }
      break;

    case BIZ:
      if (value == null) {
        unsetBiz();
      } else {
        setBiz((String)value);
      }
      break;

    case URL:
      if (value == null) {
        unsetUrl();
      } else {
        setUrl((String)value);
      }
      break;

    case CRON:
      if (value == null) {
        unsetCron();
      } else {
        setCron((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return getId();

    case BIZ:
      return getBiz();

    case URL:
      return getUrl();

    case CRON:
      return getCron();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case BIZ:
      return isSetBiz();
    case URL:
      return isSetUrl();
    case CRON:
      return isSetCron();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Worker)
      return this.equals((Worker)that);
    return false;
  }

  public boolean equals(Worker that) {
    if (that == null)
      return false;

    boolean this_present_id = true && this.isSetId();
    boolean that_present_id = true && that.isSetId();
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (!this.id.equals(that.id))
        return false;
    }

    boolean this_present_biz = true && this.isSetBiz();
    boolean that_present_biz = true && that.isSetBiz();
    if (this_present_biz || that_present_biz) {
      if (!(this_present_biz && that_present_biz))
        return false;
      if (!this.biz.equals(that.biz))
        return false;
    }

    boolean this_present_url = true && this.isSetUrl();
    boolean that_present_url = true && that.isSetUrl();
    if (this_present_url || that_present_url) {
      if (!(this_present_url && that_present_url))
        return false;
      if (!this.url.equals(that.url))
        return false;
    }

    boolean this_present_cron = true && this.isSetCron();
    boolean that_present_cron = true && that.isSetCron();
    if (this_present_cron || that_present_cron) {
      if (!(this_present_cron && that_present_cron))
        return false;
      if (!this.cron.equals(that.cron))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_id = true && (isSetId());
    list.add(present_id);
    if (present_id)
      list.add(id);

    boolean present_biz = true && (isSetBiz());
    list.add(present_biz);
    if (present_biz)
      list.add(biz);

    boolean present_url = true && (isSetUrl());
    list.add(present_url);
    if (present_url)
      list.add(url);

    boolean present_cron = true && (isSetCron());
    list.add(present_cron);
    if (present_cron)
      list.add(cron);

    return list.hashCode();
  }

  @Override
  public int compareTo(Worker other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBiz()).compareTo(other.isSetBiz());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBiz()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.biz, other.biz);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUrl()).compareTo(other.isSetUrl());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUrl()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.url, other.url);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCron()).compareTo(other.isSetCron());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCron()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cron, other.cron);
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
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Worker(");
    boolean first = true;

    sb.append("id:");
    if (this.id == null) {
      sb.append("null");
    } else {
      sb.append(this.id);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("biz:");
    if (this.biz == null) {
      sb.append("null");
    } else {
      sb.append(this.biz);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("url:");
    if (this.url == null) {
      sb.append("null");
    } else {
      sb.append(this.url);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("cron:");
    if (this.cron == null) {
      sb.append("null");
    } else {
      sb.append(this.cron);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (id == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'id' was not present! Struct: " + toString());
    }
    if (biz == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'biz' was not present! Struct: " + toString());
    }
    if (url == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'url' was not present! Struct: " + toString());
    }
    if (cron == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'cron' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class WorkerStandardSchemeFactory implements SchemeFactory {
    public WorkerStandardScheme getScheme() {
      return new WorkerStandardScheme();
    }
  }

  private static class WorkerStandardScheme extends StandardScheme<Worker> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Worker struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.id = iprot.readString();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // BIZ
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.biz = iprot.readString();
              struct.setBizIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // URL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.url = iprot.readString();
              struct.setUrlIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CRON
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.cron = iprot.readString();
              struct.setCronIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Worker struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.id != null) {
        oprot.writeFieldBegin(ID_FIELD_DESC);
        oprot.writeString(struct.id);
        oprot.writeFieldEnd();
      }
      if (struct.biz != null) {
        oprot.writeFieldBegin(BIZ_FIELD_DESC);
        oprot.writeString(struct.biz);
        oprot.writeFieldEnd();
      }
      if (struct.url != null) {
        oprot.writeFieldBegin(URL_FIELD_DESC);
        oprot.writeString(struct.url);
        oprot.writeFieldEnd();
      }
      if (struct.cron != null) {
        oprot.writeFieldBegin(CRON_FIELD_DESC);
        oprot.writeString(struct.cron);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class WorkerTupleSchemeFactory implements SchemeFactory {
    public WorkerTupleScheme getScheme() {
      return new WorkerTupleScheme();
    }
  }

  private static class WorkerTupleScheme extends TupleScheme<Worker> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Worker struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.id);
      oprot.writeString(struct.biz);
      oprot.writeString(struct.url);
      oprot.writeString(struct.cron);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Worker struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.id = iprot.readString();
      struct.setIdIsSet(true);
      struct.biz = iprot.readString();
      struct.setBizIsSet(true);
      struct.url = iprot.readString();
      struct.setUrlIsSet(true);
      struct.cron = iprot.readString();
      struct.setCronIsSet(true);
    }
  }

}

