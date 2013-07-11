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

import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingEmailDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingEmail;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingEmail/{xmmeGuid}")
public class XmMeetingEmailResource extends SyBaseResource{
	
	private String xmmeGuid;
	
	private XmMeetingEmailDaoImpl xmMeetingEmailDao;
	
	public void setXmMeetingEmailDao(XmMeetingEmailDaoImpl xmMeetingEmailDao) {
		this.xmMeetingEmailDao = xmMeetingEmailDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmeGuid=getAttribute("xmmeGuid");
		XmMeetingEmail xmMeetingEmail =  xmMeetingEmailDao.findById(xmmeGuid);
		if(null==xmMeetingEmail){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingEmail,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingEmailDao.delete(xmmeGuid);
		return new StringRepresentation(xmmeGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmeGuid =  form.getFirstValue("xmmeGuid");
		XmMeetingEmail b= xmMeetingEmailDao.findById(xmmeGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}