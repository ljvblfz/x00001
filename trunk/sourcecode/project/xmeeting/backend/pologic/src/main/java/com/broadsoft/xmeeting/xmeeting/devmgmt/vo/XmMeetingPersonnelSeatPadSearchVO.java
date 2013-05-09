/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.vo;

/** no table comments对应页面查询VO */
public class XmMeetingPersonnelSeatPadSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmpspGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 人员PID */
	public String xmpiGuid;
	/** 设备PID */
	public String xmpdGuid;
	/** 会议室座位 */
	public String xmridGuid;
	/** 人员会议角色 */
	public String xmmpspMeetingRole;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmpspGuid() {
		return this.xmmpspGuid;
	}
	/**
	 * 设置PID
	 * @param xmmpspGuid PID
	 */
	public void setXmmpspGuid(String xmmpspGuid) {
		this.xmmpspGuid = xmmpspGuid;
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
	 * 取得人员PID
	 * return 人员PID
	 */
	public String getXmpiGuid() {
		return this.xmpiGuid;
	}
	/**
	 * 设置人员PID
	 * @param xmpiGuid 人员PID
	 */
	public void setXmpiGuid(String xmpiGuid) {
		this.xmpiGuid = xmpiGuid;
	}
	/**
	 * 取得设备PID
	 * return 设备PID
	 */
	public String getXmpdGuid() {
		return this.xmpdGuid;
	}
	/**
	 * 设置设备PID
	 * @param xmpdGuid 设备PID
	 */
	public void setXmpdGuid(String xmpdGuid) {
		this.xmpdGuid = xmpdGuid;
	}
	/**
	 * 取得会议室座位
	 * return 会议室座位
	 */
	public String getXmridGuid() {
		return this.xmridGuid;
	}
	/**
	 * 设置会议室座位
	 * @param xmridGuid 会议室座位
	 */
	public void setXmridGuid(String xmridGuid) {
		this.xmridGuid = xmridGuid;
	}
	/**
	 * 取得人员会议角色
	 * return 人员会议角色
	 */
	public String getXmmpspMeetingRole() {
		return this.xmmpspMeetingRole;
	}
	/**
	 * 设置人员会议角色
	 * @param xmmpspMeetingRole 人员会议角色
	 */
	public void setXmmpspMeetingRole(String xmmpspMeetingRole) {
		this.xmmpspMeetingRole = xmmpspMeetingRole;
	}
	
	 
 
}

