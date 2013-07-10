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
@Table(name = "XM_MEETING_EMAIL_HISTORY_LOG")
public class XmMeetingEmailHistoryLog extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMEHL_GUID= "xmmehlGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMMEHL_FROM= "xmmehlFrom";
	public static String XMMEHL_TO= "xmmehlTo";
	public static String XMMEHL_CC= "xmmehlCc";
	public static String XMMEHL_BCC= "xmmehlBcc";
	public static String XMMEHL_STATU= "xmmehlStatu"; 

	//primary key
	/** PID */
	private java.lang.String xmmehlGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	/** 发件人 */
	private java.lang.String xmmehlFrom;
	/** 收件人 */
	private java.lang.String xmmehlTo;
	/** 抄送 */
	private java.lang.String xmmehlCc;
	/** 隐抄 */
	private java.lang.String xmmehlBcc;
	/** 发送状态 */
	private java.lang.String xmmehlStatu; 

	

	public XmMeetingEmailHistoryLog(){
	}

	public XmMeetingEmailHistoryLog(java.lang.String xmmehlGuid ){
		this.xmmehlGuid = xmmehlGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmehlGuid() {
		return this.xmmehlGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmehlGuid PID
	 */
	public void setXmmehlGuid(java.lang.String xmmehlGuid) {
		this.xmmehlGuid = xmmehlGuid;
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
	public java.lang.String getXmmehlFrom() {
		return this.xmmehlFrom;
	}
	
	/**
	 * 设置发件人
	 * @param xmmehlFrom 发件人
	 */
	public void setXmmehlFrom(java.lang.String value) {
		this.xmmehlFrom = value;
	}
		/**
		 * 取得收件人
		 * return 收件人
		 */
	public java.lang.String getXmmehlTo() {
		return this.xmmehlTo;
	}
	
	/**
	 * 设置收件人
	 * @param xmmehlTo 收件人
	 */
	public void setXmmehlTo(java.lang.String value) {
		this.xmmehlTo = value;
	}
		/**
		 * 取得抄送
		 * return 抄送
		 */
	public java.lang.String getXmmehlCc() {
		return this.xmmehlCc;
	}
	
	/**
	 * 设置抄送
	 * @param xmmehlCc 抄送
	 */
	public void setXmmehlCc(java.lang.String value) {
		this.xmmehlCc = value;
	}
		/**
		 * 取得隐抄
		 * return 隐抄
		 */
	public java.lang.String getXmmehlBcc() {
		return this.xmmehlBcc;
	}
	
	/**
	 * 设置隐抄
	 * @param xmmehlBcc 隐抄
	 */
	public void setXmmehlBcc(java.lang.String value) {
		this.xmmehlBcc = value;
	}
		/**
		 * 取得发送状态
		 * return 发送状态
		 */
	public java.lang.String getXmmehlStatu() {
		return this.xmmehlStatu;
	}
	
	/**
	 * 设置发送状态
	 * @param xmmehlStatu 发送状态
	 */
	public void setXmmehlStatu(java.lang.String value) {
		this.xmmehlStatu = value;
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
			XmMeetingEmailHistoryLog castObj = (XmMeetingEmailHistoryLog) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmehlGuid(), castObj.getXmmehlGuid());
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
			XmMeetingEmailHistoryLog castObj = (XmMeetingEmailHistoryLog) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmehlGuid(), castObj.getXmmehlGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

