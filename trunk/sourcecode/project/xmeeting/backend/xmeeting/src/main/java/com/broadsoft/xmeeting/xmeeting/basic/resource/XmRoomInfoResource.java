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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.resource.BaseResource;
import com.founder.sipbus.common.util.*;
import com.broadsoft.xmeeting.xmeeting.basic.dao.XmRoomInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmRoomInfo;
import com.founder.sipbus.syweb.au.base.SyBaseResource;



@Component
@Scope(value="prototype")
@RestletResource(urls="/xmRoomInfo/{xmriGuid}")
public class XmRoomInfoResource extends SyBaseResource{
	
	private String xmriGuid;
	
	private XmRoomInfoDaoImpl xmRoomInfoDao;
	
	public void setXmRoomInfoDao(XmRoomInfoDaoImpl xmRoomInfoDao) {
		this.xmRoomInfoDao = xmRoomInfoDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmriGuid=getAttribute("xmriGuid");
		XmRoomInfo xmRoomInfo =  xmRoomInfoDao.findById(xmriGuid);
		if(null==xmRoomInfo){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmRoomInfo,config));
	}
	@Delete
	public Representation delete() {
		xmRoomInfoDao.delete(xmriGuid);
		return new StringRepresentation(xmriGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmriGuid =  form.getFirstValue("xmriGuid");
		XmRoomInfo b= xmRoomInfoDao.findById(xmriGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}