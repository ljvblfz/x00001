package com.broadsoft.xmdownload.wsservice;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androiddao.DaoHolder;
import com.broadsoft.xmcommon.androidwebsocket.WebSocketClient;
import com.broadsoft.xmdownload.ViewHolder;
import com.broadsoft.xmdownload.rsservice.RsServiceOnCompanyInfoSupport;
import com.broadsoft.xmdownload.rsservice.RsServiceOnMeetingInfoSupport;
import com.broadsoft.xmdownload.rsservice.RsServiceOnPadInfoSupport;


/**
 * 
 * @author lu.zhen
 *
 */
public class WsDownloadServiceSupport {
	private final String TAG="WsDownloadServiceSupport";

//	private String wspath="ws://172.29.135.151:8080/websocket/ws/download?padId=android&roleName=DEVICE";
	private String wspath;
	protected WebSocketClient client;
	
	
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
		        Log.d(TAG, "padId--->"+padId);
		        //convert to JSONOjbect
				try {
					
					JSONObject jsonObject=new JSONObject(message);
					String msgtype=jsonObject.getString("msgtype");
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
//									ViewHolder.getInstance().getTextViewDownloadStatus().setText("会议信息下载中");
									RsServiceOnMeetingInfoSupport.download(meetingid);
//									ViewHolder.getInstance().getTextViewDownloadStatus().setText("会议信息下载完成");
								}
							}
						}  else if("02".equals(msgtype)){//激活会议
							for(String strTo:toList){
								if(strTo.equals(padId)){
//									ViewHolder.getInstance().getTextViewDownloadStatus().setText("激活会议中");
									DaoHolder.getInstance().getDownloadInfoDao().activate(meetingid);
//									ViewHolder.getInstance().getTextViewDownloadStatus().setText("激活会议完成");
								}
							} 
						} else if("03".equals(msgtype)){//下载设备信息
							for(String strTo:toList){
								if(strTo.equals(padId)){ 
//									ViewHolder.getInstance().getTextViewDownloadStatus().setText("设备信息下载中");
									RsServiceOnPadInfoSupport.download();
//									ViewHolder.getInstance().getTextViewDownloadStatus().setText("设备信息下载完成");
								}
							} 
						} else if("04".equals(msgtype)){//下载公司信息
							for(String strTo:toList){
								if(strTo.equals(padId)){ 
//									ViewHolder.getInstance().getTextViewDownloadStatus().setText("公司信息下载中");
									RsServiceOnCompanyInfoSupport.download();
//									ViewHolder.getInstance().getTextViewDownloadStatus().setText("公司信息下载完成");
								}
							} 
						}
						
					}//end of if
					
					
				} catch (JSONException e) { 
					Log.e(TAG, e.getMessage());
				}   
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
		
	}
	
	
	public final static String MSG_TYPE_DOWNLOADSERVICE="01";//下载服务  
	public final static String MSG_TYPE_ACTIVATESERVICE="02";//激活服务  
	
 
	
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
