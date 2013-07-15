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
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingDocumentDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingDocument;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.service.SyCodeService;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingDocument/{xmmdGuid}")
public class XmMeetingDocumentResource extends SyBaseResource{
	
	private String xmmdGuid;
	
	private XmMeetingDocumentDaoImpl xmMeetingDocumentDao;
	
	public void setXmMeetingDocumentDao(XmMeetingDocumentDaoImpl xmMeetingDocumentDao) {
		this.xmMeetingDocumentDao = xmMeetingDocumentDao;
	}
	private SyCodeService syCodeService; 
	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}
	

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmdGuid=getAttribute("xmmdGuid");
		XmMeetingDocument xmMeetingDocument =  xmMeetingDocumentDao.findById(xmmdGuid);
		if(null==xmMeetingDocument){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingDocument,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingDocumentDao.delete(xmmdGuid);
		return new StringRepresentation(xmmdGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmdGuid =  form.getFirstValue("xmmdGuid");
		XmMeetingDocument b= xmMeetingDocumentDao.findById(xmmdGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}