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
import com.broadsoft.xmeeting.xmeeting.basic.dao.XmPersonnelInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmPersonnelInfo;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingServicePersonnelDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingServicePersonnel;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingServicePersonnel/{xmmspGuid}")
public class XmMeetingServicePersonnelResource extends SyBaseResource{
	
	private String xmmspGuid;
	
	private XmMeetingServicePersonnelDaoImpl xmMeetingServicePersonnelDao;
	
	public void setXmMeetingServicePersonnelDao(XmMeetingServicePersonnelDaoImpl xmMeetingServicePersonnelDao) {
		this.xmMeetingServicePersonnelDao = xmMeetingServicePersonnelDao;
	}
	

	private XmPersonnelInfoDaoImpl xmPersonnelInfoDao; 
	public void setXmPersonnelInfoDao(XmPersonnelInfoDaoImpl xmPersonnelInfoDao) {
		this.xmPersonnelInfoDao = xmPersonnelInfoDao;
	}
	
	//==============================

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmspGuid=getAttribute("xmmspGuid");
		XmMeetingServicePersonnel xmMeetingServicePersonnel =  xmMeetingServicePersonnelDao.findById(xmmspGuid);

		String xmpiGuid=xmMeetingServicePersonnel.getXmpiGuid();
		XmPersonnelInfo xmPersonnelInfo=xmPersonnelInfoDao.findById(xmpiGuid); 
		
		xmMeetingServicePersonnel.setXmpiGuidLabel(xmPersonnelInfo.getXmpiName());
		if(null==xmMeetingServicePersonnel){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingServicePersonnel,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingServicePersonnelDao.delete(xmmspGuid);
		return new StringRepresentation(xmmspGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmspGuid =  form.getFirstValue("xmmspGuid");
		XmMeetingServicePersonnel b= xmMeetingServicePersonnelDao.findById(xmmspGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}