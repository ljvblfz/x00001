/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.po;

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
@Table(name = "XM_MEETING_INFO")
public class XmMeetingInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMRI_GUID= "xmriGuid";
	public static String XMMI_NAME= "xmmiName";
	public static String XMMI_DESCRIPTION= "xmmiDescription";
	public static String XMMI_STATUS= "xmmiStatus"; 

	//primary key
	/** PID */
	private java.lang.String xmmiGuid;
	/** 会议室 PID*/
	private java.lang.String xmriGuid;
	private java.lang.String xmriGuidLabel;
	/** 会议名称 */
	private java.lang.String xmmiName;
	/** 会议介绍 */
	private java.lang.String xmmiDescription;
	/** 会议状态 */
	private java.lang.String xmmiStatus; 
	private java.lang.String xmmiStatusLabel; 

	

	public XmMeetingInfo(){
	}

	public XmMeetingInfo(java.lang.String xmmiGuid ){
		this.xmmiGuid = xmmiGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmiGuid() {
		return this.xmmiGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmiGuid PID
	 */
	public void setXmmiGuid(java.lang.String xmmiGuid) {
		this.xmmiGuid = xmmiGuid;
	}
	
	
	
	
	public java.lang.String getXmriGuid() {
		return xmriGuid;
	}

	public void setXmriGuid(java.lang.String xmriGuid) {
		this.xmriGuid = xmriGuid;
	}

		/**
		 * 取得会议名称
		 * return 会议名称
		 */
	public java.lang.String getXmmiName() {
		return this.xmmiName;
	}
	
	/**
	 * 设置会议名称
	 * @param xmmiName 会议名称
	 */
	public void setXmmiName(java.lang.String value) {
		this.xmmiName = value;
	}
		/**
		 * 取得会议介绍
		 * return 会议介绍
		 */
	public java.lang.String getXmmiDescription() {
		return this.xmmiDescription;
	}
	
	/**
	 * 设置会议介绍
	 * @param xmmiDescription 会议介绍
	 */
	public void setXmmiDescription(java.lang.String value) {
		this.xmmiDescription = value;
	}
		/**
		 * 取得会议状态
		 * return 会议状态
		 */
	public java.lang.String getXmmiStatus() {
		return this.xmmiStatus;
	}
	
	/**
	 * 设置会议状态
	 * @param xmmiStatus 会议状态
	 */
	public void setXmmiStatus(java.lang.String value) {
		this.xmmiStatus = value;
	}
 

	@Transient
	public java.lang.String getXmmiStatusLabel() {
		return xmmiStatusLabel;
	}

	public void setXmmiStatusLabel(java.lang.String xmmiStatusLabel) {
		this.xmmiStatusLabel = xmmiStatusLabel;
	}

	@Transient
	public java.lang.String getXmriGuidLabel() {
		return xmriGuidLabel;
	}

	public void setXmriGuidLabel(java.lang.String xmriGuidLabel) {
		this.xmriGuidLabel = xmriGuidLabel;
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
			XmMeetingInfo castObj = (XmMeetingInfo) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmiGuid(), castObj.getXmmiGuid());
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
			XmMeetingInfo castObj = (XmMeetingInfo) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmiGuid(), castObj.getXmmiGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

