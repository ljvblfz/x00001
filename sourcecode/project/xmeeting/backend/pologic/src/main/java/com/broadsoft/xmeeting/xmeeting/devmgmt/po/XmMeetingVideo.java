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
@Table(name = "XM_MEETING_VIDEO")
public class XmMeetingVideo extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMV_GUID= "xmmvGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMMV_NAME= "xmmiName";
	public static String XMMV_DESCRIPTION= "xmmiDescription";
	public static String XMMV_FILE= "xmmiFile"; 

	//primary key
	/** PID */
	private java.lang.String xmmvGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	/** 视频名称 */
	private java.lang.String xmmvName;
	/** 视频描述 */
	private java.lang.String xmmvDescription;
	/** 视频文件 */
	private java.lang.String xmmvFile; 

	

	public XmMeetingVideo(){
	}

	public XmMeetingVideo(java.lang.String xmmvGuid ){
		this.xmmvGuid = xmmvGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmvGuid() {
		return this.xmmvGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmvGuid PID
	 */
	public void setXmmvGuid(java.lang.String xmmvGuid) {
		this.xmmvGuid = xmmvGuid;
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
	
	public java.lang.String getXmmvName() {
		return xmmvName;
	}

	public void setXmmvName(java.lang.String xmmvName) {
		this.xmmvName = xmmvName;
	}

	public java.lang.String getXmmvDescription() {
		return xmmvDescription;
	}

	public void setXmmvDescription(java.lang.String xmmvDescription) {
		this.xmmvDescription = xmmvDescription;
	}

	public java.lang.String getXmmvFile() {
		return xmmvFile;
	}

	public void setXmmvFile(java.lang.String xmmvFile) {
		this.xmmvFile = xmmvFile;
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
			XmMeetingVideo castObj = (XmMeetingVideo) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmvGuid(), castObj.getXmmvGuid());
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
			XmMeetingVideo castObj = (XmMeetingVideo) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmvGuid(), castObj.getXmmvGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

