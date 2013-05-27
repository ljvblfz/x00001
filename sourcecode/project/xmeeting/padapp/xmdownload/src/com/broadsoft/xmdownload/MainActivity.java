package com.broadsoft.xmdownload;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.broadsoft.xmdownload.appsupport.AppInitSupport;


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
		Log.d(TAG, "onCreate end");
		
	}//end of onCreate 



	
	
	

	@Override
	protected void onDestroy(){
		Log.d(TAG, "onDestroy begin");
		super.onDestroy();
		AppInitSupport.destroyApp(this.getApplicationContext(),this.getAssets());
		Log.d(TAG, "onDestroy end");
	}
}//end of MainActivity
