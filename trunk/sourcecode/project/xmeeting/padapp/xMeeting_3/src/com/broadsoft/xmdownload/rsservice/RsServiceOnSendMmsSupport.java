package com.broadsoft.xmdownload.rsservice;

import java.text.MessageFormat;

import org.json.JSONObject;

import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androidhttp.HttpRestSupport;

public class RsServiceOnSendMmsSupport {
	private static final String TAG="RsServiceOnSendMmsSupport";  
	private final static String rspathSendMms="http://{0}/xmeeting/rs/open/sendmms/xmmiGuid/{1}/mT0/{2}/fromName/{3}";  
	
	public static void sendMms(String meetingId,String mTo,String fromName){
		Log.d(TAG, String.format("sendMms begin, meetingId:  %s", meetingId));
		// 
		String serveriport=DomAppConfigFactory.getAppConfig().getServeripport();
		String[] arguments={serveriport,meetingId,mTo,fromName}; 
		 
		String rspathSendMmsResult = MessageFormat.format(rspathSendMms,arguments);  
		Log.d(TAG, "rspathSendMmsResult  is : "+rspathSendMmsResult); 
		try {
			JSONObject jsonRespSendSms=HttpRestSupport.getByHttpClientWithGzip(rspathSendMmsResult);
			Log.d(TAG,"sendMms jsonRespSendSms----->"+ jsonRespSendSms.toString());
		} catch (Exception e) { 
			e.printStackTrace();
			Log.d(TAG,"sendMms raise the error----->"+ e.getMessage());
		} 
		Log.d(TAG, "sendMms end"); 
	}//end of download
	
	 
	
}//end of DownloadMeetingInfoRunnable
