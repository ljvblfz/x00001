package com.broadsoft.xmeeting.xmeeting.meeting.vo;

public class XmMeetingSigninlIVO {

	public String xmmsGuid; 
	public String xmmsTime;
	/** 人员 */
	public String xmpiName;
	public String xmpiGuid;
	/** 会议 */
	public String xmmiName;
	public String xmmiGuid;
	public String getXmmsGuid() {
		return xmmsGuid;
	}
	public void setXmmsGuid(String xmmsGuid) {
		this.xmmsGuid = xmmsGuid;
	}
	public String getXmmsTime() {
		return xmmsTime;
	}
	public void setXmmsTime(String xmmsTime) {
		this.xmmsTime = xmmsTime;
	}
	public String getXmpiName() {
		return xmpiName;
	}
	public void setXmpiName(String xmpiName) {
		this.xmpiName = xmpiName;
	}
	public String getXmpiGuid() {
		return xmpiGuid;
	}
	public void setXmpiGuid(String xmpiGuid) {
		this.xmpiGuid = xmpiGuid;
	}
	public String getXmmiName() {
		return xmmiName;
	}
	public void setXmmiName(String xmmiName) {
		this.xmmiName = xmmiName;
	}
	public String getXmmiGuid() {
		return xmmiGuid;
	}
	public void setXmmiGuid(String xmmiGuid) {
		this.xmmiGuid = xmmiGuid;
	}
	@Override
	public String toString() {
		return "XmMeetingSigninlIVO [xmmsGuid=" + xmmsGuid + ", xmmsTime="
				+ xmmsTime + ", xmpiName=" + xmpiName + ", xmpiGuid="
				+ xmpiGuid + ", xmmiName=" + xmmiName + ", xmmiGuid="
				+ xmmiGuid + "]";
	}
 

}
