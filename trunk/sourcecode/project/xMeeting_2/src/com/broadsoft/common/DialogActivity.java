package com.broadsoft.common;


import com.broadsoft.xmeeting.DesktopActivity;
import com.broadsoft.xmeeting.R;
import com.broadsoft.xmeeting.WelcomeActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DialogActivity extends Activity {
	//private MyDialog dialog;
	private LinearLayout layout;
	private Activity act;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog);
	
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
        	  try {Thread.sleep(1000);
              } catch (InterruptedException e) 
              {}
			return null;
       
          }

  		@Override
          protected void onPostExecute(String[] result) {

            
  			finish();
  		}
  	}
	
}
