/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.open.resource;

import net.sf.json.JSONObject;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;



/**
 * 此服务为pad展示所需的信息
 * @author lu.zhen
 *
 */

@Component
@Scope(value="prototype")
@RestletResource(urls="/open/service/personnel/padid/{padid}")
public class ServicePersonnelPadResource extends SyBaseResource{
	  
	private String padid; 
	
	@Override
    protected void doInit() throws ResourceException {  
		padid=this.getAttribute("padid");
	}
	
	@Get
	public Representation get(Representation entity) throws ResourceException { 
		JSONObject allInfo=new JSONObject();
	 
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(allInfo));    
	}
	
	
	//=============IOC================================= 
 
}