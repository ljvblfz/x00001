/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.vo;

/** no table comments对应页面查询VO */
public class XmMeetingMmsContentSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmcGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 彩信图片 */
	public String xmmcImage;
	/** 彩信文字描述1 */
	public String xmmcDescription1;
	/** 彩信文字描述2 */
	public String xmmcDescription2;
	/** 彩信文字描述3 */
	public String xmmcDescription3;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmcGuid() {
		return this.xmmcGuid;
	}
	/**
	 * 设置PID
	 * @param xmmcGuid PID
	 */
	public void setXmmcGuid(String xmmcGuid) {
		this.xmmcGuid = xmmcGuid;
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
	 * 取得彩信图片
	 * return 彩信图片
	 */
	public String getXmmcImage() {
		return this.xmmcImage;
	}
	/**
	 * 设置彩信图片
	 * @param xmmcImage 彩信图片
	 */
	public void setXmmcImage(String xmmcImage) {
		this.xmmcImage = xmmcImage;
	}
	/**
	 * 取得彩信文字描述1
	 * return 彩信文字描述1
	 */
	public String getXmmcDescription1() {
		return this.xmmcDescription1;
	}
	/**
	 * 设置彩信文字描述1
	 * @param xmmcDescription1 彩信文字描述1
	 */
	public void setXmmcDescription1(String xmmcDescription1) {
		this.xmmcDescription1 = xmmcDescription1;
	}
	/**
	 * 取得彩信文字描述2
	 * return 彩信文字描述2
	 */
	public String getXmmcDescription2() {
		return this.xmmcDescription2;
	}
	/**
	 * 设置彩信文字描述2
	 * @param xmmcDescription2 彩信文字描述2
	 */
	public void setXmmcDescription2(String xmmcDescription2) {
		this.xmmcDescription2 = xmmcDescription2;
	}
	/**
	 * 取得彩信文字描述3
	 * return 彩信文字描述3
	 */
	public String getXmmcDescription3() {
		return this.xmmcDescription3;
	}
	/**
	 * 设置彩信文字描述3
	 * @param xmmcDescription3 彩信文字描述3
	 */
	public void setXmmcDescription3(String xmmcDescription3) {
		this.xmmcDescription3 = xmmcDescription3;
	}
	
	 
 
}

