/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.codeprinciple.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

@Entity
@Table(name = "UTIL_CODE_PRINCIPLE_DETAIL")

public class UtilCodePrincipleDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String UCPD_GUID= "ucpdGuid";
	public static String UCP_GUID= "ucpGuid";
	public static String UCP_CODE= "ucpCode";
	public static String UCPD_NAME= "ucpdName";
	public static String UCPD_CODE= "ucpdCode";
	public static String UCPD_CODE_LENGTH= "ucpdCodeLength";
	public static String UCPD_COMMENT= "ucpdComment";
	public static String UCPD_TYPE= "ucpdType";
	public static String UCPD_START= "ucpdStart";
	public static String UCPD_END= "ucpdEnd";
	public static String UCPD_FORMART= "ucpdFormat";
	public static String UCPD_ORDER= "ucpdOrder";
	public static String UCPD_USE_TYPE= "ucpdUseType";
	 
	public static String UCPD_FIELD_NAME= "ucpdFieldName";
	public static String UCPD_FIELD_ID= "ucpdFieldId";
	//primary key
	/**
	 * PID
	 */
	private java.lang.String ucpdGuid;

	/**
	 * 原则ID
	 */
	private java.lang.String ucpGuid;
	/**
	 * 原则代号
	 */
	private java.lang.String ucpCode;
	/**
	 * 原则详细名称
	 */
	private java.lang.String ucpdName;
	/**
	 * 原则详细代号
	 */
	private java.lang.String ucpdCode;
	/**
	 * 编码长度
	 */
	private java.lang.String ucpdCodeLength;
	/**
	 * 备注
	 */
	private java.lang.String ucpdComment;
	/**
	 * 编码类型
	 */
	private java.lang.String ucpdType;
	/**
	 * 起始位
	 */
	private java.lang.Integer ucpdStart;
	/**
	 * 终止位
	 */
	private java.lang.Integer ucpdEnd;
	/**
	 * 格式
	 */
	private java.lang.String ucpdFormat;
	/**
	 * 项次
	 */
	private java.lang.Integer ucpdOrder;
	/**
	 * 使用规则类型
	 */
	private java.lang.String ucpdUseType;
 
	public java.lang.String ucpdFieldName;
	

	public java.lang.String ucpdFieldId;
	
	private java.lang.String ucpdFormatName;
	 
	public UtilCodePrincipleDetail(){
	}

	public UtilCodePrincipleDetail(java.lang.String ucpdGuid ){
		this.ucpdGuid = ucpdGuid;
	}


	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getUcpdGuid() {
		return this.ucpdGuid;
	}
	
	public void setUcpdGuid(java.lang.String value) {
		this.ucpdGuid = value;
	}
	public java.lang.String getUcpGuid() {
		return this.ucpGuid;
	}
	
	public void setUcpGuid(java.lang.String value) {
		this.ucpGuid = value;
	}
	public java.lang.String getUcpCode() {
		return this.ucpCode;
	}
	
	public void setUcpCode(java.lang.String value) {
		this.ucpCode = value;
	}
	public java.lang.String getUcpdName() {
		return this.ucpdName;
	}
	
	public void setUcpdName(java.lang.String value) {
		this.ucpdName = value;
	}
	public java.lang.String getUcpdCode() {
		return this.ucpdCode;
	}
	
	public void setUcpdCode(java.lang.String value) {
		this.ucpdCode = value;
	}
	public java.lang.String getUcpdCodeLength() {
		return this.ucpdCodeLength;
	}
	
	public void setUcpdCodeLength(java.lang.String value) {
		this.ucpdCodeLength = value;
	}
	public java.lang.String getUcpdComment() {
		return this.ucpdComment;
	}
	
	public void setUcpdComment(java.lang.String value) {
		this.ucpdComment = value;
	}
	public java.lang.String getUcpdType() {
		return this.ucpdType;
	}
	
	public void setUcpdType(java.lang.String value) {
		this.ucpdType = value;
	}
	public java.lang.Integer getUcpdStart() {
		return this.ucpdStart;
	}
	
	public void setUcpdStart(java.lang.Integer value) {
		this.ucpdStart = value;
	}
	public java.lang.Integer getUcpdEnd() {
		return this.ucpdEnd;
	}
	
	public void setUcpdEnd(java.lang.Integer value) {
		this.ucpdEnd = value;
	}
 
	public java.lang.Integer getUcpdOrder() {
		return this.ucpdOrder;
	}
	
	public void setUcpdOrder(java.lang.Integer value) {
		this.ucpdOrder = value;
	}
	public java.lang.String getUcpdUseType() {
		return this.ucpdUseType;
	}
	
	public void setUcpdUseType(java.lang.String value) {
		this.ucpdUseType = value;
	}
 
	public java.lang.String getUcpdFieldName() {
		return ucpdFieldName;
	}

	public void setUcpdFieldName(java.lang.String ucpdFieldName) {
		this.ucpdFieldName = ucpdFieldName;
	}

	public java.lang.String getUcpdFieldId() {
		return ucpdFieldId;
	}

	public void setUcpdFieldId(java.lang.String ucpdFieldId) {
		this.ucpdFieldId = ucpdFieldId;
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
			UtilCodePrincipleDetail castObj = (UtilCodePrincipleDetail) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getUcpdGuid(), castObj.getUcpdGuid());
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
			UtilCodePrincipleDetail castObj = (UtilCodePrincipleDetail) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getUcpdGuid(), castObj.getUcpdGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}

	public java.lang.String getUcpdFormat() {
		return ucpdFormat;
	}

	public void setUcpdFormat(java.lang.String ucpdFormat) {
		this.ucpdFormat = ucpdFormat;
	}
	@Transient
	public java.lang.String getUcpdFormatName() {
		return ucpdFormatName;
	}

	public void setUcpdFormatName(java.lang.String ucpdFormatName) {
		this.ucpdFormatName = ucpdFormatName;
	}
}

