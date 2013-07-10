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
@Table(name = "XM_MEETING_MMS_CONTENT")
public class XmMeetingMmsContent extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMC_GUID= "xmmcGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMMC_IMAGE= "xmmcImage";
	public static String XMMC_DESCRIPTION1= "xmmcDescription1";
	public static String XMMC_DESCRIPTION2= "xmmcDescription2";
	public static String XMMC_DESCRIPTION3= "xmmcDescription3"; 

	//primary key
	/** PID */
	private java.lang.String xmmcGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	/** 彩信图片 */
	private java.lang.String xmmcImage;
	/** 彩信文字描述1 */
	private java.lang.String xmmcDescription1;
	/** 彩信文字描述2 */
	private java.lang.String xmmcDescription2;
	/** 彩信文字描述3 */
	private java.lang.String xmmcDescription3; 

	

	public XmMeetingMmsContent(){
	}

	public XmMeetingMmsContent(java.lang.String xmmcGuid ){
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
		 * 取得彩信图片
		 * return 彩信图片
		 */
	public java.lang.String getXmmcImage() {
		return this.xmmcImage;
	}
	
	/**
	 * 设置彩信图片
	 * @param xmmcImage 彩信图片
	 */
	public void setXmmcImage(java.lang.String value) {
		this.xmmcImage = value;
	}
		/**
		 * 取得彩信文字描述1
		 * return 彩信文字描述1
		 */
	public java.lang.String getXmmcDescription1() {
		return this.xmmcDescription1;
	}
	
	/**
	 * 设置彩信文字描述1
	 * @param xmmcDescription1 彩信文字描述1
	 */
	public void setXmmcDescription1(java.lang.String value) {
		this.xmmcDescription1 = value;
	}
		/**
		 * 取得彩信文字描述2
		 * return 彩信文字描述2
		 */
	public java.lang.String getXmmcDescription2() {
		return this.xmmcDescription2;
	}
	
	/**
	 * 设置彩信文字描述2
	 * @param xmmcDescription2 彩信文字描述2
	 */
	public void setXmmcDescription2(java.lang.String value) {
		this.xmmcDescription2 = value;
	}
		/**
		 * 取得彩信文字描述3
		 * return 彩信文字描述3
		 */
	public java.lang.String getXmmcDescription3() {
		return this.xmmcDescription3;
	}
	
	/**
	 * 设置彩信文字描述3
	 * @param xmmcDescription3 彩信文字描述3
	 */
	public void setXmmcDescription3(java.lang.String value) {
		this.xmmcDescription3 = value;
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
			XmMeetingMmsContent castObj = (XmMeetingMmsContent) obj;
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
			XmMeetingMmsContent castObj = (XmMeetingMmsContent) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmcGuid(), castObj.getXmmcGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

