package com.broadsoft.xmdownload.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.broadsoft.xmdownload.service.DownloadService;

public class BootReceiver extends BroadcastReceiver {
	
	private String TAG="BootReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {  
		Log.d(TAG, "onReceive begin"); 
		Intent mBootIntent = new Intent(context, DownloadService.class);
		context.startService(mBootIntent);
		Log.d(TAG, "onReceive end");
	}
}
