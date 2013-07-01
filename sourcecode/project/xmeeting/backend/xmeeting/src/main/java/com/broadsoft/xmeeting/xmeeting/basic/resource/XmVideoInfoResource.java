/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.basic.resource;

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

import com.broadsoft.xmeeting.xmeeting.basic.dao.XmVideoInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmVideoInfo;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmVideoInfo/{xmviGuid}")
public class XmVideoInfoResource extends SyBaseResource{
	
	private String xmviGuid;
	
	private XmVideoInfoDaoImpl xmVideoInfoDao;
	
	public void setXmVideoInfoDao(XmVideoInfoDaoImpl xmVideoInfoDao) {
		this.xmVideoInfoDao = xmVideoInfoDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		xmviGuid=getAttribute("xmviGuid");
		XmVideoInfo xmVideoInfo =  xmVideoInfoDao.findById(xmviGuid);
		if(null==xmVideoInfo){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(xmVideoInfo,config));
	}
	@Delete
	public Representation delete() {
		xmVideoInfoDao.delete(xmviGuid);
		return new StringRepresentation(xmviGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		xmviGuid =  form.getFirstValue("xmviGuid");
		XmVideoInfo b= xmVideoInfoDao.findById(xmviGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}