/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.open.resource;

import net.sf.json.JSONObject;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingSigninDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.po.XmMeetingSignin;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;



/**
 * 此服务为pad签到
 * @author lu.zhen
 *
 */

@Component
@Scope(value="prototype")
@RestletResource(urls="/open/signin/xmpiGuid/{xmpiGuid}/xmmiGuid/{xmmiGuid}")
public class XmPadSigninResource extends SyBaseResource{
	
	//人员Pid
	private String xmpiGuid;
	//会议Pid
	private String xmmiGuid;
	 
	@Override
    protected void doInit() throws ResourceException {
		xmpiGuid=getAttribute("xmpiGuid");
		xmmiGuid=getAttribute("xmmiGuid");
	}
	
	@Get
	public Representation get(Representation entity) throws ResourceException {  
		JSONObject jsonObject=new JSONObject();
		if(xmMeetingSigninDao.countByXmpiGuidAndXmmiGuid(xmpiGuid, xmmiGuid)>0){
			jsonObject.put("xmmsGuid", ""); 
			return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jsonObject));   
		} 
		XmMeetingSignin xmMeetingSignin=new XmMeetingSignin();
		xmMeetingSignin.setXmmiGuid(xmmiGuid);
		xmMeetingSignin.setXmmsPersonnel(xmpiGuid);
		xmMeetingSignin.setXmmsTime(new java.util.Date());
		XmMeetingSignin uxmMeetingSignin=xmMeetingSigninDao.insert(xmMeetingSignin); 
		jsonObject.put("xmmsGuid", uxmMeetingSignin.getXmmsGuid());
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jsonObject));   
	}
	
	
	//=============IOC=================================
	private XmMeetingSigninDaoImpl  xmMeetingSigninDao; 
 
	public void setXmMeetingSigninDao(XmMeetingSigninDaoImpl xmMeetingSigninDao) {
		this.xmMeetingSigninDao = xmMeetingSigninDao;
	}
	
	
}