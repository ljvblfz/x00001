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
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingPictureDetailDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingPictureDetail;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingPictureDetail/{xmmpicdGuid}")
public class XmMeetingPictureDetailResource extends SyBaseResource{
	
	private String xmmpicdGuid;
	
	private XmMeetingPictureDetailDaoImpl xmMeetingPictureDetailDao;
	
	public void setXmMeetingPictureDetailDao(XmMeetingPictureDetailDaoImpl xmMeetingPictureDetailDao) {
		this.xmMeetingPictureDetailDao = xmMeetingPictureDetailDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmpicdGuid=getAttribute("xmmpicdGuid");
		XmMeetingPictureDetail xmMeetingPictureDetail =  xmMeetingPictureDetailDao.findById(xmmpicdGuid);
		if(null==xmMeetingPictureDetail){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingPictureDetail,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingPictureDetailDao.delete(xmmpicdGuid);
		return new StringRepresentation(xmmpicdGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmpicdGuid =  form.getFirstValue("xmmpicdGuid");
		XmMeetingPictureDetail b= xmMeetingPictureDetailDao.findById(xmmpicdGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}