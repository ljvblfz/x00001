package com.broadsoft.xmeeting.websocket.download;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * message.to
 * message.meetingid
 * message.msgtype
 * @author lu.zhen
 *
 */
public class DownloadMessageInbound extends MessageInbound {
	private Logger logger = LoggerFactory.getLogger(DownloadMessageInbound.class);
	  
	
	public static String ROLE_NAME_DEVICE="DEVICE";
	public static String ROLE_NAME_PERSONNEL="PERSONNEL";
	
	private String padId;
	private String roleName;
	public DownloadMessageInbound( String padId,String roleName){
		super(); 
		this.padId=padId;
		this.roleName=roleName;
	}

	@Override
	protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 *  一个客户端发送数据后,触发它自己的onTextMessage方法,在这个方法里给所有在线的客户端发送这条消息 
	 * 消息格式:
	 * 1~2:消息格式-->01下载服务/02设备更新
	 * 4~: 消息内容
	 */
	@Override
	protected void onTextMessage(CharBuffer msg) throws IOException { 
		if(logger.isTraceEnabled()){
			logger.trace("onTextMessage--->begin"); 
		} 
		String strMsg=msg.toString();
		if(logger.isTraceEnabled()){
			logger.trace("onTextMessage--->strMsg={}.",strMsg); 
		} 
		//
		JSONObject jsonObject=null;
		String[] toList=null;
		try {
			jsonObject=new JSONObject(strMsg);
			String msgType=jsonObject.getString("msgtype");  
			//心跳
			if("10".equals(msgType)){ 
//				logger.trace("onTextMessage--->心跳信息."); 
//				getWsOutbound().writeTextMessage(msg); 
	
				return;
			}//end of if
			//
			String meetingId="";
			if(jsonObject.has("meetingid")){
				meetingId=jsonObject.getString("meetingid");
			}
			String to=jsonObject.getString("to");
			
			if(to!=null){
				if(to.indexOf(",")>0){
					toList=to.split(",");
				}else{
					toList=new String[1];
					toList[0]=to;
				}
			}
			
			if(logger.isTraceEnabled()){
				logger.trace("msgType is {} and meetingId is {}.",msgType,meetingId);
			}
		} catch (JSONException e) { 
			e.printStackTrace(); 
			if(logger.isErrorEnabled()){
				logger.error("raise the error--->{} .",  e);
			}
		} 
		dispatchTextMessage(toList,msg);  
		if(logger.isTraceEnabled()){
			logger.trace("onTextMessage--->end"); 
		} 
	}//end of onTextMessage

	
	
	
	
	/**
	 * 
	 * @param toList
	 * @param msg
	 * @throws IOException
	 */
	private void dispatchTextMessage(String[] toList,CharBuffer msg) throws IOException {  
		for (DownloadMessageInbound messageInbound : DownloadMessageInboundHolder.getSocketList()) {  
            CharBuffer buffer = CharBuffer.wrap(msg);  
            String roleName=messageInbound.getRoleName();
            String padId=messageInbound.getPadId(); 
            if(roleName.toUpperCase().equals(ROLE_NAME_DEVICE)&&isExist(padId,toList)){//只有设备下发 
                WsOutbound outbound = messageInbound.getWsOutbound();  
                outbound.writeTextMessage(buffer);  
                outbound.flush();   
            }
        }//end of for 
		
	
		
	}//end of dispatchTextMessage

	
	/**
	 * 
	 * @param to
	 * @param toList
	 * @return
	 */
	private boolean  isExist(String to,String[] toList){
		
		for(String strTo:toList){
			if(to.equals(strTo)){
				return true;
			}
		}
		return false;
		
	}
	
	
	/**
	 * 销毁客户端的连接
	 * 一个客户端断开时,从List中移除 
	 */
	@Override
	protected void onClose(int status) {
		if(logger.isTraceEnabled()){
			logger.trace("onClose--->{}.",status);
			System.out.println("["+Thread.currentThread().getId()+"]onClose--->"+this.getPadId());
		} 
		if(DownloadMessageInboundHolder.checkIfExist(this)){
//			DownloadMessageInboundHolder.getSocketList().remove(this); 
			DownloadMessageInboundHolder.removeByPadId(padId);
			System.out.println("["+Thread.currentThread().getId()+"]onClose--->remove the pad: "+this.getPadId());
			super.onClose(status);
		}
	}

	
	
	/**
	 * 注册客户端的连接
	 * 一个客户端连上来时,将它加入List 
	 */
	@Override
	protected void onOpen(WsOutbound outbound) {
		if(logger.isTraceEnabled()){
			logger.trace("onOpen--->"); 
			System.out.println("["+Thread.currentThread().getId()+"]onOpen--->"+this.getPadId());
		}  
		if(!DownloadMessageInboundHolder.checkIfExist(this)){ 
			super.onOpen(outbound);
			DownloadMessageInboundHolder.add(this); 
			System.out.println("["+Thread.currentThread().getId()+"]onOpen--->add the pad: "+this.getPadId());
		}else{ 
			if(logger.isTraceEnabled()){
				logger.trace("PadID[{}]已经存在.",padId);  
				System.out.println("["+Thread.currentThread().getId()+"]onOpen--->pad已经存在: "+this.getPadId());
				DownloadMessageInboundHolder.removeByPadId(padId);
				DownloadMessageInboundHolder.add(this); 
			} 
		}
	}//end of onOpen

	public String getPadId() {
		return padId;
	}

	public String getRoleName() {
		return roleName;
	} 

}
