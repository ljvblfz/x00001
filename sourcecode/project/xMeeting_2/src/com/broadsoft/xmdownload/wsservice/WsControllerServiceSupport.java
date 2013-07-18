package com.broadsoft.xmdownload.wsservice;

import java.util.concurrent.atomic.AtomicInteger;

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

	private static AtomicInteger countOfReconnect=new AtomicInteger(0);

	private boolean keepAlive=true;
	
	private static WsControllerServiceSupport wsServiceSupport=new WsControllerServiceSupport();

//	private WebSocketConnection client = new WebSocketConnection();
	private WebSocketConnection client ;
	
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
//		this.wspath="ws://"+serveripport+"/websocket/ws/controller?meetingId=" + meetingId + "&memberId=" + memberId ;
	}
 
 

	WebSocketHandler wsHandler=new WebSocketHandler() { 
		@Override
		public void onOpen() {
			Log.d(TAG, "[onOpen]Status: Connected to " + wspath); 
			NotifyUIHandler.getInstance().sendOnlineMessage();
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
				}else if("10".equals(msgtype)){//心跳消息  
//					lastConnectedTime=System.currentTimeMillis();
				}
			} catch (JSONException e) { 
				e.printStackTrace();
			}
		}//end of onTextMessage

		@Override
		public void onClose(int code, String reason) {
			Log.d(TAG, "[onClose]"+code+"--"+reason);
			NotifyUIHandler.getInstance().sendOfflineMessage();
			//try to reconnect
			if(keepAlive){ 
				Log.d(TAG, "[onClose]countOfReconnect is:  "+countOfReconnect.incrementAndGet());
				reconnect(); 
			}
		}
	};
	

	Runnable heartRunnable = new Runnable() { 
		@Override
		public void run() { 
			sendHearbeat();
		}

	};

	public void sendHearbeat(){
		while(keepAlive){  
			if(null!=client&&client.isConnected()){  
				JSONObject jsonObject=new JSONObject();
				try {
					jsonObject.put("msgtype", "10");
					jsonObject.put("msgtimstamp", ""+System.currentTimeMillis());
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
	
	private void reconnect(){
		Log.d(TAG, "reconnect begin."); 
		if(null!=client){
			Log.d(TAG, "connect status: "+client.isConnected()); 
//			disconnect();
//			try {
//				Thread.sleep(10*1000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			connect();
		}
		Log.d(TAG, "reconnect end."); 
	}

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
	

	/**
	 * 
	 * @param msg
	 */
	private void sendMessage(String msg){  
		Log.d(TAG, "[sendMessage]msg : " + msg);  
		client.sendTextMessage(msg);
	}
	 
	 
	/**
	 * connect
	 */
	public void connect(){ 
		Log.d(TAG, "[connect]begin: wspath is: " + wspath); 
		this.keepAlive=true;
		try {
			WebSocketOptions   options  =new WebSocketOptions  (); 
			options.setSocketConnectTimeout(1000*1000);//ms
			options.setSocketReceiveTimeout(1000*1000);//ms
			client = new WebSocketConnection();
			client.connect(wspath,wsHandler,options );
			Log.d(TAG, "[connect]client.isConnected() is: " + client.isConnected()); 
		} catch (WebSocketException e) { 
			Log.d(TAG, e.toString());
		}   
		Log.d(TAG, "[connect]end"); 
	}//end of connect


	/**
	 * disconnect
	 */
	public void disconnect(){
		this.keepAlive=false;
		if(null!=client){ 
			if(client.isConnected()){
				client.disconnect(); 
			}
		}//end of if
		
	} //end of disconnect
	/**
	 * isConnected
	 */
	public boolean isConnected(){
		Log.d(TAG, "[isConnected]client.isConnected() is: " + client.isConnected()); 
		if(null!=client){ 
			return client.isConnected();
		}else{
			return false;
		} 
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
