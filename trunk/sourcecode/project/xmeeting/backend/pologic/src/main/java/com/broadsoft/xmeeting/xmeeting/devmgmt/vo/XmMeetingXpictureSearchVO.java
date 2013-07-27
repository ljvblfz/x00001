/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.vo;

/** no table comments对应页面查询VO */
public class XmMeetingXpictureSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmxpicGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 图片主题PID */
	public String xmmpicGuid;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmxpicGuid() {
		return this.xmmxpicGuid;
	}
	/**
	 * 设置PID
	 * @param xmmxpicGuid PID
	 */
	public void setXmmxpicGuid(String xmmxpicGuid) {
		this.xmmxpicGuid = xmmxpicGuid;
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
	 * 取得图片主题PID
	 * return 图片主题PID
	 */
	public String getXmmpicGuid() {
		return this.xmmpicGuid;
	}
	/**
	 * 设置图片主题PID
	 * @param xmmpicGuid 图片主题PID
	 */
	public void setXmmpicGuid(String xmmpicGuid) {
		this.xmmpicGuid = xmmpicGuid;
	}
	
	 
 
}

