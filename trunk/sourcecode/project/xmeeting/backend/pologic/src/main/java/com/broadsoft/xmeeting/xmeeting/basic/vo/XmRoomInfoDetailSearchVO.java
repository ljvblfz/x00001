/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.basic.vo;

/** no table comments对应页面查询VO */
public class XmRoomInfoDetailSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmridGuid;
	/** 人员名字 */
	public String xmriGuid;
	/** 座位编号 */
	public String xmridSeatno;
	/** 座位描述 */
	public String xmridSeatdesc;
	/** 设备PID */
	public String xmpdGuid;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmridGuid() {
		return this.xmridGuid;
	}
	/**
	 * 设置PID
	 * @param xmridGuid PID
	 */
	public void setXmridGuid(String xmridGuid) {
		this.xmridGuid = xmridGuid;
	}
	/**
	 * 取得人员名字
	 * return 人员名字
	 */
	public String getXmriGuid() {
		return this.xmriGuid;
	}
	/**
	 * 设置人员名字
	 * @param xmriGuid 人员名字
	 */
	public void setXmriGuid(String xmriGuid) {
		this.xmriGuid = xmriGuid;
	}
	/**
	 * 取得座位编号
	 * return 座位编号
	 */
	public String getXmridSeatno() {
		return this.xmridSeatno;
	}
	/**
	 * 设置座位编号
	 * @param xmridSeatno 座位编号
	 */
	public void setXmridSeatno(String xmridSeatno) {
		this.xmridSeatno = xmridSeatno;
	}
	/**
	 * 取得座位描述
	 * return 座位描述
	 */
	public String getXmridSeatdesc() {
		return this.xmridSeatdesc;
	}
	/**
	 * 设置座位描述
	 * @param xmridSeatdesc 座位描述
	 */
	public void setXmridSeatdesc(String xmridSeatdesc) {
		this.xmridSeatdesc = xmridSeatdesc;
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
	
	 
 
}

