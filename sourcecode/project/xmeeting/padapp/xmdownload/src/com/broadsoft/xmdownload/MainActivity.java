package com.broadsoft.xmdownload;

import android.app.Activity;
import android.os.Bundle;

import com.broadsoft.xmdownload.appsupport.AppInitSupport;
import com.broadsoft.xmdownload.wsservice.WsServiceSupport;


/**
 * 测试下载数据
 * @author lu.zhen
 *
 */
public class MainActivity extends Activity {
	private static final String TAG="MainActivity"; 

	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); 
		AppInitSupport.initApp(this.getApplicationContext(),this.getAssets());
		
	}//end of onCreate 



	
	
	

	@Override
	protected void onDestroy(){
		super.onDestroy();
		WsServiceSupport.getInstance().disconnect();
	}
}//end of MainActivity
