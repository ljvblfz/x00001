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
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.service.SyCodeService;
import com.founder.sipbus.syweb.view.dao.SyViewDaoImpl;
import com.founder.sipbus.syweb.view.po.SyView;


@Component
@Scope(value="prototype")
@RestletResource(urls="/syView/{svGuid}")
public class SyViewResource extends SyBaseResource{
	
	private String svGuid;
	
	private SyViewDaoImpl syViewDao;
	
	public void setSyViewDao(SyViewDaoImpl syViewDao) {
		this.syViewDao = syViewDao;
	}
	

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		svGuid=getAttribute("svGuid");
		SyView syView =  syViewDao.findById(svGuid);
		if(null==syView){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(syView,config));
	}
//	@Delete
//	public Representation delete() {
//		syViewDao.delete(svGuid);
//		return new StringRepresentation(svGuid);
//	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		svGuid =  form.getFirstValue("svGuid");
		SyView b= syViewDao.findById(svGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}