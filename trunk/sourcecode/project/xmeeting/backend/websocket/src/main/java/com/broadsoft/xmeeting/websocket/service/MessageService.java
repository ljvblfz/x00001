package com.broadsoft.xmeeting.websocket.service;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broadsoft.xmeeting.springholder.SpringDaoHolder;
import com.broadsoft.xmeeting.xmeeting.basic.dao.XmPersonnelInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingMessageDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.po.XmMeetingMessage;

public class MessageService extends  BaseService{
	private static Logger logger = LoggerFactory.getLogger(MessageService.class);
	@Override
	protected void updateDB(JSONObject responseContent) throws JSONException {
		XmMeetingMessageDaoImpl xmMeetingMessageDao=SpringDaoHolder.getInstance().getXmMeetingMessageDao();
		XmPersonnelInfoDaoImpl xmPersonnelInfoDao=SpringDaoHolder.getInstance().getXmPersonnelInfoDao();
		 
		//
		String to=responseContent.getString("to");
		if(!to.isEmpty()){

			if(to.indexOf(",")>0){
				String[] arrTo=to.split(",");
				for(String strTo:arrTo){  
					XmMeetingMessage xmMeetingMessage=new XmMeetingMessage(); 
					initXmMeetingMessage(responseContent, xmMeetingMessage);
					xmMeetingMessage.setXmmmTo(strTo);
					String strToDisplayName=xmPersonnelInfoDao.findById(strTo).getXmpiName();
					xmMeetingMessage.setXmmmToDisplayname(strToDisplayName);
					xmMeetingMessage.setXmmmStatus("1");
					xmMeetingMessageDao.insert(xmMeetingMessage);
				}//end of for 
			}else{
				XmMeetingMessage xmMeetingMessage=new XmMeetingMessage(); 
				initXmMeetingMessage(responseContent, xmMeetingMessage);
				xmMeetingMessage.setXmmmTo(to);
				String strToDisplayName=xmPersonnelInfoDao.findById(to).getXmpiName();
				xmMeetingMessage.setXmmmToDisplayname(strToDisplayName);
				xmMeetingMessage.setXmmmStatus("1");
				xmMeetingMessageDao.insert(xmMeetingMessage);
			}
		}
		
	}//end of updateDB
	
	
	private void initXmMeetingMessage(JSONObject responseContent,
			XmMeetingMessage xmMeetingMessage) throws JSONException {
		XmPersonnelInfoDaoImpl xmPersonnelInfoDao=SpringDaoHolder.getInstance().getXmPersonnelInfoDao();
		//
		xmMeetingMessage.setXmmiGuid(responseContent.getString("meetingid"));
		xmMeetingMessage.setXmmmFrom(responseContent.getString("from"));
		String strFrom=responseContent.getString("from");
		String strFromDisplayName=xmPersonnelInfoDao.findById(strFrom).getXmpiName();
		xmMeetingMessage.setXmmmFromDisplayname(strFromDisplayName);
		xmMeetingMessage.setXmmmMessage(responseContent.getString("msgcontent"));
		xmMeetingMessage.setXmmmTime(new Date());
	}

}
