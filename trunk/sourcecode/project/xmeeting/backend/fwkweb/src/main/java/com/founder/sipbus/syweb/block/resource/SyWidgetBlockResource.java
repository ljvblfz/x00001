/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.block.resource;

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
import com.founder.sipbus.syweb.block.dao.SyWidgetBlockDaoImpl;
import com.founder.sipbus.syweb.block.po.SyWidgetBlock;


@Component
@Scope(value="prototype")
@RestletResource(urls="/syWidgetBlock/{swbguid}")
public class SyWidgetBlockResource extends SyBaseResource{
	
	private String swbguid;
	
	private SyWidgetBlockDaoImpl syWidgetBlockDao;
	
	public void setSyWidgetBlockDao(SyWidgetBlockDaoImpl syWidgetBlockDao) {
		this.syWidgetBlockDao = syWidgetBlockDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		swbguid=getAttribute("swbguid");
		SyWidgetBlock syWidgetBlock =  syWidgetBlockDao.findById(swbguid);
		if(null==syWidgetBlock){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(syWidgetBlock,config));
	}
	@Delete
	public Representation delete() {
		swbguid=getAttribute("swbguid");
		syWidgetBlockDao.delete(swbguid);
		return new StringRepresentation(swbguid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		swbguid =  form.getFirstValue("swbguid");
		SyWidgetBlock b= syWidgetBlockDao.findById(swbguid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}