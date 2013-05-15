package com.broadsoft.xmeeting.xmeeting.meeting.vo;

public class XmMeetingMessageIVO {

	private String xmmmGuid;
	private String xmmmFrom;
	private String xmmmTo;
	private String xmmmFromDisplayname;
	private String xmmmToDisplayname;  
	private String xmmmMessage; 
	private String xmmmTime; 
	
	
	
	
	/** 会议 */
	private String xmmiName;
	private String xmmiGuid;
	public String getXmmmGuid() {
		return xmmmGuid;
	}
	public void setXmmmGuid(String xmmmGuid) {
		this.xmmmGuid = xmmmGuid;
	}
	public String getXmmmFrom() {
		return xmmmFrom;
	}
	public void setXmmmFrom(String xmmmFrom) {
		this.xmmmFrom = xmmmFrom;
	}
	public String getXmmmTo() {
		return xmmmTo;
	}
	public void setXmmmTo(String xmmmTo) {
		this.xmmmTo = xmmmTo;
	}
	public String getXmmmFromDisplayname() {
		return xmmmFromDisplayname;
	}
	public void setXmmmFromDisplayname(String xmmmFromDisplayname) {
		this.xmmmFromDisplayname = xmmmFromDisplayname;
	}
	public String getXmmmToDisplayname() {
		return xmmmToDisplayname;
	}
	public void setXmmmToDisplayname(String xmmmToDisplayname) {
		this.xmmmToDisplayname = xmmmToDisplayname;
	}
	public String getXmmmMessage() {
		return xmmmMessage;
	}
	public void setXmmmMessage(String xmmmMessage) {
		this.xmmmMessage = xmmmMessage;
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
	public String getXmmmTime() {
		return xmmmTime;
	}
	public void setXmmmTime(String xmmmTime) {
		this.xmmmTime = xmmmTime;
	}
	@Override
	public String toString() {
		return "XmMeetingMessageIVO [xmmmGuid=" + xmmmGuid + ", xmmmFrom="
				+ xmmmFrom + ", xmmmTo=" + xmmmTo + ", xmmmFromDisplayname="
				+ xmmmFromDisplayname + ", xmmmToDisplayname="
				+ xmmmToDisplayname + ", xmmmMessage=" + xmmmMessage
				+ ", xmmmTime=" + xmmmTime + ", xmmiName=" + xmmiName
				+ ", xmmiGuid=" + xmmiGuid + "]";
	}
 

 

}
