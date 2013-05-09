/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.onsite.po;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

/** no table comments对应实体类 */
@Entity
@Table(name = "XM_MEETING_VOTE")
public class XmMeetingVote extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMV_GUID= "xmmvGuid";
	public static String XMMI_GUID= "xmmiGuid";
	public static String XMMV_TITLE= "xmmvTitle";
	public static String XMMV_TYPE= "xmmvType";
	public static String XMMV_ISANONYM= "xmmvIsanonym";
	public static String XMMV_MAXCOUNT= "xmmvMaxcount"; 

	//primary key
	/** PID */
	private java.lang.String xmmvGuid;
	/** 会议PID */
	private java.lang.String xmmiGuid;
	/** 投票主题 */
	private java.lang.String xmmvTitle;
	/** 题目类型 */
	private java.lang.String xmmvType;
	private java.lang.String xmmvTypeLabel;
	/** 是否匿名 */
	private java.lang.String xmmvIsanonym;
	private java.lang.String xmmvIsanonymLabel;
	/** 最多选几条 */
	private java.lang.String xmmvMaxcount; 
	
	
	private List<XmMeetingVoteDetail> listOfXmMeetingVoteDetail=new ArrayList<XmMeetingVoteDetail>();

	

	public XmMeetingVote(){
	}

	public XmMeetingVote(java.lang.String xmmvGuid ){
		this.xmmvGuid = xmmvGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmvGuid() {
		return this.xmmvGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmvGuid PID
	 */
	public void setXmmvGuid(java.lang.String xmmvGuid) {
		this.xmmvGuid = xmmvGuid;
	}
		/**
		 * 取得会议PID
		 * return 会议PID
		 */
	public java.lang.String getXmmiGuid() {
		return this.xmmiGuid;
	}
	
	/**
	 * 设置会议PID
	 * @param xmmiGuid 会议PID
	 */
	public void setXmmiGuid(java.lang.String value) {
		this.xmmiGuid = value;
	}
		/**
		 * 取得投票主题
		 * return 投票主题
		 */
	public java.lang.String getXmmvTitle() {
		return this.xmmvTitle;
	}
	
	/**
	 * 设置投票主题
	 * @param xmmvTitle 投票主题
	 */
	public void setXmmvTitle(java.lang.String value) {
		this.xmmvTitle = value;
	}
		/**
		 * 取得题目类型
		 * return 题目类型
		 */
	public java.lang.String getXmmvType() {
		return this.xmmvType;
	}
	
	/**
	 * 设置题目类型
	 * @param xmmvType 题目类型
	 */
	public void setXmmvType(java.lang.String value) {
		this.xmmvType = value;
	}
		/**
		 * 取得是否匿名
		 * return 是否匿名
		 */
	public java.lang.String getXmmvIsanonym() {
		return this.xmmvIsanonym;
	}
	
	/**
	 * 设置是否匿名
	 * @param xmmvIsanonym 是否匿名
	 */
	public void setXmmvIsanonym(java.lang.String value) {
		this.xmmvIsanonym = value;
	}
		/**
		 * 取得最多选几条
		 * return 最多选几条
		 */
	public java.lang.String getXmmvMaxcount() {
		return this.xmmvMaxcount;
	}
	
	/**
	 * 设置最多选几条
	 * @param xmmvMaxcount 最多选几条
	 */
	public void setXmmvMaxcount(java.lang.String value) {
		this.xmmvMaxcount = value;
	}
		 

	@Transient
	public java.lang.String getXmmvTypeLabel() {
		return xmmvTypeLabel;
	}

	public void setXmmvTypeLabel(java.lang.String xmmvTypeLabel) {
		this.xmmvTypeLabel = xmmvTypeLabel;
	}
	
	@Transient
	public java.lang.String getXmmvIsanonymLabel() {
		return xmmvIsanonymLabel;
	}

	public void setXmmvIsanonymLabel(java.lang.String xmmvIsanonymLabel) {
		this.xmmvIsanonymLabel = xmmvIsanonymLabel;
	}

	@Transient
	public List<XmMeetingVoteDetail> getListOfXmMeetingVoteDetail() {
		return listOfXmMeetingVoteDetail;
	}

	public void setListOfXmMeetingVoteDetail(
			List<XmMeetingVoteDetail> listOfXmMeetingVoteDetail) {
		this.listOfXmMeetingVoteDetail = listOfXmMeetingVoteDetail;
	}

	public int compareTo(Object obj) {
		int compare = -1;
	
		if (obj == null)
			compare = -1;
		else if (this == obj)
			compare = 0;
		else if (!(obj instanceof BaseEntity))
			compare = -1;
		else if (!this.getClass().equals(obj.getClass()))
			compare = -1;
		else {
			XmMeetingVote castObj = (XmMeetingVote) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmvGuid(), castObj.getXmmvGuid());
			compare = builder.toComparison();
		}
		return compare;
	}
		
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if (obj == null) {
			isEqual = false;
		} else if (this == obj) {
			isEqual = true;
		} else if (!(obj instanceof BaseEntity)) {
			isEqual = false;
		} else if (!this.getClass().equals(obj.getClass())) {
			isEqual = false;
		} else {
			XmMeetingVote castObj = (XmMeetingVote) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmvGuid(), castObj.getXmmvGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

