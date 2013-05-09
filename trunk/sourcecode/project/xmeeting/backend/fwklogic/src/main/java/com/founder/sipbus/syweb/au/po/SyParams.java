/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.au.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

@Entity
@Table(name = "SY_PARAMS")

public class SyParams extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String PARAM_ID= "paramId";
	public static String PARAM_NAME= "paramName";
	public static String VALUE= "value";
	public static String DATA_TYPE= "dataType";
	public static String STATUS= "status";
	public static String NOTES= "notes";
	public static String PARAM_CODE= "paramCode";

	//primary key
	/**
	 * 参数编号
	 */
	private java.lang.String paramId;

	/**
	 * 参数名
	 */
	private java.lang.String paramName;
	/**
	 * 参数值
	 */
	private java.lang.String value;
	/**
	 * 参数类型 
	 */
	private java.lang.Short dataType;
	/**
	 * 参数状态1: 正常 0:删除/失效
	 */
	private java.lang.Integer status;

	/**
	 * 备注
	 */
	private java.lang.String notes;
	/**
	 * 参数编号，不能重复
	 */
	private java.lang.String paramCode;

	

	public SyParams(){
	}

	public SyParams(java.lang.String paramId ){
		this.paramId = paramId;
	}


	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getParamId() {
		return this.paramId;
	}
	
	public void setParamId(java.lang.String value) {
		this.paramId = value;
	}
	public java.lang.String getParamName() {
		return this.paramName;
	}
	
	public void setParamName(java.lang.String value) {
		this.paramName = value;
	}
	public java.lang.String getValue() {
		return this.value;
	}
	
	public void setValue(java.lang.String value) {
		this.value = value;
	}
	public java.lang.Short getDataType() {
		return this.dataType;
	}
	
	public void setDataType(java.lang.Short value) {
		this.dataType = value;
	}
	public java.lang.Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}

	public java.lang.String getNotes() {
		return this.notes;
	}
	
	public void setNotes(java.lang.String value) {
		this.notes = value;
	}
	public java.lang.String getParamCode() {
		return this.paramCode;
	}
	
	public void setParamCode(java.lang.String value) {
		this.paramCode = value;
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
			SyParams castObj = (SyParams) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getParamId(), castObj.getParamId());
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
			SyParams castObj = (SyParams) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getParamId(), castObj.getParamId());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

