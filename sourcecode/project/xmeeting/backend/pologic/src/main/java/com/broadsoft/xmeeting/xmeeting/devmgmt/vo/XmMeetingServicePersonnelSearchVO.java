/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.vo;

/** no table comments对应页面查询VO */
public class XmMeetingServicePersonnelSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmspGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 人员名字 */
	public String xmpiGuid;
	/** 人员服务角色 */
	public String xmmspServiceRole;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmspGuid() {
		return this.xmmspGuid;
	}
	/**
	 * 设置PID
	 * @param xmmspGuid PID
	 */
	public void setXmmspGuid(String xmmspGuid) {
		this.xmmspGuid = xmmspGuid;
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
	 * 取得人员名字
	 * return 人员名字
	 */
	public String getXmpiGuid() {
		return this.xmpiGuid;
	}
	/**
	 * 设置人员名字
	 * @param xmpiGuid 人员名字
	 */
	public void setXmpiGuid(String xmpiGuid) {
		this.xmpiGuid = xmpiGuid;
	}
	/**
	 * 取得人员服务角色
	 * return 人员服务角色
	 */
	public String getXmmspServiceRole() {
		return this.xmmspServiceRole;
	}
	/**
	 * 设置人员服务角色
	 * @param xmmspServiceRole 人员服务角色
	 */
	public void setXmmspServiceRole(String xmmspServiceRole) {
		this.xmmspServiceRole = xmmspServiceRole;
	}
	
	 
 
}

