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
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingPictureDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingPicture;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingPicture/{xmmpicGuid}")
public class XmMeetingPictureResource extends SyBaseResource{
	
	private String xmmpicGuid;
	
	private XmMeetingPictureDaoImpl xmMeetingPictureDao;
	
	public void setXmMeetingPictureDao(XmMeetingPictureDaoImpl xmMeetingPictureDao) {
		this.xmMeetingPictureDao = xmMeetingPictureDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmpicGuid=getAttribute("xmmpicGuid");
		XmMeetingPicture xmMeetingPicture =  xmMeetingPictureDao.findById(xmmpicGuid);
		if(null==xmMeetingPicture){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingPicture,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingPictureDao.delete(xmmpicGuid);
		return new StringRepresentation(xmmpicGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmpicGuid =  form.getFirstValue("xmmpicGuid");
		XmMeetingPicture b= xmMeetingPictureDao.findById(xmmpicGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}