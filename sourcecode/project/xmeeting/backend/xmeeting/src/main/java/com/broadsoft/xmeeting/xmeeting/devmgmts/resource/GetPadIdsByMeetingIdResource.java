/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.devmgmts.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.meeting.dao.XmeetingDaoImpl;
import com.broadsoft.xmeeting.xmeeting.meeting.vo.XmMeetingPadIVO;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageRequest;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/getPadIdsByMeetingIdResource/{xmmiGuid}")
public class GetPadIdsByMeetingIdResource extends SyBaseResource{
	
	private String xmmiGuid;
	
	 
	
	//==============================

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmiGuid=getAttribute("xmmiGuid"); 

		//Init param
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("xmmiGuid", xmmiGuid); 
		PageRequest pageRequest=new PageRequest();
		pageRequest.setPageNumber(1);
		pageRequest.setPageSize(200); 
		
		PageResponse<XmMeetingPadIVO> respOfXmMeetingPadIVO=xmeetingDao.findPadInfoByMeetingID(pageRequest, paramMap); 
		if(null==respOfXmMeetingPadIVO){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		} 
		List<XmMeetingPadIVO> listOfXmMeetingPadIVO=respOfXmMeetingPadIVO.getList(); 
		 
		System.out.println("listOfXmMeetingPadIVO.size--->"+listOfXmMeetingPadIVO.size());
		
		JSONObject json=new JSONObject();
		json.put("listOfXmMeetingPadIVO", listOfXmMeetingPadIVO);
		
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(json));    
	} 
	
	

	//=============IOC=================================  
	private XmeetingDaoImpl xmeetingDao;

	public void setXmeetingDao(XmeetingDaoImpl xmeetingDao) {
		this.xmeetingDao = xmeetingDao;
	} 
}