/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.onsite.po;

import java.util.Date;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

/** no table comments对应实体类 */
@Entity
@Table(name = "XM_MEETING_CALL")
public class XmMeetingCall extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMCALL_GUID= "xmmcallGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMMCALL_CALLER= "xmmcallCaller";
	public static String XMMCALL_MESSAGE= "xmmcallMessage"; 
	public static String XMMCALL_STATUS= "xmmcallStatus"; 

	//primary key
	/** PID */
	private java.lang.String xmmcallGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	/** 呼叫者 */
	private java.lang.String xmmcallCaller;
	private java.lang.String xmmcallCallerDisplayname;
	/** 呼叫内容 */
	private java.lang.String xmmcallMessage; 
	/** 状态 */
	private java.lang.String xmmcallStatus; 


	private Date xmmcallTime;

	public XmMeetingCall(){
	}

	public XmMeetingCall(java.lang.String xmmcallGuid ){
		this.xmmcallGuid = xmmcallGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmcallGuid() {
		return this.xmmcallGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmcallGuid PID
	 */
	public void setXmmcallGuid(java.lang.String xmmcallGuid) {
		this.xmmcallGuid = xmmcallGuid;
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
		 * 取得呼叫者
		 * return 呼叫者
		 */
	public java.lang.String getXmmcallCaller() {
		return this.xmmcallCaller;
	}
	
	/**
	 * 设置呼叫者
	 * @param xmmcallCaller 呼叫者
	 */
	public void setXmmcallCaller(java.lang.String value) {
		this.xmmcallCaller = value;
	}
		/**
		 * 取得呼叫内容
		 * return 呼叫内容
		 */
	public java.lang.String getXmmcallMessage() {
		return this.xmmcallMessage;
	}
	
	/**
	 * 设置呼叫内容
	 * @param xmmcallMessage 呼叫内容
	 */
	public void setXmmcallMessage(java.lang.String value) {
		this.xmmcallMessage = value;
	} 

	public java.lang.String getXmmcallCallerDisplayname() {
		return xmmcallCallerDisplayname;
	}

	public void setXmmcallCallerDisplayname(
			java.lang.String xmmcallCallerDisplayname) {
		this.xmmcallCallerDisplayname = xmmcallCallerDisplayname;
	}

	public Date getXmmcallTime() {
		return xmmcallTime;
	}

	public void setXmmcallTime(Date xmmcallTime) {
		this.xmmcallTime = xmmcallTime;
	}

	public java.lang.String getXmmcallStatus() {
		return xmmcallStatus;
	}

	public void setXmmcallStatus(java.lang.String xmmcallStatus) {
		this.xmmcallStatus = xmmcallStatus;
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
			XmMeetingCall castObj = (XmMeetingCall) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmcallGuid(), castObj.getXmmcallGuid());
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
			XmMeetingCall castObj = (XmMeetingCall) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmcallGuid(), castObj.getXmmcallGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

