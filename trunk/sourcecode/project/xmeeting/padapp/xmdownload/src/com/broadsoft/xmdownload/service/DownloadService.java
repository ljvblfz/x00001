package com.broadsoft.xmdownload.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.broadsoft.xmdownload.wsservice.WsServiceSupport;

/**
 * 下载服务
 * 
 * @author lu.zhen
 * 
 */
public class DownloadService extends Service {
	private String TAG = "TestService";

	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate begin");
		super.onCreate();
		// 监听websocket消息
		WsServiceSupport.getInstance().initData(getAndroidID());
		WsServiceSupport.getInstance().connect();

		Log.d(TAG, "onCreate end");

	}

	private String getAndroidID() {
		String padId = "000000000XMMEETINGINFO13041820484043";
		return padId;
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy begin");
		super.onDestroy();
		WsServiceSupport.getInstance().disconnect(); 
		Log.d(TAG, "onDestroy end");

	}

	public IBinder onBind(Intent intent) {
		Log.d(TAG, "onBind begin");
		Log.d(TAG, "onBind end");
		return null;
	}
}