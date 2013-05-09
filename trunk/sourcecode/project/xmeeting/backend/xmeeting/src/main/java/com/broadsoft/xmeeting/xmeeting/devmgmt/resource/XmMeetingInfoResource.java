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

import com.broadsoft.xmeeting.xmeeting.basic.dao.XmRoomInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmRoomInfo;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingInfo;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingInfo/{xmmiGuid}")
public class XmMeetingInfoResource extends SyBaseResource{
	
	private String xmmiGuid;
	
	private XmMeetingInfoDaoImpl xmMeetingInfoDao;
	
	public void setXmMeetingInfoDao(XmMeetingInfoDaoImpl xmMeetingInfoDao) {
		this.xmMeetingInfoDao = xmMeetingInfoDao;
	}
	private XmRoomInfoDaoImpl xmRoomInfoDao;
	
	public void setXmRoomInfoDao(XmRoomInfoDaoImpl xmRoomInfoDao) {
		this.xmRoomInfoDao = xmRoomInfoDao;
	}
	
	//
	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmiGuid=getAttribute("xmmiGuid");
		XmMeetingInfo xmMeetingInfo =  xmMeetingInfoDao.findById(xmmiGuid);
		
		String xmriGuid=xmMeetingInfo.getXmriGuid();
		XmRoomInfo xmRoomInfo=xmRoomInfoDao.findById(xmriGuid);
		xmMeetingInfo.setXmriGuidLabel(xmRoomInfo.getXmriName());
		//
		if(null==xmMeetingInfo){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingInfo,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingInfoDao.delete(xmmiGuid);
		return new StringRepresentation(xmmiGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmiGuid =  form.getFirstValue("xmmiGuid");
		XmMeetingInfo b= xmMeetingInfoDao.findById(xmmiGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}