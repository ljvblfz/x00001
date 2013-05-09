/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.onsite.resource;

import java.util.Date;

import net.sf.json.*;

import org.restlet.data.*;
import org.restlet.engine.application.EncodeRepresentation;
import org.restlet.representation.*;
import org.restlet.resource.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.resource.BaseResource;
import com.founder.sipbus.common.util.*;
import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingCallDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.po.XmMeetingCall;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingCall/{xmmcallGuid}")
public class XmMeetingCallResource extends SyBaseResource{
	
	private String xmmcallGuid;
	
	private XmMeetingCallDaoImpl xmMeetingCallDao;
	
	public void setXmMeetingCallDao(XmMeetingCallDaoImpl xmMeetingCallDao) {
		this.xmMeetingCallDao = xmMeetingCallDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmcallGuid=getAttribute("xmmcallGuid");
		XmMeetingCall xmMeetingCall =  xmMeetingCallDao.findById(xmmcallGuid);
		if(null==xmMeetingCall){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingCall,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingCallDao.delete(xmmcallGuid);
		return new StringRepresentation(xmmcallGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmcallGuid =  form.getFirstValue("xmmcallGuid");
		XmMeetingCall b= xmMeetingCallDao.findById(xmmcallGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}