package com.broadsoft.xmdownload;
 
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.BaseAdapter;
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
		
		Log.d(TAG, "onCreate end");
		
	}//end of onCreate 



	
	
	

	@Override
	protected void onDestroy(){
		Log.d(TAG, "onDestroy begin");
		super.onDestroy();
//		AppInitSupport.destroyApp(this.getApplicationContext(),this.getAssets());
//		try {
//			Thread.sleep(10*1000);
//		} catch (InterruptedException e) { 
//			e.printStackTrace();
//		}
		Log.d(TAG, "onDestroy end");
	}
}//end of MainActivity
