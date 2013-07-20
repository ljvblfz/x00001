package com.broadsoft.xmeeting.uihandler;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.xmeeting.ChatActivity;

public class NotifyUIHandler extends Handler {
	private final String TAG = "NotifyUIHandler";
	private Activity act;

	private static NotifyUIHandler notifyUIHandler;
	public NotifyUIHandler(Activity act) {
		this.act = act;
	}
	
	
	public static void init(Activity act){
		System.out.println("======================notifyUIHandler====================="+notifyUIHandler);
		if(null==notifyUIHandler){
			notifyUIHandler=new NotifyUIHandler(act);
		} 
	}//end of init
	
	
	public static NotifyUIHandler getInstance(){
		return notifyUIHandler;
	}


	@Override
	public void handleMessage(Message msg) {
		Log.d(TAG, "handleMessage begin");
		super.handleMessage(msg);
		System.out.println("============================receivesuccess1=======================");
		Bundle bundle = msg.getData();
		String payload = bundle.getString("payload");
		System.out.println("============================receivesuccess2======================="+payload);
		try {
			JSONObject jo = new JSONObject(payload);

			System.out.println("--------------------------0");
			if(jo.has("msgcontent")){
				System.out.println("--------------------------0.1");
				((ChatActivity)act).notifyMsgList(jo.getString("msgcontent"));
			}
		} catch (JSONException e) { 
			e.printStackTrace();
		}
		Log.d(TAG, "handleMessage end");
	}
	
	
	 
	public void sendControllerMessage(String payload){

		Message msg = new Message();  
        Bundle bundle = new Bundle();  
        bundle.putString("payload", payload);  
        msg.setData(bundle);   
        sendMessage(msg);
	}

	protected void showDialog(String msg) {
		Toast toast = Toast.makeText(act, msg, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	protected void showDialog2(String msg) {
		AlertDialog.Builder builder = new Builder(act);
		builder.setMessage(msg);

		builder.setTitle("提示");

		builder.setPositiveButton("确认", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});

		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}
	
	
	public void destory(){
		notifyUIHandler=null;
	}
	
//	private ArrayList<String> messageList=new ArrayList<String>();
//	public ArrayList<String> getMessageList() {
//		return messageList;
//	}
	
 
	
}
