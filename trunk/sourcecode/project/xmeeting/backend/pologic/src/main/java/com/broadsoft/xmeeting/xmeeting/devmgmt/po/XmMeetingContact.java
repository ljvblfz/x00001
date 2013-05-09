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
@Table(name = "XM_MEETING_CONTACT")
public class XmMeetingContact extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMC_GUID= "xmmcGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMMC_DESCRIPTION= "xmmcDescription"; 

	//primary key
	/** PID */
	private java.lang.String xmmcGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	/** 联系方式 */
	private java.lang.String xmmcDescription; 

	

	public XmMeetingContact(){
	}

	public XmMeetingContact(java.lang.String xmmcGuid ){
		this.xmmcGuid = xmmcGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmcGuid() {
		return this.xmmcGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmcGuid PID
	 */
	public void setXmmcGuid(java.lang.String xmmcGuid) {
		this.xmmcGuid = xmmcGuid;
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
		 * 取得联系方式
		 * return 联系方式
		 */
	public java.lang.String getXmmcDescription() {
		return this.xmmcDescription;
	}
	
	/**
	 * 设置联系方式
	 * @param xmmcDescription 联系方式
	 */
	public void setXmmcDescription(java.lang.String value) {
		this.xmmcDescription = value;
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
			XmMeetingContact castObj = (XmMeetingContact) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmcGuid(), castObj.getXmmcGuid());
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
			XmMeetingContact castObj = (XmMeetingContact) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmcGuid(), castObj.getXmmcGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

