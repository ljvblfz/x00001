package com.broadsoft.xmeeting.websocket.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.catalina.websocket.MessageInbound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerMessageInboundHolder {
	private Logger logger = LoggerFactory.getLogger(ControllerMessageInboundHolder.class);

	
	
	
	private static ConcurrentHashMap<String, List<ControllerMessageInbound>> socketMapList = new ConcurrentHashMap<String, List<ControllerMessageInbound>>(); 
	public static synchronized List<ControllerMessageInbound> getSocketListByMeetingId(String meetingId) {
		if(socketMapList.containsKey(meetingId)){
			return socketMapList.get(meetingId);
		}else{ 
			List<ControllerMessageInbound> socketList = new ArrayList<ControllerMessageInbound>();
			socketMapList.putIfAbsent(meetingId, socketList);
			return socketMapList.get(meetingId); 
		} 
	}//end of getSocketListByMeetingId
	
	
	public static synchronized ConcurrentHashMap<String, List<ControllerMessageInbound>>  getAllSocketList() {
		return socketMapList;
	}//end of getAllSocketList
	
	
	
	
	public static synchronized boolean checkIfExist(ControllerMessageInbound messageInbound) {
		String meetingId=messageInbound.getMeetingId();
		String memberId=messageInbound.getMemberId();
		
		List<ControllerMessageInbound> existListOfInbound=getSocketListByMeetingId(meetingId);
		
		
		for(ControllerMessageInbound existMessageInbound:existListOfInbound){
			if(memberId.equals(existMessageInbound.getMemberId())){
				return true;
			}
		}
		return false;
	}

	
 
}
