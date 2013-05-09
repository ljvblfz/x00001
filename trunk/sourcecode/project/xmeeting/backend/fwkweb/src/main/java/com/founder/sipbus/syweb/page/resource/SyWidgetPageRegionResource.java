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
import com.founder.sipbus.syweb.page.dao.SyWidgetPageRegionDaoImpl;
import com.founder.sipbus.syweb.page.po.SyWidgetPageRegion;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/syWidgetPageRegion/{sprguid}")
public class SyWidgetPageRegionResource extends SyBaseResource{
	
	private String sprguid;
	
	private SyWidgetPageRegionDaoImpl syWidgetPageRegionDao;
	
	public void setSyWidgetPageRegionDao(SyWidgetPageRegionDaoImpl syWidgetPageRegionDao) {
		this.syWidgetPageRegionDao = syWidgetPageRegionDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		sprguid=getAttribute("sprguid");
		SyWidgetPageRegion syWidgetPageRegion =  syWidgetPageRegionDao.findById(sprguid);
		if(null==syWidgetPageRegion){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(syWidgetPageRegion,config));
	}
	@Delete
	public Representation delete() {
		syWidgetPageRegionDao.delete(sprguid);
		return new StringRepresentation(sprguid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		sprguid =  form.getFirstValue("sprguid");
		SyWidgetPageRegion b= syWidgetPageRegionDao.findById(sprguid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}