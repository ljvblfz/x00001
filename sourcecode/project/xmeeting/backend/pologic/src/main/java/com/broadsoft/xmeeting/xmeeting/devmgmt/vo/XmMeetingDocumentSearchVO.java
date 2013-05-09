/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.vo;

/** no table comments对应页面查询VO */
public class XmMeetingDocumentSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmdGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 文稿名称 */
	public String xmmdName;
	/** 文稿描述 */
	public String xmmdDescription;
	/** 文稿文件 */
	public String xmmdFile;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmdGuid() {
		return this.xmmdGuid;
	}
	/**
	 * 设置PID
	 * @param xmmdGuid PID
	 */
	public void setXmmdGuid(String xmmdGuid) {
		this.xmmdGuid = xmmdGuid;
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
	 * 取得文稿名称
	 * return 文稿名称
	 */
	public String getXmmdName() {
		return this.xmmdName;
	}
	/**
	 * 设置文稿名称
	 * @param xmmdName 文稿名称
	 */
	public void setXmmdName(String xmmdName) {
		this.xmmdName = xmmdName;
	}
	/**
	 * 取得文稿描述
	 * return 文稿描述
	 */
	public String getXmmdDescription() {
		return this.xmmdDescription;
	}
	/**
	 * 设置文稿描述
	 * @param xmmdDescription 文稿描述
	 */
	public void setXmmdDescription(String xmmdDescription) {
		this.xmmdDescription = xmmdDescription;
	}
	/**
	 * 取得文稿文件
	 * return 文稿文件
	 */
	public String getXmmdFile() {
		return this.xmmdFile;
	}
	/**
	 * 设置文稿文件
	 * @param xmmdFile 文稿文件
	 */
	public void setXmmdFile(String xmmdFile) {
		this.xmmdFile = xmmdFile;
	}
	
	 
 
}

