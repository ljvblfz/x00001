package com.broadsoft.xmeeting;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.broadsoft.common.BaseActivity;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

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
 
		

		new GetDataTask().execute();
	}
	
	




	//执行异步的操作
  	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

  		  @Override  
          protected void onPreExecute() {  
              //第一个执行方法  
              super.onPreExecute();  
          }  
  		  
  		  
          @Override
          protected String[] doInBackground(Void... params) {
              // Simulates a background job.
        	  try {Thread.sleep(3000);
              } catch (InterruptedException e) 
              {}
			return null;
       
          }

  		@Override
          protected void onPostExecute(String[] result) {

            //Call onRefreshComplete when the list has been refreshed.
  			Intent intent = new Intent();
  			intent.setClass(WelcomeActivity.this, DesktopActivity.class);// 指定了跳转前的Activity和跳转后的Activity
  			intent.setData(Uri.parse("one"));// 向下一个Activity传递了string类型参数"one"
  			startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity
  			finish();
  		}
          
    }


 
	
	//

  



}
