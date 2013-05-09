package com.broadsoft.xmeeting.websocket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceFactory {


	private static Logger logger = LoggerFactory.getLogger(ServiceFactory.class);
	
	/**
	 * 01呼叫服务/02消息服务/03投票服务/04控制服务
	 * @param type
	 * @return
	 */
	public static IService createService(String type){;
	if(logger.isTraceEnabled()){
		logger.trace("createService--->type={}",type); 
	} 
		if("01".equals(type)){
			return  new CallService();
		}else if("02".equals(type)){
			return  new MessageService(); 
		}else if("03".equals(type)){
			return  new VoteService(); 
		}else if("04".equals(type)){
			return  new CommandService(); 
		}
		return null;
	}//end of createService
}
