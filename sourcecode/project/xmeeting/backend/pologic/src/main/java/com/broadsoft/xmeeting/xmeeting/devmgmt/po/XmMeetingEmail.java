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
@Table(name = "XM_MEETING_EMAIL")
public class XmMeetingEmail extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMME_GUID= "xmmeGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMME_TO_ADDRESS= "xmmeToAddress";
	public static String XMME_TO_NAME= "xmmeToName";
	public static String XMME_STATUS= "xmmeStatus"; 
	public static String XMME_ATTACHMENT= "xmmeAttachment"; 

	//primary key
	/** PID */
	private java.lang.String xmmeGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	/** 收件人地址 */
	private java.lang.String xmmeToAddress;
	/** 收件人名字 */
	private java.lang.String xmmeToName;
	/** 状态 */
	private java.lang.String xmmeStatus; 
	/** 邮件附件 */
	private java.lang.String xmmeAttachment; 

	

	public XmMeetingEmail(){
	}

	public XmMeetingEmail(java.lang.String xmmeGuid ){
		this.xmmeGuid = xmmeGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmeGuid() {
		return this.xmmeGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmeGuid PID
	 */
	public void setXmmeGuid(java.lang.String xmmeGuid) {
		this.xmmeGuid = xmmeGuid;
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
		 * 取得收件人地址
		 * return 收件人地址
		 */
	public java.lang.String getXmmeToAddress() {
		return this.xmmeToAddress;
	}
	
	/**
	 * 设置收件人地址
	 * @param xmmeToAddress 收件人地址
	 */
	public void setXmmeToAddress(java.lang.String value) {
		this.xmmeToAddress = value;
	}
		/**
		 * 取得收件人名字
		 * return 收件人名字
		 */
	public java.lang.String getXmmeToName() {
		return this.xmmeToName;
	}
	
	/**
	 * 设置收件人名字
	 * @param xmmeToName 收件人名字
	 */
	public void setXmmeToName(java.lang.String value) {
		this.xmmeToName = value;
	}
		/**
		 * 取得状态
		 * return 状态
		 */
	public java.lang.String getXmmeStatus() {
		return this.xmmeStatus;
	}
	
	/**
	 * 设置状态
	 * @param xmmeStatus 状态
	 */
	public void setXmmeStatus(java.lang.String value) {
		this.xmmeStatus = value;
	}
 

	public java.lang.String getXmmeAttachment() {
		return xmmeAttachment;
	}

	public void setXmmeAttachment(java.lang.String xmmeAttachment) {
		this.xmmeAttachment = xmmeAttachment;
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
			XmMeetingEmail castObj = (XmMeetingEmail) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmeGuid(), castObj.getXmmeGuid());
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
			XmMeetingEmail castObj = (XmMeetingEmail) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmeGuid(), castObj.getXmmeGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

