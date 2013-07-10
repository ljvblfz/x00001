/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.vo;

/** no table comments对应页面查询VO */
public class XmMeetingMmsHistoryLogSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmmhlGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 发件人 */
	public String xmmmhlFrom;
	/** 收件人 */
	public String xmmmhlTo;
	/** 发送状态 */
	public String xmmmhlStatus;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmmhlGuid() {
		return this.xmmmhlGuid;
	}
	/**
	 * 设置PID
	 * @param xmmmhlGuid PID
	 */
	public void setXmmmhlGuid(String xmmmhlGuid) {
		this.xmmmhlGuid = xmmmhlGuid;
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
	 * 取得发件人
	 * return 发件人
	 */
	public String getXmmmhlFrom() {
		return this.xmmmhlFrom;
	}
	/**
	 * 设置发件人
	 * @param xmmmhlFrom 发件人
	 */
	public void setXmmmhlFrom(String xmmmhlFrom) {
		this.xmmmhlFrom = xmmmhlFrom;
	}
	/**
	 * 取得收件人
	 * return 收件人
	 */
	public String getXmmmhlTo() {
		return this.xmmmhlTo;
	}
	/**
	 * 设置收件人
	 * @param xmmmhlTo 收件人
	 */
	public void setXmmmhlTo(String xmmmhlTo) {
		this.xmmmhlTo = xmmmhlTo;
	}
	/**
	 * 取得发送状态
	 * return 发送状态
	 */
	public String getXmmmhlStatus() {
		return this.xmmmhlStatus;
	}
	/**
	 * 设置发送状态
	 * @param xmmmhlStatus 发送状态
	 */
	public void setXmmmhlStatus(String xmmmhlStatus) {
		this.xmmmhlStatus = xmmmhlStatus;
	}
	
	 
 
}

