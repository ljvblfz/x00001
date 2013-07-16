package com.broadsoft.xmeeting.uihandler;

import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.broadsoft.xmeeting.R;

public class DownloadOnlineStatusUIHandler extends Handler {
	private final String TAG = "DownloadOnlineStatusUIHandler";
	private Activity act;

	private static DownloadOnlineStatusUIHandler downloadOnlineStatusUIHandler;
	public DownloadOnlineStatusUIHandler(Activity act) {
		this.act = act;
	}
	
	
	public static void init(Activity act){
		if(null==downloadOnlineStatusUIHandler){
			downloadOnlineStatusUIHandler=new DownloadOnlineStatusUIHandler(act);
		} 
	}//end of init
	
	
	public static DownloadOnlineStatusUIHandler getInstance(){
		return downloadOnlineStatusUIHandler;
	}


	@Override
	public void handleMessage(Message msg) {
		Log.d(TAG, "handleMessage begin");
		super.handleMessage(msg);
		Bundle bundle = msg.getData();
		String payload = bundle.getString("payload");
		TextView tvOnlineStatus=(TextView)act.findViewById(R.id.tvValueOnlineStatus);
		try {
			JSONObject jo = new JSONObject(payload);
			if(jo.has("status")){ 
				String status=jo.getString("status");
				if("1".equals(status)){//在线
					tvOnlineStatus.setText("在线");
				} else{//离线
					tvOnlineStatus.setText("离线");
					
				}
			}//end of if
		} catch (JSONException e) { 
			e.printStackTrace();
		}
		Log.d(TAG, "handleMessage end");
	}
	
	
	public static String getCurrentTime() {
		String parrten="MM-dd HH:mm";
		String timestr; 
		java.util.Date cday = new java.util.Date();

		SimpleDateFormat sdf = new SimpleDateFormat(parrten);
		timestr = sdf.format(cday);
		return timestr;
	}
	
	public void sendDownloadOnlineMessage(String payload){ 
		Message msg = new Message();  
        Bundle bundle = new Bundle();  
        bundle.putString("payload", payload);  
        msg.setData(bundle);   
        sendMessage(msg);
	}
  
	
}
