package com.broadsoft.xmeeting.uihandler;

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

import com.broadsoft.xmcommon.androiddao.EntityInfoHolder;
import com.broadsoft.xmcommon.appsupport.AppInitSupport;
import com.broadsoft.xmdownload.adapter.MeetingInfoLVButtonAdapter;
import com.broadsoft.xmeeting.R;


/**
 * 
 * @author lu.zhen
 *
 */
public class DownloadByWsUIHandler extends Handler {
	private final String TAG = "DownloadByWsUIHandler";
	private static DownloadByWsUIHandler downloadUIHandler;
	private Activity act;
	private MeetingInfoLVButtonAdapter meetingListItemAdapter ;

	private DownloadByWsUIHandler(Activity act,MeetingInfoLVButtonAdapter meetingListItemAdapter ) {
		this.act = act;
		this.meetingListItemAdapter=meetingListItemAdapter;
	}
	
	
	public static void init(Activity act,MeetingInfoLVButtonAdapter meetingListItemAdapter ){
		if(null==downloadUIHandler){
			downloadUIHandler=new DownloadByWsUIHandler(act,meetingListItemAdapter);
		} 
	}//end of init 
	
	public static DownloadByWsUIHandler getInstance(){
		return downloadUIHandler;
	}

	@Override
	public void handleMessage(Message msg) {
		Log.d(TAG, "handleMessage begin.  "+msg);
		super.handleMessage(msg);
		Bundle bundle = msg.getData();
		String payload = bundle.getString("payload");
		JSONObject jo;
		try {
			jo = new JSONObject(payload);
			String statusinfo=jo.getString("statusinfo"); 
			TextView tvDownloadStatus=(TextView)act.findViewById(R.id.textViewDownloadStatus);
			tvDownloadStatus.setText(statusinfo);
			if(jo.has("isfinish")){
				String isfinish=jo.getString("isfinish");
				if("1".equals(isfinish)){
					AppInitSupport.reloadEntity();
					meetingListItemAdapter.reload(); 
					if(jo.has("type")){
						String type=jo.getString("type");
						if("01".equals(type)){ //更新设备资产编号
							try {
								String padAssetCode=EntityInfoHolder.getInstance().getAssetCode(); 
								TextView textViewDeviceCode=(TextView)act.findViewById(R.id.textViewDeviceCode);
								textViewDeviceCode.setText(padAssetCode);
							} catch (JSONException e) { 
								e.printStackTrace();
							}
						} //end of if on 01
					}
				}//end of if
			}//end of if isfinish

		} catch (JSONException e) { 
			e.printStackTrace();
		}    
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
			jsonMessage.put("isfinish", "0");
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
			jsonMessage.put("isfinish", "1");
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
			jsonMessage.put("isfinish", "0");
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
			jsonMessage.put("isfinish", "1");
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
			jsonMessage.put("isfinish", "1");
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
