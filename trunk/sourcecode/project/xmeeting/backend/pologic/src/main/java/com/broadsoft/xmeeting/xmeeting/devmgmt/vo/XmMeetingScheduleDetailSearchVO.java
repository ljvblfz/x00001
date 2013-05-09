/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.vo;

/** no table comments对应页面查询VO */
public class XmMeetingScheduleDetailSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmsdGuid;
	/** 会议PID */
	public String xmmsGuid;
	/** 时间 */
	public String xmmsdTime;
	/** 描述 */
	public String xmmsdDescription;
	/** 顺序_开始 */
	public String xmmsdSortno_start;
	/** 顺序_结束 */
	public String xmmsdSortno_end;
	/** 顺序 */
	public String xmmsdSortno;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmsdGuid() {
		return this.xmmsdGuid;
	}
	/**
	 * 设置PID
	 * @param xmmsdGuid PID
	 */
	public void setXmmsdGuid(String xmmsdGuid) {
		this.xmmsdGuid = xmmsdGuid;
	}
	/**
	 * 取得会议PID
	 * return 会议PID
	 */
	public String getXmmsGuid() {
		return this.xmmsGuid;
	}
	/**
	 * 设置会议PID
	 * @param xmmsGuid 会议PID
	 */
	public void setXmmsGuid(String xmmsGuid) {
		this.xmmsGuid = xmmsGuid;
	}
	/**
	 * 取得时间
	 * return 时间
	 */
	public String getXmmsdTime() {
		return this.xmmsdTime;
	}
	/**
	 * 设置时间
	 * @param xmmsdTime 时间
	 */
	public void setXmmsdTime(String xmmsdTime) {
		this.xmmsdTime = xmmsdTime;
	}
	/**
	 * 取得描述
	 * return 描述
	 */
	public String getXmmsdDescription() {
		return this.xmmsdDescription;
	}
	/**
	 * 设置描述
	 * @param xmmsdDescription 描述
	 */
	public void setXmmsdDescription(String xmmsdDescription) {
		this.xmmsdDescription = xmmsdDescription;
	}
	/**
	 * 取得顺序_开始
	 * return 顺序_开始
	 */
	public String getXmmsdSortno_start() {
		return this.xmmsdSortno_start;
	}
	/**
	 * 设置顺序_开始
	 * @param xmmsdSortno_start 顺序_开始
	 */
	public void setXmmsdSortno_start(String xmmsdSortno_start) {
		this.xmmsdSortno_start = xmmsdSortno_start;
	}
	/**
	 * 取得顺序_结束
	 * return 顺序_结束
	 */
	public String getXmmsdSortno_end() {
		return this.xmmsdSortno_end;
	}
	/**
	 * 设置顺序_结束
	 * @param xmmsdSortno_end 顺序_结束
	 */
	public void setXmmsdSortno_end(String xmmsdSortno_end) {
		this.xmmsdSortno_end = xmmsdSortno_end;
	}
	/**
	 * 取得顺序
	 * return 顺序
	 */
	public String getXmmsdSortno() {
		return this.xmmsdSortno;
	}
	/**
	 * 设置顺序
	 * @param xmmsdSortno 顺序
	 */
	public void setXmmsdSortno(String value) {
		this.xmmsdSortno = value;
	}
	
	 
 
}

