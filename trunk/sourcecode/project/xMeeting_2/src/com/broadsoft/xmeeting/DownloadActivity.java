package com.broadsoft.xmeeting;
 
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.broadsoft.xmcommon.androiddao.EntityInfoHolder;
import com.broadsoft.xmcommon.androidnetwork.NetworkSupport;
import com.broadsoft.xmcommon.androidutil.AndroidIdSupport;
import com.broadsoft.xmcommon.appsupport.AppInitSupport;
import com.broadsoft.xmdownload.adapter.MeetingInfoLVButtonAdapter;
import com.broadsoft.xmdownload.rsservice.RsServiceOnPadInfoSupport;
import com.broadsoft.xmdownload.wsservice.WsControllerServiceSupport;
import com.broadsoft.xmdownload.wsservice.WsDownloadServiceSupport;
import com.broadsoft.xmeeting.uihandler.DownloadByHandUIHandler;
import com.broadsoft.xmeeting.uihandler.DownloadByWsUIHandler;
import com.broadsoft.xmeeting.uihandler.DownloadOnlineStatusUIHandler;


/**
 * 测试下载数据
 * @author lu.zhen
 *
 */
public class DownloadActivity extends Activity {
	private static final String TAG="DownloadActivity"; 


	private static int REQUEST_CODE = 2; 
	 
	private ToggleButton  toggleBtnDownload ;
	
	 

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
			textViewDeviceCode.setText(padAssetCode);
		} catch (JSONException e) { 
			e.printStackTrace();
		}
		//Wifi连接状态 
		TextView tvValueWifiStatus=(TextView)this.findViewById(R.id.tvValueWifiStatus);
    	if(isConnected()){ 
    		tvValueWifiStatus.setText("Wifi连接成功!");
    	}else{
    		tvValueWifiStatus.setText("Wifi连接失败!");
		}
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
        //
        WsDownloadServiceSupport.getInstance().connect(); 
        //
//		toggleBtnDownload = (ToggleButton ) findViewById(R.id.toggleBtnDownload);
//		toggleBtnDownload.setOnClickListener(new OnClickListener() {      
//            public void onClick(View v) {    
//            	if(isConnected()){
//                    if (toggleBtnDownload.isChecked()) {              
//                    	WsDownloadServiceSupport.getInstance().connect(); 
//                    }else {          
//                        WsDownloadServiceSupport.getInstance().disconnect();    
//                    }     
//            	}else{
//					Toast toast=Toast.makeText(DownloadActivity.this, "网络不通,请检查wifi设置!",Toast.LENGTH_LONG);
//					toast.setGravity(Gravity.CENTER, 0, 0);
//					toast.show();
//				} //end of if else
//            }  //end of onClick
//        });     
		
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
		
		new CheckOnlineStatusTask().execute();
		Log.d(TAG, "onCreate end");
		
	}//end of onCreate  
	
	
	private boolean flagOnTask=true;
	
	private class CheckOnlineStatusTask extends AsyncTask<Void, Void, Void> { 
 
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			while (flagOnTask) {
				boolean status = WsControllerServiceSupport.getInstance().isConnected();
				JSONObject jsonMessage = createJsonMessage(status);
				DownloadOnlineStatusUIHandler.getInstance().sendDownloadOnlineMessage(jsonMessage.toString());
				try {
					Thread.sleep(100 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		private JSONObject createJsonMessage(boolean status) {
			JSONObject jsonStatus = new JSONObject();
			try {
				if (status) {
					jsonStatus.put("status", "1");
				} else {
					jsonStatus.put("status", "0"); 
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return jsonStatus;
		}

		@Override
		protected void onPostExecute(Void result) {
 

  		}
          
    }
	
	
	

	@Override
	protected void onDestroy(){ 
		flagOnTask=false;
		super.onDestroy();  
		
	}
}//end of MainActivity
