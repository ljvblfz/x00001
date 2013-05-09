/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.script.resource;

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
import com.founder.sipbus.syweb.script.dao.SyScriptConfigDaoImpl;
import com.founder.sipbus.syweb.script.po.SyScriptConfig;
import com.founder.sipbus.syweb.au.base.SyBaseResource;



/***
 * 
 * @author zk
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syScriptConfig/{sscguid}")
public class SyScriptConfigResource extends SyBaseResource{
	
	private String sscguid;
	
	private SyScriptConfigDaoImpl syScriptConfigDao;
	
	public void setSyScriptConfigDao(SyScriptConfigDaoImpl syScriptConfigDao) {
		this.syScriptConfigDao = syScriptConfigDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		sscguid=getAttribute("sscguid");
		SyScriptConfig syScriptConfig =  syScriptConfigDao.findById(sscguid);
		if(null==syScriptConfig){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(syScriptConfig,config));
	}
}