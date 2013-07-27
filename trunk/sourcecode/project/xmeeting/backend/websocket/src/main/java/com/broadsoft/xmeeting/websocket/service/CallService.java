package com.broadsoft.xmeeting.websocket.service;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broadsoft.xmeeting.springholder.SpringDaoHolder;
import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingCallDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.po.XmMeetingCall;

/**
 * 
 * @author 呼叫服务
 *
 */
public class CallService extends  BaseService{
	private static Logger logger = LoggerFactory.getLogger(CallService.class);
	
	@Override
	protected void updateDB(JSONObject responseContent) throws JSONException {
		XmMeetingCallDaoImpl xmMeetingCallDao=SpringDaoHolder.getInstance().getXmMeetingCallDao();
		XmMeetingCall xmMeetingCall=new XmMeetingCall(); 
		xmMeetingCall.setXmmcallCaller(responseContent.getString("from"));
		xmMeetingCall.setXmmcallCallerDisplayname(responseContent.getString("fromDisplayName"));
		xmMeetingCall.setXmmcallMessage(responseContent.getString("msgcontent"));
		xmMeetingCall.setXmmcallTime(new Date());
		xmMeetingCall.setXmmiGuid(responseContent.getString("meetingid"));
		xmMeetingCall.setXmmcallStatus("0"); 
		xmMeetingCallDao.insert(xmMeetingCall);
		responseContent.put("xmmcallGuid", xmMeetingCall.getXmmcallGuid());
		responseContent.put("xmmcallTime", xmMeetingCall.getXmmcallTime().getTime());
	}//end of updateDB

}