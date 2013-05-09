/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.basic.vo;

/** no table comments对应页面查询VO */
public class XmPersonnelInfoSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmpiGuid;
	/** 人员名字 */
	public String xmpiName;
	/** 人员单位 */
	public String xmpiDeptinfo;
	/** 人员职务 */
	public String xmpiTitle;
	/** 联系方式 */
	public String xmpiContact;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmpiGuid() {
		return this.xmpiGuid;
	}
	/**
	 * 设置PID
	 * @param xmpiGuid PID
	 */
	public void setXmpiGuid(String xmpiGuid) {
		this.xmpiGuid = xmpiGuid;
	}
	/**
	 * 取得人员名字
	 * return 人员名字
	 */
	public String getXmpiName() {
		return this.xmpiName;
	}
	/**
	 * 设置人员名字
	 * @param xmpiName 人员名字
	 */
	public void setXmpiName(String xmpiName) {
		this.xmpiName = xmpiName;
	}
	/**
	 * 取得人员单位
	 * return 人员单位
	 */
	public String getXmpiDeptinfo() {
		return this.xmpiDeptinfo;
	}
	/**
	 * 设置人员单位
	 * @param xmpiDeptinfo 人员单位
	 */
	public void setXmpiDeptinfo(String xmpiDeptinfo) {
		this.xmpiDeptinfo = xmpiDeptinfo;
	}
	/**
	 * 取得人员职务
	 * return 人员职务
	 */
	public String getXmpiTitle() {
		return this.xmpiTitle;
	}
	/**
	 * 设置人员职务
	 * @param xmpiTitle 人员职务
	 */
	public void setXmpiTitle(String xmpiTitle) {
		this.xmpiTitle = xmpiTitle;
	}
	/**
	 * 取得联系方式
	 * return 联系方式
	 */
	public String getXmpiContact() {
		return this.xmpiContact;
	}
	/**
	 * 设置联系方式
	 * @param xmpiContact 联系方式
	 */
	public void setXmpiContact(String xmpiContact) {
		this.xmpiContact = xmpiContact;
	}
	
	 
 
}

