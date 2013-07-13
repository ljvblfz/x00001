package com.broadsoft.xmcommon.appsupport;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.AppConfig;
import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androidsdcard.SDCardSupport;
import com.broadsoft.xmcommon.androidutil.AndroidIdSupport;
import com.broadsoft.xmeeting.wsservice.WsControllerServiceSupport;

public class AppInitSupport {
	private static final String TAG="AppInitSupport"; 


	public static void initApp(Context ctx,AssetManager assetManager) {
		//初始化配置
		DomAppConfigFactory.init(assetManager);
		AppConfig appConfig=DomAppConfigFactory.getAppConfig();
		Log.d(TAG, "[Config]AppConfig---->"+appConfig);
		//初始化设备ID
		AndroidIdSupport.init(ctx);
		Log.d(TAG, "[AndroidI]AndroidID---->"+AndroidIdSupport.getAndroidID()); 
		//SD card
		String sdcardDir = SDCardSupport.getSDCardDirectory();
		Log.d(TAG, "[SDCard]sdcardDir---->"+sdcardDir);   
		//WS service
//		WsControllerServiceSupport.getInstance().initData(meetingId, memberId, memberDisplayName)

	}//end of initApp
 
	
	

	public static void destroyApp(Context ctx,AssetManager assetManager) { 
		WsControllerServiceSupport.getInstance().disconnect();
	}
}
