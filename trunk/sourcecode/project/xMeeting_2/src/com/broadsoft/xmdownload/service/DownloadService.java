package com.broadsoft.xmdownload.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.broadsoft.xmcommon.appsupport.AppInitSupport;

/**
 * 下载服务
 * 
 * @author lu.zhen
 * 
 */
public class DownloadService extends Service {
	private String TAG = "DownloadService";

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate begin");
		super.onCreate(); 
//		DomAppConfigFactory.init(getAssets());
//		// 监听websocket消息
//		WsServiceSupport.getInstance().initData(AndroidIdSupport.getAndroidID());
//		WsServiceSupport.getInstance().connect();
//		// 初始化数据库		
//		DaoHolder.getInstance().init(getApplicationContext()); 

		AppInitSupport.initApp(this.getApplicationContext(), this.getAssets()); 
		Log.d(TAG, "onCreate end"); 
	}

	

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy begin");
		super.onDestroy(); 
		AppInitSupport.destroyApp(this.getApplicationContext(), this.getAssets());
		Log.d(TAG, "onDestroy end");

	}

	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind begin");
		Log.d(TAG, "onBind end");
		return null;
	}
}