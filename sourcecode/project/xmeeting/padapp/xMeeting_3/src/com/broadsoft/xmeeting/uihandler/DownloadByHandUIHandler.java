package com.broadsoft.xmeeting.uihandler;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.broadsoft.xmcommon.appsupport.AppInitSupport;
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
	
	public static void destroy(){
		downloadUIHandler=null;
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
		JSONObject jo;
		try {
			jo = new JSONObject(payload); 
			String downloadinfo  = jo.getString("statusinfo"); 
			if("begin".equals(downloadinfo)){//下载开始
				createLoadingDialog(); 
			}else if("end".equals(downloadinfo)){//下载结束
				AppInitSupport.reloadEntity();
				meetingInfoLVButtonAdapter.reload(); 
				destroyLoadingDialog();
			}else if("error".equals(downloadinfo)){//下载错误
				Toast.makeText(act, "会议信息下载失败,请重新下载.", Toast.LENGTH_LONG).show();   
				destroyLoadingDialog();
			}else if("progress".equals(downloadinfo)){//下载中  
				if(jo.has("kbsize")){
					String kbsize=jo.getString("kbsize");   
					progressDialog.setMessage("下载中(已经下载:"+kbsize+" kb),请等待...");
				}//end of isfinishx 
			}else if("activate".equals(downloadinfo)){//激活会议
				AppInitSupport.reloadEntity();
				meetingInfoLVButtonAdapter.reload(); 
				Toast.makeText(act, "会议信息激活.", Toast.LENGTH_LONG).show();  
			} else if("delete".equals(downloadinfo)){//激活会议
				AppInitSupport.reloadEntity();
				meetingInfoLVButtonAdapter.reload(); 
				Toast.makeText(act, "会议信息删除.", Toast.LENGTH_LONG).show();  
			} 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		Log.d(TAG, "handleMessage end");
	}
 
	private ProgressDialog progressDialog;
	private void createLoadingDialog(){ 
		if(null==progressDialog){
			 progressDialog = new ProgressDialog(act);
			 progressDialog.setMessage("下载中,请等待...");
			 progressDialog.setCancelable(false);
			 progressDialog.setIndeterminate(true);
			 progressDialog.show(); 			
		}
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
	
	
	public void sendDownloadMessageOnBegin() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "06");
			jsonMessage.put("statusinfo", "begin");
		} catch (JSONException e1) { 
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}
	public void sendDownloadMessageOnProgress(long kb) {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "06");
			jsonMessage.put("statusinfo", "progress");
			jsonMessage.put("kbsize", ""+kb);
		} catch (JSONException e1) { 
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}
	
	public void sendDownloadMessageOnError() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "06");
			jsonMessage.put("statusinfo", "error");
		} catch (JSONException e1) { 
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}
	
	public void sendDownloadMessageOnEnd() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "06");
			jsonMessage.put("statusinfo", "end");
		} catch (JSONException e1) { 
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}
	
	
	public void sendActivateMessage() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "06");
			jsonMessage.put("statusinfo", "activate");
		} catch (JSONException e1) { 
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}	
	public void sendDeleteMessage() {
        JSONObject jsonMessage=new JSONObject();
        try {
			jsonMessage.put("type", "06");
			jsonMessage.put("statusinfo", "delete");
		} catch (JSONException e1) { 
			e1.printStackTrace();
		}
		sendJsonMessage(jsonMessage);
	}

	 
}//end of DownloadByHandUIHandler
