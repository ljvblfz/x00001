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
@Table(name = "XM_MEETING_X_PICTURE")
public class XmMeetingXpicture extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMXPIC_GUID= "xmmxpicGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMMPIC_GUID= "xmmpicGuid"; 

	//primary key
	/** PID */
	private java.lang.String xmmxpicGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	/** 图片主题PID */
	private java.lang.String xmmpicGuid; 
	private java.lang.String xmmpicGuidLabel;

	

	public XmMeetingXpicture(){
	}

	public XmMeetingXpicture(java.lang.String xmmxpicGuid ){
		this.xmmxpicGuid = xmmxpicGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmxpicGuid() {
		return this.xmmxpicGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmxpicGuid PID
	 */
	public void setXmmxpicGuid(java.lang.String xmmxpicGuid) {
		this.xmmxpicGuid = xmmxpicGuid;
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
		 * 取得图片主题PID
		 * return 图片主题PID
		 */
	public java.lang.String getXmmpicGuid() {
		return this.xmmpicGuid;
	}
	
	/**
	 * 设置图片主题PID
	 * @param xmmpicGuid 图片主题PID
	 */
	public void setXmmpicGuid(java.lang.String value) {
		this.xmmpicGuid = value;
	}
 

	@Transient
	public java.lang.String getXmmpicGuidLabel() {
		return xmmpicGuidLabel;
	}

	public void setXmmpicGuidLabel(java.lang.String xmmpicGuidLabel) {
		this.xmmpicGuidLabel = xmmpicGuidLabel;
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
			XmMeetingXpicture castObj = (XmMeetingXpicture) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmxpicGuid(), castObj.getXmmxpicGuid());
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
			XmMeetingXpicture castObj = (XmMeetingXpicture) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmxpicGuid(), castObj.getXmmxpicGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

