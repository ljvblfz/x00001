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
import com.broadsoft.xmeeting.xmeeting.basic.dao.XmCompanyInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmCompanyInfo;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmCompanyInfo/{xmciGuid}")
public class XmCompanyInfoResource extends SyBaseResource{
	
	private String xmciGuid;
	
	private XmCompanyInfoDaoImpl xmCompanyInfoDao;
	
	public void setXmCompanyInfoDao(XmCompanyInfoDaoImpl xmCompanyInfoDao) {
		this.xmCompanyInfoDao = xmCompanyInfoDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmciGuid=getAttribute("xmciGuid");
		XmCompanyInfo xmCompanyInfo =  xmCompanyInfoDao.findById(xmciGuid);
		if(null==xmCompanyInfo){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmCompanyInfo,config));
	}
	@Delete
	public Representation delete() {
		xmCompanyInfoDao.delete(xmciGuid);
		return new StringRepresentation(xmciGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmciGuid =  form.getFirstValue("xmciGuid");
		XmCompanyInfo b= xmCompanyInfoDao.findById(xmciGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}