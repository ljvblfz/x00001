package com.broadsoft.xmdownload.daoservice;

import com.broadsoft.xmcommon.androiddao.DaoHolder;

import android.util.Log;

/**
 * 
 * @author lu.zhen
 *
 */
public class DaoServiceSupport {
	private static final String TAG="DaoServiceSupport";
	
	
	/**
	 * 
	 * @param meetingId
	 */
	public static void activate(String meetingId){
		Log.d(TAG, String.format("activate begin, meetingId:  %s", meetingId));
		 
		DaoHolder.getInstance().getDownloadInfoDao().activate(meetingId);
		
		Log.d(TAG, "activate end"); 
	}//end of download 
 

}


 
