/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.vo;

/** no table comments对应页面查询VO */
public class XmMeetingContactSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmcGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 联系方式 */
	public String xmmcDescription;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmcGuid() {
		return this.xmmcGuid;
	}
	/**
	 * 设置PID
	 * @param xmmcGuid PID
	 */
	public void setXmmcGuid(String xmmcGuid) {
		this.xmmcGuid = xmmcGuid;
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
	/**
	 * 取得联系方式
	 * return 联系方式
	 */
	public String getXmmcDescription() {
		return this.xmmcDescription;
	}
	/**
	 * 设置联系方式
	 * @param xmmcDescription 联系方式
	 */
	public void setXmmcDescription(String xmmcDescription) {
		this.xmmcDescription = xmmcDescription;
	}
	
	 
 
}

