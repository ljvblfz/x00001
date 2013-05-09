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
@Table(name = "XM_MEETING_SCHEDULE")
public class XmMeetingSchedule extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMS_GUID= "xmmsGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMMS_TITLE= "xmmsTitle";
	public static String XMMS_SORTNO= "xmmsSortno"; 

	//primary key
	/** PID */
	private java.lang.String xmmsGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	/** 主题描述 */
	private java.lang.String xmmsTitle;
	/** 顺序 */
	private java.lang.String xmmsSortno; 
	
	
	private List<XmMeetingScheduleDetail> listOfXmMeetingScheduleDetail=new ArrayList<XmMeetingScheduleDetail>();

	

	public XmMeetingSchedule(){
	}

	public XmMeetingSchedule(java.lang.String xmmsGuid ){
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
		 * 取得主题描述
		 * return 主题描述
		 */
	public java.lang.String getXmmsTitle() {
		return this.xmmsTitle;
	}
	
	/**
	 * 设置主题描述
	 * @param xmmsTitle 主题描述
	 */
	public void setXmmsTitle(java.lang.String value) {
		this.xmmsTitle = value;
	}
		/**
		 * 取得顺序
		 * return 顺序
		 */
	public java.lang.String getXmmsSortno() {
		return this.xmmsSortno;
	}
	
	/**
	 * 设置顺序
	 * @param xmmsSortno 顺序
	 */
	public void setXmmsSortno(java.lang.String value) {
		this.xmmsSortno = value;
	} 
	
	
	
	
	@Transient
	public List<XmMeetingScheduleDetail> getListOfXmMeetingScheduleDetail() {
		return listOfXmMeetingScheduleDetail;
	}

	public void setListOfXmMeetingScheduleDetail(
			List<XmMeetingScheduleDetail> listOfXmMeetingScheduleDetail) {
		this.listOfXmMeetingScheduleDetail = listOfXmMeetingScheduleDetail;
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
			XmMeetingSchedule castObj = (XmMeetingSchedule) obj;
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
			XmMeetingSchedule castObj = (XmMeetingSchedule) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmsGuid(), castObj.getXmmsGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

