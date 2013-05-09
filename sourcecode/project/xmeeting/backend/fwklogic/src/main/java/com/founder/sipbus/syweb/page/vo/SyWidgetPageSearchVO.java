/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.page.vo;

/** no table comments对应页面查询VO */
public class SyWidgetPageSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String spguid;
	/** 页面名称 */
	public String name;
	/** 页面类型 */
	public String sptype;
	/** 页面内容 */
	public String pagesource;
	/** 页面描述 */
	public String description;
	/** 页面状态 */
	public String status;
	/** 页面分组 */
	public String groupname;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getSpguid() {
		return this.spguid;
	}
	/**
	 * 设置PID
	 * @param spguid PID
	 */
	public void setSpguid(String spguid) {
		this.spguid = spguid;
	}
	/**
	 * 取得页面名称
	 * return 页面名称
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * 设置页面名称
	 * @param name 页面名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 取得页面类型
	 * return 页面类型
	 */
	public String getSptype() {
		return this.sptype;
	}
	/**
	 * 设置页面类型
	 * @param sptype 页面类型
	 */
	public void setSptype(String sptype) {
		this.sptype = sptype;
	}
	/**
	 * 取得页面内容
	 * return 页面内容
	 */
	public String getPagesource() {
		return this.pagesource;
	}
	/**
	 * 设置页面内容
	 * @param pagesource 页面内容
	 */
	public void setPagesource(String pagesource) {
		this.pagesource = pagesource;
	}
	/**
	 * 取得页面描述
	 * return 页面描述
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * 设置页面描述
	 * @param description 页面描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 取得页面状态
	 * return 页面状态
	 */
	public String getStatus() {
		return this.status;
	}
	/**
	 * 设置页面状态
	 * @param status 页面状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 取得页面分组
	 * return 页面分组
	 */
	public String getGroupname() {
		return this.groupname;
	}
	/**
	 * 设置页面分组
	 * @param groupname 页面分组
	 */
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
	 
 
}

