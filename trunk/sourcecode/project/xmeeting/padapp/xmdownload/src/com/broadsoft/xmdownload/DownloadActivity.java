package com.broadsoft.xmdownload;
 
import android.app.Activity;
import android.content.Intent;
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
import com.broadsoft.xmdownload.adapter.MeetingInfoLVButtonAdapter;
import com.broadsoft.xmdownload.appsupport.AppInitSupport;
import com.broadsoft.xmdownload.wsservice.WsDownloadServiceSupport;


/**
 * 测试下载数据
 * @author lu.zhen
 *
 */
public class DownloadActivity extends Activity {
	private static final String TAG="MainActivity"; 


	private static int REQUEST_CODE = 2; 
	 
	private ToggleButton  toggleBtnDownload ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate begin");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download_activity_main); 
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//
		AppInitSupport.initApp(this.getApplicationContext(),this.getAssets()); 
		//设备ID
		String androidId=AndroidIdSupport.getAndroidID();
		TextView tvAndroidId=(TextView)this.findViewById(R.id.textViewDeviceId);
		tvAndroidId.setText(androidId);  
		//UI handler
		DownloadUIHandler.init(this);
		
		//会议列表
		ListView lvMeetingInfo=(ListView)this.findViewById(R.id.lvMeetingList);   
        BaseAdapter meetingListItemAdapter = new MeetingInfoLVButtonAdapter(this);   
        lvMeetingInfo.setAdapter(meetingListItemAdapter);  
        //
		toggleBtnDownload = (ToggleButton ) findViewById(R.id.toggleBtnDownload);
		toggleBtnDownload.setOnClickListener(new OnClickListener() {      
            public void onClick(View v) {      
                if (toggleBtnDownload.isChecked()) {              
                	WsDownloadServiceSupport.getInstance().connect(); 
//                    Toast.makeText(DownloadActivity.this, "进入", Toast.LENGTH_LONG).show();         
                }else {          
                    WsDownloadServiceSupport.getInstance().disconnect();    
//                    Toast.makeText(DownloadActivity.this, "退出", Toast.LENGTH_LONG).show();  
                }      
            }  //end of onClick
        });     
		
		Button btnEntryActivateMeeting =(Button)findViewById(R.id.btnEntryActivateMeeting);
		btnEntryActivateMeeting.setOnClickListener(new OnClickListener() {      
            public void onClick(View v) {       
            	//TODO:fixme
        		Intent intent = new Intent();
//        		intent.setClass(DownloadActivity.this, LoginActivity.class); 
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
