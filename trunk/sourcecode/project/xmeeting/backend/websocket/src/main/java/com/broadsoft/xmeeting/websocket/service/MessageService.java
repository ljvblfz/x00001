package com.broadsoft.xmeeting.websocket.service;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broadsoft.xmeeting.springholder.SpringDaoHolder;
import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingMessageDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.po.XmMeetingMessage;

public class MessageService extends  BaseService{
	private static Logger logger = LoggerFactory.getLogger(MessageService.class);
	@Override
	protected void updateDB(JSONObject responseContent) throws JSONException {
		XmMeetingMessageDaoImpl xmMeetingMessageDao=SpringDaoHolder.getInstance().getXmMeetingMessageDao();
		
//		xmMeetingCall.setXmmcallCaller(responseContent.getString("from"));
//		xmMeetingCall.setXmmcallCallerDisplayname(responseContent.getString("from"));
//		xmMeetingCall.setXmmcallMessage(responseContent.getString("msgcontent"));
//		xmMeetingCall.setXmmcallTime(new Date());
//		xmMeetingCall.setXmmiGuid(responseContent.getString("meetingid"));
		
		String to=responseContent.getString("to");
		if(!to.isEmpty()){

			if(to.indexOf(",")>0){
				String[] arrTo=to.split(",");
				for(String strTo:arrTo){  
					XmMeetingMessage xmMeetingMessage=new XmMeetingMessage(); 
					initXmMeetingMessage(responseContent, xmMeetingMessage);
					xmMeetingMessage.setXmmmTo(strTo);
					xmMeetingMessage.setXmmmToDisplayname(strTo);
					xmMeetingMessageDao.insert(xmMeetingMessage);
				}//end of for 
			}else{
				XmMeetingMessage xmMeetingMessage=new XmMeetingMessage(); 
				initXmMeetingMessage(responseContent, xmMeetingMessage);
				xmMeetingMessage.setXmmmTo(to);
				xmMeetingMessage.setXmmmToDisplayname(to);
				xmMeetingMessageDao.insert(xmMeetingMessage);
			}
		}
		
	}//end of updateDB
	
	
	private void initXmMeetingMessage(JSONObject responseContent,
			XmMeetingMessage xmMeetingMessage) throws JSONException {
		xmMeetingMessage.setXmmiGuid(responseContent.getString("meetingid"));
		xmMeetingMessage.setXmmmFrom(responseContent.getString("from"));
		xmMeetingMessage.setXmmmFromDisplayname(responseContent.getString("from"));
		xmMeetingMessage.setXmmmMessage(responseContent.getString("msgcontent"));
		xmMeetingMessage.setXmmmTime(new Date());
	}

}
