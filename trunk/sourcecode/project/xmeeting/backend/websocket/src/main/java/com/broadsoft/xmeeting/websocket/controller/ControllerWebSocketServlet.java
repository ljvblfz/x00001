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

public class ControllerWebSocketServlet extends WebSocketServlet {

	private Logger logger = LoggerFactory
			.getLogger(ControllerWebSocketServlet.class);
	private static final long serialVersionUID = -7178893327801338294L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config); 
//		new Thread(heartRunnable).start();

	}

	private boolean stop = false;

	@Override
	public void destroy() {
		stop = true;
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
		String memberDisplayName = request.getParameter("memberDisplayName");
		ControllerMessageInbound controllerMessageInbound=new ControllerMessageInbound(meetingId, memberId,memberDisplayName);
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
				if (stop) {
					return;
				}
			}// end of while
		}

	};

}