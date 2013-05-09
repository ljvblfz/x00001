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
@Table(name = "SY_FSM_STATE_ACTOR")
public class SyFsmStateActor extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String SFSAGUID= "sfsaguid";
	public static String SFGUID= "sfguid";
	public static String TRANSITION= "transition";
	public static String FROM_SFSGUID= "fromSfsguid";
	public static String TO_SFSGUID= "toSfsguid";
	public static String SFAGUID= "sfaguid";
	public static String DESCRIPTION= "description"; 

	//primary key
	/** PID */
	private java.lang.String sfsaguid;
	/** SY_FSM的主键 */
	private java.lang.String sfguid;
	/** 转移名称 */
	private java.lang.String transition;
	/** FROM STATE */
	private java.lang.String fromSfsguid;
	/** TO STATE */
	private java.lang.String toSfsguid;
	/** actor */
	private java.lang.String sfaguid;
	/** 描述 */
	private java.lang.String description; 

	

	public SyFsmStateActor(){
	}

	public SyFsmStateActor(java.lang.String sfsaguid ){
		this.sfsaguid = sfsaguid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getSfsaguid() {
		return this.sfsaguid;
	}
	
	/**
	 * 设置PID
	 * @param sfsaguid PID
	 */
	public void setSfsaguid(java.lang.String sfsaguid) {
		this.sfsaguid = sfsaguid;
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
		 * 取得转移名称
		 * return 转移名称
		 */
	public java.lang.String getTransition() {
		return this.transition;
	}
	
	/**
	 * 设置转移名称
	 * @param transition 转移名称
	 */
	public void setTransition(java.lang.String value) {
		this.transition = value;
	}
		/**
		 * 取得FROM STATE
		 * return FROM STATE
		 */
	public java.lang.String getFromSfsguid() {
		return this.fromSfsguid;
	}
	
	/**
	 * 设置FROM STATE
	 * @param fromSfsguid FROM STATE
	 */
	public void setFromSfsguid(java.lang.String value) {
		this.fromSfsguid = value;
	}
		/**
		 * 取得TO STATE
		 * return TO STATE
		 */
	public java.lang.String getToSfsguid() {
		return this.toSfsguid;
	}
	
	/**
	 * 设置TO STATE
	 * @param toSfsguid TO STATE
	 */
	public void setToSfsguid(java.lang.String value) {
		this.toSfsguid = value;
	}
		/**
		 * 取得actor
		 * return actor
		 */
	public java.lang.String getSfaguid() {
		return this.sfaguid;
	}
	
	/**
	 * 设置actor
	 * @param sfaguid actor
	 */
	public void setSfaguid(java.lang.String value) {
		this.sfaguid = value;
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
			SyFsmStateActor castObj = (SyFsmStateActor) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getSfsaguid(), castObj.getSfsaguid());
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
			SyFsmStateActor castObj = (SyFsmStateActor) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getSfsaguid(), castObj.getSfsaguid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

