/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.basic.vo;

/** no table comments对应页面查询VO */
public class XmPadDeviceSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmpdGuid;
	/** 资产编号 */
	public String xmpdCode;
	/** 设备编号 */
	public String xmpdDeviceId;
	/** 设备规格 */
	public String xmpdDeviceSpec;
	/** 硬件版本 */
	public String xmpdHardwareVersion;
	/** 软件版本 */
	public String xmpdSoftwareVersion;
	/** 设备类型 */
	public String xmpdType;
	

	/** 子机设备编号 */
	private java.lang.String xmpdSubDeviceId; 
	/** 设备状态 */
	private java.lang.String xmpdStatus; 
	/** 设备备注*/
	private java.lang.String xmpdComment; 
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmpdGuid() {
		return this.xmpdGuid;
	}
	/**
	 * 设置PID
	 * @param xmpdGuid PID
	 */
	public void setXmpdGuid(String xmpdGuid) {
		this.xmpdGuid = xmpdGuid;
	}
	/**
	 * 取得资产编号
	 * return 资产编号
	 */
	public String getXmpdCode() {
		return this.xmpdCode;
	}
	/**
	 * 设置资产编号
	 * @param xmpdCode 资产编号
	 */
	public void setXmpdCode(String xmpdCode) {
		this.xmpdCode = xmpdCode;
	}
	/**
	 * 取得设备编号
	 * return 设备编号
	 */
	public String getXmpdDeviceId() {
		return this.xmpdDeviceId;
	}
	/**
	 * 设置设备编号
	 * @param xmpdDeviceId 设备编号
	 */
	public void setXmpdDeviceId(String xmpdDeviceId) {
		this.xmpdDeviceId = xmpdDeviceId;
	}
	/**
	 * 取得设备规格
	 * return 设备规格
	 */
	public String getXmpdDeviceSpec() {
		return this.xmpdDeviceSpec;
	}
	/**
	 * 设置设备规格
	 * @param xmpdDeviceSpec 设备规格
	 */
	public void setXmpdDeviceSpec(String xmpdDeviceSpec) {
		this.xmpdDeviceSpec = xmpdDeviceSpec;
	}
	/**
	 * 取得硬件版本
	 * return 硬件版本
	 */
	public String getXmpdHardwareVersion() {
		return this.xmpdHardwareVersion;
	}
	/**
	 * 设置硬件版本
	 * @param xmpdHardwareVersion 硬件版本
	 */
	public void setXmpdHardwareVersion(String xmpdHardwareVersion) {
		this.xmpdHardwareVersion = xmpdHardwareVersion;
	}
	/**
	 * 取得软件版本
	 * return 软件版本
	 */
	public String getXmpdSoftwareVersion() {
		return this.xmpdSoftwareVersion;
	}
	/**
	 * 设置软件版本
	 * @param xmpdSoftwareVersion 软件版本
	 */
	public void setXmpdSoftwareVersion(String xmpdSoftwareVersion) {
		this.xmpdSoftwareVersion = xmpdSoftwareVersion;
	}
	/**
	 * 取得设备类型
	 * return 设备类型
	 */
	public String getXmpdType() {
		return this.xmpdType;
	}
	/**
	 * 设置设备类型
	 * @param xmpdType 设备类型
	 */
	public void setXmpdType(String xmpdType) {
		this.xmpdType = xmpdType;
	}
	public java.lang.String getXmpdSubDeviceId() {
		return xmpdSubDeviceId;
	}
	public void setXmpdSubDeviceId(java.lang.String xmpdSubDeviceId) {
		this.xmpdSubDeviceId = xmpdSubDeviceId;
	}
	public java.lang.String getXmpdStatus() {
		return xmpdStatus;
	}
	public void setXmpdStatus(java.lang.String xmpdStatus) {
		this.xmpdStatus = xmpdStatus;
	}
	public java.lang.String getXmpdComment() {
		return xmpdComment;
	}
	public void setXmpdComment(java.lang.String xmpdComment) {
		this.xmpdComment = xmpdComment;
	}
	
	 
 
}

