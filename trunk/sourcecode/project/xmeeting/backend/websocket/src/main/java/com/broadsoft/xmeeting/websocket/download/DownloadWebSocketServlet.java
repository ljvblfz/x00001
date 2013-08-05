package com.broadsoft.xmeeting.websocket.download;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Collection;
import java.util.Iterator;

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
import com.broadsoft.xmeeting.xmeeting.basic.po.XmPadDevice;

public class DownloadWebSocketServlet extends WebSocketServlet {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory
			.getLogger(DownloadWebSocketServlet.class);

	private boolean stopKeepAlive=false;
	private boolean stopTimeout=false;
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		new Thread(timeoutRunnable).start();
		 

	}
	
	
	@Override
	public void destroy(){
		stopKeepAlive=true;
		stopTimeout=true;
	}

	/**
	 * /websocket/ws/download?padId=xxxx&roleName=device
	 */
	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol,
			HttpServletRequest request) {
		if (logger.isTraceEnabled()) {
			logger.trace("createWebSocketInbound begin--->{}.", subProtocol); 
		}
		String padId = request.getParameter("padId");
		String roleName = request.getParameter("roleName");
		XmPadDevice xmPadDevice=SpringDaoHolder.getInstance().getXmPadDeviceDao().findByXmpdDeviceId(padId);
		String padCode="";
		if(null!=xmPadDevice){
			padCode=xmPadDevice.getXmpdCode();
		}
		
		DownloadMessageInbound downloadMessageInbound=new DownloadMessageInbound(padId,padCode, roleName);
		if (logger.isTraceEnabled()) {
			logger.trace("createWebSocketInbound end."); 
		}
		return downloadMessageInbound;
	}

	public void dispatchHeartMessage() {
		if (logger.isTraceEnabled()) {
			logger.trace("dispatchHeartMessage--->心跳信息."); 
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("msgtype", "10");
		String msg = jsonObject.toString();
		for (DownloadMessageInbound messageInbound : DownloadMessageInboundHolder
				.getSocketList()) {
			CharBuffer buffer = CharBuffer.wrap(msg);
			WsOutbound outbound = messageInbound.getWsOutbound();
			try {
				outbound.writeTextMessage(buffer);
				outbound.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}// end of for
	}

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
				if(stopKeepAlive){
					return;
				}
			}//end of while
		}

	};
	
	
	Runnable timeoutRunnable = new Runnable() { 
		@Override
		public void run() {
			while (true) { 
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e) {
					 e.printStackTrace();
				}
				Collection<DownloadMessageInbound> collOfInbound=DownloadMessageInboundHolder.getSocketList();
				Iterator<DownloadMessageInbound> iterOfInbound=collOfInbound.iterator();
				while(iterOfInbound.hasNext()){
					DownloadMessageInbound inbound=iterOfInbound.next();
					long lastUpdatedTimestamp=inbound.getLastUpdatedTimestamp();
					long currentTimestamp=System.currentTimeMillis();
					//超过600秒要删除
					if((currentTimestamp-lastUpdatedTimestamp)>2*60*1000){
						String padId=inbound.getPadId();
						String roleName=inbound.getRoleName();
						if("DEVICE".equals(roleName)){
							DownloadMessageInboundHolder.removeByPadId(padId);  
							if (logger.isTraceEnabled()) {
								logger.trace("[timeoutRunnable]remove the pad:  "+padId); 
							} 
						}//end of roleName
					}
				}//end of while
				
				if(stopTimeout){
					return;
				}
			}//end of while
		}//end of run

	};//end of timeoutRunnable
}