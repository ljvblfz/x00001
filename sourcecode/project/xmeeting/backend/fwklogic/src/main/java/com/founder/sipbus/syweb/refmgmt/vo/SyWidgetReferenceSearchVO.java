/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.refmgmt.vo;

/** no table comments对应页面查询VO */
public class SyWidgetReferenceSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String swrGuid;
	/** CODE */
	public String swrCode;
	/** 名称 */
	public String swrName;
	/** 描述 */
	public String description;
	/** 控件类型 */
	public String widgetType;
	/** 数据reference */
	public String referenceValue; 
	/** autocomplete 配置 */
	public String autocompleteConfig;
	public String groupname;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getSwrGuid() {
		return this.swrGuid;
	}
	/**
	 * 设置PID
	 * @param swrGuid PID
	 */
	public void setSwrGuid(String swrGuid) {
		this.swrGuid = swrGuid;
	}
	/**
	 * 取得CODE
	 * return CODE
	 */
	public String getSwrCode() {
		return this.swrCode;
	}
	/**
	 * 设置CODE
	 * @param swrCode CODE
	 */
	public void setSwrCode(String swrCode) {
		this.swrCode = swrCode;
	}
	/**
	 * 取得名称
	 * return 名称
	 */
	public String getSwrName() {
		return this.swrName;
	}
	/**
	 * 设置名称
	 * @param swrName 名称
	 */
	public void setSwrName(String swrName) {
		this.swrName = swrName;
	}
	/**
	 * 取得描述
	 * return 描述
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * 设置描述
	 * @param description 描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 取得控件类型
	 * return 控件类型
	 */
	public String getWidgetType() {
		return this.widgetType;
	}
	/**
	 * 设置控件类型
	 * @param widgetType 控件类型
	 */
	public void setWidgetType(String widgetType) {
		this.widgetType = widgetType;
	}
	/**
	 * 取得数据reference
	 * return 数据reference
	 */
	public String getReferenceValue() {
		return this.referenceValue;
	}
	/**
	 * 设置数据reference
	 * @param referenceValue 数据reference
	 */
	public void setReferenceValue(String referenceValue) {
		this.referenceValue = referenceValue;
	}
 
	/**
	 * 取得autocomplete 配置
	 * return autocomplete 配置
	 */
	public String getAutocompleteConfig() {
		return this.autocompleteConfig;
	}
	/**
	 * 设置autocomplete 配置
	 * @param autocompleteConfig autocomplete 配置
	 */
	public void setAutocompleteConfig(String autocompleteConfig) {
		this.autocompleteConfig = autocompleteConfig;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
	 
 
}

