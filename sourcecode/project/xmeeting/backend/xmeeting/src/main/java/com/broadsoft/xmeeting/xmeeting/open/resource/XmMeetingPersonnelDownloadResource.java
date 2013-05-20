/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.open.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingInfo;
import com.broadsoft.xmeeting.xmeeting.meeting.dao.XmeetingDaoImpl;
import com.broadsoft.xmeeting.xmeeting.meeting.vo.XmMeetingPersonnelSeatPadIVO;
import com.broadsoft.xmeeting.xmeeting.meeting.vo.XmMeetingServicePersonnelIVO;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageRequest;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;



/**
 * 此服务为pad展示所需的信息
 * @author lu.zhen
 *
 */

@Component
@Scope(value="prototype")
@RestletResource(urls="/open/meetingpersonnel/download/xmmiGuid/{xmmiGuid}")
public class XmMeetingPersonnelDownloadResource extends SyBaseResource{
	 
	//会议Pid
	private String xmmiGuid;
	 
	@Override
    protected void doInit() throws ResourceException { 
		xmmiGuid=getAttribute("xmmiGuid");
	}
	
	@Get
	public Representation get(Representation entity) throws ResourceException { 
		JSONObject allInfo=new JSONObject();  
		//Init param
		PageRequest pageRequest=new PageRequest();
		pageRequest.setPageNumber(1);
		pageRequest.setPageSize(200); 
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("xmmiGuid", xmmiGuid); 
		//会议参与人员
		PageResponse<XmMeetingPersonnelSeatPadIVO>  responseOfXmMeetingPersonnelSeatPadIVO=xmeetingDao.findMeetingPersonnelSeatPadByMeetingID(pageRequest, paramMap); 
		List<XmMeetingPersonnelSeatPadIVO> listOfXmMeetingPersonnelSeatPadIVO= responseOfXmMeetingPersonnelSeatPadIVO.getList(); 
		allInfo.put("listOfXmMeetingPersonnelSeatPadIVO", listOfXmMeetingPersonnelSeatPadIVO);
		//会议服务人员
		PageResponse<XmMeetingServicePersonnelIVO> responseOfXmMeetingServicePersonnelIVO=xmeetingDao.findServicePersonnelByMeetingID(pageRequest, paramMap);
		List<XmMeetingServicePersonnelIVO> listOfXmMeetingServicePersonnelIVO= responseOfXmMeetingServicePersonnelIVO.getList(); 
		allInfo.put("listOfXmMeetingServicePersonnelIVO", listOfXmMeetingServicePersonnelIVO); 
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(allInfo));    
	}
	
	
	//=============IOC=================================  
	private XmeetingDaoImpl xmeetingDao;

	public void setXmeetingDao(XmeetingDaoImpl xmeetingDao) {
		this.xmeetingDao = xmeetingDao;
	} 
	 

 
	

	
}