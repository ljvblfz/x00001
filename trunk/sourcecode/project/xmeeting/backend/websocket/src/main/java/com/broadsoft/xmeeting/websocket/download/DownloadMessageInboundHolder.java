package com.broadsoft.xmeeting.websocket.download;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadMessageInboundHolder {
	private Logger logger = LoggerFactory.getLogger(DownloadMessageInboundHolder.class); 
	

	private static ConcurrentHashMap<String,DownloadMessageInbound> socketMap=new ConcurrentHashMap <String,DownloadMessageInbound>();
	
	
	
	public static Collection<DownloadMessageInbound> getSocketList() {
		Collection<DownloadMessageInbound > coll=socketMap.values();
	 
		return coll;
	}//end of getSocketListByMeetingId
	
	public static boolean checkIfExist(DownloadMessageInbound messageInbound) {
		 
		Set<String> setOfPad=socketMap.keySet();
		
		Iterator<String> iterOfKeys=setOfPad.iterator();
		while(iterOfKeys.hasNext()){
			String key=iterOfKeys.next();
			if(key.equals(messageInbound.getPadId())){
				return true;
			}
		}
//		for(DownloadMessageInbound downloadMessageInbound:socketList){
//			if(messageInbound.getPadId().equals(downloadMessageInbound.getPadId())){
//				return true;
//			}
//		}//end of for
		return false;
	}//end of checkIfExist 

	public static void removeByPadId(String padId) {
		socketMap.remove(padId);
	 
	}
	
	
	public static void add(DownloadMessageInbound messageInbound) { 
		socketMap.put(messageInbound.getPadId(), messageInbound);
	}
	
	
//	private static List<DownloadMessageInbound> socketList = new ArrayList<DownloadMessageInbound>(); 
	
//	public static synchronized List<DownloadMessageInbound> getSocketList() {
//		return socketList;
//	}//end of getSocketListByMeetingId
//	
//	public static synchronized boolean checkIfExist(DownloadMessageInbound messageInbound) {
//		 
//		for(DownloadMessageInbound downloadMessageInbound:socketList){
//			if(messageInbound.getPadId().equals(downloadMessageInbound.getPadId())){
//				return true;
//			}
//		}//end of for
//		return false;
//	}//end of checkIfExist 
//
//	public static synchronized void removeByPadId(String padId) {
//		for(DownloadMessageInbound downloadMessageInbound:socketList){
//			if(padId.equals(downloadMessageInbound.getPadId())){
//				socketList.remove(downloadMessageInbound);
//			}
//		}//end of for
//	}
//	
//	
//	public static synchronized void add(DownloadMessageInbound messageInbound) {
//		socketList.add(messageInbound);
//	}
	
	
	
}
