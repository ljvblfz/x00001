package com.broadsoft.xmeeting.websocket.service;

import java.io.IOException;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broadsoft.xmeeting.websocket.controller.ControllerMessageInbound;
import com.broadsoft.xmeeting.websocket.controller.ControllerMessageInboundHolder;


/**
 * 
 * @author lu.zhen
 *
 */
public class BaseService implements IService{
	private static Logger logger = LoggerFactory.getLogger(BaseService.class);

	
	/**
	 * 发送消息
	 */
	@Override
	public void execute(JSONObject context) throws JSONException {
		if(logger.isTraceEnabled()){
			logger.trace("execute--->begin"); 
		} 
		// TODO Auto-generated method stub
		String meetingid=context.getString("meetingid");  
		String msgtype=context.getString("msgtype");  
		String msgcontent=context.getString("msgcontent");
		String from=context.getString("from");
		String fromDisplayName=context.getString("fromDisplayName");
		String to=context.getString("to");

		int count=0;
		try { 
			JSONObject responseContent=null;
			for (ControllerMessageInbound messageInbound : ControllerMessageInboundHolder.getSocketListByMeetingId(meetingid)) { 
				//检查消息是否要发给此人
				if(validateMember(to, messageInbound)){
//					writeResponse(meetingid, msgtype, msgcontent, from,fromDisplayName, to, messageInbound);
					count++;
					if(count==1){
						responseContent = logDB(meetingid, msgtype, msgcontent, from, fromDisplayName, to); 
					}
					if(null!=responseContent){
						String resp=responseContent.toString(); 
						writeResponse(resp,messageInbound); 
					}
				} 
				if(logger.isTraceEnabled()){
					logger.trace("send message back to from:{} with inbound memberid: {} .",from,messageInbound.getMemberId()); 
				} 
				if(validateMember(from, messageInbound)){ 
					if(null!=responseContent){
						String resp=responseContent.toString(); 
						writeResponse(resp,messageInbound); 
						if(logger.isTraceEnabled()){
							logger.trace("send message back to from:{} successfully.",from); 
						} 
					}
				} 

			}//end of for
		} catch (IOException e) { 
			e.printStackTrace();
			if(logger.isErrorEnabled()){
				logger.error("raised the error---->{}.",e);
			}
		}
		if(logger.isTraceEnabled()){
			logger.trace("execute--->end"); 
		} 
	}//end of execute
	

	
	
	
	
	/**
	 * 
	 * @param memberId
	 * @param messageInbound
	 * @return
	 */
	protected boolean validateMember(String memberId,
			ControllerMessageInbound messageInbound) {
		if(memberId!=null&&!memberId.isEmpty()){
			if(memberId.indexOf(",")>0){
				String[] arrTo=memberId.split(",");
				for(String strTo:arrTo){
					if(strTo.equals(messageInbound.getMemberId())){
						return true;
					}
				}//end of for 
			}else if(memberId.equals(messageInbound.getMemberId())){
				return true;
			}
		}
		return false;
	}//end of validateMember

	/**
	 * 
	 * @param meetingid
	 * @param msgtype
	 * @param msgcontent
	 * @param from
	 * @param to
	 * @param messageInbound
	 * @throws JSONException
	 * @throws IOException
	 */
	protected void writeResponse(String resp,   
			MessageInbound messageInbound) throws JSONException, IOException {

		if(logger.isTraceEnabled()){
			logger.trace("发送到客户端的消息是: {}.",resp);
		}
		CharBuffer buffer =CharBuffer.wrap(resp.toCharArray());  
		WsOutbound outbound = messageInbound.getWsOutbound();
		outbound.writeTextMessage(buffer);
		outbound.flush();
	}//end of writeResponse






	private JSONObject logDB(String meetingid, String msgtype,
			String msgcontent, String from, String fromDisplayName, String to)
			throws JSONException {
		JSONObject responseContent=new JSONObject();
		responseContent.put("meetingid", meetingid);
		responseContent.put("msgtype", msgtype);
		responseContent.put("msgcontent", msgcontent);
		responseContent.put("from", from);
		responseContent.put("fromDisplayName", fromDisplayName);
		responseContent.put("to", to);
		updateDB(responseContent);
		return responseContent;
	}
	
	

	protected void updateDB(JSONObject responseContent)  throws JSONException{
		
	}

}
