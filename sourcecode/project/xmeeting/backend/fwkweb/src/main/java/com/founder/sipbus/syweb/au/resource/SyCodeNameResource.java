/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.au.resource;

import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.po.SyCode;
import com.founder.sipbus.syweb.au.service.SyCodeService;



/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syNameCode/{typeName}")
public class SyCodeNameResource extends SyBaseResource{
	
	private String typeName;
	
	
	private SyCodeService syCodeService;
	
	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}

	@Override
    protected void doInit() throws ResourceException {
		typeName =  (String) getRequestAttributes().get("typeName");
	}
	
	@Get
	public Representation get(Representation entity) {
		
		List<SyCode> list = syCodeService.getSyCodeByTypeName(typeName);
		JSON jp = JSONSerializer.toJSON(list,config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));
	}
	
}