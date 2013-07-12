package com.broadsoft.xmeeting.websocket.controller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broadsoft.xmeeting.websocket.service.IService;
import com.broadsoft.xmeeting.websocket.service.ServiceFactory;


/**
 * 
 * 聊天协议:1-20: 发件人
 * 		 21-40:收件人
 * 		 other: 内容
 * @author lu.zhen
 *
 */
public class ControllerMessageInbound extends MessageInbound {
	private Logger logger = LoggerFactory.getLogger(ControllerMessageInbound.class);
	 
	private String meetingId;
	private String memberId;
	private String memberDisplayName;
	public ControllerMessageInbound(String meetingId,String memberId,String memberDisplayName){
		super();
		this.meetingId=meetingId;
		this.memberId=memberId; 
		this.memberDisplayName=memberDisplayName;
	}

	@Override
	protected void onBinaryMessage(ByteBuffer arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * 消息格式:
	 * 1~2:消息格式-->01呼叫服务/02消息服务/03投票服务/04控制服务
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
		try {
			jsonObject=new JSONObject(strMsg);
			String msgType=jsonObject.getString("msgtype");  
			if("10".equals(msgType)){//心跳消息
				if(logger.isTraceEnabled()){
					logger.trace("onTextMessage--->心跳消息"); 
				} 
				getWsOutbound().writeTextMessage(msg); 
				return;
			} //end of if
			IService service=ServiceFactory.createService(msgType);
			//发送消息
			service.execute(jsonObject);  
		} catch (JSONException e) { 
			e.printStackTrace(); 
			if(logger.isErrorEnabled()){
				logger.error("raise the error--->{} .",  e);
			}
		}   
		if(logger.isTraceEnabled()){
			logger.trace("onTextMessage--->end"); 
		} 
	}//end of onTextMessage
 
	 
	

	
	
	/**
	 * 销毁客户端的连接
	 */
	@Override
	protected void onClose(int status) {
		System.out.println("["+Thread.currentThread().getId()+"]onClose--->meeting id: "+this.getMeetingId()+"----member id: "+this.getMemberId());
		if(logger.isTraceEnabled()){
			logger.trace("onClose--->{}.",status); 
		} 
		if(ControllerMessageInboundHolder.checkIfExist(this)){
			ControllerMessageInboundHolder.getSocketListByMeetingId(meetingId).remove(this);
			super.onClose(status);
		}
	}

	
	
	/**
	 * 注册客户端的连接
	 */
	@Override
	protected void onOpen(WsOutbound outbound) {
		System.out.println("["+Thread.currentThread().getId()+"]onOpen--->meeting id: "+this.getMeetingId()+"----member id: "+this.getMemberId());
		if(logger.isTraceEnabled()){
			logger.trace("onOpen--->"); 
		}  
		if(!ControllerMessageInboundHolder.checkIfExist(this)){
			if(logger.isTraceEnabled()){
				logger.trace("用户[{}]可以添加.",memberId); 
				System.out.println("["+Thread.currentThread().getId()+"]onOpen--->[add] meeting id: "+this.getMeetingId()+"----member id: "+this.getMemberId());
			} 
			super.onOpen(outbound);
			ControllerMessageInboundHolder.getSocketListByMeetingId(meetingId).add(this); 
		}else{ 
			if(logger.isTraceEnabled()){
				logger.trace("用户[{}]已经存在.",memberId); 
				System.out.println("["+Thread.currentThread().getId()+"]onOpen--->[exist] meeting id: "+this.getMeetingId()+"----member id: "+this.getMemberId());
			} 
		}
	}

	public String getMeetingId() {
		return meetingId;
	}

	public String getMemberId() {
		return memberId;
	}

	public String getMemberDisplayName() {
		return memberDisplayName;
	}
 
	
	//
	
	
	

}
