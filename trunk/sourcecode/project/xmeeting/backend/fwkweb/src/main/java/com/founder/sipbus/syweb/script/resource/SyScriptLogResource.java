/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.script.resource;

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

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.script.dao.SyScriptLogDaoImpl;
import com.founder.sipbus.syweb.script.po.SyScriptLog;



/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syScriptLog/{gslguid}")
public class SyScriptLogResource extends SyBaseResource{
	
	private String gslguid;
	
	private SyScriptLogDaoImpl syScriptLogDao;
	
	public void setSyScriptLogDao(SyScriptLogDaoImpl syScriptLogDao) {
		this.syScriptLogDao = syScriptLogDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		gslguid=getAttribute("gslguid");
		SyScriptLog syScriptLog =  syScriptLogDao.findById(gslguid);
		if(null==syScriptLog){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(syScriptLog,config));
	}
	@Delete
	public Representation delete() {
		syScriptLogDao.delete(gslguid);
		return new StringRepresentation(gslguid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		gslguid =  form.getFirstValue("gslguid");
		SyScriptLog b= syScriptLogDao.findById(gslguid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}