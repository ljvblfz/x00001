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
import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingVoteDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.po.XmMeetingVote;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingVote/{xmmvGuid}")
public class XmMeetingVoteResource extends SyBaseResource{
	
	private String xmmvGuid;
	
	private XmMeetingVoteDaoImpl xmMeetingVoteDao;
	
	public void setXmMeetingVoteDao(XmMeetingVoteDaoImpl xmMeetingVoteDao) {
		this.xmMeetingVoteDao = xmMeetingVoteDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmvGuid=getAttribute("xmmvGuid");
		XmMeetingVote xmMeetingVote =  xmMeetingVoteDao.findById(xmmvGuid);
		if(null==xmMeetingVote){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingVote,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingVoteDao.delete(xmmvGuid);
		return new StringRepresentation(xmmvGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmvGuid =  form.getFirstValue("xmmvGuid");
		XmMeetingVote b= xmMeetingVoteDao.findById(xmmvGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}