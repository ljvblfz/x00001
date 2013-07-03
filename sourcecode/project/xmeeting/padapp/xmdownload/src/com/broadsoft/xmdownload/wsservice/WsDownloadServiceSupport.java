package com.broadsoft.xmdownload.wsservice;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androiddao.DaoHolder;
import com.broadsoft.xmdownload.rsservice.RsServiceOnMeetingInfoSupport;
import com.broadsoft.xmdownload.rsservice.RsServiceOnPadInfoSupport;

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
	}
	

	private final WebSocketConnection client = new WebSocketConnection();

	WebSocketHandler handler=new WebSocketHandler() { 
		@Override
		public void onOpen() {
			Log.d(TAG, "[onOpen]Status: Connected to " + wspath);
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
//							ViewHolder.getInstance().getTextViewDownloadStatus().setText("会议信息下载中");
							RsServiceOnMeetingInfoSupport.download(meetingid);
//							ViewHolder.getInstance().getTextViewDownloadStatus().setText("会议信息下载完成");
						}
					}
				}  else if("02".equals(msgtype)){//激活会议
					for(String strTo:toList){
						if(strTo.equals(padId)){
//							ViewHolder.getInstance().getTextViewDownloadStatus().setText("激活会议中");
							DaoHolder.getInstance().getDownloadInfoDao().activate(meetingid);
//							ViewHolder.getInstance().getTextViewDownloadStatus().setText("激活会议完成");
						}
					} 
				} else if("03".equals(msgtype)){//下载设备信息
					for(String strTo:toList){
						if(strTo.equals(padId)){ 
//							ViewHolder.getInstance().getTextViewDownloadStatus().setText("设备信息下载中");
							RsServiceOnPadInfoSupport.download();
//							ViewHolder.getInstance().getTextViewDownloadStatus().setText("设备信息下载完成");
						}
					} 
				} else if("04".equals(msgtype)){//下载公司信息
					for(String strTo:toList){
						if(strTo.equals(padId)){ 
//							ViewHolder.getInstance().getTextViewDownloadStatus().setText("公司信息下载中");
//							RsServiceOnCompanyInfoSupport.download();
//							ViewHolder.getInstance().getTextViewDownloadStatus().setText("公司信息下载完成");
						}
					} 
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
