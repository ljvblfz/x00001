/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.vo;

/** no table comments对应页面查询VO */
public class XmMeetingVideoSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmvGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 视频名称 */
	public String xmmvName;
	/** 视频描述 */
	public String xmmvDescription;
	/** 视频文件 */
	public String xmmvFile;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmvGuid() {
		return this.xmmvGuid;
	}
	/**
	 * 设置PID
	 * @param xmmvGuid PID
	 */
	public void setXmmvGuid(String xmmvGuid) {
		this.xmmvGuid = xmmvGuid;
	}
	/**
	 * 取得会议PID
	 * return 会议PID
	 */
	public String getXmmiGuid() {
		return this.xmmiGuid;
	}
	/**
	 * 设置会议PID
	 * @param xmmiGuid 会议PID
	 */
	public void setXmmiGuid(String xmmiGuid) {
		this.xmmiGuid = xmmiGuid;
	}
	public String getXmmvName() {
		return xmmvName;
	}
	public void setXmmvName(String xmmvName) {
		this.xmmvName = xmmvName;
	}
	public String getXmmvDescription() {
		return xmmvDescription;
	}
	public void setXmmvDescription(String xmmvDescription) {
		this.xmmvDescription = xmmvDescription;
	}
	public String getXmmvFile() {
		return xmmvFile;
	}
	public void setXmmvFile(String xmmvFile) {
		this.xmmvFile = xmmvFile;
	}
	 
	
	 
 
}

