/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
package com.broadsoft.xmeeting.xmeeting.onsite.vo;

/** no table comments对应页面查询VO */
public class XmMeetingMessageSearchVO {

	private static final long serialVersionUID = 1L;

	/** PID */
	public String xmmmGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 发送者 */
	public String xmmmFrom;
	private java.lang.String xmmmFromDisplayname;
	/** 接收者 */
	public String xmmmTo;
	private java.lang.String xmmmToDisplayname;
	/** 消息内容 */
	public String xmmmMessage;
	/** 状态 */
	private java.lang.String xmmmStatus;

	/**
	 * 取得PID return PID
	 */
	public String getXmmmGuid() {
		return this.xmmmGuid;
	}

	/**
	 * 设置PID
	 * 
	 * @param xmmmGuid
	 *            PID
	 */
	public void setXmmmGuid(String xmmmGuid) {
		this.xmmmGuid = xmmmGuid;
	}

	/**
	 * 取得会议PID return 会议PID
	 */
	public String getXmmiGuid() {
		return this.xmmiGuid;
	}

	/**
	 * 设置会议PID
	 * 
	 * @param xmmiGuid
	 *            会议PID
	 */
	public void setXmmiGuid(String xmmiGuid) {
		this.xmmiGuid = xmmiGuid;
	}

	/**
	 * 取得发送者 return 发送者
	 */
	public String getXmmmFrom() {
		return this.xmmmFrom;
	}

	/**
	 * 设置发送者
	 * 
	 * @param xmmmFrom
	 *            发送者
	 */
	public void setXmmmFrom(String xmmmFrom) {
		this.xmmmFrom = xmmmFrom;
	}

	/**
	 * 取得接收者 return 接收者
	 */
	public String getXmmmTo() {
		return this.xmmmTo;
	}

	/**
	 * 设置接收者
	 * 
	 * @param xmmmTo
	 *            接收者
	 */
	public void setXmmmTo(String xmmmTo) {
		this.xmmmTo = xmmmTo;
	}

	/**
	 * 取得消息内容 return 消息内容
	 */
	public String getXmmmMessage() {
		return this.xmmmMessage;
	}

	/**
	 * 设置消息内容
	 * 
	 * @param xmmmMessage
	 *            消息内容
	 */
	public void setXmmmMessage(String xmmmMessage) {
		this.xmmmMessage = xmmmMessage;
	}

	public java.lang.String getXmmmFromDisplayname() {
		return xmmmFromDisplayname;
	}

	public void setXmmmFromDisplayname(java.lang.String xmmmFromDisplayname) {
		this.xmmmFromDisplayname = xmmmFromDisplayname;
	}

	public java.lang.String getXmmmToDisplayname() {
		return xmmmToDisplayname;
	}

	public void setXmmmToDisplayname(java.lang.String xmmmToDisplayname) {
		this.xmmmToDisplayname = xmmmToDisplayname;
	}

	public java.lang.String getXmmmStatus() {
		return xmmmStatus;
	}

	public void setXmmmStatus(java.lang.String xmmmStatus) {
		this.xmmmStatus = xmmmStatus;
	}

}
