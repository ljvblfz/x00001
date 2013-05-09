/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.block.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

/** no table comments对应实体类 */
@Entity
@Table(name = "SY_WIDGET_BLOCK")
public class SyWidgetBlock extends BaseEntity {

	private static final long serialVersionUID = 1L;
	

	public static String SWBGUID= "swbguid";
	public static String SWBTYPE= "swbtype";
	public static String BLOCKSOURCE= "blocksource";
	
//	public static String SPGUID= "spguid";
	public static String NAME= "name";
//	public static String SPTYPE= "sptype";
//	public static String PAGESOURCE= "pagesource";
	public static String DESCRIPTION= "description";
	public static String STATUS= "status";
	public static String GROUPNAME= "groupname"; 
	public static String TITLE= "title";

	//primary key

	private java.lang.String swbguid;
	private java.lang.String swbtype;
	private java.lang.String blocksource;
	
	/** PID */
//	private java.lang.String spguid;
	/** 区块名称 */
	private java.lang.String name;
	/** 区块类型 */
//	private java.lang.String sptype;
	/** 区块内容 */
//	private java.lang.String pagesource;
	/** 区块描述 */
	private java.lang.String description;
	/** 区块状态 */
	private java.lang.String status;
	/** 区块分组 */
	private java.lang.String groupname; 
	/**  */
	private java.lang.String title;

	private java.lang.String swbtypelabel;
	private java.lang.String groupnamelabel; 
	private java.lang.String statuslabel;
	

	public SyWidgetBlock(){
	}

	public SyWidgetBlock(java.lang.String swbguid ){
		this.swbguid = swbguid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getSwbguid() {
		return this.swbguid;
	}
	
	/**
	 * 设置PID
	 * @param spguid PID
	 */
	public void setSwbguid(java.lang.String swbguid) {
		this.swbguid = swbguid;
	}
		/**
		 * 取得区块名称
		 * return 区块名称
		 */
	public java.lang.String getName() {
		return this.name;
	}
	
	/**
	 * 设置区块名称
	 * @param name 区块名称
	 */
	public void setName(java.lang.String value) {
		this.name = value;
	}
		/**
		 * 取得区块类型
		 * return 区块类型
		 */
	public java.lang.String getSwbtype() {
		return this.swbtype;
	}
	
	/**
	 * 设置区块类型
	 * @param sptype 区块类型
	 */
	public void setSwbtype(java.lang.String value) {
		this.swbtype = value;
	}
		/**
		 * 取得区块内容
		 * return 区块内容
		 */
	public java.lang.String getBlocksource() {
		return this.blocksource;
	}
	
	/**
	 * 设置区块内容
	 * @param pagesource 区块内容
	 */
	public void setBlocksource(java.lang.String value) {
		this.blocksource = value;
	}
		/**
		 * 取得区块描述
		 * return 区块描述
		 */
	public java.lang.String getDescription() {
		return this.description;
	}
	
	/**
	 * 设置区块描述
	 * @param description 区块描述
	 */
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
		/**
		 * 取得区块状态
		 * return 区块状态
		 */
	public java.lang.String getStatus() {
		return this.status;
	}
	
	/**
	 * 设置区块状态
	 * @param status 区块状态
	 */
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
		/**
		 * 取得区块分组
		 * return 区块分组
		 */
	public java.lang.String getGroupname() {
		return this.groupname;
	}
	
	/**
	 * 设置区块分组
	 * @param groupname 区块分组
	 */
	public void setGroupname(java.lang.String value) {
		this.groupname = value;
	}
 
		/**
		 * 取得
		 * return 
		 */
	public java.lang.String getTitle() {
		return this.title;
	}
	
	/**
	 * 设置
	 * @param title 
	 */
	public void setTitle(java.lang.String value) {
		this.title = value;
	}

	@Transient
	public java.lang.String getSwbtypelabel() {
		return swbtypelabel;
	}

	public void setSwbtypelabel(java.lang.String swbtypelabel) {
		this.swbtypelabel = swbtypelabel;
	}

	@Transient
	public java.lang.String getGroupnamelabel() {
		return groupnamelabel;
	}

	public void setGroupnamelabel(java.lang.String groupnamelabel) {
		this.groupnamelabel = groupnamelabel;
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
			SyWidgetBlock castObj = (SyWidgetBlock) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getSwbguid(), castObj.getSwbguid());
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
			SyWidgetBlock castObj = (SyWidgetBlock) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getSwbguid(), castObj.getSwbguid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

