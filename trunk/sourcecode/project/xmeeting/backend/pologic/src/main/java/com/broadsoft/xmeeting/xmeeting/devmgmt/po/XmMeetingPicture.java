/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.po;

import java.util.ArrayList;
import java.util.List;

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
@Table(name = "XM_MEETING_PICTURE")
public class XmMeetingPicture extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMPIC_GUID= "xmmpicGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMMPIC_IMAGE_TITLE= "xmmpicImageTitle";
	public static String XMMPIC_IMAGE_DESCRIPTION= "xmmpicImageDescription"; 

	//primary key
	/** PID */
	private java.lang.String xmmpicGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	/** 图片主题名称 */
	private java.lang.String xmmpicImageTitle;
	/** 图片主题描述 */
	private java.lang.String xmmpicImageDescription; 
	
	
	private List<XmMeetingPictureDetail> listOfXmMeetingPictureDetail=new ArrayList<XmMeetingPictureDetail>();

	

	public XmMeetingPicture(){
	}

	public XmMeetingPicture(java.lang.String xmmpicGuid ){
		this.xmmpicGuid = xmmpicGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmpicGuid() {
		return this.xmmpicGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmpicGuid PID
	 */
	public void setXmmpicGuid(java.lang.String xmmpicGuid) {
		this.xmmpicGuid = xmmpicGuid;
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
		 * 取得图片主题名称
		 * return 图片主题名称
		 */
	public java.lang.String getXmmpicImageTitle() {
		return this.xmmpicImageTitle;
	}
	
	/**
	 * 设置图片主题名称
	 * @param xmmpicImageTitle 图片主题名称
	 */
	public void setXmmpicImageTitle(java.lang.String value) {
		this.xmmpicImageTitle = value;
	}
		/**
		 * 取得图片主题描述
		 * return 图片主题描述
		 */
	public java.lang.String getXmmpicImageDescription() {
		return this.xmmpicImageDescription;
	}
	
	/**
	 * 设置图片主题描述
	 * @param xmmpicImageDescription 图片主题描述
	 */
	public void setXmmpicImageDescription(java.lang.String value) {
		this.xmmpicImageDescription = value;
	} 
	
	@Transient
	public List<XmMeetingPictureDetail> getListOfXmMeetingPictureDetail() {
		return listOfXmMeetingPictureDetail;
	}

	public void setListOfXmMeetingPictureDetail(
			List<XmMeetingPictureDetail> listOfXmMeetingPictureDetail) {
		this.listOfXmMeetingPictureDetail = listOfXmMeetingPictureDetail;
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
			XmMeetingPicture castObj = (XmMeetingPicture) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmpicGuid(), castObj.getXmmpicGuid());
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
			XmMeetingPicture castObj = (XmMeetingPicture) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmpicGuid(), castObj.getXmmpicGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

