/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.vo;

/** no table comments对应页面查询VO */
public class XmMeetingScheduleSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmsGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 主题描述 */
	public String xmmsTitle;
	/** 顺序 */
	public String xmmsSortno;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmsGuid() {
		return this.xmmsGuid;
	}
	/**
	 * 设置PID
	 * @param xmmsGuid PID
	 */
	public void setXmmsGuid(String xmmsGuid) {
		this.xmmsGuid = xmmsGuid;
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
	 * 取得主题描述
	 * return 主题描述
	 */
	public String getXmmsTitle() {
		return this.xmmsTitle;
	}
	/**
	 * 设置主题描述
	 * @param xmmsTitle 主题描述
	 */
	public void setXmmsTitle(String xmmsTitle) {
		this.xmmsTitle = xmmsTitle;
	}
	/**
	 * 取得顺序
	 * return 顺序
	 */
	public String getXmmsSortno() {
		return this.xmmsSortno;
	}
	/**
	 * 设置顺序
	 * @param xmmsSortno 顺序
	 */
	public void setXmmsSortno(String xmmsSortno) {
		this.xmmsSortno = xmmsSortno;
	}
	
	 
 
}

