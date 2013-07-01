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
@Table(name = "XM_ROOM_INFO_DETAIL")
public class XmRoomInfoDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMRID_GUID= "xmridGuid";
	public static String XMRI_GUID= "xmriGuid";
	public static String XMRID_SEATNO= "xmridSeatno";
	public static String XMRID_SEATDESC= "xmridSeatdesc";
	public static String XMPD_GUID= "xmpdGuid"; 

	//primary key
	/** PID */
	private java.lang.String xmridGuid;
	/** 人员名字 */
	private java.lang.String xmriGuid;
	/** 座位编号 */
	private java.lang.String xmridSeatno;
	/** 座位描述 */
	private java.lang.String xmridSeatdesc;
	/** 设备PID */
	private java.lang.String xmpdGuid; 
	/** 设备PID */
	private java.lang.String xmpdGuidLabel; 

	

	public XmRoomInfoDetail(){
	}

	public XmRoomInfoDetail(java.lang.String xmridGuid ){
		this.xmridGuid = xmridGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmridGuid() {
		return this.xmridGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmridGuid PID
	 */
	public void setXmridGuid(java.lang.String xmridGuid) {
		this.xmridGuid = xmridGuid;
	}
		/**
		 * 取得人员名字
		 * return 人员名字
		 */
	public java.lang.String getXmriGuid() {
		return this.xmriGuid;
	}
	
	/**
	 * 设置人员名字
	 * @param xmriGuid 人员名字
	 */
	public void setXmriGuid(java.lang.String value) {
		this.xmriGuid = value;
	}
		/**
		 * 取得座位编号
		 * return 座位编号
		 */
	public java.lang.String getXmridSeatno() {
		return this.xmridSeatno;
	}
	
	/**
	 * 设置座位编号
	 * @param xmridSeatno 座位编号
	 */
	public void setXmridSeatno(java.lang.String value) {
		this.xmridSeatno = value;
	}
		/**
		 * 取得座位描述
		 * return 座位描述
		 */
	public java.lang.String getXmridSeatdesc() {
		return this.xmridSeatdesc;
	}
	
	/**
	 * 设置座位描述
	 * @param xmridSeatdesc 座位描述
	 */
	public void setXmridSeatdesc(java.lang.String value) {
		this.xmridSeatdesc = value;
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
 
	@Transient
	public java.lang.String getXmpdGuidLabel() {
		return xmpdGuidLabel;
	}

	public void setXmpdGuidLabel(java.lang.String xmpdGuidLabel) {
		this.xmpdGuidLabel = xmpdGuidLabel;
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
			XmRoomInfoDetail castObj = (XmRoomInfoDetail) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmridGuid(), castObj.getXmridGuid());
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
			XmRoomInfoDetail castObj = (XmRoomInfoDetail) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmridGuid(), castObj.getXmridGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

