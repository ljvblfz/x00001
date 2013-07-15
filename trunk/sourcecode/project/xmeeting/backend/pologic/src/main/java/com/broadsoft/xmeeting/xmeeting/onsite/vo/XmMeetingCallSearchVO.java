/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.onsite.vo;

/** no table comments对应页面查询VO */
public class XmMeetingCallSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmcallGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 呼叫者 */
	public String xmmcallCaller;
	/** 呼叫内容 */
	public String xmmcallMessage;
	/** 状态 */
	private java.lang.String xmmcallStatus; 
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmcallGuid() {
		return this.xmmcallGuid;
	}
	/**
	 * 设置PID
	 * @param xmmcallGuid PID
	 */
	public void setXmmcallGuid(String xmmcallGuid) {
		this.xmmcallGuid = xmmcallGuid;
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
	 * 取得呼叫者
	 * return 呼叫者
	 */
	public String getXmmcallCaller() {
		return this.xmmcallCaller;
	}
	/**
	 * 设置呼叫者
	 * @param xmmcallCaller 呼叫者
	 */
	public void setXmmcallCaller(String xmmcallCaller) {
		this.xmmcallCaller = xmmcallCaller;
	}
	/**
	 * 取得呼叫内容
	 * return 呼叫内容
	 */
	public String getXmmcallMessage() {
		return this.xmmcallMessage;
	}
	/**
	 * 设置呼叫内容
	 * @param xmmcallMessage 呼叫内容
	 */
	public void setXmmcallMessage(String xmmcallMessage) {
		this.xmmcallMessage = xmmcallMessage;
	}
	public java.lang.String getXmmcallStatus() {
		return xmmcallStatus;
	}
	public void setXmmcallStatus(java.lang.String xmmcallStatus) {
		this.xmmcallStatus = xmmcallStatus;
	}
	
	 
 
}

