package com.broadsoft.xmeeting.wsservice;

import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Looper;
import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmeeting.uihandler.NotifyUIHandler;
import com.broadsoft.xmeeting.uihandler.OnlineStatusUIHandler;
import com.broadsoft.xmeeting.uihandler.ToDoUIHandler;

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
	private static final int MAX_RECONNECT=2;

	private boolean keepAlive=true;
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
		this.wspath="ws://"+serveripport+"/websocket/ws/controller?meetingId=" + meetingId + "&memberId=" + memberId ;
//		this.wspath="ws://"+serveripport+"/websocket/ws/controller?meetingId=" + meetingId + "&memberId=" + memberId ;
//		this.wspath="ws://"+serveripport+"/websocket/ws/controller?meetingId=000000000XMMEETINGINFO13041820484043&memberId=0000000XMPERSONNELINFO13041820484041" ;
		//
//		this.wspath="ws://"+serveripport+"/websocket/ws/controller?meetingId=" + meetingId + "&memberId=" + memberId + "&memberDisplayName=" + memberDisplayName;
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
	 * @param msgcontent
	 * @param to
	 */
	public void sendMessageServiceMessage(String msgcontent,String to){  
		JSONObject jsonMessage=new  JSONObject();
		try {
			jsonMessage.put("meetingid", this.meetingId);
			jsonMessage.put("from", this.memberId);
			jsonMessage.put("fromDisplayName", this.memberDisplayName);
			jsonMessage.put("msgtype", "02");
			jsonMessage.put("msgcontent", msgcontent);
			jsonMessage.put("to", to);
		} catch (JSONException e) { 
			e.printStackTrace();
		}
		sendMessage(jsonMessage.toString());
		System.out.println("============================sendsuccess=======================");
	}//end of sendCallServiceMessage
	
	
	
	
	public void sendHearbeat(){
		while(keepAlive){  
			if(client.isConnected()){
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
			connect();
		}
		Log.d(TAG, "reconnect end."); 
	}


//	private int truecount=0;
//
//	private int falsecount=0;
	
	/**
	 * 
	 * @param msg
	 */
	private void sendMessage(String msg){ 
//		Looper.prepare(); 
//		if(client.isConnected()){
//			truecount++;
//		}else{
//			falsecount++;
//		} 
		client.sendTextMessage(msg);
	}
	
	private WebSocketConnection client;

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
//			NotifyUIHandler.getInstance().sendOnlineMessage();
			OnlineStatusUIHandler.getInstance().sendOnlineOnMessage();
			new Thread(heartRunnable).start();
		}

		@Override
		public void onTextMessage(String payload) {
			Log.d(TAG, "[onTextMessage]Got echo: " + payload);    
			
			try {
				JSONObject jo = new JSONObject(payload);
				if(jo.has("msgtype")){
					String msgtype=jo.getString("msgtype");
					if("02".equals(msgtype)){
						NotifyUIHandler.getInstance().sendControllerMessage(payload);
					}else if("01".equals(msgtype)){
						ToDoUIHandler.getInstance().sendControllerMessage(payload);
					}else if("10".equals(msgtype)){
						//TODO:
					} 
				}//end of msgtype
			} catch (JSONException e) { 
				e.printStackTrace();
			}
			
		}

		@Override
		public void onClose(int code, String reason) {
			Log.d(TAG, "[onClose]"+code+"--"+reason);
//			NotifyUIHandler.getInstance().sendOfflineMessage();
			OnlineStatusUIHandler.getInstance().sendOnlineOffMessage();
			//try to reconnect
			if(keepAlive){ 
				int count=countOfReconnect.incrementAndGet();
				Log.d(TAG, "[onClose]countOfReconnect is:  "+count);
//				if(count<MAX_RECONNECT)
				reconnect(); 
			}
		}
	};
	
	 
	/**
	 * connect
	 */
	public void connect(){ 
		try {
			WebSocketOptions   options  =new WebSocketOptions  (); 
			options.setSocketConnectTimeout(10*1000);//ms
			options.setSocketReceiveTimeout(10*1000);//ms
			client = new WebSocketConnection();
			client.connect(wspath,wsHandler,options );
		} catch (WebSocketException e) { 
			Log.d(TAG, e.toString());
		}   
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
