package com.broadsoft.xmeeting;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 
 * @author lu.zhen
 *
 */
public class DownloadUIHandler extends Handler {
	private final String TAG = "DownloadUIHandler";
	private static DownloadUIHandler downloadUIHandler;
	private Activity act;

	private DownloadUIHandler(Activity act) {
		this.act = act;
	}
	
	
	public static void init(Activity act){
		if(null==downloadUIHandler){
			downloadUIHandler=new DownloadUIHandler(act);
		} 
	}//end of init 
	
	public static DownloadUIHandler getInstance(){
		return downloadUIHandler;
	}

	@Override
	public void handleMessage(Message msg) {
		Log.d(TAG, "handleMessage begin");
		super.handleMessage(msg);
		Bundle bundle = msg.getData();
		String payload = bundle.getString("payload");
		String downloadinfo  = processMessage(payload);
		TextView tvDownloadStatus=(TextView)act.findViewById(R.id.textViewDownloadStatus);
		tvDownloadStatus.setText(downloadinfo);
		Log.d(TAG, "handleMessage end");
	}


	private String processMessage(String payload) {
		String statusinfo="";
		try {
			JSONObject jo = new JSONObject(payload);   
			statusinfo=jo.getString("statusinfo"); 
		} catch (JSONException e) { 
			e.printStackTrace();
		}
		return statusinfo;
	}

	protected void showDialog(String msg) {
		Toast toast = Toast.makeText(act, msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}


	public void sendJsonMessage(JSONObject jsonMessage) {
		Message msg = new Message();  
        Bundle bundle = new Bundle();  
        bundle.putString("payload", jsonMessage.toString());  
        msg.setData(bundle);    
		sendMessage(msg);
	}
	//================================下载会议信息
	public void sendDownloadMeetingMessageOnBegin() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "02");
			jsonMessage.put("statusinfo", "下载会议信息中.");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}
	
	public void sendDownloadMeetingMessageOnEnd() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "02");
			jsonMessage.put("statusinfo", "下载会议信息结束.");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}
	//================================下载设备信息
	public void sendDownloadPadInfoOnBegin() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "01");
			jsonMessage.put("statusinfo", "下载设备信息中.");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}
	
	public void sendDownloadPadInfoOnEnd() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "01");
			jsonMessage.put("statusinfo", "下载设备信息结束.");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}
	//================================激活会议
	public void sendActivateMeetingInfoOnPad() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "03");
			jsonMessage.put("statusinfo", "激活会议完成");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}
	//================================下载状态
	public void sendEntryDownloadStatus() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "04");
			jsonMessage.put("statusinfo", "等待指令,准备下载");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}
	public void sendExitDownloadStatus() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "04");
			jsonMessage.put("statusinfo", "退出下载");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	} 
}//end of DownloadUIHandler
