/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.script.resource;

import net.sf.json.JSONObject;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.script.dao.SyScriptDecisiontableDaoImpl;
import com.founder.sipbus.syweb.script.po.SyScriptDecisiontable;



/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syScriptDecisiontable/{gsguid}")
public class SyScriptDecisiontableResource extends SyBaseResource{
	
	private String gsguid;
	
	private SyScriptDecisiontableDaoImpl syScriptDecisiontableDao;
	
	public void setSyScriptDecisiontableDao(SyScriptDecisiontableDaoImpl syScriptDecisiontableDao) {
		this.syScriptDecisiontableDao = syScriptDecisiontableDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		gsguid =  form.getFirstValue("gsguid");
		SyScriptDecisiontable b= syScriptDecisiontableDao.findById(gsguid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}