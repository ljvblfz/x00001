/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.page.vo;

/** no table comments对应页面查询VO */
public class SyWidgetPageRegionSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String sprguid;
	/** 页面PID */
	public String spguid;
	/** 区域列数 */
	public String sprColumn;
	/** 区域行数 */
	public String sprRow;
	/** 区块PID */
	public String swbguid;
	/** 区域描述 */
	public String description;
	/** 区域状态 */
	public String status;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getSprguid() {
		return this.sprguid;
	}
	/**
	 * 设置PID
	 * @param sprguid PID
	 */
	public void setSprguid(String sprguid) {
		this.sprguid = sprguid;
	}
	/**
	 * 取得页面PID
	 * return 页面PID
	 */
	public String getSpguid() {
		return this.spguid;
	}
	/**
	 * 设置页面PID
	 * @param spguid 页面PID
	 */
	public void setSpguid(String spguid) {
		this.spguid = spguid;
	}
	/**
	 * 取得区域列数
	 * return 区域列数
	 */
	public String getSprColumn() {
		return this.sprColumn;
	}
	/**
	 * 设置区域列数
	 * @param sprColumn 区域列数
	 */
	public void setSprColumn(String sprColumn) {
		this.sprColumn = sprColumn;
	}
	/**
	 * 取得区域行数
	 * return 区域行数
	 */
	public String getSprRow() {
		return this.sprRow;
	}
	/**
	 * 设置区域行数
	 * @param sprRow 区域行数
	 */
	public void setSprRow(String sprRow) {
		this.sprRow = sprRow;
	}
	/**
	 * 取得区块PID
	 * return 区块PID
	 */
	public String getSwbguid() {
		return this.swbguid;
	}
	/**
	 * 设置区块PID
	 * @param swbguid 区块PID
	 */
	public void setSwbguid(String swbguid) {
		this.swbguid = swbguid;
	}
	/**
	 * 取得区域描述
	 * return 区域描述
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * 设置区域描述
	 * @param description 区域描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 取得区域状态
	 * return 区域状态
	 */
	public String getStatus() {
		return this.status;
	}
	/**
	 * 设置区域状态
	 * @param status 区域状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	 
 
}

