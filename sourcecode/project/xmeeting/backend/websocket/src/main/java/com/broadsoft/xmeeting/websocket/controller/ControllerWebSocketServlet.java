package com.broadsoft.xmeeting.websocket.controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broadsoft.xmeeting.springholder.SpringDaoHolder;
 

public class ControllerWebSocketServlet extends WebSocketServlet {

	private Logger logger = LoggerFactory.getLogger(ControllerWebSocketServlet.class);
	private static final long serialVersionUID = -7178893327801338294L;
	
	

	@Override
	public void init(ServletConfig config) throws ServletException{
		 super.init(config);
//
//		 if(logger.isTraceEnabled()){
//			logger.trace("====ControllerWebSocketServlet======init=====begin====="); 
//		 } 
//		 if(logger.isTraceEnabled()){
//			 logger.trace("xmMeetingVoteDao---------->"+SpringDaoHolder.getInstance().getXmMeetingVoteDao()); 
//			 logger.trace("xmMeetingVoteDetailDao---------->"+SpringDaoHolder.getInstance().getXmMeetingVoteDetailDao());
//			 logger.trace("xmMeetingCallDao---------->"+SpringDaoHolder.getInstance().getXmMeetingCallDao());
//			 logger.trace("xmMeetingMessageDao---------->"+SpringDaoHolder.getInstance().getXmMeetingMessageDao());
//			 logger.trace("====ControllerWebSocketServlet=====init======end======"); 
//		 }
		  
		 
	 }

	
	
	/**
	 * 
	 */
	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
		if(logger.isTraceEnabled()){
			logger.trace("createWebSocketInbound--->{}.",subProtocol);
			System.out.println("-------->createWebSocketInbound---->"+subProtocol);
		}
		String memberId=request.getParameter("memberId");
		String meetingId=request.getParameter("meetingId");
		String memberDisplayName=request.getParameter("memberDisplayName");
		return new ControllerMessageInbound(meetingId,memberId,memberDisplayName);
	}
	

	
	
	
	

}