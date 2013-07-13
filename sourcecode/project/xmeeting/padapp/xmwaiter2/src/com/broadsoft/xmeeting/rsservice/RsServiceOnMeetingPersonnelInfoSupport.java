package com.broadsoft.xmeeting.rsservice;

import java.text.MessageFormat;

import org.json.JSONObject;

import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androidhttp.HttpRestSupport;

public class RsServiceOnMeetingPersonnelInfoSupport {
	private static final String TAG="RsServiceOnMeetingPersonnelInfoSupport";  
	private final static String rspathMeetingPersonnelInfo="http://{0}/xmeeting/rs/open/meetingpersonnel/download/xmmiGuid/{1}";  
	
	public static JSONObject requestMeetingPersonnelInfo(String meetingId){ 
		Log.d(TAG, String.format("requestMeetingPersonnelInfo begin, meetingId:  %s", meetingId));
		// 
		String serveriport=DomAppConfigFactory.getAppConfig().getServeripport();
		String[] arguments={serveriport,meetingId}; 
		 
		String rspathMeetingPersonnelInfoResult = MessageFormat.format(rspathMeetingPersonnelInfo,arguments);  
		Log.d(TAG, "rspathMeetingPersonnelInfoResult  is : "+rspathMeetingPersonnelInfoResult); 
		try {
			JSONObject jsonRespMeetingPersonnel=HttpRestSupport.getByHttpClientWithGzip(rspathMeetingPersonnelInfoResult);
			Log.d(TAG,"requestMeetingPersonnelInfo jsonRespMeetingPersonnel----->"+ jsonRespMeetingPersonnel.toString());
			return jsonRespMeetingPersonnel;
		} catch (Exception e) { 
			e.printStackTrace();
			Log.d(TAG,"requestMeetingPersonnelInfo raise the error----->"+ e.getMessage());
		} 
		Log.d(TAG, "requestMeetingPersonnelInfo end"); 
		return null;
	}//end of requestWaiterInfo
	
	 
	
}//end of RsServiceOnWaiterInfoSupport
