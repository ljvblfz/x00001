package com.broadsoft.xmdownload.wsservice;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.broadsoft.xmcommon.androidwebsocket.WebSocketClient;
import com.broadsoft.xmdownload.rsservice.RsServiceSupport;

public class WsServiceSupport {

	private String wspath="ws://172.29.135.151:8080/websocket/ws/download?padId=android&roleName=DEVICE";
	private final String TAG="WsServiceSupport";
	protected WebSocketClient client;
	
	
	private String padId="000000000XMMEETINGINFO13041820484043"; 
	
	private static WsServiceSupport wsServiceSupport=new WsServiceSupport();
	
	private  WsServiceSupport(){
		
	}
	
	public static WsServiceSupport getInstance(){
		return wsServiceSupport;
	} 
	
	
	public void initData( String padId){  
		this.padId=padId;  
		this.wspath="ws://172.29.135.151:8080/websocket/ws/download?padId="+padId+"&roleName=DEVICE";
	}
	
	/**
	 * connect
	 */
	public void connect(){
		List<BasicNameValuePair> extraHeaders = Arrays.asList( new BasicNameValuePair("Cookie", "session=android") );
		
		URI uri=URI.create(wspath); 
		client = new WebSocketClient(uri, new WebSocketClient.Listener() {
		    @Override
		    public void onConnect() {
		        Log.d(TAG, "Listener=========>Connected!");
		    }

		    
		    /**
		     * message.msgtype
		     * message.meetingid
		     * message.to
		     */
		    @Override
		    public void onMessage(String message) {
		        Log.d(TAG, String.format("Listener=========>Got string message! %s", message));
		        //convert to JSONOjbect
				try {
					JSONObject jsonObject=new JSONObject(message);
					String msgtype=jsonObject.getString("msgtype");
					String meetingid=jsonObject.getString("meetingid");
					String to=jsonObject.getString("to");
					if(null!=to){
						String[] toList=null;
						if(to.indexOf(",")>0){
							toList=to.split(",");
						}else{
							toList=new String[1];
							toList[0]=to;
						}
						
						for(String strTo:toList){
							if(strTo.equals(padId)){
								RsServiceSupport.download(meetingid);
							}
						}
						
					}//end of if  
					
					
				} catch (JSONException e) { 
					Log.e(TAG, e.getMessage());
				}   
		    }

		    @Override
		    public void onMessage(byte[] data) {
//			        Log.d(TAG, String.format("Got binary message! %s", toHexString(data)));
		        Log.d(TAG, String.format("Listener=========>Got binary message! %s", data.toString()));
		        
		        
		    }

		    @Override
		    public void onDisconnect(int code, String reason) {
		        Log.d(TAG, String.format("Listener=========>Disconnected! Code: %d Reason: %s", code, reason));
		    }

		    @Override
		    public void onError(Exception error) {
		        Log.e(TAG, "Listener=========>Error!", error);
		    }
		}, extraHeaders); 
		client.connect();
 
	}//end of connect


	/**
	 * disconnect
	 */
	public void disconnect(){
		client.disconnect();
		
	}
	
	
	public final static String MSG_TYPE_DOWNLOADSERVICE="01";//下载服务  
	
 
	
	/**
	 * 
	 * @param msg
	 */
	private void sendMessage(String msg){  
		client.send(msg); 
	}

	public String getPadId() {
		return padId;
	}
	

	
}
