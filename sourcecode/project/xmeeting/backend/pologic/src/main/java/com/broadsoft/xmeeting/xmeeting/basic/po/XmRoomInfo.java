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
@Table(name = "XM_ROOM_INFO")
public class XmRoomInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMRI_GUID= "xmriGuid";
	public static String XMRI_NAME= "xmriName";
	public static String XMRI_CODE= "xmriCode";
	public static String XMRI_DESC= "xmriDesc"; 
	public static String XMRI_STATUS= "xmriStatus"; 

	//primary key
	/** PID */
	private java.lang.String xmriGuid;
	/** 会议室名称 */
	private java.lang.String xmriName;
	/** 会议室编号 */
	private java.lang.String xmriCode;
	/** 会议室描述 */
	private java.lang.String xmriDesc; 
	/** 会议室状态*/
	private java.lang.String xmriStatus; 
	private java.lang.String xmriStatusLabel; 

	

	public XmRoomInfo(){
	}

	public XmRoomInfo(java.lang.String xmriGuid ){
		this.xmriGuid = xmriGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmriGuid() {
		return this.xmriGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmriGuid PID
	 */
	public void setXmriGuid(java.lang.String xmriGuid) {
		this.xmriGuid = xmriGuid;
	}
		/**
		 * 取得会议室名称
		 * return 会议室名称
		 */
	public java.lang.String getXmriName() {
		return this.xmriName;
	}
	
	/**
	 * 设置会议室名称
	 * @param xmriName 会议室名称
	 */
	public void setXmriName(java.lang.String value) {
		this.xmriName = value;
	}
		/**
		 * 取得会议室编号
		 * return 会议室编号
		 */
	public java.lang.String getXmriCode() {
		return this.xmriCode;
	}
	
	/**
	 * 设置会议室编号
	 * @param xmriCode 会议室编号
	 */
	public void setXmriCode(java.lang.String value) {
		this.xmriCode = value;
	}
		/**
		 * 取得会议室描述
		 * return 会议室描述
		 */
	public java.lang.String getXmriDesc() {
		return this.xmriDesc;
	}
	
	/**
	 * 设置会议室描述
	 * @param xmriDesc 会议室描述
	 */
	public void setXmriDesc(java.lang.String value) {
		this.xmriDesc = value;
	}
 

	public java.lang.String getXmriStatus() {
		return xmriStatus;
	}

	public void setXmriStatus(java.lang.String xmriStatus) {
		this.xmriStatus = xmriStatus;
	}

	@Transient
	public java.lang.String getXmriStatusLabel() {
		return xmriStatusLabel;
	}

	public void setXmriStatusLabel(java.lang.String xmriStatusLabel) {
		this.xmriStatusLabel = xmriStatusLabel;
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
			XmRoomInfo castObj = (XmRoomInfo) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmriGuid(), castObj.getXmriGuid());
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
			XmRoomInfo castObj = (XmRoomInfo) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmriGuid(), castObj.getXmriGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

