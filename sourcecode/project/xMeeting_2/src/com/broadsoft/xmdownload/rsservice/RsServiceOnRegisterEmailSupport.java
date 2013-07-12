package com.broadsoft.xmdownload.rsservice;

import java.text.MessageFormat;

import org.json.JSONObject;

import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androidhttp.HttpRestSupport;

public class RsServiceOnRegisterEmailSupport {
	private static final String TAG="RsServiceOnRegisterEmailSupport";  
	private final static String rspathRegisterationEmail="http://{0}/xmeeting/rs/open/registeremail/xmmiGuid/{1}/toAddress/{2}/toName/{3}";  
	
	public static void registerEmail(String meetingId,String toAddress,String toName){
		Log.d(TAG, String.format("registerEmail begin, meetingId:  %s", meetingId));
		// 
		String serveriport=DomAppConfigFactory.getAppConfig().getServeripport();
		String[] arguments={serveriport,meetingId,toAddress,toName}; 
		 
		String rspathRegisterationEmailResult = MessageFormat.format(rspathRegisterationEmail,arguments);  
		Log.d(TAG, "rspathRegisterationEmailResult  is : "+rspathRegisterationEmailResult); 
		try {
			JSONObject jsonRespSendSms=HttpRestSupport.getByHttpClientWithGzip(rspathRegisterationEmailResult);
			Log.d(TAG,"registerEmail jsonRespSendSms----->"+ jsonRespSendSms.toString());
		} catch (Exception e) { 
			e.printStackTrace();
			Log.d(TAG,"registerEmail raise the error----->"+ e.getMessage());
		} 
		Log.d(TAG, "registerEmail end"); 
	}//end of download
	
	 
	
}//end of DownloadMeetingInfoRunnable
