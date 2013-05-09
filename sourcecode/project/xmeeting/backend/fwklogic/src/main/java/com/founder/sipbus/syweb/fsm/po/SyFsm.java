/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.fsm.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

/** no table comments对应实体类 */
@Entity
@Table(name = "SY_FSM")
public class SyFsm extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String SFGUID= "sfguid";
	public static String NAME= "name";
	public static String VERSION= "version";
	public static String CODE= "code";
	public static String DESCRIPTION= "description";
	public static String STATUS= "status"; 

	//primary key
	/** PID */
	private java.lang.String sfguid;
	/** 名称 */
	private java.lang.String name;
	/** 版本 */
	private java.lang.String version;
	/** 自编码 */
	private java.lang.String code;
	/** 备注 */
	private java.lang.String description;
	/** 状态 */
	private java.lang.String status; 

	

	public SyFsm(){
	}

	public SyFsm(java.lang.String sfguid ){
		this.sfguid = sfguid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getSfguid() {
		return this.sfguid;
	}
	
	/**
	 * 设置PID
	 * @param sfguid PID
	 */
	public void setSfguid(java.lang.String sfguid) {
		this.sfguid = sfguid;
	}
		/**
		 * 取得名称
		 * return 名称
		 */
	public java.lang.String getName() {
		return this.name;
	}
	
	/**
	 * 设置名称
	 * @param name 名称
	 */
	public void setName(java.lang.String value) {
		this.name = value;
	}
		/**
		 * 取得版本
		 * return 版本
		 */
	public java.lang.String getVersion() {
		return this.version;
	}
	
	/**
	 * 设置版本
	 * @param version 版本
	 */
	public void setVersion(java.lang.String value) {
		this.version = value;
	}
		/**
		 * 取得自编码
		 * return 自编码
		 */
	public java.lang.String getCode() {
		return this.code;
	}
	
	/**
	 * 设置自编码
	 * @param code 自编码
	 */
	public void setCode(java.lang.String value) {
		this.code = value;
	}
		/**
		 * 取得备注
		 * return 备注
		 */
	public java.lang.String getDescription() {
		return this.description;
	}
	
	/**
	 * 设置备注
	 * @param description 备注
	 */
	public void setDescription(java.lang.String value) {
		this.description = value;
	}
		/**
		 * 取得状态
		 * return 状态
		 */
	public java.lang.String getStatus() {
		return this.status;
	}
	
	/**
	 * 设置状态
	 * @param status 状态
	 */
	public void setStatus(java.lang.String value) {
		this.status = value;
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
			SyFsm castObj = (SyFsm) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getSfguid(), castObj.getSfguid());
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
			SyFsm castObj = (SyFsm) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getSfguid(), castObj.getSfguid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

