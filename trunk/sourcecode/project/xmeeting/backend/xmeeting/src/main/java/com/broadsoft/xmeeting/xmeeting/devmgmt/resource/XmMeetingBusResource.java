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
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingBusDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingBus;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingBus/{xmmbGuid}")
public class XmMeetingBusResource extends SyBaseResource{
	
	private String xmmbGuid;
	
	private XmMeetingBusDaoImpl xmMeetingBusDao;
	
	public void setXmMeetingBusDao(XmMeetingBusDaoImpl xmMeetingBusDao) {
		this.xmMeetingBusDao = xmMeetingBusDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmbGuid=getAttribute("xmmbGuid");
		XmMeetingBus xmMeetingBus =  xmMeetingBusDao.findById(xmmbGuid);
		if(null==xmMeetingBus){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingBus,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingBusDao.delete(xmmbGuid);
		return new StringRepresentation(xmmbGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmbGuid =  form.getFirstValue("xmmbGuid");
		XmMeetingBus b= xmMeetingBusDao.findById(xmmbGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}