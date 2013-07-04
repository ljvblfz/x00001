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
@Table(name = "XM_MEETING_SERVICE_PERSONNEL")
public class XmMeetingServicePersonnel extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMSP_GUID= "xmmspGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMPI_GUID= "xmpiGuid";
	public static String XMPD_GUID= "xmpdGuid";
	public static String XMMSP_SERVICE_ROLE= "xmmspServiceRole"; 
	
	

	//primary key
	/** PID */
	private java.lang.String xmmspGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	/** 人员名字 */
	private java.lang.String xmpiGuid;
	private java.lang.String xmpiGuidLabel;
	/** 人员服务角色 */
	private java.lang.String xmmspServiceRole; 
	/** 设备ID*/
	private java.lang.String xmpdGuid;
	private java.lang.String xmpdGuidLabel;

	

	public XmMeetingServicePersonnel(){
	}

	public XmMeetingServicePersonnel(java.lang.String xmmspGuid ){
		this.xmmspGuid = xmmspGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmspGuid() {
		return this.xmmspGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmspGuid PID
	 */
	public void setXmmspGuid(java.lang.String xmmspGuid) {
		this.xmmspGuid = xmmspGuid;
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
		 * 取得人员名字
		 * return 人员名字
		 */
	public java.lang.String getXmpiGuid() {
		return this.xmpiGuid;
	}
	
	/**
	 * 设置人员名字
	 * @param xmpiGuid 人员名字
	 */
	public void setXmpiGuid(java.lang.String value) {
		this.xmpiGuid = value;
	}
		/**
		 * 取得人员服务角色
		 * return 人员服务角色
		 */
	public java.lang.String getXmmspServiceRole() {
		return this.xmmspServiceRole;
	}
	
	/**
	 * 设置人员服务角色
	 * @param xmmspServiceRole 人员服务角色
	 */
	public void setXmmspServiceRole(java.lang.String value) {
		this.xmmspServiceRole = value;
	}
 
	@Transient
	public java.lang.String getXmpiGuidLabel() {
		return xmpiGuidLabel;
	}

	public void setXmpiGuidLabel(java.lang.String xmpiGuidLabel) {
		this.xmpiGuidLabel = xmpiGuidLabel;
	}

	public java.lang.String getXmpdGuid() {
		return xmpdGuid;
	}

	public void setXmpdGuid(java.lang.String xmpdGuid) {
		this.xmpdGuid = xmpdGuid;
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
			XmMeetingServicePersonnel castObj = (XmMeetingServicePersonnel) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmspGuid(), castObj.getXmmspGuid());
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
			XmMeetingServicePersonnel castObj = (XmMeetingServicePersonnel) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmspGuid(), castObj.getXmmspGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

