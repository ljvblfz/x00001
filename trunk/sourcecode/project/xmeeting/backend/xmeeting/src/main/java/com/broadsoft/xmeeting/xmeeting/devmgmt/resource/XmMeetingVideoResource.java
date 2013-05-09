/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.devmgmt.resource;

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
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingVideoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingVideo;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingVideo/{xmmvGuid}")
public class XmMeetingVideoResource extends SyBaseResource{
	
	private String xmmvGuid;
	
	private XmMeetingVideoDaoImpl xmMeetingVideoDao;
	
	public void setXmMeetingVideoDao(XmMeetingVideoDaoImpl xmMeetingVideoDao) {
		this.xmMeetingVideoDao = xmMeetingVideoDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmvGuid=getAttribute("xmmvGuid");
		XmMeetingVideo xmMeetingVideo =  xmMeetingVideoDao.findById(xmmvGuid);
		if(null==xmMeetingVideo){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingVideo,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingVideoDao.delete(xmmvGuid);
		return new StringRepresentation(xmmvGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmvGuid =  form.getFirstValue("xmmvGuid");
		XmMeetingVideo b= xmMeetingVideoDao.findById(xmmvGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}