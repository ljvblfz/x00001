/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.script.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.AbstractEntity;

@Entity
@Table(name = "SY_SCRIPT_LOG")

public class SyScriptLog extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	public static String GSLGUID= "gslguid";
	public static String GSGUID= "gsguid";
	public static String SCRIPT_VERSION= "scriptVersion";
	public static String SCRIPT_NAME= "scriptName";
	public static String SCRIPT_TYPE= "scriptType";
	public static String BEAN_NAME= "beanName";
	public static String GROUP_NAME= "groupName";
	public static String SCRIPT_SOURCE= "scriptSource";
	public static String DESCRIPTION= "description";
	public static String REMARK= "remark";
	public static String STATUS= "status";
	public static String DEL_FLAG= "delFlag";

	//primary key
	/**
	 * PID
	 */
	private java.lang.String gslguid;

	/**
	 * 脚本表PID
	 */
	private java.lang.String gsguid;
	/**
	 * 脚本版本
	 */
	private java.lang.Integer scriptVersion;
	/**
	 * 脚本名称
	 */
	private java.lang.String scriptName;
	/**
	 * 脚本类型
	 */
	private java.lang.String scriptType;
	/**
	 * BEAN NAME
	 */
	private java.lang.String beanName;
	/**
	 * 群组名称
	 */
	private java.lang.String groupName;
	/**
	 * 脚本内容
	 */
	private java.lang.String scriptSource;
	/**
	 * 描述
	 */
	private java.lang.String description;
	/**
	 * 备注
	 */
	private java.lang.String remark;
	/**
	 * 状态
	 */
	private java.lang.String status;
	/**
	 * 删除标志
	 */
	private java.lang.Integer delFlag;

	private java.lang.String createBy;
	private java.lang.String updateBy;
	
	private java.util.Date createDt;
	private java.util.Date updateDt;
	
	

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

	public SyScriptLog(){
	}

	public SyScriptLog(java.lang.String gslguid ){
		this.gslguid = gslguid;
	}


	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getGslguid() {
		return this.gslguid;
	}
	
	public void setGslguid(java.lang.String value) {
		this.gslguid = value;
	}
	public java.lang.String getGsguid() {
		return this.gsguid;
	}
	
	public void setGsguid(java.lang.String value) {
		this.gsguid = value;
	}
	

	public java.lang.Integer getScriptVersion() {
		return scriptVersion;
	}

	public void setScriptVersion(java.lang.Integer scriptVersion) {
		this.scriptVersion = scriptVersion;
	}

	public java.lang.String getScriptName() {
		return this.scriptName;
	}
	
	public void setScriptName(java.lang.String value) {
		this.scriptName = value;
	}
	public java.lang.String getScriptType() {
		return this.scriptType;
	}
	
	public void setScriptType(java.lang.String value) {
		this.scriptType = value;
	}
	public java.lang.String getBeanName() {
		return this.beanName;
	}
	
	public void setBeanName(java.lang.String value) {
		this.beanName = value;
	}
	public java.lang.String getGroupName() {
		return this.groupName;
	}
	
	public void setGroupName(java.lang.String value) {
		this.groupName = value;
	}
	public java.lang.String getScriptSource() {
		return this.scriptSource;
	}
	
	public void setScriptSource(java.lang.String value) {
		this.scriptSource = value;
	}
	public java.lang.String getDescription() {
		return this.description;
	}
	
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
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
			SyScriptLog castObj = (SyScriptLog) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getGslguid(), castObj.getGslguid());
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
			SyScriptLog castObj = (SyScriptLog) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getGslguid(), castObj.getGslguid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

