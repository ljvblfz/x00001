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
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingContactDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingContact;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingContact/{xmmcGuid}")
public class XmMeetingContactResource extends SyBaseResource{
	
	private String xmmcGuid;
	
	private XmMeetingContactDaoImpl xmMeetingContactDao;
	
	public void setXmMeetingContactDao(XmMeetingContactDaoImpl xmMeetingContactDao) {
		this.xmMeetingContactDao = xmMeetingContactDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmcGuid=getAttribute("xmmcGuid");
		XmMeetingContact xmMeetingContact =  xmMeetingContactDao.findById(xmmcGuid);
		if(null==xmMeetingContact){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingContact,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingContactDao.delete(xmmcGuid);
		return new StringRepresentation(xmmcGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmcGuid =  form.getFirstValue("xmmcGuid");
		XmMeetingContact b= xmMeetingContactDao.findById(xmmcGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}