/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.block.vo;

/** no table comments对应页面查询VO */
public class SyWidgetBlockSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String spguid;
	/** 区块名称 */
	public String name;
	/** 区块类型 */
	public String sptype;
	/** 区块内容 */
	public String pagesource;
	/** 区块描述 */
	public String description;
	/** 区块状态 */
	public String status;
	/** 区块分组 */
	public String groupname;
	/**  */
	public String title;
	
	
	

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
	 * 取得区块名称
	 * return 区块名称
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * 设置区块名称
	 * @param name 区块名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 取得区块类型
	 * return 区块类型
	 */
	public String getSptype() {
		return this.sptype;
	}
	/**
	 * 设置区块类型
	 * @param sptype 区块类型
	 */
	public void setSptype(String sptype) {
		this.sptype = sptype;
	}
	/**
	 * 取得区块内容
	 * return 区块内容
	 */
	public String getPagesource() {
		return this.pagesource;
	}
	/**
	 * 设置区块内容
	 * @param pagesource 区块内容
	 */
	public void setPagesource(String pagesource) {
		this.pagesource = pagesource;
	}
	/**
	 * 取得区块描述
	 * return 区块描述
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * 设置区块描述
	 * @param description 区块描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 取得区块状态
	 * return 区块状态
	 */
	public String getStatus() {
		return this.status;
	}
	/**
	 * 设置区块状态
	 * @param status 区块状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 取得区块分组
	 * return 区块分组
	 */
	public String getGroupname() {
		return this.groupname;
	}
	/**
	 * 设置区块分组
	 * @param groupname 区块分组
	 */
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	/**
	 * 取得
	 * return 
	 */
	public String getTitle() {
		return this.title;
	}
	/**
	 * 设置
	 * @param title 
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	 
 
}

