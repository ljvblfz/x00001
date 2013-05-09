/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.script.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

/** no table comments对应实体类 */
@Entity
@Table(name = "SY_SCRIPT_DECISIONTABLE")
public class SyScriptDecisiontable extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String GSDTGUID= "gsdtguid";
	public static String GSGUID= "gsguid";
	public static String MIN_VALUE= "minValue";
	public static String MAX_VALUE= "maxValue";
	public static String ACTION_VALUE= "actionValue";
	public static String SHORT_COMMENT= "shortComment"; 

	//primary key
	/** PID */
	private java.lang.String gsdtguid;
	/** 脚本表PID */
	private java.lang.String gsguid;
	/** 最小值 */
	private java.lang.String minValue;
	/** 最大值 */
	private java.lang.String maxValue;
	/** 满足条件值 */
	private java.lang.String actionValue;
	/** 备注 */
	private java.lang.String shortComment; 

	

	public SyScriptDecisiontable(){
	}

	public SyScriptDecisiontable(java.lang.String gsdtguid ){
		this.gsdtguid = gsdtguid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getGsdtguid() {
		return this.gsdtguid;
	}
	
	/**
	 * 设置PID
	 * @param gsdtguid PID
	 */
	public void setGsdtguid(java.lang.String gsdtguid) {
		this.gsdtguid = gsdtguid;
	}
		/**
		 * 取得脚本表PID
		 * return 脚本表PID
		 */
	public java.lang.String getGsguid() {
		return this.gsguid;
	}
	
	/**
	 * 设置脚本表PID
	 * @param gsguid 脚本表PID
	 */
	public void setGsguid(java.lang.String value) {
		this.gsguid = value;
	}
		/**
		 * 取得最小值
		 * return 最小值
		 */
	public java.lang.String getMinValue() {
		return this.minValue;
	}
	
	/**
	 * 设置最小值
	 * @param minValue 最小值
	 */
	public void setMinValue(java.lang.String value) {
		this.minValue = value;
	}
		/**
		 * 取得最大值
		 * return 最大值
		 */
	public java.lang.String getMaxValue() {
		return this.maxValue;
	}
	
	/**
	 * 设置最大值
	 * @param maxValue 最大值
	 */
	public void setMaxValue(java.lang.String value) {
		this.maxValue = value;
	}
		/**
		 * 取得满足条件值
		 * return 满足条件值
		 */
	public java.lang.String getActionValue() {
		return this.actionValue;
	}
	
	/**
	 * 设置满足条件值
	 * @param actionValue 满足条件值
	 */
	public void setActionValue(java.lang.String value) {
		this.actionValue = value;
	}
		/**
		 * 取得备注
		 * return 备注
		 */
	public java.lang.String getShortComment() {
		return this.shortComment;
	}
	
	/**
	 * 设置备注
	 * @param shortComment 备注
	 */
	public void setShortComment(java.lang.String value) {
		this.shortComment = value;
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
			SyScriptDecisiontable castObj = (SyScriptDecisiontable) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getGsdtguid(), castObj.getGsdtguid());
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
			SyScriptDecisiontable castObj = (SyScriptDecisiontable) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getGsdtguid(), castObj.getGsdtguid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

