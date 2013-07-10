/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

/** no table comments对应实体类 */
@Entity
@Table(name = "XM_MEETING_MMS_HISTORY_LOG")
public class XmMeetingMmsHistoryLog extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMMHL_GUID= "xmmmhlGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMMMHL_FROM= "xmmmhlFrom";
	public static String XMMMHL_TO= "xmmmhlTo";
	public static String XMMMHL_STATUS= "xmmmhlStatus";
	public static String DEL_FLAG= "delFlag";

	//primary key
	/** PID */
	private java.lang.String xmmmhlGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	/** 发件人 */
	private java.lang.String xmmmhlFrom;
	/** 收件人 */
	private java.lang.String xmmmhlTo;
	/** 发送状态 */
	private java.lang.String xmmmhlStatus; 

	

	public XmMeetingMmsHistoryLog(){
	}

	public XmMeetingMmsHistoryLog(java.lang.String xmmmhlGuid ){
		this.xmmmhlGuid = xmmmhlGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmmhlGuid() {
		return this.xmmmhlGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmmhlGuid PID
	 */
	public void setXmmmhlGuid(java.lang.String xmmmhlGuid) {
		this.xmmmhlGuid = xmmmhlGuid;
	}
		/**
		 * 取得会议PID
		 * return 会议PID
		 */
	public java.lang.String getXmmiGuid() {
		return this.xmmiGuid;
	}
	
	/**
	 * 设置会议PID
	 * @param xmmiGuid 会议PID
	 */
	public void setXmmiGuid(java.lang.String value) {
		this.xmmiGuid = value;
	}
		/**
		 * 取得发件人
		 * return 发件人
		 */
	public java.lang.String getXmmmhlFrom() {
		return this.xmmmhlFrom;
	}
	
	/**
	 * 设置发件人
	 * @param xmmmhlFrom 发件人
	 */
	public void setXmmmhlFrom(java.lang.String value) {
		this.xmmmhlFrom = value;
	}
		/**
		 * 取得收件人
		 * return 收件人
		 */
	public java.lang.String getXmmmhlTo() {
		return this.xmmmhlTo;
	}
	
	/**
	 * 设置收件人
	 * @param xmmmhlTo 收件人
	 */
	public void setXmmmhlTo(java.lang.String value) {
		this.xmmmhlTo = value;
	}
		/**
		 * 取得发送状态
		 * return 发送状态
		 */
	public java.lang.String getXmmmhlStatus() {
		return this.xmmmhlStatus;
	}
	
	/**
	 * 设置发送状态
	 * @param xmmmhlStatus 发送状态
	 */
	public void setXmmmhlStatus(java.lang.String value) {
		this.xmmmhlStatus = value;
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
			XmMeetingMmsHistoryLog castObj = (XmMeetingMmsHistoryLog) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmmhlGuid(), castObj.getXmmmhlGuid());
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
			XmMeetingMmsHistoryLog castObj = (XmMeetingMmsHistoryLog) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmmhlGuid(), castObj.getXmmmhlGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

