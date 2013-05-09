/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.cck.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

/** SY_CCK_TYPE 对应实体类 */
@Entity
@Table(name = "SY_CCK_TYPE")
public class SyCckType extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String SCT_GUID= "sctGuid";
	public static String GROUPNAME= "groupname";
	public static String TYPETABLE= "typetable";
	public static String CODE= "code";
	public static String NAME= "name";
	public static String AUTHORITY= "authority";
	public static String DESCRIPTION= "description";
	public static String STATUS= "status"; 
	public static String SHOW_TYPE= "showType";
	public static String NAME_COLUMN= "nameColumn"; 
	public static String LOG_TABLE_NAME= "logTableName"; 
	public static String PARENT_ID_TYPE="parentIdType";
//	public static String DELETEMODE="deletemode";
//	public static String DELETEMODECOLUMN="deletemodecolumn";
	//primary key
	/** PID */
	private java.lang.String sctGuid;
	/** 群组名称 */
	private java.lang.String groupname;
	/** DB Table */
	private java.lang.String typetable;
	/** 类型自编号 */
	private java.lang.String code;
	/** 名称 */
	private java.lang.String name;
	/** 数据权限 */
	private java.lang.String authority;
	/** 描述 */
	private java.lang.String description;
	/** 状态 */
	private java.lang.String status; 

	private java.lang.String showType;
	/** 状态 */
	private java.lang.String nameColumn; 
 
	private java.lang.String groupnameString;
	private java.lang.String authorityString;
	private java.lang.String showTypeString;
	private java.lang.String statusString; 
	
 	private java.lang.String logTableName;
 	
 	private java.lang.String parentIdType;
//	private java.lang.String deletemodecolumn;
	public SyCckType(){
	}

	public SyCckType(java.lang.String sctGuid ){
		this.sctGuid = sctGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getSctGuid() {
		return this.sctGuid;
	}
	
	/**
	 * 设置PID
	 * @param sctGuid PID
	 */
	public void setSctGuid(java.lang.String sctGuid) {
		this.sctGuid = sctGuid;
	}
		/**
		 * 取得群组名称
		 * return 群组名称
		 */
	public java.lang.String getGroupname() {
		return this.groupname;
	}
	
	/**
	 * 设置群组名称
	 * @param groupname 群组名称
	 */
	public void setGroupname(java.lang.String value) {
		this.groupname = value;
	}
		/**
		 * 取得DB Table
		 * return DB Table
		 */
	public java.lang.String getTypetable() {
		return this.typetable;
	}
	
	/**
	 * 设置DB Table
	 * @param typetable DB Table
	 */
	public void setTypetable(java.lang.String value) {
		this.typetable = value;
	}
		/**
		 * 取得类型自编号
		 * return 类型自编号
		 */
	public java.lang.String getCode() {
		return this.code;
	}
	
	/**
	 * 设置类型自编号
	 * @param code 类型自编号
	 */
	public void setCode(java.lang.String value) {
		this.code = value;
	}
		/**
		 * 取得名称
		 * return 名称
		 */
	public java.lang.String getName() {
		return this.name;
	}
	
	/**
	 * 设置名称
	 * @param name 名称
	 */
	public void setName(java.lang.String value) {
		this.name = value;
	}
		/**
		 * 取得数据权限
		 * return 数据权限
		 */
	public java.lang.String getAuthority() {
		return this.authority;
	}
	
	/**
	 * 设置数据权限
	 * @param authority 数据权限
	 */
	public void setAuthority(java.lang.String value) {
		this.authority = value;
	}
		/**
		 * 取得描述
		 * return 描述
		 */
	public java.lang.String getDescription() {
		return this.description;
	}
	
	/**
	 * 设置描述
	 * @param description 描述
	 */
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
		/**
		 * 取得状态
		 * return 状态
		 */
	public java.lang.String getStatus() {
		return this.status;
	}
	
	/**
	 * 设置状态
	 * @param status 状态
	 */
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
		 

	public int compareTo(Object obj) {
		int compare = -1;
	
		if (obj == null)
			compare = -1;
		else if (this == obj)
			compare = 0;
		else if (!(obj instanceof BaseEntity))
			compare = -1;
		else if (!this.getClass().equals(obj.getClass()))
			compare = -1;
		else {
			SyCckType castObj = (SyCckType) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getSctGuid(), castObj.getSctGuid());
			compare = builder.toComparison();
		}
		return compare;
	}
		
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if (obj == null) {
			isEqual = false;
		} else if (this == obj) {
			isEqual = true;
		} else if (!(obj instanceof BaseEntity)) {
			isEqual = false;
		} else if (!this.getClass().equals(obj.getClass())) {
			isEqual = false;
		} else {
			SyCckType castObj = (SyCckType) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getSctGuid(), castObj.getSctGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}

	public java.lang.String getShowType() {
		return showType;
	}

	public void setShowType(java.lang.String showType) {
		this.showType = showType;
	}

	public java.lang.String getNameColumn() {
		return nameColumn;
	}

	public void setNameColumn(java.lang.String nameColumn) {
		this.nameColumn = nameColumn;
	}
	@Transient
	public java.lang.String getGroupnameString() {
		return groupnameString;
	}

	public void setGroupnameString(java.lang.String groupnameString) {
		this.groupnameString = groupnameString;
	}
	@Transient
	public java.lang.String getAuthorityString() {
		return authorityString;
	}

	public void setAuthorityString(java.lang.String authorityString) {
		this.authorityString = authorityString;
	}
	@Transient
	public java.lang.String getShowTypeString() {
		return showTypeString;
	}

	public void setShowTypeString(java.lang.String showTypeString) {
		this.showTypeString = showTypeString;
	}
	@Transient
	public java.lang.String getStatusString() {
		return statusString;
	}

	public void setStatusString(java.lang.String statusString) {
		this.statusString = statusString;
	}

	public java.lang.String getLogTableName() {
		return logTableName;
	}

	public void setLogTableName(java.lang.String logTableName) {
		this.logTableName = logTableName;
	}

	public java.lang.String getParentIdType() {
		return parentIdType;
	}

	public void setParentIdType(java.lang.String parentIdType) {
		this.parentIdType = parentIdType;
	}

//	public java.lang.String getDeletemodecolumn() {
//		return deletemodecolumn;
//	}
//
//	public void setDeletemodecolumn(java.lang.String deletemodecolumn) {
//		this.deletemodecolumn = deletemodecolumn;
//	}
//
//	public java.lang.String getDeletemode() {
//		return deletemode;
//	}
//
//	public void setDeletemode(java.lang.String deletemode) {
//		this.deletemode = deletemode;
//	}
}

