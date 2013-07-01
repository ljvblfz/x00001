/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.basic.vo;

/** no table comments对应页面查询VO */
public class XmVideoInfoSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmviGuid;
	/** 视频名称 */
	public String xmviName;
	/** 视频描述 */
	public String xmviDescription;
	/** 视频文件 */
	public String xmviFile;
	/** 视频状态 */
	public String xmviStatus;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmviGuid() {
		return this.xmviGuid;
	}
	/**
	 * 设置PID
	 * @param xmviGuid PID
	 */
	public void setXmviGuid(String xmviGuid) {
		this.xmviGuid = xmviGuid;
	}
	/**
	 * 取得视频名称
	 * return 视频名称
	 */
	public String getXmviName() {
		return this.xmviName;
	}
	/**
	 * 设置视频名称
	 * @param xmviName 视频名称
	 */
	public void setXmviName(String xmviName) {
		this.xmviName = xmviName;
	}
	/**
	 * 取得视频描述
	 * return 视频描述
	 */
	public String getXmviDescription() {
		return this.xmviDescription;
	}
	/**
	 * 设置视频描述
	 * @param xmviDescription 视频描述
	 */
	public void setXmviDescription(String xmviDescription) {
		this.xmviDescription = xmviDescription;
	}
	/**
	 * 取得视频文件
	 * return 视频文件
	 */
	public String getXmviFile() {
		return this.xmviFile;
	}
	/**
	 * 设置视频文件
	 * @param xmviFile 视频文件
	 */
	public void setXmviFile(String xmviFile) {
		this.xmviFile = xmviFile;
	}
	/**
	 * 取得视频状态
	 * return 视频状态
	 */
	public String getXmviStatus() {
		return this.xmviStatus;
	}
	/**
	 * 设置视频状态
	 * @param xmviStatus 视频状态
	 */
	public void setXmviStatus(String xmviStatus) {
		this.xmviStatus = xmviStatus;
	}
	
	 
 
}

