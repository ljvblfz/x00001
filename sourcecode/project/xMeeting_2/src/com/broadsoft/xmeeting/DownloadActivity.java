package com.broadsoft.xmeeting;
 
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.broadsoft.xmcommon.androidutil.AndroidIdSupport;
import com.broadsoft.xmcommon.appsupport.AppInitSupport;
import com.broadsoft.xmdownload.adapter.MeetingInfoLVButtonAdapter;
import com.broadsoft.xmdownload.wsservice.WsDownloadServiceSupport;
import com.broadsoft.xmeeting.uihandler.DownloadByHandUIHandler;
import com.broadsoft.xmeeting.uihandler.DownloadByWsUIHandler;


/**
 * 测试下载数据
 * @author lu.zhen
 *
 */
public class DownloadActivity extends Activity {
	private static final String TAG="DownloadActivity"; 


	private static int REQUEST_CODE = 2; 
	 
	private ToggleButton  toggleBtnDownload ;
	
	
	
//	protected Context getFriendContext(){
//		Context friendContext=null;
//		String packageName="com.broadsoft.xmdownload";
//		try {
//			friendContext = this.createPackageContext(packageName,Context.CONTEXT_IGNORE_SECURITY);
//		} catch (NameNotFoundException e) { 
//			e.printStackTrace();
//			Log.e(TAG, "getFriendContext raise the exception:  "+e.getMessage());
//		}
//		Log.d(TAG, "friendContext is:  "+friendContext);
//		return friendContext;
//	}//end of getFriendContext
	
	
	
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
		//会议列表
		ListView lvMeetingInfo=(ListView)this.findViewById(R.id.lvMeetingList);   
		MeetingInfoLVButtonAdapter meetingListItemAdapter = new MeetingInfoLVButtonAdapter(this);   
        lvMeetingInfo.setAdapter(meetingListItemAdapter);   
		//UI handler
		DownloadByWsUIHandler.init(this);
        DownloadByHandUIHandler.init(this, meetingListItemAdapter);
        //
		toggleBtnDownload = (ToggleButton ) findViewById(R.id.toggleBtnDownload);
		toggleBtnDownload.setOnClickListener(new OnClickListener() {      
            public void onClick(View v) {      
                if (toggleBtnDownload.isChecked()) {              
                	WsDownloadServiceSupport.getInstance().connect(); 
                }else {          
                    WsDownloadServiceSupport.getInstance().disconnect();    
                }      
            }  //end of onClick
        });     
		
		Button btnEntryActivateMeeting =(Button)findViewById(R.id.btnEntryActivateMeeting);
		btnEntryActivateMeeting.setOnClickListener(new OnClickListener() {      
            public void onClick(View v) {       
            	//TODO:fixme
        		Intent intent = new Intent();
        		intent.setClass(DownloadActivity.this, LoginActivity.class); 
        		intent.setData(Uri.parse("one")); 
        		startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity 
            }   
        });    
		Log.d(TAG, "onCreate end");
		
	}//end of onCreate  
	

	@Override
	protected void onDestroy(){ 
		super.onDestroy();  
	}
}//end of MainActivity
