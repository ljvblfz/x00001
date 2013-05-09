/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.vo;

/** no table comments对应页面查询VO */
public class XmMeetingPictureSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmpicGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 图片主题名称 */
	public String xmmpicImageTitle;
	/** 图片主题描述 */
	public String xmmpicImageDescription;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmpicGuid() {
		return this.xmmpicGuid;
	}
	/**
	 * 设置PID
	 * @param xmmpicGuid PID
	 */
	public void setXmmpicGuid(String xmmpicGuid) {
		this.xmmpicGuid = xmmpicGuid;
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
	 * 取得图片主题名称
	 * return 图片主题名称
	 */
	public String getXmmpicImageTitle() {
		return this.xmmpicImageTitle;
	}
	/**
	 * 设置图片主题名称
	 * @param xmmpicImageTitle 图片主题名称
	 */
	public void setXmmpicImageTitle(String xmmpicImageTitle) {
		this.xmmpicImageTitle = xmmpicImageTitle;
	}
	/**
	 * 取得图片主题描述
	 * return 图片主题描述
	 */
	public String getXmmpicImageDescription() {
		return this.xmmpicImageDescription;
	}
	/**
	 * 设置图片主题描述
	 * @param xmmpicImageDescription 图片主题描述
	 */
	public void setXmmpicImageDescription(String xmmpicImageDescription) {
		this.xmmpicImageDescription = xmmpicImageDescription;
	}
	
	 
 
}

