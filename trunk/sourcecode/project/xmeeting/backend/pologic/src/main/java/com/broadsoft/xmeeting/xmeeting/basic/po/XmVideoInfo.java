/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.basic.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

/** no table comments对应实体类 */
@Entity
@Table(name = "XM_VIDEO_INFO")
public class XmVideoInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMVI_GUID= "xmviGuid";
	public static String XMVI_NAME= "xmviName";
	public static String XMVI_DESCRIPTION= "xmviDescription";
	public static String XMVI_FILE= "xmviFile";
	public static String XMVI_STATUS= "xmviStatus"; 

	//primary key
	/** PID */
	private java.lang.String xmviGuid;
	/** 视频名称 */
	private java.lang.String xmviName;
	/** 视频描述 */
	private java.lang.String xmviDescription;
	/** 视频文件 */
	private java.lang.String xmviFile;
	/** 视频状态 */
	private java.lang.String xmviStatus; 
	private java.lang.String xmviStatusLabel; 

	

	public XmVideoInfo(){
	}

	public XmVideoInfo(java.lang.String xmviGuid ){
		this.xmviGuid = xmviGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmviGuid() {
		return this.xmviGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmviGuid PID
	 */
	public void setXmviGuid(java.lang.String xmviGuid) {
		this.xmviGuid = xmviGuid;
	}
		/**
		 * 取得视频名称
		 * return 视频名称
		 */
	public java.lang.String getXmviName() {
		return this.xmviName;
	}
	
	/**
	 * 设置视频名称
	 * @param xmviName 视频名称
	 */
	public void setXmviName(java.lang.String value) {
		this.xmviName = value;
	}
		/**
		 * 取得视频描述
		 * return 视频描述
		 */
	public java.lang.String getXmviDescription() {
		return this.xmviDescription;
	}
	
	/**
	 * 设置视频描述
	 * @param xmviDescription 视频描述
	 */
	public void setXmviDescription(java.lang.String value) {
		this.xmviDescription = value;
	}
		/**
		 * 取得视频文件
		 * return 视频文件
		 */
	public java.lang.String getXmviFile() {
		return this.xmviFile;
	}
	
	/**
	 * 设置视频文件
	 * @param xmviFile 视频文件
	 */
	public void setXmviFile(java.lang.String value) {
		this.xmviFile = value;
	}
		/**
		 * 取得视频状态
		 * return 视频状态
		 */
	public java.lang.String getXmviStatus() {
		return this.xmviStatus;
	}
	
	/**
	 * 设置视频状态
	 * @param xmviStatus 视频状态
	 */
	public void setXmviStatus(java.lang.String value) {
		this.xmviStatus = value;
	}
 

	@Transient
	public java.lang.String getXmviStatusLabel() {
		return xmviStatusLabel;
	}

	public void setXmviStatusLabel(java.lang.String xmviStatusLabel) {
		this.xmviStatusLabel = xmviStatusLabel;
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
			XmVideoInfo castObj = (XmVideoInfo) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmviGuid(), castObj.getXmviGuid());
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
			XmVideoInfo castObj = (XmVideoInfo) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmviGuid(), castObj.getXmviGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

