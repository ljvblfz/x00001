/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.basic.vo;

/** no table comments对应页面查询VO */
public class XmCompanyInfoSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmciGuid;
	/** 公司名称 */
	public String xmciCompanyName;
	/** 介绍描述 */
	public String xmciDescription;
	/** 介绍附件 */
	public String xmciAttachment;
	/** 介绍类型 */
	public String xmciType;
	/** 介绍状态 */
	public String xmciStatus;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmciGuid() {
		return this.xmciGuid;
	}
	/**
	 * 设置PID
	 * @param xmciGuid PID
	 */
	public void setXmciGuid(String xmciGuid) {
		this.xmciGuid = xmciGuid;
	}
	/**
	 * 取得公司名称
	 * return 公司名称
	 */
	public String getXmciCompanyName() {
		return this.xmciCompanyName;
	}
	/**
	 * 设置公司名称
	 * @param xmciCompanyName 公司名称
	 */
	public void setXmciCompanyName(String xmciCompanyName) {
		this.xmciCompanyName = xmciCompanyName;
	}
	/**
	 * 取得介绍描述
	 * return 介绍描述
	 */
	public String getXmciDescription() {
		return this.xmciDescription;
	}
	/**
	 * 设置介绍描述
	 * @param xmciDescription 介绍描述
	 */
	public void setXmciDescription(String xmciDescription) {
		this.xmciDescription = xmciDescription;
	}
	/**
	 * 取得介绍附件
	 * return 介绍附件
	 */
	public String getXmciAttachment() {
		return this.xmciAttachment;
	}
	/**
	 * 设置介绍附件
	 * @param xmciAttachment 介绍附件
	 */
	public void setXmciAttachment(String xmciAttachment) {
		this.xmciAttachment = xmciAttachment;
	}
	/**
	 * 取得介绍类型
	 * return 介绍类型
	 */
	public String getXmciType() {
		return this.xmciType;
	}
	/**
	 * 设置介绍类型
	 * @param xmciType 介绍类型
	 */
	public void setXmciType(String xmciType) {
		this.xmciType = xmciType;
	}
	/**
	 * 取得介绍状态
	 * return 介绍状态
	 */
	public String getXmciStatus() {
		return this.xmciStatus;
	}
	/**
	 * 设置介绍状态
	 * @param xmciStatus 介绍状态
	 */
	public void setXmciStatus(String xmciStatus) {
		this.xmciStatus = xmciStatus;
	}
	
	 
 
}

