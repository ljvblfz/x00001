/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.open.resource;

import net.sf.json.JSONObject;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.basic.dao.XmPadDeviceDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmPadDevice;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmDownloadStatusDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingPersonnelSeatPadDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmDownloadStatus;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingPersonnelSeatPad;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;



/**
 * 此服务为pad现在资料的状态
 * @author lu.zhen
 *
 */

@Component
@Scope(value="prototype")
@RestletResource(urls="/open/downloadstatus/query/xmpdDeviceId/{xmpdDeviceId}")
public class XmPadDownloadStatusQueryResource extends SyBaseResource{
	
	//设备id-物理编号
	private String xmpdDeviceId; 
	 
	@Override
    protected void doInit() throws ResourceException {
		xmpdDeviceId=getAttribute("xmpdDeviceId"); 
	}
	
	@Get
	public Representation get(Representation entity) throws ResourceException { 
		JSONObject allInfo=new JSONObject();
		XmPadDevice xmPadDevice=xmPadDeviceDao.findByXmpdDeviceId(xmpdDeviceId);
		allInfo.put("xmPadDevice", xmPadDevice);
//		if(null==xmPadDevice){
//			return this.getFailedRepresentation();  
//		}
		String xmpdGuid=xmPadDevice.getXmpdGuid();
		
		XmMeetingPersonnelSeatPad xmMeetingPersonnelSeatPad=xmMeetingPersonnelSeatPadDao.findByXmpdGuid(xmpdGuid);
//		if(null==xmMeetingPersonnelSeatPad){
//			return this.getFailedRepresentation();   
//		}
		String xmmiGuid=xmMeetingPersonnelSeatPad.getXmmiGuid();
		XmDownloadStatus xmDownloadStatus=xmDownloadStatusDao.findByXmpdGuidAndXmmiGuid(xmpdGuid,xmmiGuid);
		allInfo.put("xmDownloadStatus", xmDownloadStatus);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(allInfo));   
	}
	
	
	//=============IOC=================================
	private XmDownloadStatusDaoImpl xmDownloadStatusDao; 
	public void setXmDownloadStatusDao(XmDownloadStatusDaoImpl xmDownloadStatusDao) {
		this.xmDownloadStatusDao = xmDownloadStatusDao;
	} 
	
	private XmPadDeviceDaoImpl xmPadDeviceDao; 
	public void setXmPadDeviceDao(XmPadDeviceDaoImpl xmPadDeviceDao) {
		this.xmPadDeviceDao = xmPadDeviceDao;
	}
	
	private XmMeetingPersonnelSeatPadDaoImpl xmMeetingPersonnelSeatPadDao;

	public void setXmMeetingPersonnelSeatPadDao(
			XmMeetingPersonnelSeatPadDaoImpl xmMeetingPersonnelSeatPadDao) {
		this.xmMeetingPersonnelSeatPadDao = xmMeetingPersonnelSeatPadDao;
	}
	
	
}