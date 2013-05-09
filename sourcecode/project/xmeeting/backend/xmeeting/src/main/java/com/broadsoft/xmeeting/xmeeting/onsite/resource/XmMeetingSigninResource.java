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
import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingSigninDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.po.XmMeetingSignin;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingSignin/{xmmsGuid}")
public class XmMeetingSigninResource extends SyBaseResource{
	
	private String xmmsGuid;
	
	private XmMeetingSigninDaoImpl xmMeetingSigninDao;
	
	public void setXmMeetingSigninDao(XmMeetingSigninDaoImpl xmMeetingSigninDao) {
		this.xmMeetingSigninDao = xmMeetingSigninDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmsGuid=getAttribute("xmmsGuid");
		XmMeetingSignin xmMeetingSignin =  xmMeetingSigninDao.findById(xmmsGuid);
		if(null==xmMeetingSignin){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingSignin,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingSigninDao.delete(xmmsGuid);
		return new StringRepresentation(xmmsGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmsGuid =  form.getFirstValue("xmmsGuid");
		XmMeetingSignin b= xmMeetingSigninDao.findById(xmmsGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}