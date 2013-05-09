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
@Table(name = "UTIL_CODE_PRINCIPLE")
public class UtilCodePrinciple extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String UCP_GUID = "ucpGuid";
	public static String UCP_NAME = "ucpName";
	public static String UCP_CODE = "ucpCode";
	public static String UCP_CODE_LENGTH = "ucpCodeLength";
	public static String UCP_COMMENT = "ucpComment";
	public static String UCP_TYPE = "ucpType";

	// primary key
	/**
	 * PID
	 */
	private java.lang.String ucpGuid;

	/**
	 * 原则名称
	 */
	private java.lang.String ucpName;
	/**
	 * 编码代号
	 */
	public java.lang.String ucpCode;

	/**
	 * 编码长度
	 */
	private java.lang.String ucpCodeLength;
	/**
	 * 备注
	 */
	private java.lang.String ucpComment;
	/**
	 * 编码类型
	 */
	private java.lang.String ucpType;
	public java.lang.String ucpTypeName;
	
	
	
	@Transient
	public java.lang.String getUcpTypeName() {
		return ucpTypeName;
	}

	public void setUcpTypeName(java.lang.String ucpTypeName) {
		this.ucpTypeName = ucpTypeName;
	}

	 
	public UtilCodePrinciple() {
	}

	public UtilCodePrinciple(java.lang.String ucpGuid) {
		this.ucpGuid = ucpGuid;
	}

	@Id
	@GeneratedValue(generator = "trustIdGenerator")
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")
	public java.lang.String getUcpGuid() {
		return this.ucpGuid;
	}

	public void setUcpGuid(java.lang.String value) {
		this.ucpGuid = value;
	}

	public java.lang.String getUcpName() {
		return this.ucpName;
	}

	public void setUcpName(java.lang.String value) {
		this.ucpName = value;
	}

	public java.lang.String getUcpCode() {
		return ucpCode;
	}

	public void setUcpCode(java.lang.String ucpCode) {
		this.ucpCode = ucpCode;
	}

	public java.lang.String getUcpCodeLength() {
		return this.ucpCodeLength;
	}

	public void setUcpCodeLength(java.lang.String value) {
		this.ucpCodeLength = value;
	}

	public java.lang.String getUcpComment() {
		return this.ucpComment;
	}

	public void setUcpComment(java.lang.String value) {
		this.ucpComment = value;
	}

	public java.lang.String getUcpType() {
		return this.ucpType;
	}

	public void setUcpType(java.lang.String value) {
		this.ucpType = value;
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
			UtilCodePrinciple castObj = (UtilCodePrinciple) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getUcpGuid(), castObj.getUcpGuid());
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
			UtilCodePrinciple castObj = (UtilCodePrinciple) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getUcpGuid(), castObj.getUcpGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}
