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
@Table(name = "XM_PAD_DEVICE")
public class XmPadDevice extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMPD_GUID= "xmpdGuid";
	public static String XMPD_CODE= "xmpdCode";
	public static String XMPD_DEVICE_ID= "xmpdDeviceId";
	public static String XMPD_DEVICE_SPEC= "xmpdDeviceSpec";
	public static String XMPD_HARDWARE_VERSION= "xmpdHardwareVersion";
	public static String XMPD_SOFTWARE_VERSION= "xmpdSoftwareVersion";
	public static String XMPD_TYPE= "xmpdType";  
	public static String XMPD_SUB_DEVICE_ID= "xmpdSubDeviceId"; 
	public static String XMPD_STATUS= "xmpdStatus"; 
	public static String XMPD_COMMENT= "xmpdComment"; 

	//primary key
	/** PID */
	private java.lang.String xmpdGuid;
	/** 资产编号 */
	private java.lang.String xmpdCode;
	/** 设备编号 */
	private java.lang.String xmpdDeviceId;
	/** 设备规格 */
	private java.lang.String xmpdDeviceSpec;
	/** 硬件版本 */
	private java.lang.String xmpdHardwareVersion;
	/** 软件版本 */
	private java.lang.String xmpdSoftwareVersion;
	/** 设备类型 */
	private java.lang.String xmpdType;   
	private java.lang.String xmpdTypeLabel;   
	/** 子机设备编号 */
	private java.lang.String xmpdSubDeviceId; 
	/** 设备状态 */
	private java.lang.String xmpdStatus;   
	private java.lang.String xmpdStatusLabel; 
	/** 设备备注*/
	private java.lang.String xmpdComment; 

	

	public XmPadDevice(){
	}

	public XmPadDevice(java.lang.String xmpdGuid ){
		this.xmpdGuid = xmpdGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmpdGuid() {
		return this.xmpdGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmpdGuid PID
	 */
	public void setXmpdGuid(java.lang.String xmpdGuid) {
		this.xmpdGuid = xmpdGuid;
	}
		/**
		 * 取得资产编号
		 * return 资产编号
		 */
	public java.lang.String getXmpdCode() {
		return this.xmpdCode;
	}
	
	/**
	 * 设置资产编号
	 * @param xmpdCode 资产编号
	 */
	public void setXmpdCode(java.lang.String value) {
		this.xmpdCode = value;
	}
		/**
		 * 取得设备编号
		 * return 设备编号
		 */
	public java.lang.String getXmpdDeviceId() {
		return this.xmpdDeviceId;
	}
	
	/**
	 * 设置设备编号
	 * @param xmpdDeviceId 设备编号
	 */
	public void setXmpdDeviceId(java.lang.String value) {
		this.xmpdDeviceId = value;
	}
		/**
		 * 取得设备规格
		 * return 设备规格
		 */
	public java.lang.String getXmpdDeviceSpec() {
		return this.xmpdDeviceSpec;
	}
	
	/**
	 * 设置设备规格
	 * @param xmpdDeviceSpec 设备规格
	 */
	public void setXmpdDeviceSpec(java.lang.String value) {
		this.xmpdDeviceSpec = value;
	}
		/**
		 * 取得硬件版本
		 * return 硬件版本
		 */
	public java.lang.String getXmpdHardwareVersion() {
		return this.xmpdHardwareVersion;
	}
	
	/**
	 * 设置硬件版本
	 * @param xmpdHardwareVersion 硬件版本
	 */
	public void setXmpdHardwareVersion(java.lang.String value) {
		this.xmpdHardwareVersion = value;
	}
		/**
		 * 取得软件版本
		 * return 软件版本
		 */
	public java.lang.String getXmpdSoftwareVersion() {
		return this.xmpdSoftwareVersion;
	}
	
	/**
	 * 设置软件版本
	 * @param xmpdSoftwareVersion 软件版本
	 */
	public void setXmpdSoftwareVersion(java.lang.String value) {
		this.xmpdSoftwareVersion = value;
	}
		/**
		 * 取得设备类型
		 * return 设备类型
		 */
	public java.lang.String getXmpdType() {
		return this.xmpdType;
	}
	
	/**
	 * 设置设备类型
	 * @param xmpdType 设备类型
	 */
	public void setXmpdType(java.lang.String value) {
		this.xmpdType = value;
	}
 

	public java.lang.String getXmpdSubDeviceId() {
		return xmpdSubDeviceId;
	}

	public void setXmpdSubDeviceId(java.lang.String xmpdSubDeviceId) {
		this.xmpdSubDeviceId = xmpdSubDeviceId;
	}

	public java.lang.String getXmpdStatus() {
		return xmpdStatus;
	}

	public void setXmpdStatus(java.lang.String xmpdStatus) {
		this.xmpdStatus = xmpdStatus;
	}

	public java.lang.String getXmpdComment() {
		return xmpdComment;
	}

	public void setXmpdComment(java.lang.String xmpdComment) {
		this.xmpdComment = xmpdComment;
	}

	@Transient
	public java.lang.String getXmpdTypeLabel() {
		return xmpdTypeLabel;
	}

	public void setXmpdTypeLabel(java.lang.String xmpdTypeLabel) {
		this.xmpdTypeLabel = xmpdTypeLabel;
	}

	@Transient
	public java.lang.String getXmpdStatusLabel() {
		return xmpdStatusLabel;
	}

	public void setXmpdStatusLabel(java.lang.String xmpdStatusLabel) {
		this.xmpdStatusLabel = xmpdStatusLabel;
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
			XmPadDevice castObj = (XmPadDevice) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmpdGuid(), castObj.getXmpdGuid());
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
			XmPadDevice castObj = (XmPadDevice) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmpdGuid(), castObj.getXmpdGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}

	@Override
	public String toString() {
		return "XmPadDevice [xmpdGuid=" + xmpdGuid + ", xmpdCode=" + xmpdCode
				+ ", xmpdDeviceId=" + xmpdDeviceId + ", xmpdDeviceSpec="
				+ xmpdDeviceSpec + ", xmpdHardwareVersion="
				+ xmpdHardwareVersion + ", xmpdSoftwareVersion="
				+ xmpdSoftwareVersion + ", xmpdType=" + xmpdType
				+ ", xmpdSubDeviceId=" + xmpdSubDeviceId + ", xmpdStatus="
				+ xmpdStatus + ", xmpdComment=" + xmpdComment + "]";
	}
	
	
}

