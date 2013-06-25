package com.broadsoft.xmeeting.xmeeting.meeting.vo;

public class XmMeetingPersonnelSeatPadIVO {

	
	 
	public String xmmpspGuid;
	/** 会议PID */
	public String xmmiGuid;
	/** 人员 */
	public String xmpiName;
	public String xmpiGuid; 
	public String xmpiDeptinfo;
	public String xmpiTitle;
	public String xmpiContact;
	
	/** 设备资产编号 */
	public String xmpdCode;
	public String xmpdDeviceId;
	/** 会议室座位 */
	public String xmridSeatno;
	/** 人员会议角色 */
	public String meetingRole;
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
	public String getXmpdCode() {
		return xmpdCode;
	}
	public void setXmpdCode(String xmpdCode) {
		this.xmpdCode = xmpdCode;
	}
	public String getXmridSeatno() {
		return xmridSeatno;
	}
	public void setXmridSeatno(String xmridSeatno) {
		this.xmridSeatno = xmridSeatno;
	}
	public String getMeetingRole() {
		return meetingRole;
	}
	public void setMeetingRole(String meetingRole) {
		this.meetingRole = meetingRole;
	} 
	public String getXmmpspGuid() {
		return xmmpspGuid;
	}
	public void setXmmpspGuid(String xmmpspGuid) {
		this.xmmpspGuid = xmmpspGuid;
	}
	public String getXmpiGuid() {
		return xmpiGuid;
	}
	public void setXmpiGuid(String xmpiGuid) {
		this.xmpiGuid = xmpiGuid;
	}
	public String getXmpdDeviceId() {
		return xmpdDeviceId;
	}
	public void setXmpdDeviceId(String xmpdDeviceId) {
		this.xmpdDeviceId = xmpdDeviceId;
	}
	public String getXmpiDeptinfo() {
		return xmpiDeptinfo;
	}
	public void setXmpiDeptinfo(String xmpiDeptinfo) {
		this.xmpiDeptinfo = xmpiDeptinfo;
	}
	public String getXmpiTitle() {
		return xmpiTitle;
	}
	public void setXmpiTitle(String xmpiTitle) {
		this.xmpiTitle = xmpiTitle;
	}
	public String getXmpiContact() {
		return xmpiContact;
	}
	public void setXmpiContact(String xmpiContact) {
		this.xmpiContact = xmpiContact;
	} 
	@Override
	public String toString() {
		return "XmMeetingPersonnelSeatPadIVO [xmmpspGuid=" + xmmpspGuid
				+ ", xmmiGuid=" + xmmiGuid + ", xmpiName=" + xmpiName
				+ ", xmpiGuid=" + xmpiGuid + ", xmpiDeptinfo=" + xmpiDeptinfo
				+ ", xmpiTitle=" + xmpiTitle + ", xmpiContact=" + xmpiContact
				+ ", xmpdCode=" + xmpdCode + ", xmpdDeviceId=" + xmpdDeviceId
				+ ", xmridSeatno=" + xmridSeatno + ", meetingRole="
				+ meetingRole + "]";
	}
	
	
}
