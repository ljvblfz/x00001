/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.au.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.AbstractEntity;

@Entity
@Table(name = "HRATTENDANCELOG")

public class Hrattendancelog extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	public static String ID= "id";
	public static String OLDUSERID= "olduserid";
	public static String NEWUSERID= "newuserid";

	//primary key
	/**
	 * 
	 */
	private java.lang.String id;

	/**
	 * 
	 */
	private java.lang.String olduserid;
	/**
	 * 
	 */
	private java.lang.String newuserid;
	
	private java.lang.String createBy;
	private java.lang.String updateBy;
	
	private java.util.Date createDt;
	private java.util.Date updateDt;
	

	public Hrattendancelog(){
	}

	public Hrattendancelog(java.lang.String id ){
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
	public java.lang.String getOlduserid() {
		return this.olduserid;
	}
	
	public void setOlduserid(java.lang.String value) {
		this.olduserid = value;
	}
	public java.lang.String getNewuserid() {
		return this.newuserid;
	}
	
	public void setNewuserid(java.lang.String value) {
		this.newuserid = value;
	}
	
	
	

	public java.lang.String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}

	public java.lang.String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(java.lang.String updateBy) {
		this.updateBy = updateBy;
	}

	public java.util.Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(java.util.Date createDt) {
		this.createDt = createDt;
	}

	public java.util.Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(java.util.Date updateDt) {
		this.updateDt = updateDt;
	}

	public int compareTo(Object obj) {
		int compare = -1;
	
		if (obj == null)
			compare = -1;
		else if (this == obj)
			compare = 0;
		else if (!(obj instanceof AbstractEntity))
			compare = -1;
		else if (!this.getClass().equals(obj.getClass()))
			compare = -1;
		else {
			Hrattendancelog castObj = (Hrattendancelog) obj;
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
		} else if (!(obj instanceof AbstractEntity)) {
			isEqual = false;
		} else if (!this.getClass().equals(obj.getClass())) {
			isEqual = false;
		} else {
			Hrattendancelog castObj = (Hrattendancelog) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getId(), castObj.getId());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

