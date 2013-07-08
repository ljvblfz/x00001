package com.broadsoft.xmdownload.wsservice;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androiddao.DaoHolder;
import com.broadsoft.xmdownload.rsservice.RsServiceOnMeetingInfoSupport;
import com.broadsoft.xmdownload.rsservice.RsServiceOnPadInfoSupport;
import com.broadsoft.xmeeting.DownloadUIHandler;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;
import de.tavendo.autobahn.WebSocketOptions;


/**
 * 
 * @author lu.zhen
 *
 */
public class WsDownloadServiceSupport {
	private final String TAG="WsDownloadServiceSupport";

	private String wspath;  
	private String padId; //android id
	
	private static WsDownloadServiceSupport wsDownloadServiceSupport=new WsDownloadServiceSupport();
	
	private  WsDownloadServiceSupport(){
		
	}
	
	public static WsDownloadServiceSupport getInstance(){
		return wsDownloadServiceSupport;
	} 
	
	
	public void initData(String padId){  
		this.padId=padId;  
		String serveripport=DomAppConfigFactory.getAppConfig().getServeripport();
		this.wspath="ws://"+serveripport+"/websocket/ws/download?padId="+padId+"&roleName=DEVICE";
		Log.d(TAG, "wspath------------->"+this.wspath);
	}
	

	private final WebSocketConnection client = new WebSocketConnection();

	WebSocketHandler handler=new WebSocketHandler() { 
		@Override
		public void onOpen() {
			Log.d(TAG, "[onOpen]Status: Connected to " + wspath);
			DownloadUIHandler.getInstance().sendEntryDownloadStatus();
			new Thread(heartRunnable).start();
		}

		@Override
		public void onTextMessage(String payload) {
			Log.d(TAG, "[onTextMessage]Got echo: " + payload);
			processMessage(payload);
		}

		@Override
		public void onClose(int code, String reason) {
			Log.d(TAG, "[onClose]Connection lost.");
			DownloadUIHandler.getInstance().sendExitDownloadStatus();
		}
	};
	
	Runnable heartRunnable = new Runnable() {

		@Override
		public void run() { 
			sendHearbeat();
		}

	};
	 
	
	/**
	 * connect
	 */
	public void connect(){   
		try {
			WebSocketOptions   options  =new WebSocketOptions  (); 
			options.setSocketConnectTimeout(1000*1000);//ms
			options.setSocketReceiveTimeout(1000*1000);//ms
			client.connect(wspath,handler,options );
		} catch (WebSocketException e) { 
			Log.d(TAG, e.toString());
		} 
 
	}//end of connect
	
	public boolean isConnected(){
		if(client!=null){
			return client.isConnected(); 
		}
		return false;
	}
	
	
	public void sendHearbeat(){
		while(true){
			if(client.isConnected()){
				JSONObject jsonObject=new JSONObject();
				try {
					jsonObject.put("msgtype", "10");
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
	
	
	
	
	private void processMessage(String message) {
		try {
			
			JSONObject jsonObject=new JSONObject(message);
			String msgtype=jsonObject.getString("msgtype");
			if("10".equals(msgtype)){ 
				 Log.d(TAG, "收到心跳");
				 return;
			}
			String meetingid="";
			if(jsonObject.has("meetingid")){
				meetingid=jsonObject.getString("meetingid");
			}
			String to=jsonObject.getString("to");

			if(null!=to){
				String[] toList=null;
				if(to.indexOf(",")>0){
					toList=to.split(",");
				}else{
					toList=new String[1];
					toList[0]=to;
				} 
				if("01".equals(msgtype)){//下载会议
					for(String strTo:toList){
						if(strTo.equals(padId)){
							RsServiceOnMeetingInfoSupport.download(meetingid);
						}
					}
				}  else if("02".equals(msgtype)){//激活会议
					for(String strTo:toList){
						if(strTo.equals(padId)){
							DaoHolder.getInstance().getDownloadInfoDao().activate(meetingid);
							DownloadUIHandler.getInstance().sendActivateMeetingInfoOnPad();
						}
					} 
				} else if("03".equals(msgtype)){//下载设备信息
					for(String strTo:toList){
						if(strTo.equals(padId)){ 
							RsServiceOnPadInfoSupport.download();
						}
					} 
				} else if("04".equals(msgtype)){//下载公司信息
					 //nothing to do
				} 
				
			}//end of if
			
			
		} catch (JSONException e) { 
			Log.e(TAG, e.getMessage());
		}
	}


	/**
	 * disconnect
	 */
	public void disconnect(){
		if(null!=client){ 
			client.disconnect(); 
		} 
	}
	
	
	public final static String MSG_TYPE_DOWNLOADSERVICE="01";//下载服务  
	public final static String MSG_TYPE_ACTIVATESERVICE="02";//激活服务  
	
 
	
	/**
	 * 
	 * @param msg
	 */
	private void sendMessage(String msg){  
		client.sendTextMessage(msg);
	}

	public String getPadId() {
		return padId;
	}
	

	
}
