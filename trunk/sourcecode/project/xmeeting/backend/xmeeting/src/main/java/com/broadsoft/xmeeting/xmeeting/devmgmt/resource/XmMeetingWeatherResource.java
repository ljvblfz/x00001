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
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingWeatherDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingWeather;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingWeather/{xmmwGuid}")
public class XmMeetingWeatherResource extends SyBaseResource{
	
	private String xmmwGuid;
	
	private XmMeetingWeatherDaoImpl xmMeetingWeatherDao;
	
	public void setXmMeetingWeatherDao(XmMeetingWeatherDaoImpl xmMeetingWeatherDao) {
		this.xmMeetingWeatherDao = xmMeetingWeatherDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmwGuid=getAttribute("xmmwGuid");
		XmMeetingWeather xmMeetingWeather =  xmMeetingWeatherDao.findById(xmmwGuid);
		if(null==xmMeetingWeather){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingWeather,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingWeatherDao.delete(xmmwGuid);
		return new StringRepresentation(xmmwGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmwGuid =  form.getFirstValue("xmmwGuid");
		XmMeetingWeather b= xmMeetingWeatherDao.findById(xmmwGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}