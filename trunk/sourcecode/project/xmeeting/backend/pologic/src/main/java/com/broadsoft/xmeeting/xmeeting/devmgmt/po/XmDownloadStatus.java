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
@Table(name = "XM_DOWNLOAD_STATUS")
public class XmDownloadStatus extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMDS_GUID= "xmdsGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMPD_GUID= "xmpdGuid";
	public static String XMDS_COMPANY= "xmdsCompany";
	public static String XMDS_MEETING_SCHEDULE= "xmdsMeetingSchedule";
	public static String XMDS_MEETING_MEMBER= "xmdsMeetingMember";
	public static String XMDS_MEETING_BUS= "xmdsMeetingBus";
	public static String XMDS_MEETING_WEATHER= "xmdsMeetingWeather";
	public static String XMDS_MEETING_CONTACT= "xmdsMeetingContact";
	public static String XMDS_DOCUMENT= "xmdsDocument";
	public static String XMDS_VIDEO= "xmdsVideo";
	public static String XMDS_IMAGE= "xmdsImage"; 

	//primary key
	/** PID */
	private java.lang.String xmdsGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	private java.lang.String xmmiGuidLabel;
	/** 设备PID */
	private java.lang.String xmpdGuid; 
	private java.lang.String xmpdGuidLabel; 
	/** 公司简介下载 */
	private java.lang.String xmdsCompany;
	/** 会议指南(行程安排)下载 */
	private java.lang.String xmdsMeetingSchedule;
	/** 会议指南(人员名单)下载 */
	private java.lang.String xmdsMeetingMember;
	/** 会议指南(车辆安排)下载 */
	private java.lang.String xmdsMeetingBus;
	/** 会议指南(天气情况)下载 */
	private java.lang.String xmdsMeetingWeather;
	/** 会议指南(通讯服务)下载 */
	private java.lang.String xmdsMeetingContact;
	/** 会议文稿下载 */
	private java.lang.String xmdsDocument;
	/** 会议视频下载 */
	private java.lang.String xmdsVideo;
	/** 会议图片下载 */
	private java.lang.String xmdsImage;  
	//

	private java.lang.String xmdsMeetingScheduleLabel;
	private java.lang.String xmdsDocumentLabel;
	private java.lang.String xmdsVideoLabel;
	private java.lang.String xmdsImageLabel; 

	

	public XmDownloadStatus(){
	}

	public XmDownloadStatus(java.lang.String xmdsGuid ){
		this.xmdsGuid = xmdsGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmdsGuid() {
		return this.xmdsGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmdsGuid PID
	 */
	public void setXmdsGuid(java.lang.String xmdsGuid) {
		this.xmdsGuid = xmdsGuid;
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
	
	

	public java.lang.String getXmpdGuid() {
		return xmpdGuid;
	}

	public void setXmpdGuid(java.lang.String xmpdGuid) {
		this.xmpdGuid = xmpdGuid;
	}
	
	
		/**
		 * 取得公司简介下载
		 * return 公司简介下载
		 */
	
	
	
	public java.lang.String getXmdsCompany() {
		return this.xmdsCompany;
	}
	
	/**
	 * 设置公司简介下载
	 * @param xmdsCompany 公司简介下载
	 */
	public void setXmdsCompany(java.lang.String value) {
		this.xmdsCompany = value;
	}
		/**
		 * 取得会议指南(行程安排)下载
		 * return 会议指南(行程安排)下载
		 */
	public java.lang.String getXmdsMeetingSchedule() {
		return this.xmdsMeetingSchedule;
	}
	
	/**
	 * 设置会议指南(行程安排)下载
	 * @param xmdsMeetingSchedule 会议指南(行程安排)下载
	 */
	public void setXmdsMeetingSchedule(java.lang.String value) {
		this.xmdsMeetingSchedule = value;
	}
		/**
		 * 取得会议指南(人员名单)下载
		 * return 会议指南(人员名单)下载
		 */
	public java.lang.String getXmdsMeetingMember() {
		return this.xmdsMeetingMember;
	}
	
	/**
	 * 设置会议指南(人员名单)下载
	 * @param xmdsMeetingMember 会议指南(人员名单)下载
	 */
	public void setXmdsMeetingMember(java.lang.String value) {
		this.xmdsMeetingMember = value;
	}
		/**
		 * 取得会议指南(车辆安排)下载
		 * return 会议指南(车辆安排)下载
		 */
	public java.lang.String getXmdsMeetingBus() {
		return this.xmdsMeetingBus;
	}
	
	/**
	 * 设置会议指南(车辆安排)下载
	 * @param xmdsMeetingBus 会议指南(车辆安排)下载
	 */
	public void setXmdsMeetingBus(java.lang.String value) {
		this.xmdsMeetingBus = value;
	}
		/**
		 * 取得会议指南(天气情况)下载
		 * return 会议指南(天气情况)下载
		 */
	public java.lang.String getXmdsMeetingWeather() {
		return this.xmdsMeetingWeather;
	}
	
	/**
	 * 设置会议指南(天气情况)下载
	 * @param xmdsMeetingWeather 会议指南(天气情况)下载
	 */
	public void setXmdsMeetingWeather(java.lang.String value) {
		this.xmdsMeetingWeather = value;
	}
		/**
		 * 取得会议指南(通讯服务)下载
		 * return 会议指南(通讯服务)下载
		 */
	public java.lang.String getXmdsMeetingContact() {
		return this.xmdsMeetingContact;
	}
	
	/**
	 * 设置会议指南(通讯服务)下载
	 * @param xmdsMeetingContact 会议指南(通讯服务)下载
	 */
	public void setXmdsMeetingContact(java.lang.String value) {
		this.xmdsMeetingContact = value;
	}
		/**
		 * 取得会议文稿下载
		 * return 会议文稿下载
		 */
	public java.lang.String getXmdsDocument() {
		return this.xmdsDocument;
	}
	
	/**
	 * 设置会议文稿下载
	 * @param xmdsDocument 会议文稿下载
	 */
	public void setXmdsDocument(java.lang.String value) {
		this.xmdsDocument = value;
	}
		/**
		 * 取得会议视频下载
		 * return 会议视频下载
		 */
	public java.lang.String getXmdsVideo() {
		return this.xmdsVideo;
	}
	
	/**
	 * 设置会议视频下载
	 * @param xmdsVideo 会议视频下载
	 */
	public void setXmdsVideo(java.lang.String value) {
		this.xmdsVideo = value;
	}
		/**
		 * 取得会议图片下载
		 * return 会议图片下载
		 */
	public java.lang.String getXmdsImage() {
		return this.xmdsImage;
	}
	
	/**
	 * 设置会议图片下载
	 * @param xmdsImage 会议图片下载
	 */
	public void setXmdsImage(java.lang.String value) {
		this.xmdsImage = value;
	}
 


	@Transient
	public java.lang.String getXmmiGuidLabel() {
		return xmmiGuidLabel;
	}

	public void setXmmiGuidLabel(java.lang.String xmmiGuidLabel) {
		this.xmmiGuidLabel = xmmiGuidLabel;
	}

	@Transient
	public java.lang.String getXmpdGuidLabel() {
		return xmpdGuidLabel;
	}

	public void setXmpdGuidLabel(java.lang.String xmpdGuidLabel) {
		this.xmpdGuidLabel = xmpdGuidLabel;
	}

	@Transient
	public java.lang.String getXmdsMeetingScheduleLabel() {
		return xmdsMeetingScheduleLabel;
	}

	public void setXmdsMeetingScheduleLabel(
			java.lang.String xmdsMeetingScheduleLabel) {
		this.xmdsMeetingScheduleLabel = xmdsMeetingScheduleLabel;
	}

	@Transient
	public java.lang.String getXmdsDocumentLabel() {
		return xmdsDocumentLabel;
	}

	public void setXmdsDocumentLabel(java.lang.String xmdsDocumentLabel) {
		this.xmdsDocumentLabel = xmdsDocumentLabel;
	}

	@Transient
	public java.lang.String getXmdsVideoLabel() {
		return xmdsVideoLabel;
	}

	public void setXmdsVideoLabel(java.lang.String xmdsVideoLabel) {
		this.xmdsVideoLabel = xmdsVideoLabel;
	}

	@Transient
	public java.lang.String getXmdsImageLabel() {
		return xmdsImageLabel;
	}

	public void setXmdsImageLabel(java.lang.String xmdsImageLabel) {
		this.xmdsImageLabel = xmdsImageLabel;
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
			XmDownloadStatus castObj = (XmDownloadStatus) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmdsGuid(), castObj.getXmdsGuid());
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
			XmDownloadStatus castObj = (XmDownloadStatus) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmdsGuid(), castObj.getXmdsGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

