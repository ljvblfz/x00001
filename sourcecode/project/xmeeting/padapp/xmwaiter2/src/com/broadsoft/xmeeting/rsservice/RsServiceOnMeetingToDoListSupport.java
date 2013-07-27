package com.broadsoft.xmeeting.rsservice;

import java.text.MessageFormat;

import org.json.JSONObject;

import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androidhttp.HttpRestSupport;

public class RsServiceOnMeetingToDoListSupport {
	private static final String TAG="RsServiceOnMeetingToDoListSupport";  
	private final static String rspathMeetingToDoList="http://{0}/xmeeting/rs/open/xmMeetingCall/list/xmmiGuid/{1}";  
	private final static String processToDoCall="http://{0}/xmeeting/rs/open/xmMeetingCall/process/xmmcallGuid/{1}";  
	
	public static JSONObject requestMeetingToDoList(String meetingId){ 
		Log.d(TAG, String.format("RsServiceOnMeetingToDoListSupport begin, meetingId:  %s", meetingId));
		// 
		String serveriport=DomAppConfigFactory.getAppConfig().getServeripport();
		String[] arguments={serveriport,meetingId}; 
		 
		String rspathMeetingToDoListResult = MessageFormat.format(rspathMeetingToDoList,arguments);  
		Log.d(TAG, "rspathMeetingToDoListResult  is : "+rspathMeetingToDoListResult); 
		try {
			JSONObject jsonRespMeetingToDoList=HttpRestSupport.getByHttpClientWithGzip(rspathMeetingToDoListResult);
			Log.d(TAG,"RsServiceOnMeetingToDoListSupport jsonRespMeetingToDoList----->"+ jsonRespMeetingToDoList.toString());
			return jsonRespMeetingToDoList;
		} catch (Exception e) { 
			e.printStackTrace();
			Log.d(TAG,"RsServiceOnMeetingToDoListSupport raise the error----->"+ e.getMessage());
		} 
		Log.d(TAG, "RsServiceOnMeetingToDoListSupport end"); 
		return null;
	}//end of requestMeetingToDoList
	
	public static String processToDo(String callId){ 
		Log.d(TAG, String.format("processToDo begin, callId:  %s", callId));
		String serveriport=DomAppConfigFactory.getAppConfig().getServeripport();
		String[] arguments={serveriport,callId}; 
		 
		String rspathMeetingToDoListResult = MessageFormat.format(processToDoCall,arguments);  
		try {
			 HttpRestSupport.getByHttpClientWithGzip(rspathMeetingToDoListResult);
			Log.d(TAG, "processToDo end"); 
		} catch (Exception e) { 
			e.printStackTrace();
			return "0";
		} 
		return "1";
	}//end of processToDo
	
	
}//end of RsServiceOnMeetingToDoListSupport
