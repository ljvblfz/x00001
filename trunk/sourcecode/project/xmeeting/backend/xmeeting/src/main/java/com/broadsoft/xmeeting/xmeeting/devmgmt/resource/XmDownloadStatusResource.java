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
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmDownloadStatusDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmDownloadStatus;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmDownloadStatus/{xmdsGuid}")
public class XmDownloadStatusResource extends SyBaseResource{
	
	private String xmdsGuid;
	
	private XmDownloadStatusDaoImpl xmDownloadStatusDao;
	
	public void setXmDownloadStatusDao(XmDownloadStatusDaoImpl xmDownloadStatusDao) {
		this.xmDownloadStatusDao = xmDownloadStatusDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmdsGuid=getAttribute("xmdsGuid");
		XmDownloadStatus xmDownloadStatus =  xmDownloadStatusDao.findById(xmdsGuid);
		if(null==xmDownloadStatus){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmDownloadStatus,config));
	}
	@Delete
	public Representation delete() {
		xmDownloadStatusDao.delete(xmdsGuid);
		return new StringRepresentation(xmdsGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmdsGuid =  form.getFirstValue("xmdsGuid");
		XmDownloadStatus b= xmDownloadStatusDao.findById(xmdsGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}