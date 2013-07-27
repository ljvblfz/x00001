package com.broadsoft.xmeeting.uihandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.broadsoft.common.MyImageView;
import com.broadsoft.xmeeting.R;

public class NotifyUIHandler extends Handler {
	private final String TAG = "NotifyUIHandler";
	private Activity act;

	private static NotifyUIHandler notifyUIHandler;
	public NotifyUIHandler(Activity act) {
		this.act = act;
	}
	
	
	public static void init(Activity act){
		if(null==notifyUIHandler){
			notifyUIHandler=new NotifyUIHandler(act);
		} 
	}//end of init

	public static void destroy(){
		notifyUIHandler=null;
	}//end of init
	
	public static NotifyUIHandler getInstance(){
		return notifyUIHandler;
	}


	@Override
	public void handleMessage(Message msg) {
		Log.d(TAG, "handleMessage begin");
		super.handleMessage(msg);
		Bundle bundle = msg.getData();
		String payload = bundle.getString("payload");
		try {
			JSONObject jo = new JSONObject(payload);
			if(jo.has("msgtype")){
				String msgtype = jo.getString("msgtype");
				if ("01".equals(msgtype)) {//呼叫服务
//					String msgcontent = jo.getString("msgcontent");
//					int size=notificationListItem.size();
//		            HashMap<String, Object> mapOfContent = new HashMap<String, Object>();     
//		            mapOfContent.put("notificationSeq", String.valueOf((size+1)));  
//		            mapOfContent.put("notificationContent", msgcontent);  
////		            mapOfContent.put("notificationStatus", "未读");  
//		            mapOfContent.put("notificationTime", getCurrentTime());  
//					notificationListItem.add(mapOfContent); 					
//					showDialog(msgcontent); 
				} else if ("02".equals(msgtype)) {//通知消息
					String msgcontent = jo.getString("msgcontent");
					int size=notificationListItem.size();
		            HashMap<String, Object> mapOfContent = new HashMap<String, Object>();     
		            mapOfContent.put("notificationSeq", String.valueOf((size+1)));  
		            mapOfContent.put("notificationContent", msgcontent);  
//		            mapOfContent.put("notificationStatus", "未读");  
		            mapOfContent.put("notificationTime", getCurrentTime());  
					notificationListItem.add(mapOfContent); 					
					showDialog(msgcontent); 
					MyImageView mivMessage=(MyImageView)act.findViewById(R.id.btnMessage);
					mivMessage.setImageResource(R.drawable.s_message_new);
				}else if ("90".equals(msgtype)){//在线通知
					ImageView ivOnlineIcon=(ImageView)act.findViewById(R.id.ivOnlineIcon);
					ivOnlineIcon.setImageResource(R.drawable.online_64);
					//
//					MyImageView btnCall=(MyImageView)act.findViewById(R.id.btnCall);
					
				}else if ("91".equals(msgtype)){//离线通知
					ImageView ivOnlineIcon=(ImageView)act.findViewById(R.id.ivOnlineIcon);
					ivOnlineIcon.setImageResource(R.drawable.offline_64);
				}
			}
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
	
	public void sendOnlineMessage(){

		JSONObject jo = new JSONObject();
		try {
			jo.put("msgtype", "90");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Message msg = new Message();  
        Bundle bundle = new Bundle();  
        bundle.putString("payload", jo.toString());  
        msg.setData(bundle);   
        sendMessage(msg);
	}
	
	
	public void sendOfflineMessage(){
		JSONObject jo = new JSONObject();
		try {
			jo.put("msgtype", "91");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Message msg = new Message();  
        Bundle bundle = new Bundle();  
        bundle.putString("payload", jo.toString());  
        msg.setData(bundle);   
        sendMessage(msg);
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
	
	
//	private ArrayList<String> messageList=new ArrayList<String>();
//	public ArrayList<String> getMessageList() {
//		return messageList;
//	}
	

	private  ArrayList<HashMap<String, Object>> notificationListItem = new ArrayList<HashMap<String, Object>>();
	public ArrayList<HashMap<String, Object>> getNotificationListItem() {
		return notificationListItem;
	}  
	
	
	
}
