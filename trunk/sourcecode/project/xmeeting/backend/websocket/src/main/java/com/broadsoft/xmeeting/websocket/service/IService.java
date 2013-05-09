package com.broadsoft.xmeeting.websocket.service;

import org.json.JSONException;
import org.json.JSONObject;
 



/**
 * 
 * 消息格式==>JSON
 * 1)MSGTYPE
 * 2)FROMIP
 * 3)TOIPGROUP
 * 4)MSGCONTENT
 * 
 * @author lu.zhen
 *
 */
public interface IService {
	
	
	public void execute(JSONObject context)throws JSONException ; 

}
