package com.broadsoft.xmdownload.wsservice;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androidwebsocket.WebSocketClient;
import com.hp.hpl.sparta.xpath.ThisNodeTest;


/**
 * 
 * @author lu.zhen
 *
 */
public class WsControllerServiceSupport {
	private final String TAG="WsControllerServiceSupport";

//	private String wspath="ws://172.29.135.151:8080/websocket/ws/download?padId=android&roleName=DEVICE";
	private String wspath;
	protected WebSocketClient client;
	
	
//	private String padId; //android id
	
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
//		String serveripport="172.29.135.151:8080";
		String serveripport=DomAppConfigFactory.getAppConfig().getServeripport();
//		this.wspath="ws://"+serveripport+"/websocket/ws/controller?padId="+padId+"&roleName=DEVICE"; 
		this.wspath="ws://"+serveripport+"/websocket/ws/controller?meetingId=" + meetingId + "&memberId=" + memberId + "&memberDisplayName=" + memberDisplayName;
	}
	

	/**
	 * 
	 * @param jsonMessage
	 */
	public void sendMessage(JSONObject jsonMessage){ 
		client.send(jsonMessage.toString());
	}//end of sendMessage
	

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
		sendMessage(jsonMessage);
	}//end of sendCallServiceMessage
	
	/**
	 * connect
	 */
	public void connect(){
		List<BasicNameValuePair> extraHeaders = Arrays.asList( new BasicNameValuePair("Cookie", "session=android") );
		
		URI uri=URI.create(wspath); 
		client = new WebSocketClient(uri, new WebSocketClient.Listener() {
			
			
		    @Override
		    public void onConnect() {
		        Log.d(TAG, "[Listener]onConnect=========>Connected!");
		    }

		    
		    /**
		     * message.msgtype
		     * message.meetingid
		     * message.to
		     */
		    @Override
		    public void onMessage(String message) {
		        Log.d(TAG, String.format("[Listener]onMessage=========>Got string message! %s", message)); 
		        //convert to JSONOjbect 
		    }

		    @Override
		    public void onMessage(byte[] data) { 
		        Log.d(TAG, String.format("[Listener]onMessage=========>Got binary message! %s", data.toString()));
		        
		        
		    }

		    @Override
		    public void onDisconnect(int code, String reason) {
		        Log.d(TAG, String.format("[Listener]onDisconnect=========>Disconnected! Code: %d Reason: %s", code, reason));
		    }

		    @Override
		    public void onError(Exception error) {
		        Log.e(TAG, "[Listener]onError=========>Error!", error);
		    }
		}, extraHeaders); 
		client.connect();
 
	}//end of connect


	/**
	 * disconnect
	 */
	public void disconnect(){
		if(null!=client){ 
			client.disconnect();
			client=null;
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
