/**
 * project:odyssey
 * 
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 * 
 */
package com.founder.odyssey.generator.model.db;


import java.lang.reflect.InvocationTargetException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.founder.odyssey.generator.model.IModel;
import com.founder.odyssey.generator.provider.DbModelHelper;
import com.founder.odyssey.generator.utils.StringHelper;

/**
 * 
 * @author cw peng
 * @email peng_chuanwei@founder.com
 */
public class Table implements IModel{

	String sqlName;
	String className;
	/** the name of the owner of the synonym if this table is a synonym */
	private String ownerSynonymName = null;
	List columns = new ArrayList();
	List primaryKeyColumns = new ArrayList();
	private String remarks;
	private String signlePrmaryKey;
	
	public String getSignlePrmaryKey() {
		return StringHelper
		.makeAllWordFirstLetterUpperCase(signlePrmaryKey);
	}

	public void setSignlePrmaryKey(String signlePrmaryKey) {
		this.signlePrmaryKey = signlePrmaryKey;
	}

	
	public String getIdType(){
//		compositeId
		if(null==getCompositeIdColumns()){
			for(Object c:this.columns){
				if(((Column)c).isPk()){
					return ((Column)c).getJavaType();
				}
			}
		}
//		<#if !table.compositeId>
//		<#list table.columns as column>
//		<#if column.pk>${column.javaType}</#if>
//		</#list></#if>
		return "";
	}
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDisplayDescription() {
		return getSqlName();
	}
	
	public String getClassName() {
		return className == null ? StringHelper
				.makeAllWordFirstLetterUpperCase(getSqlName()) : className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List getColumns() {
		return columns;
	}

	public void setColumns(List columns) {
		this.columns = columns;
	}

	public String getOwnerSynonymName() {
		return ownerSynonymName;
	}

	public void setOwnerSynonymName(String ownerSynonymName) {
		this.ownerSynonymName = ownerSynonymName;
	}

	public List getPrimaryKeyColumns() {
		return primaryKeyColumns;
	}

	public void setPrimaryKeyColumns(List primaryKeyColumns) {
		this.primaryKeyColumns = primaryKeyColumns;
		
		if(primaryKeyColumns.size()==1){
			setSignlePrmaryKey(primaryKeyColumns.get(0).toString());
		}
	}

	public String getSqlName() {
		return sqlName.replaceAll("_T_", "_");
	}
	public String getRealSqlName() {
		return sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}

	public void addColumn(Column column) {
		columns.add(column);
	}

	public boolean isSingleId() {
		int pkCount = 0;
		for (int i = 0; i < columns.size(); i++) {
			Column c = (Column) columns.get(i);
			if (c.isPk()) {
				pkCount++;
			}
		}
		return pkCount > 1 ? false : true;
	}

	public boolean isCompositeId() {
		return !isSingleId();
	}

	public List getCompositeIdColumns() {
		List results = new ArrayList();
		List columns = getColumns();
		for (int i = 0; i < columns.size(); i++) {
			Column c = (Column) columns.get(i);
//			if(c.getColumnName().equals("DEL_FLAG")){
//				
//			}
			if (c.isPk())
				results.add(c);
		}
		return results;
	}

	public Column getIdColumn() {
		List columns = getColumns();
		for (int i = 0; i < columns.size(); i++) {
			Column c = (Column) columns.get(i);
			if (c.isPk())
				return c;
		}
		return null;
	}

	/**
	 * This method was created in VisualAge.
	 */
	public void initImportedKeys(DatabaseMetaData dbmd)
			throws java.sql.SQLException {

		// get imported keys a

		ResultSet fkeys = dbmd.getImportedKeys(catalog, schema, this.sqlName);

		while (fkeys.next()) {
			String pktable = fkeys.getString(PKTABLE_NAME);
			String pkcol = fkeys.getString(PKCOLUMN_NAME);
			String fktable = fkeys.getString(FKTABLE_NAME);
			String fkcol = fkeys.getString(FKCOLUMN_NAME);
			String seq = fkeys.getString(KEY_SEQ);
			Integer iseq = new Integer(seq);
			getImportedKeys().addForeignKey(pktable, pkcol, fkcol, iseq);
		}
		fkeys.close();
	}

	/**
	 * This method was created in VisualAge.
	 */
	public void initExportedKeys(DatabaseMetaData dbmd)
			throws java.sql.SQLException {
		// get Exported keys

		ResultSet fkeys = dbmd.getExportedKeys(catalog, schema, this.sqlName);

		while (fkeys.next()) {
			String pktable = fkeys.getString(PKTABLE_NAME);
			String pkcol = fkeys.getString(PKCOLUMN_NAME);
			String fktable = fkeys.getString(FKTABLE_NAME);
			String fkcol = fkeys.getString(FKCOLUMN_NAME);
			String seq = fkeys.getString(KEY_SEQ);
			Integer iseq = new Integer(seq);
			getExportedKeys().addForeignKey(fktable, fkcol, pkcol, iseq);
		}
		fkeys.close();
	}

	/**
	 * @return Returns the exportedKeys.
	 */
	public ForeignKeys getExportedKeys() {
		if (exportedKeys == null) {
			exportedKeys = new ForeignKeys(this);
		}
		return exportedKeys;
	}

	/**
	 * @return Returns the importedKeys.
	 */
	public ForeignKeys getImportedKeys() {
		if (importedKeys == null) {
			importedKeys = new ForeignKeys(this);
		}
		return importedKeys;
	}

	String catalog = DbModelHelper.getInstance().catalog;
	String schema = DbModelHelper.getInstance().schema;

	private ForeignKeys exportedKeys;
	private ForeignKeys importedKeys;

	public static final String PKTABLE_NAME = "PKTABLE_NAME";
	public static final String PKCOLUMN_NAME = "PKCOLUMN_NAME";
	public static final String FKTABLE_NAME = "FKTABLE_NAME";
	public static final String FKCOLUMN_NAME = "FKCOLUMN_NAME";
	public static final String KEY_SEQ = "KEY_SEQ";

	public void mergeFilePathModel(Map fileModel) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		fileModel.putAll(BeanUtils.describe(this));
	}

	public String getTemplateModelName() {
		return "table";
	}


}
