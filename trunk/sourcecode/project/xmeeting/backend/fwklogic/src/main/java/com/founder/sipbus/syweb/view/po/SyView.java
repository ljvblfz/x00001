/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
package com.founder.sipbus.syweb.view.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

/** no table comments对应实体类 */
@Entity
@Table(name = "SY_VIEW")
public class SyView extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String SV_GUID = "svGuid";
	public static String NAME = "name";
	public static String SQL_SOURCE = "sqlSource";
	public static String DESCRIPTION = "description";
	public static String STATUS = "status";
	public static String GROUP_NAME = "groupName";
	public static String SQLTYPE = "sqltype";

	// primary key
	/** PID */
	private java.lang.String svGuid;
	/** 视图名称 */
	private java.lang.String name;
	/** sql text */
	private java.lang.String sqlSource;
	/** 描述 */
	private java.lang.String description;
	/** 状态 */
	private java.lang.String status;
	/** 视图分组 */
	private java.lang.String groupName;
	/** sql 类型 */
	private java.lang.String sqltype;

	private java.lang.String statusLabel;
	private java.lang.String groupNameLabel;
	private java.lang.String sqltypeLabel;

	public SyView() {
	}

	public SyView(java.lang.String svGuid) {
		this.svGuid = svGuid;
	}

	/**
	 * 取得PID return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")
	public java.lang.String getSvGuid() {
		return this.svGuid;
	}

	/**
	 * 设置PID
	 * 
	 * @param svGuid
	 *            PID
	 */
	public void setSvGuid(java.lang.String svGuid) {
		this.svGuid = svGuid;
	}

	/**
	 * 取得视图名称 return 视图名称
	 */
	public java.lang.String getName() {
		return this.name;
	}

	/**
	 * 设置视图名称
	 * 
	 * @param name
	 *            视图名称
	 */
	public void setName(java.lang.String value) {
		this.name = value;
	}

	/**
	 * 取得sql text return sql text
	 */
	public java.lang.String getSqlSource() {
		return this.sqlSource;
	}

	/**
	 * 设置sql text
	 * 
	 * @param sqlSource
	 *            sql text
	 */
	public void setSqlSource(java.lang.String value) {
		this.sqlSource = value;
	}

	/**
	 * 取得描述 return 描述
	 */
	public java.lang.String getDescription() {
		return this.description;
	}

	/**
	 * 设置描述
	 * 
	 * @param description
	 *            描述
	 */
	public void setDescription(java.lang.String value) {
		this.description = value;
	}

	/**
	 * 取得状态 return 状态
	 */
	public java.lang.String getStatus() {
		return this.status;
	}

	/**
	 * 设置状态
	 * 
	 * @param status
	 *            状态
	 */
	public void setStatus(java.lang.String value) {
		this.status = value;
	}

	/**
	 * 取得视图分组 return 视图分组
	 */
	public java.lang.String getGroupName() {
		return this.groupName;
	}

	/**
	 * 设置视图分组
	 * 
	 * @param groupName
	 *            视图分组
	 */
	public void setGroupName(java.lang.String value) {
		this.groupName = value;
	}

	public java.lang.String getSqltype() {
		return sqltype;
	}

	public void setSqltype(java.lang.String sqltype) {
		this.sqltype = sqltype;
	}

	//

	@Transient
	public java.lang.String getStatusLabel() {
		return statusLabel;
	}

	public void setStatusLabel(java.lang.String statusLabel) {
		this.statusLabel = statusLabel;
	}

	@Transient
	public java.lang.String getGroupNameLabel() {
		return groupNameLabel;
	}

	public void setGroupNameLabel(java.lang.String groupNameLabel) {
		this.groupNameLabel = groupNameLabel;
	}

	@Transient
	public java.lang.String getSqltypeLabel() {
		return sqltypeLabel;
	}

	public void setSqltypeLabel(java.lang.String sqltypeLabel) {
		this.sqltypeLabel = sqltypeLabel;
	}

	//

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
			SyView castObj = (SyView) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getSvGuid(), castObj.getSvGuid());
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
			SyView castObj = (SyView) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getSvGuid(), castObj.getSvGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}
