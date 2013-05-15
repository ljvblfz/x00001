/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
package com.broadsoft.xmeeting.xmeeting.devmgmt.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

/** no table comments对应实体类 */
@Entity
@Table(name = "XM_MEETING_SCHEDULE_DETAIL")
public class XmMeetingScheduleDetail extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String XMMSD_GUID = "xmmsdGuid";
	public static String XMMS_GUID = "xmmsGuid";
	public static String XMMSD_TIME = "xmmsdTime";
	public static String XMMSD_DESCRIPTION = "xmmsdDescription";
	public static String XMMSD_SORTNO = "xmmsdSortno";

	// primary key
	/** PID */
	private java.lang.String xmmsdGuid;
	/** 会议PID */
	private java.lang.String xmmsGuid;
	/** 时间 */
	private java.lang.String xmmsdTime;
	/** 标题 */
	private java.lang.String xmmsdTitle;
	/** 描述 */
	private java.lang.String xmmsdDescription;
	/** 顺序 */
	private java.lang.Integer xmmsdSortno;

	public XmMeetingScheduleDetail() {
	}

	public XmMeetingScheduleDetail(java.lang.String xmmsdGuid) {
		this.xmmsdGuid = xmmsdGuid;
	}

	/**
	 * 取得PID return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")
	public java.lang.String getXmmsdGuid() {
		return this.xmmsdGuid;
	}

	/**
	 * 设置PID
	 * 
	 * @param xmmsdGuid
	 *            PID
	 */
	public void setXmmsdGuid(java.lang.String xmmsdGuid) {
		this.xmmsdGuid = xmmsdGuid;
	}

	/**
	 * 取得会议PID return 会议PID
	 */
	public java.lang.String getXmmsGuid() {
		return this.xmmsGuid;
	}

	/**
	 * 设置会议PID
	 * 
	 * @param xmmsGuid
	 *            会议PID
	 */
	public void setXmmsGuid(java.lang.String value) {
		this.xmmsGuid = value;
	}

	/**
	 * 取得时间 return 时间
	 */
	public java.lang.String getXmmsdTime() {
		return this.xmmsdTime;
	}

	/**
	 * 设置时间
	 * 
	 * @param xmmsdTime
	 *            时间
	 */
	public void setXmmsdTime(java.lang.String value) {
		this.xmmsdTime = value;
	}

	/**
	 * 取得描述 return 描述
	 */
	public java.lang.String getXmmsdDescription() {
		return this.xmmsdDescription;
	}

	public java.lang.String getXmmsdTitle() {
		return xmmsdTitle;
	}

	public void setXmmsdTitle(java.lang.String xmmsdTitle) {
		this.xmmsdTitle = xmmsdTitle;
	}

	/**
	 * 设置描述
	 * 
	 * @param xmmsdDescription
	 *            描述
	 */
	public void setXmmsdDescription(java.lang.String value) {
		this.xmmsdDescription = value;
	}

	/**
	 * 取得顺序 return 顺序
	 */
	public java.lang.Integer getXmmsdSortno() {
		return this.xmmsdSortno;
	}

	/**
	 * 设置顺序
	 * 
	 * @param xmmsdSortno
	 *            顺序
	 */
	public void setXmmsdSortno(java.lang.Integer value) {
		this.xmmsdSortno = value;
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
			XmMeetingScheduleDetail castObj = (XmMeetingScheduleDetail) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getXmmsdGuid(), castObj.getXmmsdGuid());
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
			XmMeetingScheduleDetail castObj = (XmMeetingScheduleDetail) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getXmmsdGuid(), castObj.getXmmsdGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}
