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
@Table(name = "XM_MEETING_WEATHER")
public class XmMeetingWeather extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMW_GUID= "xmmwGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMMW_DESCRIPTION= "xmmwDescription"; 

	//primary key
	/** PID */
	private java.lang.String xmmwGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	/** 天气信息 */
	private java.lang.String xmmwDescription; 

	

	public XmMeetingWeather(){
	}

	public XmMeetingWeather(java.lang.String xmmwGuid ){
		this.xmmwGuid = xmmwGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmwGuid() {
		return this.xmmwGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmwGuid PID
	 */
	public void setXmmwGuid(java.lang.String xmmwGuid) {
		this.xmmwGuid = xmmwGuid;
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
		 * 取得天气信息
		 * return 天气信息
		 */
	public java.lang.String getXmmwDescription() {
		return this.xmmwDescription;
	}
	
	/**
	 * 设置天气信息
	 * @param xmmwDescription 天气信息
	 */
	public void setXmmwDescription(java.lang.String value) {
		this.xmmwDescription = value;
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
			XmMeetingWeather castObj = (XmMeetingWeather) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmwGuid(), castObj.getXmmwGuid());
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
			XmMeetingWeather castObj = (XmMeetingWeather) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmwGuid(), castObj.getXmmwGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

