/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.basic.vo;

/** no table comments对应页面查询VO */
public class XmRoomInfoSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmriGuid;
	/** 会议室名称 */
	public String xmriName;
	/** 会议室编号 */
	public String xmriCode;
	/** 会议室描述 */
	public String xmriDesc;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmriGuid() {
		return this.xmriGuid;
	}
	/**
	 * 设置PID
	 * @param xmriGuid PID
	 */
	public void setXmriGuid(String xmriGuid) {
		this.xmriGuid = xmriGuid;
	}
	/**
	 * 取得会议室名称
	 * return 会议室名称
	 */
	public String getXmriName() {
		return this.xmriName;
	}
	/**
	 * 设置会议室名称
	 * @param xmriName 会议室名称
	 */
	public void setXmriName(String xmriName) {
		this.xmriName = xmriName;
	}
	/**
	 * 取得会议室编号
	 * return 会议室编号
	 */
	public String getXmriCode() {
		return this.xmriCode;
	}
	/**
	 * 设置会议室编号
	 * @param xmriCode 会议室编号
	 */
	public void setXmriCode(String xmriCode) {
		this.xmriCode = xmriCode;
	}
	/**
	 * 取得会议室描述
	 * return 会议室描述
	 */
	public String getXmriDesc() {
		return this.xmriDesc;
	}
	/**
	 * 设置会议室描述
	 * @param xmriDesc 会议室描述
	 */
	public void setXmriDesc(String xmriDesc) {
		this.xmriDesc = xmriDesc;
	}
	
	 
 
}

