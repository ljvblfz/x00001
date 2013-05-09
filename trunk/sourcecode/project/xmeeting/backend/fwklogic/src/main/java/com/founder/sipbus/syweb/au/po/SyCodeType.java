/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.au.po;

import java.util.List;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

@Entity
@Table(name = "SY_CODE_TYPE")

public class SyCodeType extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String TYPE_ID= "typeId";
	public static String TYPE_NAME= "typeName";
	public static String TYPE_DESC= "typeDesc";
	public static String DOMAIN= "domain";
	public static String RED_ONY_FLG= "redOnyFlg";
	public static String DEL_FLAG= "delFlag";

	//primary key
	/**
	 * 主键
	 */
	private String typeId;

	/**
	 * 代码类别
	 */
	private java.lang.String typeName;
	/**
	 * 描述
	 */
	private java.lang.String typeDesc;
	/**
	 * 
	 */
	private java.lang.String domain;
	
	private String domainStr;
	/**
	 * 只读标记
	 */
	private java.lang.String redOnyFlg;
	
	private String redOnlyFlaStr;


	private List<SyCode> syCodeList;
	
	@Transient
	public List<SyCode> getSyCodeList() {
		return syCodeList;
	}

	public void setSyCodeList(List<SyCode> syCodeList) {
		this.syCodeList = syCodeList;
	}

	public SyCodeType(){
	}

	public SyCodeType( String typeId ){
		this.typeId = typeId;
	}


	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public String getTypeId() {
		return this.typeId;
	}
	
	public void setTypeId( String value) {
		this.typeId = value;
	}
	public java.lang.String getTypeName() {
		return this.typeName;
	}
	
	public void setTypeName(java.lang.String value) {
		this.typeName = value;
	}
	public java.lang.String getTypeDesc() {
		return this.typeDesc;
	}
	
	public void setTypeDesc(java.lang.String value) {
		this.typeDesc = value;
	}
	public java.lang.String getDomain() {
		return this.domain;
	}
	
	public void setDomain(java.lang.String value) {
		this.domain = value;
	}
	public java.lang.String getRedOnyFlg() {
		return this.redOnyFlg;
	}
	
	public void setRedOnyFlg(java.lang.String value) {
		this.redOnyFlg = value;
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
			SyCodeType castObj = (SyCodeType) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getTypeId(), castObj.getTypeId());
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
			SyCodeType castObj = (SyCodeType) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getTypeId(), castObj.getTypeId());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}

	@Transient
	public String getRedOnlyFlaStr() {
		return redOnlyFlaStr;
	}

	public void setRedOnlyFlaStr(String redOnlyFlaStr) {
		this.redOnlyFlaStr = redOnlyFlaStr;
	}
	
	@Transient
	public String getDomainStr() {
		return domainStr;
	}

	public void setDomainStr(String domainStr) {
		this.domainStr = domainStr;
	}
}

