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
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

/**
 * 此服务为pad展示所需的信息
 * 
 * @author lu.zhen
 * 
 */

@Component
@Scope(value = "prototype")
@RestletResource(urls = "/open/padinfo/download/xmpdDeviceId/{xmpdDeviceId}")
public class XmPadInfoDownloadResource extends SyBaseResource {

	// 会议Pid
	private String xmpdDeviceId;

	@Override
	protected void doInit() throws ResourceException {
		xmpdDeviceId = getAttribute("xmpdDeviceId");
	}

	@Get
	public Representation get(Representation entity) throws ResourceException {
		JSONObject allInfo = new JSONObject();
		XmPadDevice xmPadDevice=xmPadDeviceDao.findByXmpdDeviceId(xmpdDeviceId);
		allInfo.put("xmPadDevice", xmPadDevice);
		//
		return getJsonGzipRepresentation(JsonUtils
				.genSuccessReturnJson(allInfo));

	}

	// =============IOC=================================
	
	private XmPadDeviceDaoImpl xmPadDeviceDao;
	
	public void setXmPadDeviceDao(XmPadDeviceDaoImpl xmPadDeviceDao) {
		this.xmPadDeviceDao = xmPadDeviceDao;
	}
}