/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.view.resource;

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
import com.founder.sipbus.syweb.view.dao.SyViewFieldDaoImpl;
import com.founder.sipbus.syweb.view.po.SyViewField;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/syViewField/{svfGuid}")
public class SyViewFieldResource extends SyBaseResource{
	
	private String svfGuid;
	
	private SyViewFieldDaoImpl syViewFieldDao;
	
	public void setSyViewFieldDao(SyViewFieldDaoImpl syViewFieldDao) {
		this.syViewFieldDao = syViewFieldDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		svfGuid=getAttribute("svfGuid");
		SyViewField syViewField =  syViewFieldDao.findById(svfGuid);
		if(null==syViewField){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(syViewField,config));
	}
//	@Delete
//	public Representation delete() {
//		syViewFieldDao.delete(svfGuid);
//		return new StringRepresentation(svfGuid);
//	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		svfGuid =  form.getFirstValue("svfGuid");
		SyViewField b= syViewFieldDao.findById(svfGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}