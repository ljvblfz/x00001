package com.broadsoft.xmeeting.po;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 会议信息
 * 
 */
@DatabaseTable(tableName = "downloadinfo")
public class DownloadinfoPO {
	@DatabaseField 
	public String meetingId;
	@DatabaseField
	public String status;
	@DatabaseField
	public String jsonData; 
	/** 最后一次访问时间 */
	@DatabaseField
	public long last_access_time;
	public String getMeetingId() {
		return meetingId;
	}
	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	public long getLast_access_time() {
		return last_access_time;
	}
	public void setLast_access_time(long last_access_time) {
		this.last_access_time = last_access_time;
	}
	 
}
