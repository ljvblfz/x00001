package com.xmeeting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotifyHandle {

	private NotifyHandle(){};
	private static NotifyHandle notifyHandle = new NotifyHandle();
	
	public static NotifyHandle getInstance(){
		return notifyHandle;
	}
	
	private static Map<String,Map<String,List<ChatMsgEntity>>> meetingChatList = new HashMap<String, Map<String,List<ChatMsgEntity>>>();
	
	public List<ChatMsgEntity> getChatMsgList(String position, String meetingId ){
//		System.out.println(meetingChatList+"==============================");
		List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
		if(null==meetingChatList.get(position))
			mDataArrays=new ArrayList<ChatMsgEntity>();
		else    	
			mDataArrays = meetingChatList.get(position).get(meetingId);
		
		if(null==mDataArrays)
			mDataArrays=new ArrayList<ChatMsgEntity>();
		
		return mDataArrays;
	}
	
	public void storeMsg(String position, String meetingId,ChatMsgEntity entity){
		List<ChatMsgEntity>  c = getChatMsgList(position,meetingId);
		c.add(entity);
		Map<String,List<ChatMsgEntity>> positionHis = meetingChatList.get(position);
		if(null== positionHis) 
			positionHis = new HashMap<String, List<ChatMsgEntity>>();
		positionHis.put(meetingId, c);
		meetingChatList.put(position, positionHis);
	}
	
}
