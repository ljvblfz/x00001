package com.broadsoft.xmeeting.websocket.download;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadInboundHolder {
	private Logger logger = LoggerFactory.getLogger(DownloadInboundHolder.class); 
	
	private static List<DownloadMessageInbound> socketList = new ArrayList<DownloadMessageInbound>(); 
	public static synchronized List<DownloadMessageInbound> getSocketList() {
		return socketList;
	}//end of getSocketListByMeetingId
	
	public static synchronized boolean checkIfExist(DownloadMessageInbound messageInbound) {
		 
		for(DownloadMessageInbound downloadMessageInbound:socketList){
			if(messageInbound.getPadId().equals(downloadMessageInbound.getPadId())){
				return true;
			}
		}//end of for
		return false;
	}//end of checkIfExist 
}
