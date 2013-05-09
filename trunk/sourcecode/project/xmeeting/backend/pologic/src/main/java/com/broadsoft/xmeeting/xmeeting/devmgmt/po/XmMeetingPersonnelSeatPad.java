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
@Table(name = "XM_MEETING_PERSONNEL_SEAT_PAD")
public class XmMeetingPersonnelSeatPad extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMPSP_GUID= "xmmpspGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMPI_GUID= "xmpiGuid";
	public static String XMPD_GUID= "xmpdGuid";
	public static String XMRID_GUID= "xmridGuid";
	public static String XMMPSP_MEETING_ROLE= "xmmpspMeetingRole"; 

	//primary key
	/** PID */
	private java.lang.String xmmpspGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	/** 人员PID */
	private java.lang.String xmpiGuid;
	private java.lang.String xmpiGuidLabel;
	/** 设备PID */
	private java.lang.String xmpdGuid;
	private java.lang.String xmpdGuidLabel;
	/** 会议室座位 */
	private java.lang.String xmridGuid; 
	private java.lang.String xmridGuidLabel;
	/** 人员会议角色 */
	private java.lang.String xmmpspMeetingRole; 

	

	public XmMeetingPersonnelSeatPad(){
	}

	public XmMeetingPersonnelSeatPad(java.lang.String xmmpspGuid ){
		this.xmmpspGuid = xmmpspGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmpspGuid() {
		return this.xmmpspGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmpspGuid PID
	 */
	public void setXmmpspGuid(java.lang.String xmmpspGuid) {
		this.xmmpspGuid = xmmpspGuid;
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
		 * 取得人员PID
		 * return 人员PID
		 */
	public java.lang.String getXmpiGuid() {
		return this.xmpiGuid;
	}
	
	/**
	 * 设置人员PID
	 * @param xmpiGuid 人员PID
	 */
	public void setXmpiGuid(java.lang.String value) {
		this.xmpiGuid = value;
	}
		/**
		 * 取得设备PID
		 * return 设备PID
		 */
	public java.lang.String getXmpdGuid() {
		return this.xmpdGuid;
	}
	
	/**
	 * 设置设备PID
	 * @param xmpdGuid 设备PID
	 */
	public void setXmpdGuid(java.lang.String value) {
		this.xmpdGuid = value;
	}
		/**
		 * 取得会议室座位
		 * return 会议室座位
		 */
	public java.lang.String getXmridGuid() {
		return this.xmridGuid;
	}
	
	/**
	 * 设置会议室座位
	 * @param xmridGuid 会议室座位
	 */
	public void setXmridGuid(java.lang.String value) {
		this.xmridGuid = value;
	}
		/**
		 * 取得人员会议角色
		 * return 人员会议角色
		 */
	public java.lang.String getXmmpspMeetingRole() {
		return this.xmmpspMeetingRole;
	}
	
	/**
	 * 设置人员会议角色
	 * @param xmmpspMeetingRole 人员会议角色
	 */
	public void setXmmpspMeetingRole(java.lang.String value) {
		this.xmmpspMeetingRole = value;
	}
 
	@Transient
	public java.lang.String getXmpiGuidLabel() {
		return xmpiGuidLabel;
	}

	public void setXmpiGuidLabel(java.lang.String xmpiGuidLabel) {
		this.xmpiGuidLabel = xmpiGuidLabel;
	}

	@Transient
	public java.lang.String getXmpdGuidLabel() {
		return xmpdGuidLabel;
	}

	public void setXmpdGuidLabel(java.lang.String xmpdGuidLabel) {
		this.xmpdGuidLabel = xmpdGuidLabel;
	}

	@Transient
	public java.lang.String getXmridGuidLabel() {
		return xmridGuidLabel;
	}

	public void setXmridGuidLabel(java.lang.String xmridGuidLabel) {
		this.xmridGuidLabel = xmridGuidLabel;
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
			XmMeetingPersonnelSeatPad castObj = (XmMeetingPersonnelSeatPad) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmpspGuid(), castObj.getXmmpspGuid());
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
			XmMeetingPersonnelSeatPad castObj = (XmMeetingPersonnelSeatPad) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmpspGuid(), castObj.getXmmpspGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

