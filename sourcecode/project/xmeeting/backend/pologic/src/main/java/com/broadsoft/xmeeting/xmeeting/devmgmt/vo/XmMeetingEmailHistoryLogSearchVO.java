/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.vo;

/** no table comments对应页面查询VO */
public class XmMeetingEmailHistoryLogSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmehlGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 发件人 */
	public String xmmehlFrom;
	/** 收件人 */
	public String xmmehlTo;
	/** 抄送 */
	public String xmmehlCc;
	/** 隐抄 */
	public String xmmehlBcc;
	/** 发送状态 */
	public String xmmehlStatu;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmehlGuid() {
		return this.xmmehlGuid;
	}
	/**
	 * 设置PID
	 * @param xmmehlGuid PID
	 */
	public void setXmmehlGuid(String xmmehlGuid) {
		this.xmmehlGuid = xmmehlGuid;
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
	public String getXmmehlFrom() {
		return this.xmmehlFrom;
	}
	/**
	 * 设置发件人
	 * @param xmmehlFrom 发件人
	 */
	public void setXmmehlFrom(String xmmehlFrom) {
		this.xmmehlFrom = xmmehlFrom;
	}
	/**
	 * 取得收件人
	 * return 收件人
	 */
	public String getXmmehlTo() {
		return this.xmmehlTo;
	}
	/**
	 * 设置收件人
	 * @param xmmehlTo 收件人
	 */
	public void setXmmehlTo(String xmmehlTo) {
		this.xmmehlTo = xmmehlTo;
	}
	/**
	 * 取得抄送
	 * return 抄送
	 */
	public String getXmmehlCc() {
		return this.xmmehlCc;
	}
	/**
	 * 设置抄送
	 * @param xmmehlCc 抄送
	 */
	public void setXmmehlCc(String xmmehlCc) {
		this.xmmehlCc = xmmehlCc;
	}
	/**
	 * 取得隐抄
	 * return 隐抄
	 */
	public String getXmmehlBcc() {
		return this.xmmehlBcc;
	}
	/**
	 * 设置隐抄
	 * @param xmmehlBcc 隐抄
	 */
	public void setXmmehlBcc(String xmmehlBcc) {
		this.xmmehlBcc = xmmehlBcc;
	}
	/**
	 * 取得发送状态
	 * return 发送状态
	 */
	public String getXmmehlStatu() {
		return this.xmmehlStatu;
	}
	/**
	 * 设置发送状态
	 * @param xmmehlStatu 发送状态
	 */
	public void setXmmehlStatu(String xmmehlStatu) {
		this.xmmehlStatu = xmmehlStatu;
	}
	
	 
 
}

