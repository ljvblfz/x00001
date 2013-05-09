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
@Table(name = "SY_WIDGET_PAGE_REGION")
public class SyWidgetPageRegion extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String SPRGUID= "sprguid";
	public static String SPGUID= "spguid";
	public static String SPR_COLUMN= "sprColumn";
	public static String SPR_ROW= "sprRow";
	public static String SWBGUID= "swbguid";
	public static String DESCRIPTION= "description";
	public static String STATUS= "status"; 

	//primary key
	/** PID */
	private java.lang.String sprguid;
	/** 页面PID */
	private java.lang.String spguid;
	/** 区域列数 */
	private java.lang.String sprColumn;
	/** 区域行数 */
	private java.lang.String sprRow;
	/** 区块PID */
	private java.lang.String swbguid;
	/** 区域描述 */
	private java.lang.String description;
	/** 区域状态 */
	private java.lang.String status; 

	

	public SyWidgetPageRegion(){
	}

	public SyWidgetPageRegion(java.lang.String sprguid ){
		this.sprguid = sprguid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getSprguid() {
		return this.sprguid;
	}
	
	/**
	 * 设置PID
	 * @param sprguid PID
	 */
	public void setSprguid(java.lang.String sprguid) {
		this.sprguid = sprguid;
	}
		/**
		 * 取得页面PID
		 * return 页面PID
		 */
	public java.lang.String getSpguid() {
		return this.spguid;
	}
	
	/**
	 * 设置页面PID
	 * @param spguid 页面PID
	 */
	public void setSpguid(java.lang.String value) {
		this.spguid = value;
	}
		/**
		 * 取得区域列数
		 * return 区域列数
		 */
	public java.lang.String getSprColumn() {
		return this.sprColumn;
	}
	
	/**
	 * 设置区域列数
	 * @param sprColumn 区域列数
	 */
	public void setSprColumn(java.lang.String value) {
		this.sprColumn = value;
	}
		/**
		 * 取得区域行数
		 * return 区域行数
		 */
	public java.lang.String getSprRow() {
		return this.sprRow;
	}
	
	/**
	 * 设置区域行数
	 * @param sprRow 区域行数
	 */
	public void setSprRow(java.lang.String value) {
		this.sprRow = value;
	}
		/**
		 * 取得区块PID
		 * return 区块PID
		 */
	public java.lang.String getSwbguid() {
		return this.swbguid;
	}
	
	/**
	 * 设置区块PID
	 * @param swbguid 区块PID
	 */
	public void setSwbguid(java.lang.String value) {
		this.swbguid = value;
	}
		/**
		 * 取得区域描述
		 * return 区域描述
		 */
	public java.lang.String getDescription() {
		return this.description;
	}
	
	/**
	 * 设置区域描述
	 * @param description 区域描述
	 */
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
		/**
		 * 取得区域状态
		 * return 区域状态
		 */
	public java.lang.String getStatus() {
		return this.status;
	}
	
	/**
	 * 设置区域状态
	 * @param status 区域状态
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
			SyWidgetPageRegion castObj = (SyWidgetPageRegion) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getSprguid(), castObj.getSprguid());
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
			SyWidgetPageRegion castObj = (SyWidgetPageRegion) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getSprguid(), castObj.getSprguid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

