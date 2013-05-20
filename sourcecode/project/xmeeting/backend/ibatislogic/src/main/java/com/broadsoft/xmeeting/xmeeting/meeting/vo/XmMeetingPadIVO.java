package com.broadsoft.xmeeting.xmeeting.meeting.vo;

public class XmMeetingPadIVO { 
	/** 会议PID */
	public String xmmiGuid; 
	/** 设备资产编号 */
	public String xmpdCode;  
	/** Android ID */
	public String xmpdDeviceId;
	public String getXmmiGuid() {
		return xmmiGuid;
	}
	public void setXmmiGuid(String xmmiGuid) {
		this.xmmiGuid = xmmiGuid;
	}
	public String getXmpdCode() {
		return xmpdCode;
	}
	public void setXmpdCode(String xmpdCode) {
		this.xmpdCode = xmpdCode;
	}
	public String getXmpdDeviceId() {
		return xmpdDeviceId;
	}
	public void setXmpdDeviceId(String xmpdDeviceId) {
		this.xmpdDeviceId = xmpdDeviceId;
	}
	@Override
	public String toString() {
		return "XmMeetingPadIVO [xmmiGuid=" + xmmiGuid + ", xmpdCode="
				+ xmpdCode + ", xmpdDeviceId=" + xmpdDeviceId + "]";
	} 
	
	 
 
	
	
}
