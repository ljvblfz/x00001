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

import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingMmsContentDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingMmsContent;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingMmsContent/{xmmcGuid}")
public class XmMeetingMmsContentResource extends SyBaseResource{
	
	private String xmmcGuid;
	
	private XmMeetingMmsContentDaoImpl xmMeetingMmsContentDao;
	
	public void setXmMeetingMmsContentDao(XmMeetingMmsContentDaoImpl xmMeetingMmsContentDao) {
		this.xmMeetingMmsContentDao = xmMeetingMmsContentDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmcGuid=getAttribute("xmmcGuid");
		XmMeetingMmsContent xmMeetingMmsContent =  xmMeetingMmsContentDao.findById(xmmcGuid);
		if(null==xmMeetingMmsContent){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingMmsContent,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingMmsContentDao.delete(xmmcGuid);
		return new StringRepresentation(xmmcGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmcGuid =  form.getFirstValue("xmmcGuid");
		XmMeetingMmsContent b= xmMeetingMmsContentDao.findById(xmmcGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}