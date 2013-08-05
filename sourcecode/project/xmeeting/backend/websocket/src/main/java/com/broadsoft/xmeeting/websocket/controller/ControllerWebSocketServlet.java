package com.broadsoft.xmeeting.websocket.controller;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broadsoft.xmeeting.springholder.SpringDaoHolder;

public class ControllerWebSocketServlet extends WebSocketServlet {

	private Logger logger = LoggerFactory
			.getLogger(ControllerWebSocketServlet.class);
	private static final long serialVersionUID = -7178893327801338294L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config); 
		new Thread(timeoutRunnable).start();

	}

	private boolean stopKeepAlive = false; 
	private boolean stopTimeout=false;

	@Override
	public void destroy() {
		stopKeepAlive = true;
		stopTimeout=true;
	}

	/**
	 * 
	 */
	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol,
			HttpServletRequest request) {
		if (logger.isTraceEnabled()) {
			logger.trace("createWebSocketInbound begin--->{}.", subProtocol); 
		}
		
		String memberId = request.getParameter("memberId");
		String meetingId = request.getParameter("meetingId");
		String memberDisplayName =SpringDaoHolder.getInstance().getXmPersonnelInfoDao().findById(memberId).getXmpiName();
		String meetingName =SpringDaoHolder.getInstance().getXmMeetingInfoDao().findById(meetingId).getXmmiName();
		ControllerMessageInbound controllerMessageInbound=new ControllerMessageInbound(meetingId, memberId,meetingName,memberDisplayName);
		if (logger.isTraceEnabled()) {
			logger.trace("createWebSocketInbound end."); 
		}
		return controllerMessageInbound;
	}

	public void dispatchHeartMessage() {
		logger.trace("dispatchHeartMessage--->心跳信息.");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("msgtype", "10");
		String msg = jsonObject.toString();
		ConcurrentHashMap<String, List<ControllerMessageInbound>> mapListOfControllerMessageInbound = ControllerMessageInboundHolder
				.getAllSocketList();
		Collection<List<ControllerMessageInbound>> coll = mapListOfControllerMessageInbound
				.values();
		for (List<ControllerMessageInbound> listOfControllerMessageInbound : coll) {
			for (ControllerMessageInbound messageInbound : listOfControllerMessageInbound) {
				CharBuffer buffer = CharBuffer.wrap(msg);
				WsOutbound outbound = messageInbound.getWsOutbound();
				try {
					outbound.writeTextMessage(buffer);
					outbound.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}// end of on listOfControllerMessageInbound
		}// end of on coll
	}// end of dispatchHeartMessage

	Runnable heartRunnable = new Runnable() {
		@Override
		public void run() {
			while (true) {
				dispatchHeartMessage();
				try {
					Thread.sleep(100 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (stopKeepAlive) {
					return;
				}
			}// end of while
		}

	};//end of heartRunnable
	
	Runnable timeoutRunnable = new Runnable() { 
		@Override
		public void run() {
			while (true) { 
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e) {
					 e.printStackTrace();
				} 
				ConcurrentHashMap<String, List<ControllerMessageInbound>> mapListOfControllerMessageInbound = ControllerMessageInboundHolder
						.getAllSocketList();
				Collection<List<ControllerMessageInbound>> coll = mapListOfControllerMessageInbound
						.values();
				for (List<ControllerMessageInbound> listOfControllerMessageInbound : coll) {
					for (ControllerMessageInbound messageInbound : listOfControllerMessageInbound) {
						//
						long lastUpdatedTimestamp=messageInbound.getLastUpdatedTimestamp();
						long currentTimestamp=System.currentTimeMillis();
						//超过600秒要删除
						if((currentTimestamp-lastUpdatedTimestamp)>2*60*1000){
							String memberId=messageInbound.getMemberId();
							String meetingId=messageInbound.getMeetingId();
//							messageInbound.getWsOutbound().
							ControllerMessageInboundHolder.removeByMemberIdAndMeetingId(memberId,meetingId); 
							if (logger.isTraceEnabled()) {
								logger.trace("remove the member==> memberIdis : "+memberId+"--meetingId  is: "+meetingId); 
							}
						}
					}// end of on listOfControllerMessageInbound
				}// end of on coll
				if(stopTimeout){
					return;
				}
			}//end of while
		}//end of run

	};//end of timeoutRunnable

}