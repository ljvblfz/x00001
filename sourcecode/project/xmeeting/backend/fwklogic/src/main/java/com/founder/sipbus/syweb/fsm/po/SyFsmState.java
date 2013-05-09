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
@Table(name = "SY_FSM_STATE")
public class SyFsmState extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String SFSGUID= "sfsguid";
	public static String SFGUID= "sfguid";
	public static String NAME= "name";
	public static String VALUE= "value";
	public static String DESCRIPTION= "description"; 

	//primary key
	/** PID */
	private java.lang.String sfsguid;
	/** SY_FSM的主键 */
	private java.lang.String sfguid;
	/** 状态名称 */
	private java.lang.String name;
	/** 状态值 */
	private java.lang.String value;
	/** 描述 */
	private java.lang.String description; 

	

	public SyFsmState(){
	}

	public SyFsmState(java.lang.String sfsguid ){
		this.sfsguid = sfsguid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getSfsguid() {
		return this.sfsguid;
	}
	
	/**
	 * 设置PID
	 * @param sfsguid PID
	 */
	public void setSfsguid(java.lang.String sfsguid) {
		this.sfsguid = sfsguid;
	}
		/**
		 * 取得SY_FSM的主键
		 * return SY_FSM的主键
		 */
	public java.lang.String getSfguid() {
		return this.sfguid;
	}
	
	/**
	 * 设置SY_FSM的主键
	 * @param sfguid SY_FSM的主键
	 */
	public void setSfguid(java.lang.String value) {
		this.sfguid = value;
	}
		/**
		 * 取得状态名称
		 * return 状态名称
		 */
	public java.lang.String getName() {
		return this.name;
	}
	
	/**
	 * 设置状态名称
	 * @param name 状态名称
	 */
	public void setName(java.lang.String value) {
		this.name = value;
	}
		/**
		 * 取得状态值
		 * return 状态值
		 */
	public java.lang.String getValue() {
		return this.value;
	}
	
	/**
	 * 设置状态值
	 * @param value 状态值
	 */
	public void setValue(java.lang.String value) {
		this.value = value;
	}
		/**
		 * 取得描述
		 * return 描述
		 */
	public java.lang.String getDescription() {
		return this.description;
	}
	
	/**
	 * 设置描述
	 * @param description 描述
	 */
	public void setDescription(java.lang.String value) {
		this.description = value;
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
			SyFsmState castObj = (SyFsmState) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getSfsguid(), castObj.getSfsguid());
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
			SyFsmState castObj = (SyFsmState) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getSfsguid(), castObj.getSfsguid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

