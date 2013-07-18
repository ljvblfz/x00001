/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.basic.resource;

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
import com.broadsoft.xmeeting.xmeeting.basic.dao.XmPadDeviceDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.dao.XmRoomInfoDetailDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmRoomInfoDetail;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmPadDevice;
import com.founder.sipbus.syweb.au.base.SyBaseResource;



@Component
@Scope(value="prototype")
@RestletResource(urls="/xmRoomInfoDetail/{xmridGuid}")
public class XmRoomInfoDetailResource extends SyBaseResource{
	
	private String xmridGuid;
	
	private XmRoomInfoDetailDaoImpl xmRoomInfoDetailDao;
	
	public void setXmRoomInfoDetailDao(XmRoomInfoDetailDaoImpl xmRoomInfoDetailDao) {
		this.xmRoomInfoDetailDao = xmRoomInfoDetailDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmridGuid=getAttribute("xmridGuid");
		XmRoomInfoDetail xmRoomInfoDetail =  xmRoomInfoDetailDao.findById(xmridGuid);
		if(null==xmRoomInfoDetail){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		
		String xmpdGuid=xmRoomInfoDetail.getXmpdGuid();
		if(null!=xmpdGuid&&!"".equals(xmpdGuid)){
			XmPadDevice xmPadDevice=xmPadDeviceDao.findById(xmpdGuid);
			if(null!=xmPadDevice){
				xmRoomInfoDetail.setXmpdGuidLabel(xmPadDevice.getXmpdCode()); 
			} 
		}
		
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmRoomInfoDetail,config));
	}
	@Delete
	public Representation delete() {
		xmRoomInfoDetailDao.delete(xmridGuid);
		return new StringRepresentation(xmridGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmridGuid =  form.getFirstValue("xmridGuid");
		XmRoomInfoDetail b= xmRoomInfoDetailDao.findById(xmridGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
	//========================
	private XmPadDeviceDaoImpl xmPadDeviceDao; 
	public void setXmPadDeviceDao(XmPadDeviceDaoImpl xmPadDeviceDao) {
		this.xmPadDeviceDao = xmPadDeviceDao;
	}
}