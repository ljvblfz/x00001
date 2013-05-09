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
@Table(name = "SY_FSM_ACTOR")
public class SyFsmActor extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String SFAGUID= "sfaguid";
	public static String SFGUID= "sfguid";
	public static String NAME= "name";
	public static String TYPE= "type";
	public static String USERID= "userid";
	public static String SCRIPTID= "scriptid";
	public static String DESCRIPTION= "description"; 

	//primary key
	/** PID */
	private java.lang.String sfaguid;
	/** SY_FSM的主键 */
	private java.lang.String sfguid;
	/** 名称 */
	private java.lang.String name;
	/** 类型 */
	private java.lang.String type;
	/** 用户ID */
	private java.lang.String userid;
	/** 脚本ID */
	private java.lang.String scriptid;
	/** 描述 */
	private java.lang.String description; 

	

	public SyFsmActor(){
	}

	public SyFsmActor(java.lang.String sfaguid ){
		this.sfaguid = sfaguid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getSfaguid() {
		return this.sfaguid;
	}
	
	/**
	 * 设置PID
	 * @param sfaguid PID
	 */
	public void setSfaguid(java.lang.String sfaguid) {
		this.sfaguid = sfaguid;
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
		 * 取得类型
		 * return 类型
		 */
	public java.lang.String getType() {
		return this.type;
	}
	
	/**
	 * 设置类型
	 * @param type 类型
	 */
	public void setType(java.lang.String value) {
		this.type = value;
	}
		/**
		 * 取得用户ID
		 * return 用户ID
		 */
	public java.lang.String getUserid() {
		return this.userid;
	}
	
	/**
	 * 设置用户ID
	 * @param userid 用户ID
	 */
	public void setUserid(java.lang.String value) {
		this.userid = value;
	}
		/**
		 * 取得脚本ID
		 * return 脚本ID
		 */
	public java.lang.String getScriptid() {
		return this.scriptid;
	}
	
	/**
	 * 设置脚本ID
	 * @param scriptid 脚本ID
	 */
	public void setScriptid(java.lang.String value) {
		this.scriptid = value;
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
			SyFsmActor castObj = (SyFsmActor) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getSfaguid(), castObj.getSfaguid());
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
			SyFsmActor castObj = (SyFsmActor) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getSfaguid(), castObj.getSfaguid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

