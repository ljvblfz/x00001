package com.broadsoft.xmeeting.uihandler;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.broadsoft.xmdownload.adapter.MeetingInfoLVButtonAdapter;


/**
 * 
 * @author lu.zhen
 *
 */
public class DownloadByHandUIHandler extends Handler {
	private final String TAG = "DownloadByHandUIHandler";
	private static DownloadByHandUIHandler downloadUIHandler;
	private Activity act;
	private MeetingInfoLVButtonAdapter meetingInfoLVButtonAdapter;

	private DownloadByHandUIHandler(Activity act,MeetingInfoLVButtonAdapter meetingInfoLVButtonAdapter) {
		this.act = act;
		this.meetingInfoLVButtonAdapter=meetingInfoLVButtonAdapter;
	}
	
	
	public static void init(Activity act,MeetingInfoLVButtonAdapter meetingInfoLVButtonAdapter){
		if(null==downloadUIHandler){
			downloadUIHandler=new DownloadByHandUIHandler(act,meetingInfoLVButtonAdapter);
		} 
	}//end of init 
	
	public static DownloadByHandUIHandler getInstance(){
		return downloadUIHandler;
	}

	@Override
	public void handleMessage(Message msg) {
		Log.d(TAG, "handleMessage begin");
		super.handleMessage(msg);  
		Bundle bundle = msg.getData();
		String payload = bundle.getString("payload");
		String downloadinfo  = processMessage(payload); 
		if("begin".equals(downloadinfo)){//下载开始
			createLoadingDialog(); 
		}else if("end".equals(downloadinfo)){//下载结束
			meetingInfoLVButtonAdapter.reload(); 
			destroyLoadingDialog();
		}else if("error".equals(downloadinfo)){//下载错误
			Toast.makeText(act, "会议信息下载失败,请重新下载.", Toast.LENGTH_LONG).show();  
		}else if("activate".equals(downloadinfo)){//激活会议
			meetingInfoLVButtonAdapter.reload(); 
			Toast.makeText(act, "会议信息激活.", Toast.LENGTH_LONG).show();  
		} 
		Log.d(TAG, "handleMessage end");
	}
 
	private ProgressDialog progressDialog;
	private void createLoadingDialog(){ 
		 progressDialog = new ProgressDialog(act);
		 progressDialog.setMessage("会议信息下载中,请等待...");
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
	//
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
	
	public void sendJsonMessage(JSONObject jsonMessage) {
		Message msg = new Message();  
        Bundle bundle = new Bundle();  
        bundle.putString("payload", jsonMessage.toString());  
        msg.setData(bundle);    
		sendMessage(msg);
	}	
	public void sendDownloadMessageOnError() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "03");
			jsonMessage.put("statusinfo", "error");
		} catch (JSONException e1) { 
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}
	
	
	public void sendDownloadMessageOnBegin() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "03");
			jsonMessage.put("statusinfo", "begin");
		} catch (JSONException e1) { 
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}
	
	public void sendDownloadMessageOnEnd() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "03");
			jsonMessage.put("statusinfo", "end");
		} catch (JSONException e1) { 
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}
	
	
	public void sendActivateMessage() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "03");
			jsonMessage.put("statusinfo", "activate");
		} catch (JSONException e1) { 
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}

	 
}//end of DownloadByHandUIHandler
