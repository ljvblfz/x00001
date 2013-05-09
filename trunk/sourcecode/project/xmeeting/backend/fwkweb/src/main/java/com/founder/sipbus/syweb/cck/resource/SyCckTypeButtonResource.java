/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.cck.resource;

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
import com.founder.sipbus.syweb.cck.dao.SyCckTypeButtonDaoImpl;
import com.founder.sipbus.syweb.cck.po.SyCckTypeButton;
import com.founder.sipbus.syweb.cck.service.SyCckService;



/***
 * cck按钮（单条记录操作）
 * urls="/syCckType/{sctGuid}/syCckTypeButton/{sctbGuid}"
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syCckType/{sctGuid}/syCckTypeButton/{sctbGuid}")
public class SyCckTypeButtonResource extends SyBaseResource{
	
	private String sctbGuid;
	
	private SyCckTypeButtonDaoImpl syCckTypeButtonDao;
	private SyCckService syCckService;
	public void setSyCckTypeButtonDao(SyCckTypeButtonDaoImpl syCckTypeButtonDao) {
		this.syCckTypeButtonDao = syCckTypeButtonDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		sctbGuid=getAttribute("sctbGuid");
		SyCckTypeButton syCckTypeButton =  syCckTypeButtonDao.findById(sctbGuid);
		if(null==syCckTypeButton){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(syCckTypeButton,config));
	}
	@Delete
	public Representation delete() {
		SyCckTypeButton b= syCckTypeButtonDao.findById(sctbGuid);
		syCckService.clearButtonCache(b.getSctGuid());
		syCckTypeButtonDao.delete(sctbGuid);
		return new StringRepresentation(sctbGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		sctbGuid =  form.getFirstValue("sctbGuid");
		SyCckTypeButton b= syCckTypeButtonDao.findById(sctbGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		syCckService.clearButtonCache(b.getSctGuid());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}

	public SyCckService getSyCckService() {
		return syCckService;
	}

	public void setSyCckService(SyCckService syCckService) {
		this.syCckService = syCckService;
	}
}