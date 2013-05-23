package com.broadsoft.xmdownload;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.AppConfig;
import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androiddao.DaoHolder;
import com.broadsoft.xmcommon.androiddao.DownloadInfoEntity;
import com.broadsoft.xmcommon.androidsdcard.SDCardSupport;
import com.broadsoft.xmcommon.androidutil.AndroidIdSupport;
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
		//初始化配置
		DomAppConfigFactory.init(getAssets());
		AppConfig appConfig=DomAppConfigFactory.getAppConfig();
		Log.d(TAG, "[Config]AppConfig---->"+appConfig);

		String sdcardDir = SDCardSupport.getSDCardDirectory();
		Log.d(TAG, "[Dir]sdcardDir---->"+sdcardDir);
		// 监听websocket消息
		WsServiceSupport.getInstance().initData(AndroidIdSupport.getAndroidID());
		WsServiceSupport.getInstance().disconnect();
		WsServiceSupport.getInstance().connect(); 
		Log.d(TAG, "[WS]connect---->done.");
		// 初始化数据库		
		DaoHolder.getInstance().init(getApplicationContext()); 
		DownloadInfoEntity entity=DaoHolder.getInstance().getDownloadInfoDao().findByActivate();
		Log.d(TAG, "[Activate]DownloadInfoEntity---->"+entity);
	}//end of onCreate 
	

	@Override
	protected void onDestroy(){
		super.onDestroy();
		WsServiceSupport.getInstance().disconnect();
	}
}//end of MainActivity
