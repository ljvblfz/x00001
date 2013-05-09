/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.page.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

/** no table comments对应实体类 */
@Entity
@Table(name = "SY_WIDGET_PAGE")
public class SyWidgetPage extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String SPGUID= "spguid";
	public static String NAME= "name";
	public static String SPTYPE= "sptype";
	public static String PAGESOURCE= "pagesource";
	public static String DESCRIPTION= "description";
	public static String STATUS= "status";
	public static String GROUPNAME= "groupname"; 

	//primary key
	/** PID */
	private java.lang.String spguid;
	/** 页面名称 */
	private java.lang.String name;
	/** 页面类型 */
	private java.lang.String sptype;
	/** 页面内容 */
	private java.lang.String pagesource;
	/** 页面描述 */
	private java.lang.String description;
	/** 页面状态 */
	private java.lang.String status;
	/** 页面分组 */
	private java.lang.String groupname; 
	
	//

	private java.lang.String groupnamelabel; 
	private java.lang.String sptypelabel; 
	private java.lang.String statuslabel; 

	

	public SyWidgetPage(){
	}

	public SyWidgetPage(java.lang.String spguid ){
		this.spguid = spguid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getSpguid() {
		return this.spguid;
	}
	
	/**
	 * 设置PID
	 * @param spguid PID
	 */
	public void setSpguid(java.lang.String spguid) {
		this.spguid = spguid;
	}
		/**
		 * 取得页面名称
		 * return 页面名称
		 */
	public java.lang.String getName() {
		return this.name;
	}
	
	/**
	 * 设置页面名称
	 * @param name 页面名称
	 */
	public void setName(java.lang.String value) {
		this.name = value;
	}
		/**
		 * 取得页面类型
		 * return 页面类型
		 */
	public java.lang.String getSptype() {
		return this.sptype;
	}
	
	/**
	 * 设置页面类型
	 * @param sptype 页面类型
	 */
	public void setSptype(java.lang.String value) {
		this.sptype = value;
	}
		/**
		 * 取得页面内容
		 * return 页面内容
		 */
	public java.lang.String getPagesource() {
		return this.pagesource;
	}
	
	/**
	 * 设置页面内容
	 * @param pagesource 页面内容
	 */
	public void setPagesource(java.lang.String value) {
		this.pagesource = value;
	}
		/**
		 * 取得页面描述
		 * return 页面描述
		 */
	public java.lang.String getDescription() {
		return this.description;
	}
	
	/**
	 * 设置页面描述
	 * @param description 页面描述
	 */
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
		/**
		 * 取得页面状态
		 * return 页面状态
		 */
	public java.lang.String getStatus() {
		return this.status;
	}
	
	/**
	 * 设置页面状态
	 * @param status 页面状态
	 */
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
		/**
		 * 取得页面分组
		 * return 页面分组
		 */
	public java.lang.String getGroupname() {
		return this.groupname;
	}
	
	/**
	 * 设置页面分组
	 * @param groupname 页面分组
	 */
	public void setGroupname(java.lang.String value) {
		this.groupname = value;
	}
 

	@Transient
	public java.lang.String getGroupnamelabel() {
		return groupnamelabel;
	}

	public void setGroupnamelabel(java.lang.String groupnamelabel) {
		this.groupnamelabel = groupnamelabel;
	}

	@Transient
	public java.lang.String getSptypelabel() {
		return sptypelabel;
	}

	public void setSptypelabel(java.lang.String sptypelabel) {
		this.sptypelabel = sptypelabel;
	}

	@Transient
	public java.lang.String getStatuslabel() {
		return statuslabel;
	}

	public void setStatuslabel(java.lang.String statuslabel) {
		this.statuslabel = statuslabel;
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
			SyWidgetPage castObj = (SyWidgetPage) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getSpguid(), castObj.getSpguid());
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
			SyWidgetPage castObj = (SyWidgetPage) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getSpguid(), castObj.getSpguid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

