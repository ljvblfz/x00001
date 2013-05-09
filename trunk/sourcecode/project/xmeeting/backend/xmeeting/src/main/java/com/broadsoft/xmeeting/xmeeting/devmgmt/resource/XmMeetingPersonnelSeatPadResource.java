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

import com.broadsoft.xmeeting.xmeeting.basic.dao.XmPadDeviceDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.dao.XmPersonnelInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.dao.XmRoomInfoDetailDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmPadDevice;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmPersonnelInfo;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmRoomInfoDetail;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingPersonnelSeatPadDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingPersonnelSeatPad;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingPersonnelSeatPad/{xmmpspGuid}")
public class XmMeetingPersonnelSeatPadResource extends SyBaseResource{
	
	private String xmmpspGuid;
	
	private XmMeetingPersonnelSeatPadDaoImpl xmMeetingPersonnelSeatPadDao;
	
	public void setXmMeetingPersonnelSeatPadDao(XmMeetingPersonnelSeatPadDaoImpl xmMeetingPersonnelSeatPadDao) {
		this.xmMeetingPersonnelSeatPadDao = xmMeetingPersonnelSeatPadDao;
	}
	
	private XmPersonnelInfoDaoImpl xmPersonnelInfoDao; 
	public void setXmPersonnelInfoDao(XmPersonnelInfoDaoImpl xmPersonnelInfoDao) {
		this.xmPersonnelInfoDao = xmPersonnelInfoDao;
	}
	
	private XmRoomInfoDetailDaoImpl xmRoomInfoDetailDao; 
	public void setXmRoomInfoDetailDao(XmRoomInfoDetailDaoImpl xmRoomInfoDetailDao) {
		this.xmRoomInfoDetailDao = xmRoomInfoDetailDao;
	}
	
	private XmPadDeviceDaoImpl xmPadDeviceDao; 
	public void setXmPadDeviceDao(XmPadDeviceDaoImpl xmPadDeviceDao) {
		this.xmPadDeviceDao = xmPadDeviceDao;
	}
	
	//==============================

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmpspGuid=getAttribute("xmmpspGuid");
		XmMeetingPersonnelSeatPad xmMeetingPersonnelSeatPad =  xmMeetingPersonnelSeatPadDao.findById(xmmpspGuid);
		
		String xmpiGuid=xmMeetingPersonnelSeatPad.getXmpiGuid();
		String xmridGuid=xmMeetingPersonnelSeatPad.getXmridGuid();
		String xmpdGuid=xmMeetingPersonnelSeatPad.getXmpdGuid();
		XmPersonnelInfo xmPersonnelInfo=xmPersonnelInfoDao.findById(xmpiGuid);
		XmRoomInfoDetail xmRoomInfoDetail=xmRoomInfoDetailDao.findById(xmridGuid);
		XmPadDevice xmPadDevice=xmPadDeviceDao.findById(xmpdGuid);
		
		//
		xmMeetingPersonnelSeatPad.setXmpiGuidLabel(xmPersonnelInfo.getXmpiName());
		xmMeetingPersonnelSeatPad.setXmridGuidLabel(xmRoomInfoDetail.getXmridSeatno());
		xmMeetingPersonnelSeatPad.setXmpdGuidLabel(xmPadDevice.getXmpdCode());
		if(null==xmMeetingPersonnelSeatPad){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingPersonnelSeatPad,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingPersonnelSeatPadDao.delete(xmmpspGuid);
		return new StringRepresentation(xmmpspGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmpspGuid =  form.getFirstValue("xmmpspGuid");
		XmMeetingPersonnelSeatPad b= xmMeetingPersonnelSeatPadDao.findById(xmmpspGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}