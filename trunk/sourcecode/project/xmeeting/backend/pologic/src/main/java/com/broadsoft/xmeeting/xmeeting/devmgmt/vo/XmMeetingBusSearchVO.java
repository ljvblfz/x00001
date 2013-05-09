/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.vo;

/** no table comments对应页面查询VO */
public class XmMeetingBusSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmbGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 车辆编号 */
	public String xmmbCode;
	/** 车牌号 */
	public String xmmbCard;
	/** 人员安排 */
	public String xmmbDesc;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmbGuid() {
		return this.xmmbGuid;
	}
	/**
	 * 设置PID
	 * @param xmmbGuid PID
	 */
	public void setXmmbGuid(String xmmbGuid) {
		this.xmmbGuid = xmmbGuid;
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
	 * 取得车辆编号
	 * return 车辆编号
	 */
	public String getXmmbCode() {
		return this.xmmbCode;
	}
	/**
	 * 设置车辆编号
	 * @param xmmbCode 车辆编号
	 */
	public void setXmmbCode(String xmmbCode) {
		this.xmmbCode = xmmbCode;
	}
	/**
	 * 取得车牌号
	 * return 车牌号
	 */
	public String getXmmbCard() {
		return this.xmmbCard;
	}
	/**
	 * 设置车牌号
	 * @param xmmbCard 车牌号
	 */
	public void setXmmbCard(String xmmbCard) {
		this.xmmbCard = xmmbCard;
	}
	/**
	 * 取得人员安排
	 * return 人员安排
	 */
	public String getXmmbDesc() {
		return this.xmmbDesc;
	}
	/**
	 * 设置人员安排
	 * @param xmmbDesc 人员安排
	 */
	public void setXmmbDesc(String xmmbDesc) {
		this.xmmbDesc = xmmbDesc;
	}
	
	 
 
}

