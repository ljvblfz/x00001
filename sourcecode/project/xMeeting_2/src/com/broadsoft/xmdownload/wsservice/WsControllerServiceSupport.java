package com.broadsoft.xmdownload.wsservice;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmeeting.uihandler.NotifyUIHandler;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;
import de.tavendo.autobahn.WebSocketOptions;


/**
 * 
 * @author lu.zhen
 *
 */
public class WsControllerServiceSupport {
	private final String TAG="WsControllerServiceSupport";

	private String wspath; 
	
	
	
	private static WsControllerServiceSupport wsServiceSupport=new WsControllerServiceSupport();
	
	private  WsControllerServiceSupport(){
		
	}
	
	public static WsControllerServiceSupport getInstance(){
		return wsServiceSupport;
	} 
	
	
	private String meetingId;
	private String memberId;
	private String memberDisplayName;
	
	
	
	public void initData(String meetingId,String memberId,String memberDisplayName){   
		this.meetingId=meetingId;
		this.memberId=memberId;
		this.memberDisplayName=memberDisplayName;
		String serveripport=DomAppConfigFactory.getAppConfig().getServeripport();
		this.wspath="ws://"+serveripport+"/websocket/ws/controller?meetingId=" + meetingId + "&memberId=" + memberId + "&memberDisplayName=" + memberDisplayName;
	}
 
//
//	/**
//	 * 
//	 * @param jsonMessage
//	 */
//	public void sendMessage(JSONObject jsonMessage){ 
//		client.sendTextMessage(jsonMessage.toString());
//	}//end of sendMessage
	

	/**
	 * 
	 * @param msgcontent
	 * @param to
	 */
	public void sendCallServiceMessage(String msgcontent,String to){  
		JSONObject jsonMessage=new  JSONObject();
		try {
			jsonMessage.put("meetingid", this.meetingId);
			jsonMessage.put("from", this.memberId);
			jsonMessage.put("fromDisplayName", this.memberDisplayName);
			jsonMessage.put("msgtype", "01");
			jsonMessage.put("msgcontent", msgcontent);
			jsonMessage.put("to", to);
		} catch (JSONException e) { 
			e.printStackTrace();
		}
		sendMessage(jsonMessage.toString());
	}//end of sendCallServiceMessage
	
	public void sendHearbeat(){
		while(true){
			if(client.isConnected()){
				JSONObject jsonObject=new JSONObject();
				try {
					jsonObject.put("msgtype", "10");
					jsonObject.put("test", "from android");
				} catch (JSONException e) { 
					e.printStackTrace();
				}
				sendMessage(jsonObject.toString()); 
				try {
					Thread.sleep(100*1000);
				} catch (InterruptedException e) { 
					e.printStackTrace();
				}
			}else{
				return;
			}
		}
	}//end of sendHearbeat
	

	/**
	 * 
	 * @param msg
	 */
	private void sendMessage(String msg){  
		client.sendTextMessage(msg);
	}
	
	private final WebSocketConnection client = new WebSocketConnection();

	Runnable heartRunnable = new Runnable() {

		@Override
		public void run() { 
			sendHearbeat();
		}

	};
	
	
	
	WebSocketHandler wsHandler=new WebSocketHandler() { 
		@Override
		public void onOpen() {
			Log.d(TAG, "[onOpen]Status: Connected to " + wspath); 
			new Thread(heartRunnable).start();
		}

		@Override
		public void onTextMessage(String payload) {
			Log.d(TAG, "[onTextMessage]Got echo: " + payload);   

			JSONObject jsonObject;
			try {
				jsonObject = new JSONObject(payload);
				String msgtype=jsonObject.getString("msgtype");  
				if("01".equals(msgtype)){//呼叫服务
//					NotifyUIHandler.getInstance().sendControllerMessage(payload); 
				}else if("02".equals(msgtype)){//通知服务
					NotifyUIHandler.getInstance().sendControllerMessage(payload); 
				}
			} catch (JSONException e) { 
				e.printStackTrace();
			}
		}//end of onTextMessage

		@Override
		public void onClose(int code, String reason) {
			Log.d(TAG, "[onClose]Connection lost.");
		}
	};
	
	 
	/**
	 * connect
	 */
	public void connect(){ 
		Log.d(TAG, "[connect]begin: wspath is: " + wspath); 
		try {
			WebSocketOptions   options  =new WebSocketOptions  (); 
			options.setSocketConnectTimeout(1000*1000);//ms
			options.setSocketReceiveTimeout(1000*1000);//ms
			client.connect(wspath,wsHandler,options );
		} catch (WebSocketException e) { 
			Log.d(TAG, e.toString());
		}   
		Log.d(TAG, "[connect]end: wspath is: " + wspath); 
	}//end of connect


	/**
	 * disconnect
	 */
	public void disconnect(){
		if(null!=client){ 
			if(client.isConnected()){
				client.disconnect(); 
			}
		}//end of if
		
	} //end of disconnect

	//==================================================
	public String getMeetingId() {
		return meetingId;
	}

	public void setMeetingId(String meetingId) {
		this.meetingId = meetingId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberDisplayName() {
		return memberDisplayName;
	}

	public void setMemberDisplayName(String memberDisplayName) {
		this.memberDisplayName = memberDisplayName;
	} 
	
}//end of WsControllerServiceSupport
