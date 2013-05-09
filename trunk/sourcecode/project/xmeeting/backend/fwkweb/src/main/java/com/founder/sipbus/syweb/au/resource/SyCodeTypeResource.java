/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.au.resource;

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
import com.founder.sipbus.syweb.au.po.SyCodeType;
import com.founder.sipbus.syweb.au.service.SyCodeService;



/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syCodeType/{typeId}")
public class SyCodeTypeResource extends SyBaseResource{
	
	private String typeId;
	
	private SyCodeService syCodeService;
	

	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		typeId =  (String) getRequestAttributes().get(SyCodeType.TYPE_ID);
		SyCodeType syCodeType =syCodeService.getSyCodeTypeById(typeId);
		if(null==syCodeType){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(syCodeType,config));
	}
	@Delete
	public Representation delete() {
		syCodeService.deleteSyCodeType(typeId);
		return new StringRepresentation(typeId);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		typeId =  (String) getRequestAttributes().get(SyCodeType.TYPE_ID);
//		typeId =  form.getFirstValue("typeId");
		SyCodeType b= syCodeService.findById(typeId);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		syCodeService.reflushCodeType(typeId);
//		syCodeTypeDao.cacheUpdateSyCode(b);
		return getJsonGzipRepresentation(jo);
	}
	
	
}