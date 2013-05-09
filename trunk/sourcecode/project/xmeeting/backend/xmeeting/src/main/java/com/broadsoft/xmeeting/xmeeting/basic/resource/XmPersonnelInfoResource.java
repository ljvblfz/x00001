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
import com.broadsoft.xmeeting.xmeeting.basic.dao.XmPersonnelInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmPersonnelInfo;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmPersonnelInfo/{xmpiGuid}")
public class XmPersonnelInfoResource extends SyBaseResource{
	
	private String xmpiGuid;
	
	private XmPersonnelInfoDaoImpl xmPersonnelInfoDao;
	
	public void setXmPersonnelInfoDao(XmPersonnelInfoDaoImpl xmPersonnelInfoDao) {
		this.xmPersonnelInfoDao = xmPersonnelInfoDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmpiGuid=getAttribute("xmpiGuid");
		XmPersonnelInfo xmPersonnelInfo =  xmPersonnelInfoDao.findById(xmpiGuid);
		if(null==xmPersonnelInfo){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmPersonnelInfo,config));
	}
	@Delete
	public Representation delete() {
		xmPersonnelInfoDao.delete(xmpiGuid);
		return new StringRepresentation(xmpiGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmpiGuid =  form.getFirstValue("xmpiGuid");
		XmPersonnelInfo b= xmPersonnelInfoDao.findById(xmpiGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}