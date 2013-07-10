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
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingServicePersonnelDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingServicePersonnel;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingServicePersonnel/{xmmspGuid}")
public class XmMeetingServicePersonnelResource extends SyBaseResource{
	
	private String xmmspGuid;
	
	private XmMeetingServicePersonnelDaoImpl xmMeetingServicePersonnelDao;
	
	public void setXmMeetingServicePersonnelDao(XmMeetingServicePersonnelDaoImpl xmMeetingServicePersonnelDao) {
		this.xmMeetingServicePersonnelDao = xmMeetingServicePersonnelDao;
	}
	
	private XmPadDeviceDaoImpl xmPadDeviceDao; 
	public void setXmPadDeviceDao(XmPadDeviceDaoImpl xmPadDeviceDao) {
		this.xmPadDeviceDao = xmPadDeviceDao;
	}
	

	private XmPersonnelInfoDaoImpl xmPersonnelInfoDao; 
	public void setXmPersonnelInfoDao(XmPersonnelInfoDaoImpl xmPersonnelInfoDao) {
		this.xmPersonnelInfoDao = xmPersonnelInfoDao;
	}	
	private XmRoomInfoDetailDaoImpl xmRoomInfoDetailDao; 
	public void setXmRoomInfoDetailDao(XmRoomInfoDetailDaoImpl xmRoomInfoDetailDao) {
		this.xmRoomInfoDetailDao = xmRoomInfoDetailDao;
	}
	
	//==============================

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmmspGuid=getAttribute("xmmspGuid");
		XmMeetingServicePersonnel xmMeetingServicePersonnel =  xmMeetingServicePersonnelDao.findById(xmmspGuid);

		String xmpiGuid=xmMeetingServicePersonnel.getXmpiGuid();
		XmPersonnelInfo xmPersonnelInfo=xmPersonnelInfoDao.findById(xmpiGuid); 
 
		String xmpdGuid=xmMeetingServicePersonnel.getXmpdGuid();
		XmPadDevice xmPadDevice=xmPadDeviceDao.findById(xmpdGuid);

		String xmridGuid=xmMeetingServicePersonnel.getXmridGuid();
		XmRoomInfoDetail xmRoomInfoDetail=xmRoomInfoDetailDao.findById(xmridGuid);
		
		xmMeetingServicePersonnel.setXmpiGuidLabel(xmPersonnelInfo.getXmpiName());
		xmMeetingServicePersonnel.setXmridGuidLabel(xmRoomInfoDetail.getXmridSeatno());
		xmMeetingServicePersonnel.setXmpdGuidLabel(xmPadDevice.getXmpdCode());
		if(null==xmMeetingServicePersonnel){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmMeetingServicePersonnel,config));
	}
	@Delete
	public Representation delete() {
		xmMeetingServicePersonnelDao.delete(xmmspGuid);
		return new StringRepresentation(xmmspGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmmspGuid =  form.getFirstValue("xmmspGuid");
		XmMeetingServicePersonnel b= xmMeetingServicePersonnelDao.findById(xmmspGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}