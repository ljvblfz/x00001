/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.devmgmt.resource;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingEmailHistoryLogDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingEmailHistoryLog;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingEmailHistoryLog/{xmmehlGuid}")
public class XmMeetingEmailHistoryLogResource extends SyBaseResource{
	
	private String xmmehlGuid;
	
	private XmMeetingEmailHistoryLogDaoImpl xmMeetingEmailHistoryLogDao;
	
	public void setXmMeetingEmailHistoryLogDao(XmMeetingEmailHistoryLogDaoImpl xmMeetingEmailHistoryLogDao) {
		this.xmMeetingEmailHistoryLogDao = xmMeetingEmailHistoryLogDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmehlGuid=getAttribute("xmmehlGuid");
		XmMeetingEmailHistoryLog xmMeetingEmailHistoryLog =  xmMeetingEmailHistoryLogDao.findById(xmmehlGuid);
		if(null==xmMeetingEmailHistoryLog){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingEmailHistoryLog,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingEmailHistoryLogDao.delete(xmmehlGuid);
		return new StringRepresentation(xmmehlGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmehlGuid =  form.getFirstValue("xmmehlGuid");
		XmMeetingEmailHistoryLog b= xmMeetingEmailHistoryLogDao.findById(xmmehlGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}