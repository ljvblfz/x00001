/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.view.resource;

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
import com.founder.sipbus.syweb.view.dao.SyViewTypeButtonDaoImpl;
import com.founder.sipbus.syweb.view.po.SyViewTypeButton;
import com.founder.sipbus.syweb.view.service.SyViewService;



/***
 *	view按钮（单条记录操作）
 * urls="/syViewType/{sctGuid}/syViewTypeButton/{sctbGuid}"
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syViewType/{sctGuid}/syViewTypeButton/{sctbGuid}")
public class SyViewTypeButtonResource extends SyBaseResource{
	
	private String sctbGuid;
	private SyViewService	syViewService;
	private SyViewTypeButtonDaoImpl syViewTypeButtonDao;
 
	public void setSyViewTypeButtonDao(SyViewTypeButtonDaoImpl syViewTypeButtonDao) {
		this.syViewTypeButtonDao = syViewTypeButtonDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		sctbGuid=getAttribute("sctbGuid");
		SyViewTypeButton syViewTypeButton =  syViewTypeButtonDao.findById(sctbGuid);
		if(null==syViewTypeButton){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(syViewTypeButton,config));
	}
	@Delete
	public Representation delete() {
		SyViewTypeButton b= syViewTypeButtonDao.findById(sctbGuid);
		syViewService.clearButtonCache(b.getSctGuid());
		syViewTypeButtonDao.delete(sctbGuid);
		return new StringRepresentation(sctbGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		sctbGuid =  form.getFirstValue("sctbGuid");
		SyViewTypeButton b= syViewTypeButtonDao.findById(sctbGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		syViewService.clearButtonCache(b.getSctGuid());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}

	public SyViewService getSyViewService() {
		return syViewService;
	}

	public void setSyViewService(SyViewService syViewService) {
		this.syViewService = syViewService;
	}
}