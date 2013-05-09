/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.cck.resource;

import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.cck.service.SyCckService;



/***
 * 校验ccktype的 code 是否唯一
 * urls="/syCckType/validate/code/{code}"
 * @author zjl
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syCckType/validate/code/{code}")
public class SyCckTypeCodeRemoteValidateResource extends SyBaseResource{
 
	private	SyCckService	syCckService;
	public SyCckService getSyCckService() {
		return syCckService;
	}

	public void setSyCckService(SyCckService syCckService) {
		this.syCckService = syCckService;
	}
 

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
	String code = getAttribute("code");
	Map queryMap = getQueryMap();
	String sctGuid=(String) queryMap.get("sctGuid");
	if (StringUtils.isBlank(sctGuid)) {
		sctGuid=" ";
	}
	JSON jp =null;
		if (StringUtils.isNotBlank(code)) {
		boolean b = syCckService.checkCckTypeCodeUnique(  code ,sctGuid);
			if (b) {
				  jp = JSONSerializer.toJSON( "{result:true}",config);
					return getJsonGzipRepresentation( jp );  
			}else {

				 jp = JSONSerializer.toJSON( "{result:false,message:\"" + code + " 已被使用。\"}",config);
				return getJsonGzipRepresentation( jp );   
			}
		}else {
			  jp = JSONSerializer.toJSON( "{result:true }",config);
			return getJsonGzipRepresentation( jp );  
		}
	 
	}
}