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
@Table(name = "XM_MEETING_BUS")
public class XmMeetingBus extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMB_GUID= "xmmbGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMMB_CODE= "xmmbCode";
	public static String XMMB_CARD= "xmmbCard";
	public static String XMMB_DESC= "xmmbDesc";

	//primary key
	/** PID */
	private java.lang.String xmmbGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	/** 车辆编号 */
	private java.lang.String xmmbCode;
	/** 车牌号 */
	private java.lang.String xmmbCard;
	/** 人员安排 */
	private java.lang.String xmmbDesc; 

	

	public XmMeetingBus(){
	}

	public XmMeetingBus(java.lang.String xmmbGuid ){
		this.xmmbGuid = xmmbGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmbGuid() {
		return this.xmmbGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmbGuid PID
	 */
	public void setXmmbGuid(java.lang.String xmmbGuid) {
		this.xmmbGuid = xmmbGuid;
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
		 * 取得车辆编号
		 * return 车辆编号
		 */
	public java.lang.String getXmmbCode() {
		return this.xmmbCode;
	}
	
	/**
	 * 设置车辆编号
	 * @param xmmbCode 车辆编号
	 */
	public void setXmmbCode(java.lang.String value) {
		this.xmmbCode = value;
	}
		/**
		 * 取得车牌号
		 * return 车牌号
		 */
	public java.lang.String getXmmbCard() {
		return this.xmmbCard;
	}
	
	/**
	 * 设置车牌号
	 * @param xmmbCard 车牌号
	 */
	public void setXmmbCard(java.lang.String value) {
		this.xmmbCard = value;
	}
		/**
		 * 取得人员安排
		 * return 人员安排
		 */
	public java.lang.String getXmmbDesc() {
		return this.xmmbDesc;
	}
	
	/**
	 * 设置人员安排
	 * @param xmmbDesc 人员安排
	 */
	public void setXmmbDesc(java.lang.String value) {
		this.xmmbDesc = value;
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
			XmMeetingBus castObj = (XmMeetingBus) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmbGuid(), castObj.getXmmbGuid());
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
			XmMeetingBus castObj = (XmMeetingBus) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmbGuid(), castObj.getXmmbGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

