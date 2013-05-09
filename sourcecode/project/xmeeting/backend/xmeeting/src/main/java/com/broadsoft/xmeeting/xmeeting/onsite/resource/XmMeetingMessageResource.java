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
import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingMessageDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.po.XmMeetingMessage;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingMessage/{xmmmGuid}")
public class XmMeetingMessageResource extends SyBaseResource{
	
	private String xmmmGuid;
	
	private XmMeetingMessageDaoImpl xmMeetingMessageDao;
	
	public void setXmMeetingMessageDao(XmMeetingMessageDaoImpl xmMeetingMessageDao) {
		this.xmMeetingMessageDao = xmMeetingMessageDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmmGuid=getAttribute("xmmmGuid");
		XmMeetingMessage xmMeetingMessage =  xmMeetingMessageDao.findById(xmmmGuid);
		if(null==xmMeetingMessage){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingMessage,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingMessageDao.delete(xmmmGuid);
		return new StringRepresentation(xmmmGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmmGuid =  form.getFirstValue("xmmmGuid");
		XmMeetingMessage b= xmMeetingMessageDao.findById(xmmmGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}