/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.au.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.AbstractEntity;
import com.founder.sipbus.common.po.BaseEntity;

@Entity
@Table(name = "HR_ATTENDENCERECORD")

public class HrAttendencerecord extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	public static String ID= "id";
	public static String JOBNUMBER= "jobnumber";
	public static String SIGNDATE= "signdate";
	public static String GUID= "guid";
	public static String USERNAME= "username";

	//primary key
	/**
	 * 主键标识
	 */
	private java.lang.String id;

	/**
	 * 工号
	 */
	private java.lang.String jobnumber;
	/**
	 * 登记日期
	 */
	private java.util.Date signdate;
	/**
	 * 
	 */
	private java.lang.String guid;
	/**
	 * 
	 */
	private java.lang.String username;

	

	public HrAttendencerecord(){
	}

	public HrAttendencerecord(java.lang.String id ){
		this.id = id;
	}


	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getId() {
		return this.id;
	}
	
	public void setId(java.lang.String value) {
		this.id = value;
	}
	public java.lang.String getJobnumber() {
		return this.jobnumber;
	}
	
	public void setJobnumber(java.lang.String value) {
		this.jobnumber = value;
	}
	public java.util.Date getSigndate() {
		return this.signdate;
	}
	
	public void setSigndate(java.util.Date value) {
		this.signdate = value;
	}
	public java.lang.String getGuid() {
		return this.guid;
	}
	
	public void setGuid(java.lang.String value) {
		this.guid = value;
	}
	public java.lang.String getUsername() {
		return this.username;
	}
	
	public void setUsername(java.lang.String value) {
		this.username = value;
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
			HrAttendencerecord castObj = (HrAttendencerecord) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getId(), castObj.getId());
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
			HrAttendencerecord castObj = (HrAttendencerecord) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getId(), castObj.getId());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

