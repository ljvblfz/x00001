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
@Table(name = "SY_CODE")
public class SyCode extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String CODE_ID = "codeId";
	public static String TYPE_ID = "typeId";
	public static String VALUE_DESC = "valueDesc";
	public static String VALUE_CODE = "valueCode";
	public static String VALUE_NAME = "valueName";
	public static String CODE_SEQ = "codeSeq";
	public static String STATUS = "status";

	// primary key
	/**
	 * 主键
	 */
	private String codeId;

	/**
	 * 参考代码
	 */
	private String typeId;
	/**
	 * 选项描述
	 */
	private java.lang.String valueDesc;
	/**
	 * 选项值
	 */
	private java.lang.String valueCode;
	/**
	 * 选项值
	 */
	private java.lang.String valueName;
	/**
	 * 排序
	 */
	private java.lang.Short codeSeq;
	/**
	 * 状态
	 */
	private java.lang.String status;

	public SyCode() {
	}

	public SyCode(String codeId) {
		this.codeId = codeId;
	}

	@Id
	@GeneratedValue(generator = "trustIdGenerator")
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")
	public String getCodeId() {
		return this.codeId;
	}

	public void setCodeId(String value) {
		this.codeId = value;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public void setTypeId(String value) {
		this.typeId = value;
	}

	public java.lang.String getValueDesc() {
		return this.valueDesc;
	}

	public void setValueDesc(java.lang.String value) {
		this.valueDesc = value;
	}

	public java.lang.String getValueCode() {
		return this.valueCode;
	}

	public void setValueCode(java.lang.String value) {
		this.valueCode = value;
	}
	
	public java.lang.String getValueName() {
		return valueName;
	}

	public void setValueName(java.lang.String valueName) {
		this.valueName = valueName;
	}
	
	public java.lang.Short getCodeSeq() {
		return this.codeSeq;
	}

	public void setCodeSeq(java.lang.Short value) {
		this.codeSeq = value;
	}

	public java.lang.String getStatus() {
		return this.status;
	}

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
			SyCode castObj = (SyCode) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getCodeId(), castObj.getCodeId());
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
			SyCode castObj = (SyCode) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getCodeId(), castObj.getCodeId());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}
