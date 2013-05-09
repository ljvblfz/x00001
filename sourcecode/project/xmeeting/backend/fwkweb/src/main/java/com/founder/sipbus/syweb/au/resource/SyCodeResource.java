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
import com.founder.sipbus.syweb.au.dao.SyCodeDaoImpl;
import com.founder.sipbus.syweb.au.po.SyCode;
import com.founder.sipbus.syweb.au.service.SyCodeService;



/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syCode/{codeId}")
public class SyCodeResource extends SyBaseResource{
	
	private String codeId;
	
	private SyCodeDaoImpl syCodeDao;
	
	public void setSyCodeDao(SyCodeDaoImpl syCodeDao) {
		this.syCodeDao = syCodeDao;
	}

	private SyCodeService syCodeService;
	
	
	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}

	
	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		codeId =  (String) getRequestAttributes().get(SyCode.CODE_ID);
		SyCode syCode =  syCodeDao.findById(codeId);
		if(null==syCode){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(syCode,config));
	}
	@Delete
	public Representation delete() {
		SyCode b= syCodeDao.findById(codeId);
		syCodeService.reflushCodeType(b.getTypeId());
		syCodeDao.delete(b);
		return new StringRepresentation(codeId);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		codeId =  form.getFirstValue("codeId");
		SyCode b= syCodeDao.findById(codeId);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		syCodeService.reflushCodeType(b.getTypeId());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}