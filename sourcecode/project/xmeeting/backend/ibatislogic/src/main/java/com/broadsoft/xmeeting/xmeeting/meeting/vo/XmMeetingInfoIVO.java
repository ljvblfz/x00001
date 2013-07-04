package com.broadsoft.xmeeting.xmeeting.meeting.vo;

public class XmMeetingInfoIVO {

	
	  
	/** 会议PID */
	public String xmmiGuid; 
	public String xmmiName;  
	public String xmmiDescription; 
	 
	
	public String xmriGuid; 
	public String xmriName; 
	public String status;
	

	public String xmmiBeginDate;
	public String xmmiEndDate;
	
	public String getXmmiGuid() {
		return xmmiGuid;
	}
	public void setXmmiGuid(String xmmiGuid) {
		this.xmmiGuid = xmmiGuid;
	}
	public String getXmmiName() {
		return xmmiName;
	}
	public void setXmmiName(String xmmiName) {
		this.xmmiName = xmmiName;
	}
	public String getXmmiDescription() {
		return xmmiDescription;
	}
	public void setXmmiDescription(String xmmiDescription) {
		this.xmmiDescription = xmmiDescription;
	}
	public String getXmriGuid() {
		return xmriGuid;
	}
	public void setXmriGuid(String xmriGuid) {
		this.xmriGuid = xmriGuid;
	}
	public String getXmriName() {
		return xmriName;
	}
	public void setXmriName(String xmriName) {
		this.xmriName = xmriName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	public String getXmmiBeginDate() {
		return xmmiBeginDate;
	}
	public void setXmmiBeginDate(String xmmiBeginDate) {
		this.xmmiBeginDate = xmmiBeginDate;
	}
	public String getXmmiEndDate() {
		return xmmiEndDate;
	}
	public void setXmmiEndDate(String xmmiEndDate) {
		this.xmmiEndDate = xmmiEndDate;
	}
	@Override
	public String toString() {
		return "XmMeetingInfoIVO [xmmiGuid=" + xmmiGuid + ", xmmiName="
				+ xmmiName + ", xmmiDescription=" + xmmiDescription
				+ ", xmriGuid=" + xmriGuid + ", xmriName=" + xmriName
				+ ", status=" + status + ", xmmiBeginDate=" + xmmiBeginDate
				+ ", xmmiEndDate=" + xmmiEndDate + "]";
	} 
	
	
}
