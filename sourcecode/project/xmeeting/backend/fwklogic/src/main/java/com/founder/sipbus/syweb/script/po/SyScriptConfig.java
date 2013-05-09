/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.script.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.AbstractEntity;

@Entity
@Table(name = "SY_SCRIPT_CONFIG")

public class SyScriptConfig extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	public static String SSCGUID= "sscguid";
	public static String CONFIG_NAME= "configName";
	public static String CONFIG_CODE= "configCode";
	public static String CONFIG_FUNCTION_CLAZZ= "configFunctionClazz";
	public static String CONFIG_VARIABLE_CLAZZ= "configVariableClazz";
	public static String REMARK= "remark";
	public static String DEL_FLAG= "delFlag";

	//primary key
	/**
	 * PID
	 */
	private java.lang.String sscguid;

	/**
	 * 名称
	 */
	private java.lang.String configName;
	/**
	 * 自编码
	 */
	private java.lang.String configCode;
	/**
	 * 函数类名
	 */
	private java.lang.String configFunctionClazz;
	/**
	 * 变量类名
	 */
	private java.lang.String configVariableClazz;
	/**
	 * 备注
	 */
	private java.lang.String remark;
	/**
	 * 删除标志
	 */
	private java.lang.Integer delFlag;

	

	public SyScriptConfig(){
	}

	public SyScriptConfig(java.lang.String sscguid ){
		this.sscguid = sscguid;
	}


	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getSscguid() {
		return this.sscguid;
	}
	
	public void setSscguid(java.lang.String value) {
		this.sscguid = value;
	}
	public java.lang.String getConfigName() {
		return this.configName;
	}
	
	public void setConfigName(java.lang.String value) {
		this.configName = value;
	}
	public java.lang.String getConfigCode() {
		return this.configCode;
	}
	
	public void setConfigCode(java.lang.String value) {
		this.configCode = value;
	}
	public java.lang.String getConfigFunctionClazz() {
		return this.configFunctionClazz;
	}
	
	public void setConfigFunctionClazz(java.lang.String value) {
		this.configFunctionClazz = value;
	}
	public java.lang.String getConfigVariableClazz() {
		return this.configVariableClazz;
	}
	
	public void setConfigVariableClazz(java.lang.String value) {
		this.configVariableClazz = value;
	}
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	public java.lang.Integer getDelFlag() {
		return this.delFlag;
	}
	
	public void setDelFlag(java.lang.Integer value) {
		this.delFlag = value;
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
			SyScriptConfig castObj = (SyScriptConfig) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getSscguid(), castObj.getSscguid());
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
			SyScriptConfig castObj = (SyScriptConfig) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getSscguid(), castObj.getSscguid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

