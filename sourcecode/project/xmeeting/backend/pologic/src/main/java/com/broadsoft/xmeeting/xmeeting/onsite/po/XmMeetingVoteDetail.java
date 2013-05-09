/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.onsite.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

/** no table comments对应实体类 */
@Entity
@Table(name = "XM_MEETING_VOTE_DETAIL")
public class XmMeetingVoteDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMVD_GUID= "xmmvdGuid";
	public static String XMMV_GUID= "xmmvGuid";
	public static String XMMVD_ANSWER= "xmmvdAnswer";
	public static String XMMVD_SORTNO= "xmmvdSortno";
	public static String XMMVD_COUNT= "xmmvdCount"; 

	//primary key
	/** PID */
	private java.lang.String xmmvdGuid;
	/** 会议投票PID */
	private java.lang.String xmmvGuid;
	/** 投票答案 */
	private java.lang.String xmmvdAnswer;
	/** 投票答案顺序 */
	private java.lang.Integer xmmvdSortno;
	/** 投票选择数量 */
	private java.lang.Integer xmmvdCount; 

	

	public XmMeetingVoteDetail(){
	}

	public XmMeetingVoteDetail(java.lang.String xmmvdGuid ){
		this.xmmvdGuid = xmmvdGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getXmmvdGuid() {
		return this.xmmvdGuid;
	}
	
	/**
	 * 设置PID
	 * @param xmmvdGuid PID
	 */
	public void setXmmvdGuid(java.lang.String xmmvdGuid) {
		this.xmmvdGuid = xmmvdGuid;
	}
		/**
		 * 取得会议投票PID
		 * return 会议投票PID
		 */
	public java.lang.String getXmmvGuid() {
		return this.xmmvGuid;
	}
	
	/**
	 * 设置会议投票PID
	 * @param xmmvGuid 会议投票PID
	 */
	public void setXmmvGuid(java.lang.String value) {
		this.xmmvGuid = value;
	}
		/**
		 * 取得投票答案
		 * return 投票答案
		 */
	public java.lang.String getXmmvdAnswer() {
		return this.xmmvdAnswer;
	}
	
	/**
	 * 设置投票答案
	 * @param xmmvdAnswer 投票答案
	 */
	public void setXmmvdAnswer(java.lang.String value) {
		this.xmmvdAnswer = value;
	}
		/**
		 * 取得投票答案顺序
		 * return 投票答案顺序
		 */
	public java.lang.Integer getXmmvdSortno() {
		return this.xmmvdSortno;
	}
	
	/**
	 * 设置投票答案顺序
	 * @param xmmvdSortno 投票答案顺序
	 */
	public void setXmmvdSortno(java.lang.Integer value) {
		this.xmmvdSortno = value;
	}
		/**
		 * 取得投票选择数量
		 * return 投票选择数量
		 */
	public java.lang.Integer getXmmvdCount() {
		return this.xmmvdCount;
	}
	
	/**
	 * 设置投票选择数量
	 * @param xmmvdCount 投票选择数量
	 */
	public void setXmmvdCount(java.lang.Integer value) {
		this.xmmvdCount = value;
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
			XmMeetingVoteDetail castObj = (XmMeetingVoteDetail) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmvdGuid(), castObj.getXmmvdGuid());
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
			XmMeetingVoteDetail castObj = (XmMeetingVoteDetail) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmvdGuid(), castObj.getXmmvdGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

