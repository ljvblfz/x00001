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

import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingPictureDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingXpictureDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingPicture;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingXpicture;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingXpicture/{xmmxpicGuid}")
public class XmMeetingXpictureResource extends SyBaseResource{
	
	private String xmmxpicGuid;
	
	private XmMeetingXpictureDaoImpl xmMeetingXpictureDao;
	
	public void setXmMeetingXpictureDao(XmMeetingXpictureDaoImpl xmMeetingXpictureDao) {
		this.xmMeetingXpictureDao = xmMeetingXpictureDao;
	}
	private XmMeetingPictureDaoImpl xmMeetingPictureDao;
	
	public void setXmMeetingPictureDao(XmMeetingPictureDaoImpl xmMeetingPictureDao) {
		this.xmMeetingPictureDao = xmMeetingPictureDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmxpicGuid=getAttribute("xmmxpicGuid");
		XmMeetingXpicture xmMeetingXpicture =  xmMeetingXpictureDao.findById(xmmxpicGuid);
		if(null==xmMeetingXpicture){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		//
		String xmmpicGuid=xmMeetingXpicture.getXmmpicGuid();
		if(null!=xmmpicGuid&&!"".equals(xmmpicGuid)){
			XmMeetingPicture xmMeetingPicture=xmMeetingPictureDao.findById(xmmpicGuid);
			if(null!=xmMeetingPicture){
				xmMeetingXpicture.setXmmpicGuidLabel(xmMeetingPicture.getXmmpicImageTitle()); 
			} 
		}
		//return
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingXpicture,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingXpictureDao.delete(xmmxpicGuid);
		return new StringRepresentation(xmmxpicGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmxpicGuid =  form.getFirstValue("xmmxpicGuid");
		XmMeetingXpicture b= xmMeetingXpictureDao.findById(xmmxpicGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}