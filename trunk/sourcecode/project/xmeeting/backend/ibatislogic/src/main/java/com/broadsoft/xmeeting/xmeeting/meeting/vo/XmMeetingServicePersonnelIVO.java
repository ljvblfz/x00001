package com.broadsoft.xmeeting.xmeeting.meeting.vo;

public class XmMeetingServicePersonnelIVO {

	public String xmmspGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 人员 */
	public String xmpiName;
	public String xmpiGuid;
	public String xmpdGuid;
	
	/** 人员会议角色 */
	public String serviceRole;

	public String getXmmspGuid() {
		return xmmspGuid;
	}

	public void setXmmspGuid(String xmmspGuid) {
		this.xmmspGuid = xmmspGuid;
	}

	public String getXmmiGuid() {
		return xmmiGuid;
	}

	public void setXmmiGuid(String xmmiGuid) {
		this.xmmiGuid = xmmiGuid;
	}

	public String getXmpiName() {
		return xmpiName;
	}

	public void setXmpiName(String xmpiName) {
		this.xmpiName = xmpiName;
	}

	public String getServiceRole() {
		return serviceRole;
	}

	public void setServiceRole(String serviceRole) {
		this.serviceRole = serviceRole;
	}

	public String getXmpiGuid() {
		return xmpiGuid;
	}

	public void setXmpiGuid(String xmpiGuid) {
		this.xmpiGuid = xmpiGuid;
	}

	@Override
	public String toString() {
		return "XmMeetingServicePersonnelIVO [xmmspGuid=" + xmmspGuid
				+ ", xmmiGuid=" + xmmiGuid + ", xmpiName=" + xmpiName
				+ ", xmpiGuid=" + xmpiGuid + ", serviceRole=" + serviceRole
				+ "]";
	}

}
