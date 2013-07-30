package com.broadsoft.xmeeting.uihandler;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import com.broadsoft.xmeeting.LoginActivity;
import com.broadsoft.xmeeting.R;


/**
 * 
 * progress dialog:http://www.mkyong.com/android/android-progress-bar-example/
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
	
	public static void destroy(){
		downloadUIHandler=null;
	}//end of init
	
	
	public static DownloadByWsUIHandler getInstance(){
		return downloadUIHandler;
	}

	private static int REQUEST_CODE = 2; 
	@Override
	public void handleMessage(Message msg) {
		Log.d(TAG, "handleMessage begin.  "+msg);
		super.handleMessage(msg);
		Bundle bundle = msg.getData();
		String payload = bundle.getString("payload"); 
		try {
			JSONObject jo = new JSONObject(payload);
			//更新提示信息
			String statusinfo=jo.getString("statusinfo"); 
			TextView tvDownloadStatus=(TextView)act.findViewById(R.id.textViewDownloadStatus);
			tvDownloadStatus.setText(statusinfo); 
			//reload data
			if(jo.has("reload")){
				String reload=jo.getString("reload");  
				if("1".equals(reload)){
					AppInitSupport.reloadEntity();
					meetingListItemAdapter.reload();   
				}
			}//end of isfinish
			//isfinishx
			if(jo.has("isfinishx")){
				String isfinishx=jo.getString("isfinishx");  
				if("0".equals(isfinishx)){
					createLoadingDialog();
				}
				if("1".equals(isfinishx)){ 
					destroyLoadingDialog();
				}
			}//end of isfinishx
			if(jo.has("progress")){
				String progress=jo.getString("progress");   
				progressDialog.setMessage("下载中(已经下载:"+progress+" kb),请等待...");
			}//end of isfinishx
			if(jo.has("error")){
				String error=jo.getString("error");   
				destroyLoadingDialog(); 
			}//end of isfinishx
			//
			if(jo.has("type")){
					String type=jo.getString("type");   
					if("06".equals(type)){ 
		        		Intent intent = new Intent();
		        		intent.setClass(act, LoginActivity.class); 
		        		intent.setData(Uri.parse("one")); 
		        		act.startActivityForResult(intent, REQUEST_CODE);// 以传递参数的方式跳转到下一个Activity 
		        		act.finish();
					}else if("01".equals(type)){ //更新设备资产编号 
						String padAssetCode=EntityInfoHolder.getInstance().getAssetCode(); 
						TextView textViewDeviceCode=(TextView)act.findViewById(R.id.textViewDeviceCode);
						textViewDeviceCode.setText(padAssetCode); 
					} //end of if on 01
			} //end of type
		} catch (JSONException e) { 
			e.printStackTrace();
		}    
		Log.d(TAG, "handleMessage end");
	}
	private ProgressDialog progressDialog;
	private void createLoadingDialog(){ 
		 progressDialog = new ProgressDialog(act);
		 progressDialog.setMessage("下载中,请等待...");
		 progressDialog.setCancelable(false);
		 progressDialog.setIndeterminate(true);
		 progressDialog.show();
	}
	
	private void destroyLoadingDialog(){ 
		if(null!=progressDialog){
			progressDialog.dismiss();
			progressDialog=null; 
		} 
	}

//	private String processMessage(String payload) {
//		String statusinfo="";
//		try {
//			JSONObject jo = new JSONObject(payload);   
//			statusinfo=jo.getString("statusinfo"); 
//		} catch (JSONException e) { 
//			e.printStackTrace();
//		}
//		return statusinfo;
//	}

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
			jsonMessage.put("isfinishx", "0");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}
	
	public void sendDownloadMeetingMessageOnProgress(long kb) {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "02");
			jsonMessage.put("statusinfo", "下载会议信息中."); 
			jsonMessage.put("progress", ""+kb);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}

	public void sendDownloadMeetingMessageOnError() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "02");
			jsonMessage.put("statusinfo", "下载会议信息错误.");   
			jsonMessage.put("error", "1");   
		} catch (JSONException e1) { 
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}
	
	
	public void sendDownloadMeetingMessageOnEnd() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "02");
			jsonMessage.put("statusinfo", "下载会议信息结束.");
			jsonMessage.put("reload", "1");
			jsonMessage.put("isfinishx", "1");
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
//			jsonMessage.put("reload", "0");
			jsonMessage.put("isfinishx", "0");
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
			jsonMessage.put("reload", "1");
			jsonMessage.put("isfinishx", "1");
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
			jsonMessage.put("reload", "1");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}
	//================================删除会议
	public void sendDeleteMeetingInfoOnPad() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "05");
			jsonMessage.put("statusinfo", "删除会议完成");
			jsonMessage.put("reload", "1");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}
	//================================删除会议
	public void sendEntryMeetingInfoOnPad() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "06");
			jsonMessage.put("statusinfo", "进入会议完成"); 
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
