/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.open.resource;

import net.sf.json.JSONSerializer;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingCallDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.po.XmMeetingCall;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/open/xmMeetingCall/process/xmmcallGuid/{xmmcallGuid}")
public class XmMeetingCallProcessResource extends SyBaseResource{
	
	private String xmmcallGuid;
	
	private XmMeetingCallDaoImpl xmMeetingCallDao;
	
	public void setXmMeetingCallDao(XmMeetingCallDaoImpl xmMeetingCallDao) {
		this.xmMeetingCallDao = xmMeetingCallDao;
	}

	@Override
    protected void doInit() throws ResourceException {
		xmmcallGuid=getAttribute("xmmcallGuid");
	}
	
	@Get
	public Representation get(Representation entity) {
		XmMeetingCall xmMeetingCall =  xmMeetingCallDao.findById(xmmcallGuid);
		xmMeetingCall.setXmmcallStatus("1");
		if(null==xmMeetingCall){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingCall,config));
	} 
}