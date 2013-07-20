package com.broadsoft.xmeeting.uihandler;

import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.founder.enforcer.R;

public class OnlineStatusUIHandler extends Handler {
	private final String TAG = "DownloadOnlineStatusUIHandler";
	private Activity act;

	private static OnlineStatusUIHandler downloadOnlineStatusUIHandler;
	public OnlineStatusUIHandler(Activity act) {
		this.act = act;
	}
	
	
	public static void init(Activity act){
		if(null==downloadOnlineStatusUIHandler){
			downloadOnlineStatusUIHandler=new OnlineStatusUIHandler(act);
		} 
	}//end of init
	
	
	public static OnlineStatusUIHandler getInstance(){
		return downloadOnlineStatusUIHandler;
	}


	@Override
	public void handleMessage(Message msg) {
		Log.d(TAG, "handleMessage begin. "+msg);
		super.handleMessage(msg);
		Bundle bundle = msg.getData();
		String payload = bundle.getString("payload"); 
		try {
			JSONObject jo = new JSONObject(payload);
			if(jo.has("status")){ 
				String status=jo.getString("status");
				Button ivOnlineIcon=null;
				ivOnlineIcon=(Button)act.findViewById(R.id.offlineTip);
				if("1".equals(status)){//在线  
					ivOnlineIcon.setBackgroundResource(R.drawable.online_64);
				} else{//离线  
					ivOnlineIcon.setBackgroundResource(R.drawable.offline_64);
					
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
	 
	
	
	public void sendOnlineOnMessage(){ 
		String payload=createJsonMessage(true).toString();
		Message msg = new Message();  
        Bundle bundle = new Bundle();  
        bundle.putString("payload", payload);  
        msg.setData(bundle);   
        sendMessage(msg);
	}
	

	public void sendOnlineOffMessage(){ 
		String payload=createJsonMessage(false).toString();
		Message msg = new Message();  
        Bundle bundle = new Bundle();  
        bundle.putString("payload", payload);  
        msg.setData(bundle);   
        sendMessage(msg);
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
  
	
}
