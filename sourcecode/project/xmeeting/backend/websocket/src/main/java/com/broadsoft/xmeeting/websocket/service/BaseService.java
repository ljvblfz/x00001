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
	 * 
	 */
	@Override
	public void execute(JSONObject context) throws JSONException {
		// TODO Auto-generated method stub
		String meetingid=context.getString("meetingid");  
		String msgtype=context.getString("msgtype");  
		String msgcontent=context.getString("msgcontent");
		String from=context.getString("from");
		String to=context.getString("to");
		try { 
			for (ControllerMessageInbound messageInbound : ControllerMessageInboundHolder.getSocketListByMeetingId(meetingid)) { 
				//检查消息是否要发给此人
				if(validateMember(to, messageInbound)){
					writeResponse(meetingid, msgtype, msgcontent, from, to, messageInbound);
				} 
			}//end of for
		} catch (IOException e) { 
			e.printStackTrace();
			if(logger.isErrorEnabled()){
				logger.error("raised the error---->{}.",e);
			}
		}
	}//end of execute
	

	
	
	
	
	/**
	 * 
	 * @param to
	 * @param messageInbound
	 * @return
	 */
	protected boolean validateMember(String to,
			ControllerMessageInbound messageInbound) {
		if(to!=null&&!to.isEmpty()){
			if(to.indexOf(",")>0){
				String[] arrTo=to.split(",");
				for(String strTo:arrTo){
					if(strTo.equals(messageInbound.getMemberId())){
						return true;
					}
				}//end of for 
			}else if(to.equals(messageInbound.getMemberId())){
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
	protected void writeResponse(String meetingid, String msgtype,
			String msgcontent, String from, String to,
			MessageInbound messageInbound) throws JSONException, IOException {
		JSONObject responseContent=new JSONObject();
		responseContent.put("meetingid", meetingid);
		responseContent.put("msgtype", msgtype);
		responseContent.put("msgcontent", msgcontent);
		responseContent.put("from", from);
		responseContent.put("to", to);
		String resp=responseContent.toString();
		if(logger.isTraceEnabled()){
			logger.trace("发送到客户端的消息是: {}.",resp);
		}
		CharBuffer buffer =CharBuffer.wrap(resp.toCharArray());  
		WsOutbound outbound = messageInbound.getWsOutbound();
		outbound.writeTextMessage(buffer);
		outbound.flush();
		
		updateDB(responseContent);
	}//end of writeResponse
	
	

	protected void updateDB(JSONObject responseContent)  throws JSONException{
		
	}

}
