package com.broadsoft.xmeeting.websocket.download;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadWebSocketServlet extends WebSocketServlet { 
	
	
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(DownloadWebSocketServlet.class); 

	@Override
	public void init(ServletConfig config) throws ServletException{
		 super.init(config); 
	 }

	/**
	 *   /websocket/ws/download?padId=xxxx&roleName=device
	 */
	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
		if(logger.isTraceEnabled()){
			logger.trace("createWebSocketInbound--->{}.",subProtocol);
			System.out.println("-------->createWebSocketInbound---->"+subProtocol);
		}
		String padId=request.getParameter("padId"); 
		String roleName=request.getParameter("roleName"); 
		return new DownloadMessageInbound(padId,roleName);
	} 

}