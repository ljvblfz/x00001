package com.broadsoft.xmcommon.androidutil;

import android.content.Context;
import android.provider.Settings.Secure;
import android.util.Log;

public class AndroidIdSupport { 
	

	private static final String TAG="AndroidIdSupport"; 
	private static String androidId;
	public static void init(Context ctx){
		Log.d(TAG, "init begin");
		androidId = Secure.getString(ctx.getContentResolver(), Secure.ANDROID_ID);  
		Log.d(TAG, "init end.  androidId------->"+androidId);
	}
	
	public static String getAndroidID() {
//		if(null!=androidId&&!androidId.isEmpty()){
//			return androidId;
//		}else{
//			return "android_0001"; 
//		} 
		
		return "android_0001"; 
	} //end of getAndroidID
	
	
	
}
