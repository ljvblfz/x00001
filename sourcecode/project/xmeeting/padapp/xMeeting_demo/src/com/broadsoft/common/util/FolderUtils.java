package com.broadsoft.common.util;

import java.io.File;

import android.os.Environment;

public class FolderUtils {
	
	public static String demoMeetingId="10001";

	/**
	 * 获取sdcard 位置
	 * @return
	 */
	public static String getSDPath() {
		File sdDir = null;
//		boolean sdCardExist = Environment.getExternalStorageState().equals(
//				Android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
//		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
//		}
		return sdDir.getAbsolutePath();
	}

	
	public static String getHomeDir(){
		return getSDPath()+"/upload/xmeeting";

	}
	
	
	/**
	 * 获取文档目录路径
	 * @param meetingId
	 * @return
	 */
	public static String getDocumentDir(String meetingId){
		return getSDPath()+"/upload/xmeeting/"+meetingId+"/xmeeting_meetingdocument";
	}

	
	/**
	 * 获取视频目录路径
	 * @param meetingId
	 * @return
	 */
	public static String getVideoDir(String meetingId){
		return getSDPath()+"/upload/xmeeting/"+meetingId+"/xmeeting_meetingvideo";
	}
	
	
	/**
	    /upload/xmeeting/{meetingid}/xmeeting_meetingdocument
	    /upload/xmeeting/{meetingid}/xmeeting_meetingimage/{picGroupId}
	    /upload/xmeeting/{meetingid}/xmeeting_meetingvideo
	    /upload/xmeeting_companyinfo
	    
	 *
	 *  */
}
