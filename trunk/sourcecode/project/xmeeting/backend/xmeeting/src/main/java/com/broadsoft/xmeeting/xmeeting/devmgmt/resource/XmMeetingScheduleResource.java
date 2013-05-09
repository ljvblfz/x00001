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
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingScheduleDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingSchedule;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingSchedule/{xmmsGuid}")
public class XmMeetingScheduleResource extends SyBaseResource{
	
	private String xmmsGuid;
	
	private XmMeetingScheduleDaoImpl xmMeetingScheduleDao;
	
	public void setXmMeetingScheduleDao(XmMeetingScheduleDaoImpl xmMeetingScheduleDao) {
		this.xmMeetingScheduleDao = xmMeetingScheduleDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmsGuid=getAttribute("xmmsGuid");
		XmMeetingSchedule xmMeetingSchedule =  xmMeetingScheduleDao.findById(xmmsGuid);
		if(null==xmMeetingSchedule){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingSchedule,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingScheduleDao.delete(xmmsGuid);
		return new StringRepresentation(xmmsGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmsGuid =  form.getFirstValue("xmmsGuid");
		XmMeetingSchedule b= xmMeetingScheduleDao.findById(xmmsGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}