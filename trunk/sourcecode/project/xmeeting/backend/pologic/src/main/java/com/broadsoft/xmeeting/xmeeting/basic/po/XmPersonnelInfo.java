/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.basic.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

/** no table comments对应实体类 */
@Entity
@Table(name = "XM_PERSONNEL_INFO")
public class XmPersonnelInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMPI_GUID= "xmpiGuid";
	public static String XMPI_NAME= "xmpiName";
	public static String XMPI_DEPTINFO= "xmpiDeptinfo";
	public static String XMPI_TITLE= "xmpiTitle";
	public static String XMPI_CONTACT= "xmpiContact"; 

	//primary key
	/** PID */
	private java.lang.String xmpiGuid;
	/** 人员名字 */
	private java.lang.String xmpiName;
	/** 人员单位 */
	private java.lang.String xmpiDeptinfo;
	/** 人员职务 */
	private java.lang.String xmpiTitle;
	/** 联系方式 */
	private java.lang.String xmpiContact; 

	

	public XmPersonnelInfo(){
	}

	public XmPersonnelInfo(java.lang.String xmpiGuid ){
		this.xmpiGuid = xmpiGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmpiGuid() {
		return this.xmpiGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmpiGuid PID
	 */
	public void setXmpiGuid(java.lang.String xmpiGuid) {
		this.xmpiGuid = xmpiGuid;
	}
		/**
		 * 取得人员名字
		 * return 人员名字
		 */
	public java.lang.String getXmpiName() {
		return this.xmpiName;
	}
	
	/**
	 * 设置人员名字
	 * @param xmpiName 人员名字
	 */
	public void setXmpiName(java.lang.String value) {
		this.xmpiName = value;
	}
		/**
		 * 取得人员单位
		 * return 人员单位
		 */
	public java.lang.String getXmpiDeptinfo() {
		return this.xmpiDeptinfo;
	}
	
	/**
	 * 设置人员单位
	 * @param xmpiDeptinfo 人员单位
	 */
	public void setXmpiDeptinfo(java.lang.String value) {
		this.xmpiDeptinfo = value;
	}
		/**
		 * 取得人员职务
		 * return 人员职务
		 */
	public java.lang.String getXmpiTitle() {
		return this.xmpiTitle;
	}
	
	/**
	 * 设置人员职务
	 * @param xmpiTitle 人员职务
	 */
	public void setXmpiTitle(java.lang.String value) {
		this.xmpiTitle = value;
	}
		/**
		 * 取得联系方式
		 * return 联系方式
		 */
	public java.lang.String getXmpiContact() {
		return this.xmpiContact;
	}
	
	/**
	 * 设置联系方式
	 * @param xmpiContact 联系方式
	 */
	public void setXmpiContact(java.lang.String value) {
		this.xmpiContact = value;
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
			XmPersonnelInfo castObj = (XmPersonnelInfo) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmpiGuid(), castObj.getXmpiGuid());
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
			XmPersonnelInfo castObj = (XmPersonnelInfo) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmpiGuid(), castObj.getXmpiGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

