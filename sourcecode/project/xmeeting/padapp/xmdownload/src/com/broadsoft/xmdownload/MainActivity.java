package com.broadsoft.xmdownload;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.broadsoft.xmcommon.androiddao.DaoHolder;
import com.broadsoft.xmcommon.androiddao.DownloadInfoEntity;
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
		// 监听websocket消息
		WsServiceSupport.getInstance().initData(AndroidIdSupport.getAndroidID());
		WsServiceSupport.getInstance().connect(); 
		// 初始化数据库		
		DaoHolder.getInstance().init(getApplicationContext());
		String meetingId="000000000XMMEETINGINFO13041820484043";
		DownloadInfoEntity entity=DaoHolder.getInstance().getDownloadInfoDao().findByMeetingId(meetingId);
		Log.d(TAG, "[meetingId= "+meetingId+"]DownloadInfoEntity---->"+entity);
	}//end of onCreate 
	

	@Override
	protected void onDestroy(){
		super.onDestroy();
		WsServiceSupport.getInstance().disconnect();
	}
}//end of MainActivity
