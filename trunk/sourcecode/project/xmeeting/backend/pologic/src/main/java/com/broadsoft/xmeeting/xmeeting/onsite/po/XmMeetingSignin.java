/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.onsite.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

/** no table comments对应实体类 */
@Entity
@Table(name = "XM_MEETING_SIGNIN")
public class XmMeetingSignin extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMS_GUID= "xmmsGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMMS_PERSONNEL= "xmmsPersonnel";
	public static String XMMS_TIME= "xmmsTime";
	public static String XMMS_PHOTO_FILE= "xmmsPhotoFile"; 

	//primary key
	/** PID */
	private java.lang.String xmmsGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	/** 签到人员 */
	private java.lang.String xmmsPersonnel;
	/** 签到时间 */
	private java.util.Date xmmsTime;
	/** 照片路径 */
	private java.lang.String xmmsPhotoFile; 

	

	public XmMeetingSignin(){
	}

	public XmMeetingSignin(java.lang.String xmmsGuid ){
		this.xmmsGuid = xmmsGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmsGuid() {
		return this.xmmsGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmsGuid PID
	 */
	public void setXmmsGuid(java.lang.String xmmsGuid) {
		this.xmmsGuid = xmmsGuid;
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
		 * 取得签到人员
		 * return 签到人员
		 */
	public java.lang.String getXmmsPersonnel() {
		return this.xmmsPersonnel;
	}
	
	/**
	 * 设置签到人员
	 * @param xmmsPersonnel 签到人员
	 */
	public void setXmmsPersonnel(java.lang.String value) {
		this.xmmsPersonnel = value;
	}
		/**
		 * 取得签到时间
		 * return 签到时间
		 */
	public java.util.Date getXmmsTime() {
		return this.xmmsTime;
	}
	
	/**
	 * 设置签到时间
	 * @param xmmsTime 签到时间
	 */
	public void setXmmsTime(java.util.Date value) {
		this.xmmsTime = value;
	}
		/**
		 * 取得照片路径
		 * return 照片路径
		 */
	public java.lang.String getXmmsPhotoFile() {
		return this.xmmsPhotoFile;
	}
	
	/**
	 * 设置照片路径
	 * @param xmmsPhotoFile 照片路径
	 */
	public void setXmmsPhotoFile(java.lang.String value) {
		this.xmmsPhotoFile = value;
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
			XmMeetingSignin castObj = (XmMeetingSignin) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmsGuid(), castObj.getXmmsGuid());
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
			XmMeetingSignin castObj = (XmMeetingSignin) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmsGuid(), castObj.getXmmsGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

