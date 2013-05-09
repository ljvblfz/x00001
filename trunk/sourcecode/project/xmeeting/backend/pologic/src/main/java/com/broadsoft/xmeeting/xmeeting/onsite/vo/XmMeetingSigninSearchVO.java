/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.onsite.vo;

/** no table comments对应页面查询VO */
public class XmMeetingSigninSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmsGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 签到人员 */
	public String xmmsPersonnel;
	/** 签到时间_开始 */
	public String xmmsTime_start;
	/** 签到时间_结束 */
	public String xmmsTime_end;
	/** 签到时间 */
	public String xmmsTime;
	/** 照片路径 */
	public String xmmsPhotoFile;
	
	
	

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
	 * 取得签到人员
	 * return 签到人员
	 */
	public String getXmmsPersonnel() {
		return this.xmmsPersonnel;
	}
	/**
	 * 设置签到人员
	 * @param xmmsPersonnel 签到人员
	 */
	public void setXmmsPersonnel(String xmmsPersonnel) {
		this.xmmsPersonnel = xmmsPersonnel;
	}
	/**
	 * 取得签到时间_开始
	 * return 签到时间_开始
	 */
	public String getXmmsTime_start() {
		return this.xmmsTime_start;
	}
	/**
	 * 设置签到时间_开始
	 * @param xmmsTime_start 签到时间_开始
	 */
	public void setXmmsTime_start(String xmmsTime_start) {
		this.xmmsTime_start = xmmsTime_start;
	}
	/**
	 * 取得签到时间_结束
	 * return 签到时间_结束
	 */
	public String getXmmsTime_end() {
		return this.xmmsTime_end;
	}
	/**
	 * 设置签到时间_结束
	 * @param xmmsTime_end 签到时间_结束
	 */
	public void setXmmsTime_end(String xmmsTime_end) {
		this.xmmsTime_end = xmmsTime_end;
	}
	/**
	 * 取得签到时间
	 * return 签到时间
	 */
	public String getXmmsTime() {
		return this.xmmsTime;
	}
	/**
	 * 设置签到时间
	 * @param xmmsTime 签到时间
	 */
	public void setXmmsTime(String value) {
		this.xmmsTime = value;
	}
	/**
	 * 取得照片路径
	 * return 照片路径
	 */
	public String getXmmsPhotoFile() {
		return this.xmmsPhotoFile;
	}
	/**
	 * 设置照片路径
	 * @param xmmsPhotoFile 照片路径
	 */
	public void setXmmsPhotoFile(String xmmsPhotoFile) {
		this.xmmsPhotoFile = xmmsPhotoFile;
	}
	
	 
 
}

