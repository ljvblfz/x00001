package com.broadsoft.xmdownload;
 
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.broadsoft.xmcommon.androidutil.AndroidIdSupport;
import com.broadsoft.xmdownload.appsupport.AppInitSupport;
import com.broadsoft.xmdownload.wsservice.WsDownloadServiceSupport;


/**
 * 测试下载数据
 * @author lu.zhen
 *
 */
public class MainActivity extends Activity {
	private static final String TAG="MainActivity"; 

	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate begin");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); 
		AppInitSupport.initApp(this.getApplicationContext(),this.getAssets());
		//下载状态 
		TextView textViewDownloadStatus=(TextView)this.findViewById(R.id.textViewDownloadStatus);
		ViewHolder.getInstance().setTextViewDownloadStatus(textViewDownloadStatus);
		//设备ID
		String androidId=AndroidIdSupport.getAndroidID();
		TextView tvAndroidId=(TextView)this.findViewById(R.id.textViewDeviceId);
		tvAndroidId.setText(androidId);
		
		//
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		
		

		Button buttonExit = (Button) findViewById(R.id.buttonExit);
		buttonExit.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				WsDownloadServiceSupport.getInstance().disconnect();
			}
		});

		Button buttonEntry = (Button) findViewById(R.id.buttonEntry);
		
		buttonEntry.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				WsDownloadServiceSupport.getInstance().connect(); 
			}
		});
		
		Log.d(TAG, "onCreate end");
		
	}//end of onCreate 



	
	
	

	@Override
	protected void onDestroy(){
		Log.d(TAG, "onDestroy begin");
		super.onDestroy();
		AppInitSupport.destroyApp(this.getApplicationContext(),this.getAssets());
		try {
			Thread.sleep(10*1000);
		} catch (InterruptedException e) { 
			e.printStackTrace();
		}
		Log.d(TAG, "onDestroy end");
	}
}//end of MainActivity
