/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.view.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.objectweb.carol.jndi.enc.java.javaURLContextFactory;

import com.founder.sipbus.common.po.BaseEntity;

/** no table comments对应实体类 */
@Entity
@Table(name = "SY_VIEW_FIELD")
public class SyViewField extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String SVF_GUID= "svfGuid";
	public static String SV_GUID= "svGuid";
	public static String FIELD_DESCRIPTION= "fieldDescription";
	public static String FIELD_LABEL= "fieldLabel";
	public static String FIELD_COLUMN= "fieldColumn";
	public static String FIELD_COLUMN_ALIAS= "fieldColumnAlias";
	public static String FIELD_TYPE= "fieldType"; 
	public static String FIELD_CATEGORY ="fieldCategory"; 
	public static String FIELD_TYPE_REFERENCE = "fieldTypeReference";
	private java.lang.String fieldOrder;
	private java.lang.String fieldTypeReference;
	//primary key
	/** PID */
	private java.lang.String svfGuid;
	/** SY_VIEW */
	private java.lang.String svGuid;
	/** 描述 */
	private java.lang.String fieldDescription;
	/** 名称 */
	private java.lang.String fieldLabel;
	/** db column  */
	private java.lang.String fieldColumn;
	/** db column alias */
	private java.lang.String fieldColumnAlias;
	/** 类型 */
	private java.lang.String fieldType; 
	/** 类型 */
	private java.lang.String fieldCategory; 

	

	public SyViewField(){
	}

	public SyViewField(java.lang.String svfGuid ){
		this.svfGuid = svfGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getSvfGuid() {
		return this.svfGuid;
	}
	
	/**
	 * 设置PID
	 * @param svfGuid PID
	 */
	public void setSvfGuid(java.lang.String svfGuid) {
		this.svfGuid = svfGuid;
	}
		/**
		 * 取得SY_VIEW
		 * return SY_VIEW
		 */
	public java.lang.String getSvGuid() {
		return this.svGuid;
	}
	
	/**
	 * 设置SY_VIEW
	 * @param svGuid SY_VIEW
	 */
	public void setSvGuid(java.lang.String value) {
		this.svGuid = value;
	}
		/**
		 * 取得描述
		 * return 描述
		 */
	public java.lang.String getFieldDescription() {
		return this.fieldDescription;
	}
	
	/**
	 * 设置描述
	 * @param fieldDescription 描述
	 */
	public void setFieldDescription(java.lang.String value) {
		this.fieldDescription = value;
	}
		/**
		 * 取得名称
		 * return 名称
		 */
	public java.lang.String getFieldLabel() {
		return this.fieldLabel;
	}
	
	/**
	 * 设置名称
	 * @param fieldLabel 名称
	 */
	public void setFieldLabel(java.lang.String value) {
		this.fieldLabel = value;
	}
		/**
		 * 取得db column 
		 * return db column 
		 */
	public java.lang.String getFieldColumn() {
		return this.fieldColumn;
	}
	
	/**
	 * 设置db column 
	 * @param fieldColumn db column 
	 */
	public void setFieldColumn(java.lang.String value) {
		this.fieldColumn = value;
	}
		/**
		 * 取得db column alias
		 * return db column alias
		 */
	public java.lang.String getFieldColumnAlias() {
		return this.fieldColumnAlias;
	}
	
	/**
	 * 设置db column alias
	 * @param fieldColumnAlias db column alias
	 */
	public void setFieldColumnAlias(java.lang.String value) {
		this.fieldColumnAlias = value;
	}
		/**
		 * 取得类型
		 * return 类型
		 */
	public java.lang.String getFieldType() {
		return this.fieldType;
	}
	
	/**
	 * 设置类型
	 * @param fieldType 类型
	 */
	public void setFieldType(java.lang.String value) {
		this.fieldType = value;
	}
	 

	public java.lang.String getFieldCategory() {
		return fieldCategory;
	}

	public void setFieldCategory(java.lang.String fieldCategory) {
		this.fieldCategory = fieldCategory;
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
			SyViewField castObj = (SyViewField) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getSvfGuid(), castObj.getSvfGuid());
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
			SyViewField castObj = (SyViewField) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getSvfGuid(), castObj.getSvfGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}

	public java.lang.String getFieldTypeReference() {
		return fieldTypeReference;
	}

	public void setFieldTypeReference(java.lang.String fieldTypeReference) {
		this.fieldTypeReference = fieldTypeReference;
	}

	public java.lang.String getFieldOrder() {
		return fieldOrder;
	}

	public void setFieldOrder(java.lang.String fieldOrder) {
		this.fieldOrder = fieldOrder;
	}
}

