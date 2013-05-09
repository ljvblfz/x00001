/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.vo;

/** no table comments对应页面查询VO */
public class XmMeetingPictureDetailSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmpicdGuid;
	/** 会议图片PID */
	public String xmmpicGuid;
	/** 会议图片文件 */
	public String xmmpicImageFile;
	/** 会议图片描述 */
	public String xmmpicImageDesc;
	/** 顺序_开始 */
	public String xmmpicSortno_start;
	/** 顺序_结束 */
	public String xmmpicSortno_end;
	/** 顺序 */
	public String xmmpicSortno;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmpicdGuid() {
		return this.xmmpicdGuid;
	}
	/**
	 * 设置PID
	 * @param xmmpicdGuid PID
	 */
	public void setXmmpicdGuid(String xmmpicdGuid) {
		this.xmmpicdGuid = xmmpicdGuid;
	}
	/**
	 * 取得会议图片PID
	 * return 会议图片PID
	 */
	public String getXmmpicGuid() {
		return this.xmmpicGuid;
	}
	/**
	 * 设置会议图片PID
	 * @param xmmpicGuid 会议图片PID
	 */
	public void setXmmpicGuid(String xmmpicGuid) {
		this.xmmpicGuid = xmmpicGuid;
	}
	/**
	 * 取得会议图片文件
	 * return 会议图片文件
	 */
	public String getXmmpicImageFile() {
		return this.xmmpicImageFile;
	}
	/**
	 * 设置会议图片文件
	 * @param xmmpicImageFile 会议图片文件
	 */
	public void setXmmpicImageFile(String xmmpicImageFile) {
		this.xmmpicImageFile = xmmpicImageFile;
	}
	/**
	 * 取得会议图片描述
	 * return 会议图片描述
	 */
	public String getXmmpicImageDesc() {
		return this.xmmpicImageDesc;
	}
	/**
	 * 设置会议图片描述
	 * @param xmmpicImageDesc 会议图片描述
	 */
	public void setXmmpicImageDesc(String xmmpicImageDesc) {
		this.xmmpicImageDesc = xmmpicImageDesc;
	}
	/**
	 * 取得顺序_开始
	 * return 顺序_开始
	 */
	public String getXmmpicSortno_start() {
		return this.xmmpicSortno_start;
	}
	/**
	 * 设置顺序_开始
	 * @param xmmpicSortno_start 顺序_开始
	 */
	public void setXmmpicSortno_start(String xmmpicSortno_start) {
		this.xmmpicSortno_start = xmmpicSortno_start;
	}
	/**
	 * 取得顺序_结束
	 * return 顺序_结束
	 */
	public String getXmmpicSortno_end() {
		return this.xmmpicSortno_end;
	}
	/**
	 * 设置顺序_结束
	 * @param xmmpicSortno_end 顺序_结束
	 */
	public void setXmmpicSortno_end(String xmmpicSortno_end) {
		this.xmmpicSortno_end = xmmpicSortno_end;
	}
	/**
	 * 取得顺序
	 * return 顺序
	 */
	public String getXmmpicSortno() {
		return this.xmmpicSortno;
	}
	/**
	 * 设置顺序
	 * @param xmmpicSortno 顺序
	 */
	public void setXmmpicSortno(String value) {
		this.xmmpicSortno = value;
	}
	
	 
 
}

