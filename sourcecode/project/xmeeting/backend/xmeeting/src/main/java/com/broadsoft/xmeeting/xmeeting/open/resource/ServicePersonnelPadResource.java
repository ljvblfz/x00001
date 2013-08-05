/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.open.resource;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.meeting.dao.XmeetingDaoImpl;
import com.broadsoft.xmeeting.xmeeting.meeting.vo.ServicePersonnelPadIVO;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;



/**
 * 
 * @author lu.zhen
 *
 */

@Component
@Scope(value="prototype")
@RestletResource(urls="/open/service/personnel/xmpdDeviceId/{xmpdDeviceId}")
public class ServicePersonnelPadResource extends SyBaseResource{
	   
	
	@Override
    protected void doInit() throws ResourceException {   
	}
	
	@Get
	public Representation get(Representation entity) throws ResourceException { 
		String xmpdDeviceId=this.getAttribute("xmpdDeviceId");
		Map map=new HashMap();
		map.put("xmpdDeviceId", xmpdDeviceId);
		JSONObject allInfo=new JSONObject();
		PageResponse<ServicePersonnelPadIVO> p =xmeetingDao.findServicePersonnelByDeviceId(getPageRequest(), map);
		allInfo.put("xervicePersonnelPadIVO", p);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(allInfo));    
	}
	
	
	//=============IOC================================= 
	
	private XmeetingDaoImpl xmeetingDao; 
	public void setXmeetingDao(XmeetingDaoImpl xmeetingDao) {
		this.xmeetingDao = xmeetingDao;
	}

}