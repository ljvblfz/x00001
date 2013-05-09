/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.basic.resource;

import java.util.Date;

import net.sf.json.*;

import org.restlet.data.*;
import org.restlet.engine.application.EncodeRepresentation;
import org.restlet.representation.*;
import org.restlet.resource.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.resource.BaseResource;
import com.founder.sipbus.common.util.*;
import com.broadsoft.xmeeting.xmeeting.basic.dao.XmPadDeviceDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmPadDevice;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmPadDevice/{xmpdGuid}")
public class XmPadDeviceResource extends SyBaseResource{

	private Logger logger = LoggerFactory.getLogger(XmPadDeviceResource.class);
	
	private String xmpdGuid;
	
	private XmPadDeviceDaoImpl xmPadDeviceDao;
	
	public void setXmPadDeviceDao(XmPadDeviceDaoImpl xmPadDeviceDao) {
		this.xmPadDeviceDao = xmPadDeviceDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmpdGuid=getAttribute("xmpdGuid");
		XmPadDevice xmPadDevice =  xmPadDeviceDao.findById(xmpdGuid);
		if(null==xmPadDevice){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmPadDevice,config));
	}
//	@Delete
//	public Representation delete() {
//		xmPadDeviceDao.delete(xmpdGuid);
//		return new StringRepresentation(xmpdGuid);
//	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		if(logger.isTraceEnabled()){
			logger.trace("put begin");
		}
		form = new Form(entity);
		xmpdGuid =  form.getFirstValue("xmpdGuid");
		if(logger.isTraceEnabled()){
			logger.trace("xmpdGuid is: {}",xmpdGuid);
			logger.trace("form is: {}",form.getValuesMap());
		}
		XmPadDevice b= xmPadDeviceDao.findById(xmpdGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		if(logger.isTraceEnabled()){ 
			logger.trace("XmPadDevice is: XmpdStatus---> {}",b.getXmpdStatus());
			logger.trace("XmPadDevice is: XmpdComment---> {}",b.getXmpdComment());
		}
		JSONObject jo = getDefaultEditReturnJson();
		if(logger.isTraceEnabled()){
			logger.trace("put end");
		}
		return getJsonGzipRepresentation(jo);
	}
}