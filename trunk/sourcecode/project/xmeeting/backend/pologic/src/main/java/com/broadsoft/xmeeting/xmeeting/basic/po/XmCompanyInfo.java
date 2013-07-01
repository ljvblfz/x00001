/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.basic.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

/** no table comments对应实体类 */
@Entity
@Table(name = "XM_COMPANY_INFO")
public class XmCompanyInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMCI_GUID= "xmciGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMCI_COMPANY_NAME= "xmciCompanyName";
	public static String XMCI_DESCRIPTION= "xmciDescription";
	public static String XMCI_ATTACHMENT= "xmciAttachment";
	public static String XMCI_TYPE= "xmciType";
	public static String XMCI_STATUS= "xmciStatus"; 
	public static String IS_DISPLAY= "isDisplay"; 

	//primary key
	/** PID */
	private java.lang.String xmciGuid;
	/** 公司名称 */
	private java.lang.String xmciCompanyName;
	/** 介绍描述 */
	private java.lang.String xmciDescription;
	/** 介绍附件 */
	private java.lang.String xmciAttachment;
	/** 介绍类型 */
	private java.lang.String xmciType;
	private java.lang.String xmciTypeLabel;
	/** 介绍状态 */
	private java.lang.String xmciStatus; 

	private java.lang.String xmciStatusLabel; 
	// 
	private java.lang.String xmmiGuid;  
	private java.lang.String isDisplay;  
	private java.lang.String isDisplayLabel;  

	

	public XmCompanyInfo(){
	}

	public XmCompanyInfo(java.lang.String xmciGuid ){
		this.xmciGuid = xmciGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmciGuid() {
		return this.xmciGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmciGuid PID
	 */
	public void setXmciGuid(java.lang.String xmciGuid) {
		this.xmciGuid = xmciGuid;
	}
		/**
		 * 取得公司名称
		 * return 公司名称
		 */
	public java.lang.String getXmciCompanyName() {
		return this.xmciCompanyName;
	}
	
	/**
	 * 设置公司名称
	 * @param xmciCompanyName 公司名称
	 */
	public void setXmciCompanyName(java.lang.String value) {
		this.xmciCompanyName = value;
	}
		/**
		 * 取得介绍描述
		 * return 介绍描述
		 */
	public java.lang.String getXmciDescription() {
		return this.xmciDescription;
	}
	
	/**
	 * 设置介绍描述
	 * @param xmciDescription 介绍描述
	 */
	public void setXmciDescription(java.lang.String value) {
		this.xmciDescription = value;
	}
		/**
		 * 取得介绍附件
		 * return 介绍附件
		 */
	public java.lang.String getXmciAttachment() {
		return this.xmciAttachment;
	}
	
	/**
	 * 设置介绍附件
	 * @param xmciAttachment 介绍附件
	 */
	public void setXmciAttachment(java.lang.String value) {
		this.xmciAttachment = value;
	}
		/**
		 * 取得介绍类型
		 * return 介绍类型
		 */
	public java.lang.String getXmciType() {
		return this.xmciType;
	}
	
	/**
	 * 设置介绍类型
	 * @param xmciType 介绍类型
	 */
	public void setXmciType(java.lang.String value) {
		this.xmciType = value;
	}
		/**
		 * 取得介绍状态
		 * return 介绍状态
		 */
	public java.lang.String getXmciStatus() {
		return this.xmciStatus;
	}
	
	/**
	 * 设置介绍状态
	 * @param xmciStatus 介绍状态
	 */
	public void setXmciStatus(java.lang.String value) {
		this.xmciStatus = value;
	}
 
	@Transient
	public java.lang.String getXmciTypeLabel() {
		return xmciTypeLabel;
	}

	public void setXmciTypeLabel(java.lang.String xmciTypeLabel) {
		this.xmciTypeLabel = xmciTypeLabel;
	}

	@Transient
	public java.lang.String getXmciStatusLabel() {
		return xmciStatusLabel;
	}

	public void setXmciStatusLabel(java.lang.String xmciStatusLabel) {
		this.xmciStatusLabel = xmciStatusLabel;
	}

	public java.lang.String getXmmiGuid() {
		return xmmiGuid;
	}

	public void setXmmiGuid(java.lang.String xmmiGuid) {
		this.xmmiGuid = xmmiGuid;
	}

 

	public java.lang.String getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(java.lang.String isDisplay) {
		this.isDisplay = isDisplay;
	}

	@Transient
	public java.lang.String getIsDisplayLabel() {
		return isDisplayLabel;
	}

	public void setIsDisplayLabel(java.lang.String isDisplayLabel) {
		this.isDisplayLabel = isDisplayLabel;
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
			XmCompanyInfo castObj = (XmCompanyInfo) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmciGuid(), castObj.getXmciGuid());
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
			XmCompanyInfo castObj = (XmCompanyInfo) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmciGuid(), castObj.getXmciGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

