package com.broadsoft.xmeeting.xmeeting.meeting.vo;

public class XmMeetingCallIVO {

	public String xmmcallGuid;
	public String xmmcallCaller;
	public String xmmcallMessage;
	public String xmmcallCallerDisplayname;
	public String xmmcallTime;
	/** 会议 */
	public String xmmiName;
	public String xmmiGuid;
	public String xmmcallStatus;

	public String getXmmcallGuid() {
		return xmmcallGuid;
	}

	public void setXmmcallGuid(String xmmcallGuid) {
		this.xmmcallGuid = xmmcallGuid;
	}

 

	public String getXmmcallCaller() {
		return xmmcallCaller;
	}

	public void setXmmcallCaller(String xmmcallCaller) {
		this.xmmcallCaller = xmmcallCaller;
	}

	public String getXmmcallMessage() {
		return xmmcallMessage;
	}

	public void setXmmcallMessage(String xmmcallMessage) {
		this.xmmcallMessage = xmmcallMessage;
	}

	public String getXmmcallCallerDisplayname() {
		return xmmcallCallerDisplayname;
	}

	public void setXmmcallCallerDisplayname(String xmmcallCallerDisplayname) {
		this.xmmcallCallerDisplayname = xmmcallCallerDisplayname;
	}

	public String getXmmcallTime() {
		return xmmcallTime;
	}

	public void setXmmcallTime(String xmmcallTime) {
		this.xmmcallTime = xmmcallTime;
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
		return "XmMeetingCallIVO [xmmcallGuid=" + xmmcallGuid
				+ ", xmmcallCaller=" + xmmcallCaller + ", xmmcallMessage="
				+ xmmcallMessage + ", xmmcallCallerDisplayname="
				+ xmmcallCallerDisplayname + ", xmmcallTime=" + xmmcallTime
				+ ", xmmiName=" + xmmiName + ", xmmiGuid=" + xmmiGuid
				+ ", xmmcallStatus=" + xmmcallStatus + "]";
	}

	public String getXmmcallStatus() {
		return xmmcallStatus;
	}

	public void setXmmcallStatus(String xmmcallStatus) {
		this.xmmcallStatus = xmmcallStatus;
	}

 

}
