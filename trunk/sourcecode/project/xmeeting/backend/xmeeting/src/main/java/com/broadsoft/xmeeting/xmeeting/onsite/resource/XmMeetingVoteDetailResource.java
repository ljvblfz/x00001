/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.onsite.resource;

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
import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingVoteDetailDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.po.XmMeetingVoteDetail;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingVoteDetail/{xmmvdGuid}")
public class XmMeetingVoteDetailResource extends SyBaseResource{
	
	private String xmmvdGuid;
	
	private XmMeetingVoteDetailDaoImpl xmMeetingVoteDetailDao;
	
	public void setXmMeetingVoteDetailDao(XmMeetingVoteDetailDaoImpl xmMeetingVoteDetailDao) {
		this.xmMeetingVoteDetailDao = xmMeetingVoteDetailDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmvdGuid=getAttribute("xmmvdGuid");
		XmMeetingVoteDetail xmMeetingVoteDetail =  xmMeetingVoteDetailDao.findById(xmmvdGuid);
		if(null==xmMeetingVoteDetail){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingVoteDetail,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingVoteDetailDao.delete(xmmvdGuid);
		return new StringRepresentation(xmmvdGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmvdGuid =  form.getFirstValue("xmmvdGuid");
		XmMeetingVoteDetail b= xmMeetingVoteDetailDao.findById(xmmvdGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}