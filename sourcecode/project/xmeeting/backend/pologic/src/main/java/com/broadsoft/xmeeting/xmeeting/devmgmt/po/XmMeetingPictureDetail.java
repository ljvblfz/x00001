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
@Table(name = "XM_MEETING_PICTURE_DETAIL")
public class XmMeetingPictureDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMPICD_GUID= "xmmpicdGuid";
	public static String XMMPIC_GUID= "xmmpicGuid";
	public static String XMMPIC_IMAGE_FILE= "xmmpicImageFile";
	public static String XMMPIC_IMAGE_DESC= "xmmpicImageDesc";
	public static String XMMPIC_SORTNO= "xmmpicSortno"; 

	//primary key
	/** PID */
	private java.lang.String xmmpicdGuid;
	/** 会议图片PID */
	private java.lang.String xmmpicGuid;
	/** 会议图片文件 */
	private java.lang.String xmmpicImageFile;
	/** 会议图片描述 */
	private java.lang.String xmmpicImageDesc;
	/** 顺序 */
	private java.lang.Integer xmmpicSortno; 

	

	public XmMeetingPictureDetail(){
	}

	public XmMeetingPictureDetail(java.lang.String xmmpicdGuid ){
		this.xmmpicdGuid = xmmpicdGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmpicdGuid() {
		return this.xmmpicdGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmpicdGuid PID
	 */
	public void setXmmpicdGuid(java.lang.String xmmpicdGuid) {
		this.xmmpicdGuid = xmmpicdGuid;
	}
		/**
		 * 取得会议图片PID
		 * return 会议图片PID
		 */
	public java.lang.String getXmmpicGuid() {
		return this.xmmpicGuid;
	}
	
	/**
	 * 设置会议图片PID
	 * @param xmmpicGuid 会议图片PID
	 */
	public void setXmmpicGuid(java.lang.String value) {
		this.xmmpicGuid = value;
	}
		/**
		 * 取得会议图片文件
		 * return 会议图片文件
		 */
	public java.lang.String getXmmpicImageFile() {
		return this.xmmpicImageFile;
	}
	
	/**
	 * 设置会议图片文件
	 * @param xmmpicImageFile 会议图片文件
	 */
	public void setXmmpicImageFile(java.lang.String value) {
		this.xmmpicImageFile = value;
	}
		/**
		 * 取得会议图片描述
		 * return 会议图片描述
		 */
	public java.lang.String getXmmpicImageDesc() {
		return this.xmmpicImageDesc;
	}
	
	/**
	 * 设置会议图片描述
	 * @param xmmpicImageDesc 会议图片描述
	 */
	public void setXmmpicImageDesc(java.lang.String value) {
		this.xmmpicImageDesc = value;
	}
		/**
		 * 取得顺序
		 * return 顺序
		 */
	public java.lang.Integer getXmmpicSortno() {
		return this.xmmpicSortno;
	}
	
	/**
	 * 设置顺序
	 * @param xmmpicSortno 顺序
	 */
	public void setXmmpicSortno(java.lang.Integer value) {
		this.xmmpicSortno = value;
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
			XmMeetingPictureDetail castObj = (XmMeetingPictureDetail) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmpicdGuid(), castObj.getXmmpicdGuid());
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
			XmMeetingPictureDetail castObj = (XmMeetingPictureDetail) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmpicdGuid(), castObj.getXmmpicdGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

