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
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingScheduleDetailDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingScheduleDetail;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingScheduleDetail/{xmmsdGuid}")
public class XmMeetingScheduleDetailResource extends SyBaseResource{
	
	private String xmmsdGuid;
	
	private XmMeetingScheduleDetailDaoImpl xmMeetingScheduleDetailDao;
	
	public void setXmMeetingScheduleDetailDao(XmMeetingScheduleDetailDaoImpl xmMeetingScheduleDetailDao) {
		this.xmMeetingScheduleDetailDao = xmMeetingScheduleDetailDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmsdGuid=getAttribute("xmmsdGuid");
		XmMeetingScheduleDetail xmMeetingScheduleDetail =  xmMeetingScheduleDetailDao.findById(xmmsdGuid);
		if(null==xmMeetingScheduleDetail){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingScheduleDetail,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingScheduleDetailDao.delete(xmmsdGuid);
		return new StringRepresentation(xmmsdGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmsdGuid =  form.getFirstValue("xmmsdGuid");
		XmMeetingScheduleDetail b= xmMeetingScheduleDetailDao.findById(xmmsdGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}