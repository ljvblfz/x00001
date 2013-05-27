package com.broadsoft.appsupport;

import android.content.Context;
import android.provider.Settings.Secure; 


/**
 * 
 * @author lu.zhen
 *
 */
public class AndroidSupport {

	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static String getAndroidId(Context context){ 
		String androidid=Secure.getString(context.getContentResolver(),Secure.ANDROID_ID); 
		return androidid;
	}
	
	
	public static String getDeviceId(){
	 return "";
	}
}
