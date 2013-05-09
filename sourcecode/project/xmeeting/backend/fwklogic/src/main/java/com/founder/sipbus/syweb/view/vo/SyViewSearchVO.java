/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.view.vo;

/** no table comments对应页面查询VO */
public class SyViewSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String svGuid;
	/** 视图名称 */
	public String name;
	/** sql text */
	public String sqlSource;
	/** 描述 */
	public String description;
	/** 状态 */
	public String status;
	/** 视图分组 */
	public String groupName;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getSvGuid() {
		return this.svGuid;
	}
	/**
	 * 设置PID
	 * @param svGuid PID
	 */
	public void setSvGuid(String svGuid) {
		this.svGuid = svGuid;
	}
	/**
	 * 取得视图名称
	 * return 视图名称
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * 设置视图名称
	 * @param name 视图名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 取得sql text
	 * return sql text
	 */
	public String getSqlSource() {
		return this.sqlSource;
	}
	/**
	 * 设置sql text
	 * @param sqlSource sql text
	 */
	public void setSqlSource(String sqlSource) {
		this.sqlSource = sqlSource;
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
	 * 取得状态
	 * return 状态
	 */
	public String getStatus() {
		return this.status;
	}
	/**
	 * 设置状态
	 * @param status 状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 取得视图分组
	 * return 视图分组
	 */
	public String getGroupName() {
		return this.groupName;
	}
	/**
	 * 设置视图分组
	 * @param groupName 视图分组
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	 
 
}

