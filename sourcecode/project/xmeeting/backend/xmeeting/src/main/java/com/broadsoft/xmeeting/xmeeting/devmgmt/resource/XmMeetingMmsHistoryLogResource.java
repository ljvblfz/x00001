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

import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingMmsHistoryLogDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingMmsHistoryLog;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingMmsHistoryLog/{xmmmhlGuid}")
public class XmMeetingMmsHistoryLogResource extends SyBaseResource{
	
	private String xmmmhlGuid;
	
	private XmMeetingMmsHistoryLogDaoImpl xmMeetingMmsHistoryLogDao;
	
	public void setXmMeetingMmsHistoryLogDao(XmMeetingMmsHistoryLogDaoImpl xmMeetingMmsHistoryLogDao) {
		this.xmMeetingMmsHistoryLogDao = xmMeetingMmsHistoryLogDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmmhlGuid=getAttribute("xmmmhlGuid");
		XmMeetingMmsHistoryLog xmMeetingMmsHistoryLog =  xmMeetingMmsHistoryLogDao.findById(xmmmhlGuid);
		if(null==xmMeetingMmsHistoryLog){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingMmsHistoryLog,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingMmsHistoryLogDao.delete(xmmmhlGuid);
		return new StringRepresentation(xmmmhlGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmmhlGuid =  form.getFirstValue("xmmmhlGuid");
		XmMeetingMmsHistoryLog b= xmMeetingMmsHistoryLogDao.findById(xmmmhlGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}