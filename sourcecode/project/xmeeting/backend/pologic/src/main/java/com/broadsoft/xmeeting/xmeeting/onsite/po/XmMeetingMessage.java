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
@Table(name = "XM_MEETING_MESSAGE")
public class XmMeetingMessage extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMM_GUID= "xmmmGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMMM_FROM= "xmmmFrom";
	public static String XMMM_TO= "xmmmTo";
	public static String XMMM_MESSAGE= "xmmmMessage"; 

	//primary key
	/** PID */
	private java.lang.String xmmmGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	/** 发送者 */
	private java.lang.String xmmmFrom;
	private java.lang.String xmmmFromDisplayname;
	/** 接收者 */
	private java.lang.String xmmmTo;
	private java.lang.String xmmmToDisplayname;
	/** 消息内容 */
	private java.lang.String xmmmMessage; 

	private Date xmmmTime;
	

	public XmMeetingMessage(){
	}

	public XmMeetingMessage(java.lang.String xmmmGuid ){
		this.xmmmGuid = xmmmGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmmGuid() {
		return this.xmmmGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmmGuid PID
	 */
	public void setXmmmGuid(java.lang.String xmmmGuid) {
		this.xmmmGuid = xmmmGuid;
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
		 * 取得发送者
		 * return 发送者
		 */
	public java.lang.String getXmmmFrom() {
		return this.xmmmFrom;
	}
	
	/**
	 * 设置发送者
	 * @param xmmmFrom 发送者
	 */
	public void setXmmmFrom(java.lang.String value) {
		this.xmmmFrom = value;
	}
		/**
		 * 取得接收者
		 * return 接收者
		 */
	public java.lang.String getXmmmTo() {
		return this.xmmmTo;
	}
	
	/**
	 * 设置接收者
	 * @param xmmmTo 接收者
	 */
	public void setXmmmTo(java.lang.String value) {
		this.xmmmTo = value;
	}
		/**
		 * 取得消息内容
		 * return 消息内容
		 */
	public java.lang.String getXmmmMessage() {
		return this.xmmmMessage;
	}
	
	/**
	 * 设置消息内容
	 * @param xmmmMessage 消息内容
	 */
	public void setXmmmMessage(java.lang.String value) {
		this.xmmmMessage = value;
	} 

	public java.lang.String getXmmmFromDisplayname() {
		return xmmmFromDisplayname;
	}

	public void setXmmmFromDisplayname(java.lang.String xmmmFromDisplayname) {
		this.xmmmFromDisplayname = xmmmFromDisplayname;
	}

	public java.lang.String getXmmmToDisplayname() {
		return xmmmToDisplayname;
	}

	public void setXmmmToDisplayname(java.lang.String xmmmToDisplayname) {
		this.xmmmToDisplayname = xmmmToDisplayname;
	}

	public Date getXmmmTime() {
		return xmmmTime;
	}

	public void setXmmmTime(Date xmmmTime) {
		this.xmmmTime = xmmmTime;
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
			XmMeetingMessage castObj = (XmMeetingMessage) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmmGuid(), castObj.getXmmmGuid());
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
			XmMeetingMessage castObj = (XmMeetingMessage) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmmGuid(), castObj.getXmmmGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

