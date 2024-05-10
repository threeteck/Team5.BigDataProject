// ORM class for table 'bitcoin_heist'
// WARNING: This class is AUTO-GENERATED. Modify at your own risk.
//
// Debug information:
// Generated date: Fri May 10 19:38:11 MSK 2024
// For connector: org.apache.sqoop.manager.PostgresqlManager
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;
import org.apache.sqoop.lib.JdbcWritableBridge;
import org.apache.sqoop.lib.DelimiterSet;
import org.apache.sqoop.lib.FieldFormatter;
import org.apache.sqoop.lib.RecordParser;
import org.apache.sqoop.lib.BooleanParser;
import org.apache.sqoop.lib.BlobRef;
import org.apache.sqoop.lib.ClobRef;
import org.apache.sqoop.lib.LargeObjectLoader;
import org.apache.sqoop.lib.SqoopRecord;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class bitcoin_heist extends SqoopRecord  implements DBWritable, Writable {
  private final int PROTOCOL_VERSION = 3;
  public int getClassFormatVersion() { return PROTOCOL_VERSION; }
  public static interface FieldSetterCommand {    void setField(Object value);  }  protected ResultSet __cur_result_set;
  private Map<String, FieldSetterCommand> setters = new HashMap<String, FieldSetterCommand>();
  private void init0() {
    setters.put("address", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bitcoin_heist.this.address = (String)value;
      }
    });
    setters.put("year", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bitcoin_heist.this.year = (Integer)value;
      }
    });
    setters.put("day", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bitcoin_heist.this.day = (Integer)value;
      }
    });
    setters.put("length", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bitcoin_heist.this.length = (Integer)value;
      }
    });
    setters.put("weight", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bitcoin_heist.this.weight = (Double)value;
      }
    });
    setters.put("count", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bitcoin_heist.this.count = (Integer)value;
      }
    });
    setters.put("looped", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bitcoin_heist.this.looped = (Integer)value;
      }
    });
    setters.put("neighbors", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bitcoin_heist.this.neighbors = (Integer)value;
      }
    });
    setters.put("income", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bitcoin_heist.this.income = (Long)value;
      }
    });
    setters.put("label", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bitcoin_heist.this.label = (String)value;
      }
    });
  }
  public bitcoin_heist() {
    init0();
  }
  private String address;
  public String get_address() {
    return address;
  }
  public void set_address(String address) {
    this.address = address;
  }
  public bitcoin_heist with_address(String address) {
    this.address = address;
    return this;
  }
  private Integer year;
  public Integer get_year() {
    return year;
  }
  public void set_year(Integer year) {
    this.year = year;
  }
  public bitcoin_heist with_year(Integer year) {
    this.year = year;
    return this;
  }
  private Integer day;
  public Integer get_day() {
    return day;
  }
  public void set_day(Integer day) {
    this.day = day;
  }
  public bitcoin_heist with_day(Integer day) {
    this.day = day;
    return this;
  }
  private Integer length;
  public Integer get_length() {
    return length;
  }
  public void set_length(Integer length) {
    this.length = length;
  }
  public bitcoin_heist with_length(Integer length) {
    this.length = length;
    return this;
  }
  private Double weight;
  public Double get_weight() {
    return weight;
  }
  public void set_weight(Double weight) {
    this.weight = weight;
  }
  public bitcoin_heist with_weight(Double weight) {
    this.weight = weight;
    return this;
  }
  private Integer count;
  public Integer get_count() {
    return count;
  }
  public void set_count(Integer count) {
    this.count = count;
  }
  public bitcoin_heist with_count(Integer count) {
    this.count = count;
    return this;
  }
  private Integer looped;
  public Integer get_looped() {
    return looped;
  }
  public void set_looped(Integer looped) {
    this.looped = looped;
  }
  public bitcoin_heist with_looped(Integer looped) {
    this.looped = looped;
    return this;
  }
  private Integer neighbors;
  public Integer get_neighbors() {
    return neighbors;
  }
  public void set_neighbors(Integer neighbors) {
    this.neighbors = neighbors;
  }
  public bitcoin_heist with_neighbors(Integer neighbors) {
    this.neighbors = neighbors;
    return this;
  }
  private Long income;
  public Long get_income() {
    return income;
  }
  public void set_income(Long income) {
    this.income = income;
  }
  public bitcoin_heist with_income(Long income) {
    this.income = income;
    return this;
  }
  private String label;
  public String get_label() {
    return label;
  }
  public void set_label(String label) {
    this.label = label;
  }
  public bitcoin_heist with_label(String label) {
    this.label = label;
    return this;
  }
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof bitcoin_heist)) {
      return false;
    }
    bitcoin_heist that = (bitcoin_heist) o;
    boolean equal = true;
    equal = equal && (this.address == null ? that.address == null : this.address.equals(that.address));
    equal = equal && (this.year == null ? that.year == null : this.year.equals(that.year));
    equal = equal && (this.day == null ? that.day == null : this.day.equals(that.day));
    equal = equal && (this.length == null ? that.length == null : this.length.equals(that.length));
    equal = equal && (this.weight == null ? that.weight == null : this.weight.equals(that.weight));
    equal = equal && (this.count == null ? that.count == null : this.count.equals(that.count));
    equal = equal && (this.looped == null ? that.looped == null : this.looped.equals(that.looped));
    equal = equal && (this.neighbors == null ? that.neighbors == null : this.neighbors.equals(that.neighbors));
    equal = equal && (this.income == null ? that.income == null : this.income.equals(that.income));
    equal = equal && (this.label == null ? that.label == null : this.label.equals(that.label));
    return equal;
  }
  public boolean equals0(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof bitcoin_heist)) {
      return false;
    }
    bitcoin_heist that = (bitcoin_heist) o;
    boolean equal = true;
    equal = equal && (this.address == null ? that.address == null : this.address.equals(that.address));
    equal = equal && (this.year == null ? that.year == null : this.year.equals(that.year));
    equal = equal && (this.day == null ? that.day == null : this.day.equals(that.day));
    equal = equal && (this.length == null ? that.length == null : this.length.equals(that.length));
    equal = equal && (this.weight == null ? that.weight == null : this.weight.equals(that.weight));
    equal = equal && (this.count == null ? that.count == null : this.count.equals(that.count));
    equal = equal && (this.looped == null ? that.looped == null : this.looped.equals(that.looped));
    equal = equal && (this.neighbors == null ? that.neighbors == null : this.neighbors.equals(that.neighbors));
    equal = equal && (this.income == null ? that.income == null : this.income.equals(that.income));
    equal = equal && (this.label == null ? that.label == null : this.label.equals(that.label));
    return equal;
  }
  public void readFields(ResultSet __dbResults) throws SQLException {
    this.__cur_result_set = __dbResults;
    this.address = JdbcWritableBridge.readString(1, __dbResults);
    this.year = JdbcWritableBridge.readInteger(2, __dbResults);
    this.day = JdbcWritableBridge.readInteger(3, __dbResults);
    this.length = JdbcWritableBridge.readInteger(4, __dbResults);
    this.weight = JdbcWritableBridge.readDouble(5, __dbResults);
    this.count = JdbcWritableBridge.readInteger(6, __dbResults);
    this.looped = JdbcWritableBridge.readInteger(7, __dbResults);
    this.neighbors = JdbcWritableBridge.readInteger(8, __dbResults);
    this.income = JdbcWritableBridge.readLong(9, __dbResults);
    this.label = JdbcWritableBridge.readString(10, __dbResults);
  }
  public void readFields0(ResultSet __dbResults) throws SQLException {
    this.address = JdbcWritableBridge.readString(1, __dbResults);
    this.year = JdbcWritableBridge.readInteger(2, __dbResults);
    this.day = JdbcWritableBridge.readInteger(3, __dbResults);
    this.length = JdbcWritableBridge.readInteger(4, __dbResults);
    this.weight = JdbcWritableBridge.readDouble(5, __dbResults);
    this.count = JdbcWritableBridge.readInteger(6, __dbResults);
    this.looped = JdbcWritableBridge.readInteger(7, __dbResults);
    this.neighbors = JdbcWritableBridge.readInteger(8, __dbResults);
    this.income = JdbcWritableBridge.readLong(9, __dbResults);
    this.label = JdbcWritableBridge.readString(10, __dbResults);
  }
  public void loadLargeObjects(LargeObjectLoader __loader)
      throws SQLException, IOException, InterruptedException {
  }
  public void loadLargeObjects0(LargeObjectLoader __loader)
      throws SQLException, IOException, InterruptedException {
  }
  public void write(PreparedStatement __dbStmt) throws SQLException {
    write(__dbStmt, 0);
  }

  public int write(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeString(address, 1 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeInteger(year, 2 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(day, 3 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(length, 4 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeDouble(weight, 5 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeInteger(count, 6 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(looped, 7 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(neighbors, 8 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeLong(income, 9 + __off, -5, __dbStmt);
    JdbcWritableBridge.writeString(label, 10 + __off, 12, __dbStmt);
    return 10;
  }
  public void write0(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeString(address, 1 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeInteger(year, 2 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(day, 3 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(length, 4 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeDouble(weight, 5 + __off, 8, __dbStmt);
    JdbcWritableBridge.writeInteger(count, 6 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(looped, 7 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeInteger(neighbors, 8 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeLong(income, 9 + __off, -5, __dbStmt);
    JdbcWritableBridge.writeString(label, 10 + __off, 12, __dbStmt);
  }
  public void readFields(DataInput __dataIn) throws IOException {
this.readFields0(__dataIn);  }
  public void readFields0(DataInput __dataIn) throws IOException {
    if (__dataIn.readBoolean()) { 
        this.address = null;
    } else {
    this.address = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.year = null;
    } else {
    this.year = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.day = null;
    } else {
    this.day = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.length = null;
    } else {
    this.length = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.weight = null;
    } else {
    this.weight = Double.valueOf(__dataIn.readDouble());
    }
    if (__dataIn.readBoolean()) { 
        this.count = null;
    } else {
    this.count = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.looped = null;
    } else {
    this.looped = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.neighbors = null;
    } else {
    this.neighbors = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.income = null;
    } else {
    this.income = Long.valueOf(__dataIn.readLong());
    }
    if (__dataIn.readBoolean()) { 
        this.label = null;
    } else {
    this.label = Text.readString(__dataIn);
    }
  }
  public void write(DataOutput __dataOut) throws IOException {
    if (null == this.address) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, address);
    }
    if (null == this.year) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.year);
    }
    if (null == this.day) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.day);
    }
    if (null == this.length) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.length);
    }
    if (null == this.weight) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.weight);
    }
    if (null == this.count) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.count);
    }
    if (null == this.looped) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.looped);
    }
    if (null == this.neighbors) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.neighbors);
    }
    if (null == this.income) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeLong(this.income);
    }
    if (null == this.label) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, label);
    }
  }
  public void write0(DataOutput __dataOut) throws IOException {
    if (null == this.address) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, address);
    }
    if (null == this.year) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.year);
    }
    if (null == this.day) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.day);
    }
    if (null == this.length) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.length);
    }
    if (null == this.weight) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeDouble(this.weight);
    }
    if (null == this.count) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.count);
    }
    if (null == this.looped) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.looped);
    }
    if (null == this.neighbors) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.neighbors);
    }
    if (null == this.income) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeLong(this.income);
    }
    if (null == this.label) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, label);
    }
  }
  private static final DelimiterSet __outputDelimiters = new DelimiterSet((char) 44, (char) 10, (char) 0, (char) 0, false);
  public String toString() {
    return toString(__outputDelimiters, true);
  }
  public String toString(DelimiterSet delimiters) {
    return toString(delimiters, true);
  }
  public String toString(boolean useRecordDelim) {
    return toString(__outputDelimiters, useRecordDelim);
  }
  public String toString(DelimiterSet delimiters, boolean useRecordDelim) {
    StringBuilder __sb = new StringBuilder();
    char fieldDelim = delimiters.getFieldsTerminatedBy();
    __sb.append(FieldFormatter.escapeAndEnclose(address==null?"null":address, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(year==null?"null":"" + year, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(day==null?"null":"" + day, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(length==null?"null":"" + length, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(weight==null?"null":"" + weight, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(count==null?"null":"" + count, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(looped==null?"null":"" + looped, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(neighbors==null?"null":"" + neighbors, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(income==null?"null":"" + income, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(label==null?"null":label, delimiters));
    if (useRecordDelim) {
      __sb.append(delimiters.getLinesTerminatedBy());
    }
    return __sb.toString();
  }
  public void toString0(DelimiterSet delimiters, StringBuilder __sb, char fieldDelim) {
    __sb.append(FieldFormatter.escapeAndEnclose(address==null?"null":address, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(year==null?"null":"" + year, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(day==null?"null":"" + day, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(length==null?"null":"" + length, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(weight==null?"null":"" + weight, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(count==null?"null":"" + count, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(looped==null?"null":"" + looped, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(neighbors==null?"null":"" + neighbors, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(income==null?"null":"" + income, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(label==null?"null":label, delimiters));
  }
  private static final DelimiterSet __inputDelimiters = new DelimiterSet((char) 44, (char) 10, (char) 0, (char) 0, false);
  private RecordParser __parser;
  public void parse(Text __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(CharSequence __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(byte [] __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(char [] __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(ByteBuffer __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(CharBuffer __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  private void __loadFromFields(List<String> fields) {
    Iterator<String> __it = fields.listIterator();
    String __cur_str = null;
    try {
    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.address = null; } else {
      this.address = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.year = null; } else {
      this.year = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.day = null; } else {
      this.day = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.length = null; } else {
      this.length = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.weight = null; } else {
      this.weight = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.count = null; } else {
      this.count = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.looped = null; } else {
      this.looped = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.neighbors = null; } else {
      this.neighbors = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.income = null; } else {
      this.income = Long.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.label = null; } else {
      this.label = __cur_str;
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  private void __loadFromFields0(Iterator<String> __it) {
    String __cur_str = null;
    try {
    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.address = null; } else {
      this.address = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.year = null; } else {
      this.year = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.day = null; } else {
      this.day = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.length = null; } else {
      this.length = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.weight = null; } else {
      this.weight = Double.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.count = null; } else {
      this.count = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.looped = null; } else {
      this.looped = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.neighbors = null; } else {
      this.neighbors = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.income = null; } else {
      this.income = Long.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.label = null; } else {
      this.label = __cur_str;
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  public Object clone() throws CloneNotSupportedException {
    bitcoin_heist o = (bitcoin_heist) super.clone();
    return o;
  }

  public void clone0(bitcoin_heist o) throws CloneNotSupportedException {
  }

  public Map<String, Object> getFieldMap() {
    Map<String, Object> __sqoop$field_map = new HashMap<String, Object>();
    __sqoop$field_map.put("address", this.address);
    __sqoop$field_map.put("year", this.year);
    __sqoop$field_map.put("day", this.day);
    __sqoop$field_map.put("length", this.length);
    __sqoop$field_map.put("weight", this.weight);
    __sqoop$field_map.put("count", this.count);
    __sqoop$field_map.put("looped", this.looped);
    __sqoop$field_map.put("neighbors", this.neighbors);
    __sqoop$field_map.put("income", this.income);
    __sqoop$field_map.put("label", this.label);
    return __sqoop$field_map;
  }

  public void getFieldMap0(Map<String, Object> __sqoop$field_map) {
    __sqoop$field_map.put("address", this.address);
    __sqoop$field_map.put("year", this.year);
    __sqoop$field_map.put("day", this.day);
    __sqoop$field_map.put("length", this.length);
    __sqoop$field_map.put("weight", this.weight);
    __sqoop$field_map.put("count", this.count);
    __sqoop$field_map.put("looped", this.looped);
    __sqoop$field_map.put("neighbors", this.neighbors);
    __sqoop$field_map.put("income", this.income);
    __sqoop$field_map.put("label", this.label);
  }

  public void setField(String __fieldName, Object __fieldVal) {
    if (!setters.containsKey(__fieldName)) {
      throw new RuntimeException("No such field:"+__fieldName);
    }
    setters.get(__fieldName).setField(__fieldVal);
  }

}
