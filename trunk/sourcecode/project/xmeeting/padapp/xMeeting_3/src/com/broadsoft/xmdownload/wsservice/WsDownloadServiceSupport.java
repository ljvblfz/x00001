package com.broadsoft.xmdownload.wsservice;

import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androiddao.DaoHolder;
import com.broadsoft.xmdownload.rsservice.RsServiceOnMeetingInfoSupport;
import com.broadsoft.xmdownload.rsservice.RsServiceOnPadInfoSupport;
import com.broadsoft.xmeeting.uihandler.DownloadByWsUIHandler;
import com.broadsoft.xmeeting.uihandler.DownloadOnlineStatusUIHandler;

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
	//

	private String msgtimestamp="";
	private boolean keepAlive=true;
	private static AtomicInteger countOfReconnect=new AtomicInteger(0);
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
	

//	private WebSocketConnection client = new WebSocketConnection();
	private WebSocketConnection client;

	
	
	WebSocketHandler handler=new WebSocketHandler() { 
		@Override
		public void onOpen() {
			Log.d(TAG, "[onOpen]Status: Connected to " + wspath);
			DownloadByWsUIHandler.getInstance().sendEntryDownloadStatus();
			DownloadOnlineStatusUIHandler.getInstance().sendDownloadOnlineOnMessage();
			new Thread(sendHeartRunnable).start();
			new Thread(checkHeartRunnable).start();
			
			
		}

		@Override
		public void onTextMessage(String payload) {
			Log.d(TAG, "[onTextMessage]" + payload);
			processMessage(payload);
		}

		@Override
		public void onClose(int code, String reason) {
			Log.d(TAG, "[onClose]"+code+"--"+reason+"===> keepAlive:"+keepAlive);
			if(keepAlive){ 
				DownloadByWsUIHandler.getInstance().sendExitDownloadStatus(); 
				DownloadOnlineStatusUIHandler.getInstance().sendDownloadOnlineOffMessage();
	//			JSONObject jsonMessage = createOnlineStatusJsonMessage();
	//			DownloadOnlineStatusUIHandler.getInstance().sendDownloadOnlineMessage(jsonMessage.toString());
				//try to reconnect
				Log.d(TAG, "[onClose]countOfReconnect is:  "+countOfReconnect.incrementAndGet());
				reconnect(); 
			}
		}//end of onClose
	};
	
	private JSONObject createOnlineStatusJsonMessage() {
		JSONObject jsonStatus = new JSONObject();
		try { 
			jsonStatus.put("status", "0");  
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonStatus;
	}
	
	Runnable sendHeartRunnable = new Runnable() {

		@Override
		public void run() { 
			sendHearbeat();
		}

	};
	public void sendHearbeat(){
		while(keepAlive){  
			Log.d(TAG, "[sendHearbeat]client.isConnected() is: " + client.isConnected()); 
			if(null!=client&&client.isConnected()){ 
				JSONObject jsonObject=new JSONObject();
				try {
					jsonObject.put("msgtype", "10");
					jsonObject.put("msgtimestamp", System.currentTimeMillis()+"");
				} catch (JSONException e) { 
					e.printStackTrace();
				}
				sendMessage(jsonObject.toString()); 
				try {
					Thread.sleep(60*1000);
				} catch (InterruptedException e) { 
					e.printStackTrace();
				}
			}else{ 
				break;
			}
		}
	}//end of sendHearbeat
	
	Runnable checkHeartRunnable = new Runnable() {

		@Override
		public void run() { 
			checkHearbeat();
		}

	};
	

	public void checkHearbeat(){
		Log.d(TAG, "[checkHearbeat]msgtimestamp="+msgtimestamp); 
		while(keepAlive){  
			if(!"".equals(msgtimestamp)){
				long currenttimestamp=System.currentTimeMillis(); 
				long lasttimestamp=Long.valueOf(msgtimestamp);
				Log.d(TAG, "[checkHearbeat] lasttimestamp is: "+lasttimestamp); 
	
				if(currenttimestamp-lasttimestamp>3*60*1000){ 
					Log.d(TAG, "[checkHearbeat]the websocket need to reconnect."); 
					this.reconnect();
				}
			}
			//sleep 1 minute
			try {
				Thread.sleep(60*1000);
			} catch (InterruptedException e) { 
				e.printStackTrace();
			}
		}//end of while
	}//end of checkHearbeat
	 
	
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
			client.connect(wspath,handler,options );
			Log.d(TAG, "[connect]client.isConnected() is: " + client.isConnected()); 
			 
		} catch (WebSocketException e) { 
			Log.d(TAG, e.toString());
		} 
		Log.d(TAG, "[connect]end: wspath is: " + wspath); 
 
	}//end of connect
	
	public boolean isConnected(){
		if(client!=null){
			return client.isConnected(); 
		}
		return false;
	}
	
	
	
	
	
 
	private void reconnect(){
		Log.d(TAG, "reconnect begin. client is: "+client); 
		if(null!=client){
//			disconnect();
//			try {
//				Thread.sleep(10*1000);
//			} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			connect();
		}
		Log.d(TAG, "reconnect end."); 
	}
	
	
	private void processMessage(String message) {
		try {
			
			JSONObject jsonObject=new JSONObject(message);
			String msgtype=jsonObject.getString("msgtype");
			if("10".equals(msgtype)){ 
				 Log.d(TAG, "[Download]收到心跳--->"+jsonObject.toString());
				 msgtimestamp=jsonObject.getString("msgtimestamp");
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
							DownloadByWsUIHandler.getInstance().sendActivateMeetingInfoOnPad();
						}
					} 
				} else if("03".equals(msgtype)){//下载设备信息
					for(String strTo:toList){
						if(strTo.equals(padId)){ 
							RsServiceOnPadInfoSupport.download();
						}
					} 
				} else if("05".equals(msgtype)){//删除会议
					for(String strTo:toList){
						if(strTo.equals(padId)){ 
							DaoHolder.getInstance().getDownloadInfoDao().deleteByMeetingId(meetingid);
							DownloadByWsUIHandler.getInstance().sendDeleteMeetingInfoOnPad();
						}
					} 
				} else if("06".equals(msgtype)){//进入会议
					for(String strTo:toList){
						if(strTo.equals(padId)){  
							DaoHolder.getInstance().getDownloadInfoDao().activate(meetingid);
							DownloadByWsUIHandler.getInstance().sendEntryMeetingInfoOnPad();
						}
					} 
				} 
//				else if("04".equals(msgtype)){//下载公司信息
//					 //nothing to do
//				} 
				
			}//end of if
			
			
		} catch (JSONException e) { 
			Log.e(TAG, e.getMessage());
		}
	}


	
	/**
	 * disconnect
	 */
	public void disconnect(){
		this.keepAlive=false;
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

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}
	

	
}
