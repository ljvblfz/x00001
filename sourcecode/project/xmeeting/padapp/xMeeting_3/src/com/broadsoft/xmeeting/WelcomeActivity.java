package com.broadsoft.xmeeting;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

/**
 * http://blog.csdn.net/Class_Raito/article/details/3390737
 * http://blog.csdn.net/tody_guo/article/details/8164728
 * @author lu.zhen
 * 
 */
public class WelcomeActivity extends Activity  {
	
	private String TAG="WelcomeActivity";
	private static int REQUEST_CODE = 2;
//	View contentView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 应用程序开机不锁屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD,
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//
		setContentView(R.layout.welcome_activity_main); 
		
		int corePoolSize = 5;
		int maximumPoolSize = 5;
		int keepAliveTime = 10; 
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(maximumPoolSize);
		Executor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);
		new GotoDesktopActivityTask().executeOnExecutor(threadPoolExecutor);
	}
	
	




	//执行异步的操作,等待3秒
	private class GotoDesktopActivityTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected void onPreExecute() {
			Log.d(TAG, "onPreExecute begin"); 
			super.onPreExecute();
			Log.d(TAG, "onPreExecute end");
		}

		@Override
		protected String[] doInBackground(Void... params) {
			Log.d(TAG, "doInBackground begin");
			// Simulates a background job.
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Log.d(TAG, "doInBackground end");
			return null;

		}

		@Override
		protected void onPostExecute(String[] result) {
			Log.d(TAG, "onPostExecute begin");
			// Call onRefreshComplete when the list has been refreshed.
			Intent intent = new Intent();
			intent.setClass(WelcomeActivity.this, DesktopActivity.class); 
			intent.setData(Uri.parse("one")); 
			startActivityForResult(intent, REQUEST_CODE); 
			finish();
			Log.d(TAG, "onPostExecute end");
		}

	}


 
	
	//

  



}
