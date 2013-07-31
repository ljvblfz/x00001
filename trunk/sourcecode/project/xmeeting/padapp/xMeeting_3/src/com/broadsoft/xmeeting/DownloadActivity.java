package com.broadsoft.xmeeting;
 
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.broadsoft.xmcommon.androiddao.DownloadInfoEntity;
import com.broadsoft.xmcommon.androiddao.EntityInfoHolder;
import com.broadsoft.xmcommon.androidnetwork.NetworkSupport;
import com.broadsoft.xmcommon.androidutil.AndroidIdSupport;
import com.broadsoft.xmcommon.appsupport.AppInitSupport;
import com.broadsoft.xmdownload.adapter.MeetingInfoLVButtonAdapter;
import com.broadsoft.xmdownload.rsservice.RsServiceOnPadInfoSupport;
import com.broadsoft.xmdownload.wsservice.WsDownloadServiceSupport;
import com.broadsoft.xmeeting.uihandler.DownloadByHandUIHandler;
import com.broadsoft.xmeeting.uihandler.DownloadByWsUIHandler;
import com.broadsoft.xmeeting.uihandler.DownloadOnlineStatusUIHandler; 


/**
 * 测试下载数据
 * @author lu.zhen
 *
 */
public class DownloadActivity extends Activity implements Runnable{
	private static final String TAG="DownloadActivity"; 


	private static int REQUEST_CODE = 2; 
	 
//	private ToggleButton  toggleBtnDownload ;
	
	 

	private long timeOfRetry=20*1000;
	protected boolean isConnected(){
		ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		return NetworkSupport.isConnected(connMgr);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate begin");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download_activity_main); 
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//
//		AppInitSupport.initApp(this.getFriendContext(),this.getAssets()); 
		AppInitSupport.initApp(this.getApplicationContext(),this.getAssets());  
		//设备ID
		String androidId=AndroidIdSupport.getAndroidID();
		TextView tvAndroidId=(TextView)this.findViewById(R.id.textViewDeviceId);
		tvAndroidId.setText(androidId);   
		//设备Code
		try {
			String padAssetCode=EntityInfoHolder.getInstance().getAssetCode(); 
			TextView textViewDeviceCode=(TextView)this.findViewById(R.id.textViewDeviceCode);
			if(null!=padAssetCode&&!"".equals(padAssetCode)){ 
				textViewDeviceCode.setText(padAssetCode);
				textViewDeviceCode.setTextColor(Color.BLUE);
			}else{
				textViewDeviceCode.setText("没有设备编号,请同步设备信息!");
				textViewDeviceCode.setTextColor(Color.RED); 
			}
		} catch (JSONException e) { 
			e.printStackTrace();
		}
		//Wifi连接状态 
		TextView tvValueWifiStatus=(TextView)this.findViewById(R.id.tvValueWifiStatus);
    	if(isConnected()){ 
    		tvValueWifiStatus.setText("Wifi连接成功!");
    		tvValueWifiStatus.setTextColor(Color.GREEN);
    	}else{
    		tvValueWifiStatus.setText("Wifi连接失败!");
    		tvValueWifiStatus.setTextColor(Color.RED);
		}

		handlerCheckingWifi.postDelayed(this,timeOfRetry );
    	//同步设备信息
    	Button buttonSyncDeviceInfo=(Button)this.findViewById(R.id.buttonSyncDeviceInfo);
    	buttonSyncDeviceInfo.setOnClickListener(new OnClickListener() {      
            public void onClick(View v) {  
            	RsServiceOnPadInfoSupport.download(); 
            	
            }  //end of onClick
        });       
    	
		//会议列表
		ListView lvMeetingInfo=(ListView)this.findViewById(R.id.lvMeetingList);   
		MeetingInfoLVButtonAdapter meetingListItemAdapter = new MeetingInfoLVButtonAdapter(this);   
        lvMeetingInfo.setAdapter(meetingListItemAdapter);   
		//UI handler
		DownloadByWsUIHandler.init(this,meetingListItemAdapter);
        DownloadByHandUIHandler.init(this, meetingListItemAdapter);
        DownloadOnlineStatusUIHandler.init(this);
        //connect
        WsDownloadServiceSupport.getInstance().connect(); 
        //  
		Button btnEntryActivateMeeting =(Button)findViewById(R.id.btnEntryActivateMeeting);
		btnEntryActivateMeeting.setOnClickListener(new OnClickListener() {      
            public void onClick(View v) {       
            	DownloadInfoEntity downloadInfoEntity=EntityInfoHolder.getInstance().getDownloadInfoEntity();
            	if(null!=downloadInfoEntity){
                	//TODO:fixme
            		Intent intent = new Intent();
            		intent.setClass(DownloadActivity.this, LoginActivity.class); 
            		intent.setData(Uri.parse("one")); 
            		startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity 
            		finish(); 
            	}else{ 
					Toast toast=Toast.makeText(DownloadActivity.this, "请先激活的会议!",Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
            	}
            }   
        });    
		
//		new CheckOnlineStatusTask().execute();
		Log.d(TAG, "onCreate end");
		
	}//end of onCreate  
	
	
	private boolean flagOnHandler=true;

	private Handler handlerCheckingWifi = new Handler(); 
	
	

	@Override
	public void run() {
		if(!flagOnHandler){
			return;
		}
		Log.d(TAG, "[run]check the wifi status.");
		TextView tvValueWifiStatus=(TextView)this.findViewById(R.id.tvValueWifiStatus);
    	if(isConnected()){ 
    		tvValueWifiStatus.setText("Wifi连接成功!");
    		tvValueWifiStatus.setTextColor(Color.GREEN);
    	}else{
    		tvValueWifiStatus.setText("Wifi连接失败!");
    		tvValueWifiStatus.setTextColor(Color.RED);
		}
		handlerCheckingWifi.postDelayed(this,timeOfRetry );
	}
	  
	

	@Override
	protected void onDestroy(){ 
		Log.d(TAG, "[onDestroy]begin.");
		super.onDestroy();  
		flagOnHandler=false;
		WsDownloadServiceSupport.getInstance().setKeepAlive(false);
		WsDownloadServiceSupport.getInstance().disconnect();
		//
		DownloadByWsUIHandler.destroy();
        DownloadByHandUIHandler.destroy();
        DownloadOnlineStatusUIHandler.destroy();
		Log.d(TAG, "[onDestroy]end.");
		
	}
}//end of MainActivity
