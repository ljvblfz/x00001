/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.page.resource;

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
import com.founder.sipbus.syweb.page.dao.SyWidgetPageDaoImpl;
import com.founder.sipbus.syweb.page.po.SyWidgetPage;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/syWidgetPage/{spguid}")
public class SyWidgetPageResource extends SyBaseResource{
	
	private String spguid;
	
	private SyWidgetPageDaoImpl syWidgetPageDao;
	
	public void setSyWidgetPageDao(SyWidgetPageDaoImpl syWidgetPageDao) {
		this.syWidgetPageDao = syWidgetPageDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		spguid=getAttribute("spguid");
		SyWidgetPage syWidgetPage =  syWidgetPageDao.findById(spguid);
		if(null==syWidgetPage){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(syWidgetPage,config));
	}
	@Delete
	public Representation delete() {
		syWidgetPageDao.delete(spguid);
		return new StringRepresentation(spguid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		spguid =  form.getFirstValue("spguid");
		SyWidgetPage b= syWidgetPageDao.findById(spguid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}