/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.vo;

/** no table comments对应页面查询VO */
public class XmMeetingInfoSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmiGuid;
	/** 会议室 PID*/
	private java.lang.String xmriGuid;
	/** 会议名称 */
	public String xmmiName;
	/** 会议介绍 */
	public String xmmiDescription;
	/** 会议状态 */
	public String xmmiStatus;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmiGuid() {
		return this.xmmiGuid;
	}
	/**
	 * 设置PID
	 * @param xmmiGuid PID
	 */
	public void setXmmiGuid(String xmmiGuid) {
		this.xmmiGuid = xmmiGuid;
	}
	/**
	 * 取得会议名称
	 * return 会议名称
	 */
	public String getXmmiName() {
		return this.xmmiName;
	}
	/**
	 * 设置会议名称
	 * @param xmmiName 会议名称
	 */
	public void setXmmiName(String xmmiName) {
		this.xmmiName = xmmiName;
	}
	/**
	 * 取得会议介绍
	 * return 会议介绍
	 */
	public String getXmmiDescription() {
		return this.xmmiDescription;
	}
	/**
	 * 设置会议介绍
	 * @param xmmiDescription 会议介绍
	 */
	public void setXmmiDescription(String xmmiDescription) {
		this.xmmiDescription = xmmiDescription;
	}
	/**
	 * 取得会议状态
	 * return 会议状态
	 */
	public String getXmmiStatus() {
		return this.xmmiStatus;
	}
	/**
	 * 设置会议状态
	 * @param xmmiStatus 会议状态
	 */
	public void setXmmiStatus(String xmmiStatus) {
		this.xmmiStatus = xmmiStatus;
	}
	public java.lang.String getXmriGuid() {
		return xmriGuid;
	}
	public void setXmriGuid(java.lang.String xmriGuid) {
		this.xmriGuid = xmriGuid;
	}
	
	 
 
}

