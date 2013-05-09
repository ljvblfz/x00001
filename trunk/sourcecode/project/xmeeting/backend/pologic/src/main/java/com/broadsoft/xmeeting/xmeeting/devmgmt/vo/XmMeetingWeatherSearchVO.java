/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.vo;

/** no table comments对应页面查询VO */
public class XmMeetingWeatherSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmwGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 天气信息 */
	public String xmmwDescription;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmwGuid() {
		return this.xmmwGuid;
	}
	/**
	 * 设置PID
	 * @param xmmwGuid PID
	 */
	public void setXmmwGuid(String xmmwGuid) {
		this.xmmwGuid = xmmwGuid;
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
	 * 取得天气信息
	 * return 天气信息
	 */
	public String getXmmwDescription() {
		return this.xmmwDescription;
	}
	/**
	 * 设置天气信息
	 * @param xmmwDescription 天气信息
	 */
	public void setXmmwDescription(String xmmwDescription) {
		this.xmmwDescription = xmmwDescription;
	}
	
	 
 
}

