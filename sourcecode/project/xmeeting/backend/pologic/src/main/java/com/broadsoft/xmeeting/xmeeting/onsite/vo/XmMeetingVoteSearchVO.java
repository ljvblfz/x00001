/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.onsite.vo;

/** no table comments对应页面查询VO */
public class XmMeetingVoteSearchVO {

	private static final long serialVersionUID = 1L;
 
	/** PID */
	public String xmmvGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 投票主题 */
	public String xmmvTitle;
	/** 题目类型 */
	public String xmmvType;
	/** 是否匿名 */
	public String xmmvIsanonym;
	/** 最多选几条 */
	public String xmmvMaxcount;
	
	
	

	/**
	 * 取得PID
	 * return PID
	 */
	public String getXmmvGuid() {
		return this.xmmvGuid;
	}
	/**
	 * 设置PID
	 * @param xmmvGuid PID
	 */
	public void setXmmvGuid(String xmmvGuid) {
		this.xmmvGuid = xmmvGuid;
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
	 * 取得投票主题
	 * return 投票主题
	 */
	public String getXmmvTitle() {
		return this.xmmvTitle;
	}
	/**
	 * 设置投票主题
	 * @param xmmvTitle 投票主题
	 */
	public void setXmmvTitle(String xmmvTitle) {
		this.xmmvTitle = xmmvTitle;
	}
	/**
	 * 取得题目类型
	 * return 题目类型
	 */
	public String getXmmvType() {
		return this.xmmvType;
	}
	/**
	 * 设置题目类型
	 * @param xmmvType 题目类型
	 */
	public void setXmmvType(String xmmvType) {
		this.xmmvType = xmmvType;
	}
	/**
	 * 取得是否匿名
	 * return 是否匿名
	 */
	public String getXmmvIsanonym() {
		return this.xmmvIsanonym;
	}
	/**
	 * 设置是否匿名
	 * @param xmmvIsanonym 是否匿名
	 */
	public void setXmmvIsanonym(String xmmvIsanonym) {
		this.xmmvIsanonym = xmmvIsanonym;
	}
	/**
	 * 取得最多选几条
	 * return 最多选几条
	 */
	public String getXmmvMaxcount() {
		return this.xmmvMaxcount;
	}
	/**
	 * 设置最多选几条
	 * @param xmmvMaxcount 最多选几条
	 */
	public void setXmmvMaxcount(String xmmvMaxcount) {
		this.xmmvMaxcount = xmmvMaxcount;
	}
	
	 
 
}

