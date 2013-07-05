package com.broadsoft.xmeeting.xmeeting.meeting.vo;

public class ServicePersonnelPadIVO {

	public String xmmiGuid; 
	public String xmmiName; 
	public String xmpdGuid;
	public String xmpdDeviceId;
	public String xmpiName;
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
	public String getXmpdGuid() {
		return xmpdGuid;
	}
	public void setXmpdGuid(String xmpdGuid) {
		this.xmpdGuid = xmpdGuid;
	}
	public String getXmpdDeviceId() {
		return xmpdDeviceId;
	}
	public void setXmpdDeviceId(String xmpdDeviceId) {
		this.xmpdDeviceId = xmpdDeviceId;
	}
	public String getXmpiName() {
		return xmpiName;
	}
	public void setXmpiName(String xmpiName) {
		this.xmpiName = xmpiName;
	}
	
	
	@Override
	public String toString() {
		return "ServicePersonnelPadIVO [xmmiGuid=" + xmmiGuid + ", xmmiName="
				+ xmmiName + ", xmpdGuid=" + xmpdGuid + ", xmpdDeviceId="
				+ xmpdDeviceId + ", xmpiName=" + xmpiName + "]";
	}
	 

	 
}
