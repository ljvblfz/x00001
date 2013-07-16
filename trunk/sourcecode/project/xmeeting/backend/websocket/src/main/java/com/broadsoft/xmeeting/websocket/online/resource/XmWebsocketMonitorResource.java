/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */

package com.broadsoft.xmeeting.websocket.online.resource;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.websocket.controller.ControllerMessageInbound;
import com.broadsoft.xmeeting.websocket.controller.ControllerMessageInboundHolder;
import com.broadsoft.xmeeting.websocket.download.DownloadMessageInbound;
import com.broadsoft.xmeeting.websocket.download.DownloadMessageInboundHolder;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value = "prototype")
@RestletResource(urls = "/websocketmonitor")
public class XmWebsocketMonitorResource extends SyBaseResource {

	@Override
	protected void doInit() throws ResourceException {
	} 
	

	@Get
	public Representation get(Representation entity) {
		JSONObject jsonObject=new JSONObject();
		
		
		//下载信息
		JSONArray downloadArray=new JSONArray();
		Collection<DownloadMessageInbound> downloadSocketList=DownloadMessageInboundHolder.getSocketList();
		for(DownloadMessageInbound downloadMessageInbound:downloadSocketList){
			String padId=downloadMessageInbound.getPadId();
			String padCode=downloadMessageInbound.getPadCode();
			String roleName=downloadMessageInbound.getRoleName();
			JSONObject jsonDownload=new JSONObject();
			jsonDownload.put("padId", padId);
			jsonDownload.put("padCode", padCode);
			jsonDownload.put("roleName", roleName);
			downloadArray.add(jsonDownload);
		} 
		jsonObject.put("downloadmonitor", downloadArray);
		
		
		//控制信息
		JSONArray controllerArray=new JSONArray();
		ConcurrentHashMap<String, List<ControllerMessageInbound>> socketMapList =ControllerMessageInboundHolder.getAllSocketList();
		Set<String> keyList=socketMapList.keySet();
		Iterator<String> keyIter=keyList.iterator();
		while(keyIter.hasNext()){ 
			String key=keyIter.next();
			List<ControllerMessageInbound> socketList=socketMapList.get(key);
			JSONObject jsonMeetingController=new JSONObject();
			JSONArray memberArray=new JSONArray();
			for(ControllerMessageInbound controllerMessageInbound:socketList){
//				String meetingId=controllerMessageInbound.getMeetingId();
				String memberDisplayName=controllerMessageInbound.getMemberDisplayName();
				String memberId=controllerMessageInbound.getMemberId(); 
				String meetingName=controllerMessageInbound.getMeetingName();
				JSONObject jsonMember=new JSONObject();
				jsonMember.put("memberDisplayName", memberDisplayName);
				jsonMember.put("memberId", memberId);
				jsonMember.put("meetingName", meetingName);
				memberArray.add(jsonMember);
			}//end of for
			jsonMeetingController.put("meetingId", key);
			jsonMeetingController.put("memberList", memberArray);  
			controllerArray.add(jsonMeetingController);
		}//end of while 
		jsonObject.put("controllermonitor", controllerArray); 
		//return
		return getJsonGzipRepresentation(JSONSerializer.toJSON(jsonObject, config));
	}//end of get
	
	
	
}//end of XmWebsocketMonitorResource