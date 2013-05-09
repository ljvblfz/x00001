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
@Table(name = "XM_MEETING_DOCUMENT")
public class XmMeetingDocument extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMD_GUID= "xmmdGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMMD_NAME= "xmmdName";
	public static String XMMD_DESCRIPTION= "xmmdDescription";
	public static String XMMD_FILE= "xmmdFile"; 

	//primary key
	/** PID */
	private java.lang.String xmmdGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	/** 文稿名称 */
	private java.lang.String xmmdName;
	/** 文稿描述 */
	private java.lang.String xmmdDescription;
	/** 文稿文件 */
	private java.lang.String xmmdFile; 

	

	public XmMeetingDocument(){
	}

	public XmMeetingDocument(java.lang.String xmmdGuid ){
		this.xmmdGuid = xmmdGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmdGuid() {
		return this.xmmdGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmdGuid PID
	 */
	public void setXmmdGuid(java.lang.String xmmdGuid) {
		this.xmmdGuid = xmmdGuid;
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
		 * 取得文稿名称
		 * return 文稿名称
		 */
	public java.lang.String getXmmdName() {
		return this.xmmdName;
	}
	
	/**
	 * 设置文稿名称
	 * @param xmmdName 文稿名称
	 */
	public void setXmmdName(java.lang.String value) {
		this.xmmdName = value;
	}
		/**
		 * 取得文稿描述
		 * return 文稿描述
		 */
	public java.lang.String getXmmdDescription() {
		return this.xmmdDescription;
	}
	
	/**
	 * 设置文稿描述
	 * @param xmmdDescription 文稿描述
	 */
	public void setXmmdDescription(java.lang.String value) {
		this.xmmdDescription = value;
	}
		/**
		 * 取得文稿文件
		 * return 文稿文件
		 */
	public java.lang.String getXmmdFile() {
		return this.xmmdFile;
	}
	
	/**
	 * 设置文稿文件
	 * @param xmmdFile 文稿文件
	 */
	public void setXmmdFile(java.lang.String value) {
		this.xmmdFile = value;
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
			XmMeetingDocument castObj = (XmMeetingDocument) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmdGuid(), castObj.getXmmdGuid());
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
			XmMeetingDocument castObj = (XmMeetingDocument) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmdGuid(), castObj.getXmmdGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

