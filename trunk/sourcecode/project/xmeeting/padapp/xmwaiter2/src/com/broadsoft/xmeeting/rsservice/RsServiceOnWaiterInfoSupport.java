package com.broadsoft.xmeeting.rsservice;

import java.text.MessageFormat;

import org.json.JSONObject;

import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androidhttp.HttpRestSupport;
import com.broadsoft.xmcommon.androidutil.AndroidIdSupport;

public class RsServiceOnWaiterInfoSupport {
	private static final String TAG="RsServiceOnWaiterInfoSupport";  
	private final static String rspathWaiterInfo="http://{0}/xmeeting/rs/open/service/personnel/xmpdDeviceId/{1}";  
	
	public static JSONObject requestWaiterInfo(){
		String androidId=AndroidIdSupport.getAndroidID();
		Log.d(TAG, String.format("requestWaiterInfo begin, deviceId:  %s", androidId));
		// 
		String serveriport=DomAppConfigFactory.getAppConfig().getServeripport();
		String[] arguments={serveriport,androidId}; 
		 
		String rspathWaiterInfoResult = MessageFormat.format(rspathWaiterInfo,arguments);  
		Log.d(TAG, "rspathWaiterInfoResult  is : "+rspathWaiterInfoResult); 
		try {
			JSONObject jsonRespSendSms=HttpRestSupport.getByHttpClientWithGzip(rspathWaiterInfoResult);
			Log.d(TAG,"requestWaiterInfo jsonRespSendSms----->"+ jsonRespSendSms.toString());
			return jsonRespSendSms;
		} catch (Exception e) { 
			e.printStackTrace();
			Log.d(TAG,"requestWaiterInfo raise the error----->"+ e.getMessage());
		} 
		Log.d(TAG, "requestWaiterInfo end"); 
		return null;
	}//end of requestWaiterInfo
	
	 
	
}//end of RsServiceOnWaiterInfoSupport
