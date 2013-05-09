/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.view.vo;

/** no table comments对应页面查询VO */
public class SyViewFieldSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String svfGuid;
	/** SY_VIEW */
	public String svGuid;
	/** 描述 */
	public String fieldDescription;
	/** 名称 */
	public String fieldLabel;
	/** db column  */
	public String fieldColumn;
	/** db column alias */
	public String fieldColumnAlias;
	/** 类型 */
	public String fieldType;
	/** 类型 */
	public String fieldCategory;
	public String fieldTypeReference;
	public String fieldOrder;
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getSvfGuid() {
		return this.svfGuid;
	}
	/**
	 * 设置PID
	 * @param svfGuid PID
	 */
	public void setSvfGuid(String svfGuid) {
		this.svfGuid = svfGuid;
	}
	/**
	 * 取得SY_VIEW
	 * return SY_VIEW
	 */
	public String getSvGuid() {
		return this.svGuid;
	}
	/**
	 * 设置SY_VIEW
	 * @param svGuid SY_VIEW
	 */
	public void setSvGuid(String svGuid) {
		this.svGuid = svGuid;
	}
	/**
	 * 取得描述
	 * return 描述
	 */
	public String getFieldDescription() {
		return this.fieldDescription;
	}
	/**
	 * 设置描述
	 * @param fieldDescription 描述
	 */
	public void setFieldDescription(String fieldDescription) {
		this.fieldDescription = fieldDescription;
	}
	/**
	 * 取得名称
	 * return 名称
	 */
	public String getFieldLabel() {
		return this.fieldLabel;
	}
	/**
	 * 设置名称
	 * @param fieldLabel 名称
	 */
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	/**
	 * 取得db column 
	 * return db column 
	 */
	public String getFieldColumn() {
		return this.fieldColumn;
	}
	/**
	 * 设置db column 
	 * @param fieldColumn db column 
	 */
	public void setFieldColumn(String fieldColumn) {
		this.fieldColumn = fieldColumn;
	}
	/**
	 * 取得db column alias
	 * return db column alias
	 */
	public String getFieldColumnAlias() {
		return this.fieldColumnAlias;
	}
	/**
	 * 设置db column alias
	 * @param fieldColumnAlias db column alias
	 */
	public void setFieldColumnAlias(String fieldColumnAlias) {
		this.fieldColumnAlias = fieldColumnAlias;
	}
	/**
	 * 取得类型
	 * return 类型
	 */
	public String getFieldType() {
		return this.fieldType;
	}
	/**
	 * 设置类型
	 * @param fieldType 类型
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public String getFieldCategory() {
		return fieldCategory;
	}
	public void setFieldCategory(String fieldCategory) {
		this.fieldCategory = fieldCategory;
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

