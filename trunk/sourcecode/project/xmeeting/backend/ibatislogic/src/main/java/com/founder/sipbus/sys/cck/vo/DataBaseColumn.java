package com.founder.sipbus.sys.cck.vo;

import org.apache.commons.lang.builder.ToStringBuilder;

public class DataBaseColumn {
//	public static int columnNoNulls;
//	 public static int columnNullable;
//	 public static int columnNullableUnknown;
	 public  String catalogName;
	 public  String columnClassName;
	 public  int columnCount;
	 public  int columnDisplaySize;
	 public  String columnLabel;
	 public  String columnName;
	 public int isUnique;
	 public int getIsUnique() {
		return isUnique;
	}
	public void setIsUnique(int isUnique) {
		this.isUnique = isUnique;
	}
	public  int columnType;
	 @Override
	public String toString() {
		 
		return ToStringBuilder.reflectionToString(this);
	}
	public  String columnTypeName;
	 public  int precision;
	 public  int scale;
	 public  String schemaName;
	 public  String tableName;
	 public  boolean isAutoIncrement;
	 public  boolean isCaseSensitive;
	 public  boolean isCurrency;
	 public  boolean isDefinitelyWritable;
	 public  int isNullable;
	 public  boolean isReadOnly;
	 public  boolean isSearchable;
	 public  boolean isSigned;
	 public  boolean isWritable;
	 public  boolean isPrimaryKey;
	 public  boolean isSctGuid;
	public String getCatalogName() {
		return catalogName;
	}
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	public String getColumnClassName() {
		return columnClassName;
	}
	public void setColumnClassName(String columnClassName) {
		this.columnClassName = columnClassName;
	}
	public int getColumnCount() {
		return columnCount;
	}
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}
	public int getColumnDisplaySize() {
		return columnDisplaySize;
	}
	public void setColumnDisplaySize(int columnDisplaySize) {
		this.columnDisplaySize = columnDisplaySize;
	}
	public String getColumnLabel() {
		return columnLabel;
	}
	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public int getColumnType() {
		return columnType;
	}
	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}
	public String getColumnTypeName() {
		return columnTypeName;
	}
	public void setColumnTypeName(String columnTypeName) {
		this.columnTypeName = columnTypeName;
	}
	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public String getSchemaName() {
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public boolean getIsAutoIncrement() {
		return isAutoIncrement;
	}
	public void setIsAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}
	public boolean getIsCaseSensitive() {
		return isCaseSensitive;
	}
	public void setIsCaseSensitive(boolean isCaseSensitive) {
		this.isCaseSensitive = isCaseSensitive;
	}
	public boolean getIsCurrency() {
		return isCurrency;
	}
	public void setIsCurrency(boolean isCurrency) {
		this.isCurrency = isCurrency;
	}
	public boolean getIsDefinitelyWritable() {
		return isDefinitelyWritable;
	}
	public void setIsDefinitelyWritable(boolean isDefinitelyWritable) {
		this.isDefinitelyWritable = isDefinitelyWritable;
	}
	public int getIsNullable() {
		return isNullable;
	}
	public void setIsNullable(int isNullable) {
		this.isNullable = isNullable;
	}
	public boolean getIsReadOnly() {
		return isReadOnly;
	}
	public void setIsReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}
	public boolean getIsSearchable() {
		return isSearchable;
	}
	public void setIsSearchable(boolean isSearchable) {
		this.isSearchable = isSearchable;
	}
	public boolean getIsSigned() {
		return isSigned;
	}
	public void setIsSigned(boolean isSigned) {
		this.isSigned = isSigned;
	}
	public boolean getIsWritable() {
		return isWritable;
	}
	public void setIsWritable(boolean isWritable) {
		this.isWritable = isWritable;
	}
	public boolean getIsPrimaryKey() {
		return isPrimaryKey;
	}
	public void setIsPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}
	public boolean getIsSctGuid() {
		return isSctGuid;
	}
	public void setIsSctGuid(boolean isSctGuid) {
		this.isSctGuid = isSctGuid;
	}
	
}
