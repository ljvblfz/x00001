/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.vo;

/** no table comments对应页面查询VO */
public class XmMeetingEmailSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmeGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 收件人地址 */
	public String xmmeToAddress;
	/** 收件人名字 */
	public String xmmeToName;
	/** 状态 */
	public String xmmeStatus;
	public String xmmeAttachment;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmeGuid() {
		return this.xmmeGuid;
	}
	/**
	 * 设置PID
	 * @param xmmeGuid PID
	 */
	public void setXmmeGuid(String xmmeGuid) {
		this.xmmeGuid = xmmeGuid;
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
	 * 取得收件人地址
	 * return 收件人地址
	 */
	public String getXmmeToAddress() {
		return this.xmmeToAddress;
	}
	/**
	 * 设置收件人地址
	 * @param xmmeToAddress 收件人地址
	 */
	public void setXmmeToAddress(String xmmeToAddress) {
		this.xmmeToAddress = xmmeToAddress;
	}
	/**
	 * 取得收件人名字
	 * return 收件人名字
	 */
	public String getXmmeToName() {
		return this.xmmeToName;
	}
	/**
	 * 设置收件人名字
	 * @param xmmeToName 收件人名字
	 */
	public void setXmmeToName(String xmmeToName) {
		this.xmmeToName = xmmeToName;
	}
	/**
	 * 取得状态
	 * return 状态
	 */
	public String getXmmeStatus() {
		return this.xmmeStatus;
	}
	/**
	 * 设置状态
	 * @param xmmeStatus 状态
	 */
	public void setXmmeStatus(String xmmeStatus) {
		this.xmmeStatus = xmmeStatus;
	}
	
	
	public String getXmmeAttachment() {
		return xmmeAttachment;
	}
	public void setXmmeAttachment(String xmmeAttachment) {
		this.xmmeAttachment = xmmeAttachment;
	}
	
	 
 
}

