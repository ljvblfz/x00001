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
import com.founder.sipbus.syweb.cck.dao.SyCckContentDaoImpl;
import com.founder.sipbus.syweb.cck.po.SyCckContent;



/***
 * 未使用
 * urls="/syCckContent/{sccGuid}
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syCckContent/{sccGuid}")
public class SyCckContentResource extends SyBaseResource{
	
	private String sccGuid;
	
	private SyCckContentDaoImpl syCckContentDao;
	
	public void setSyCckContentDao(SyCckContentDaoImpl syCckContentDao) {
		this.syCckContentDao = syCckContentDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		sccGuid=getAttribute("sccGuid");
		SyCckContent syCckContent =  syCckContentDao.findById(sccGuid);
		if(null==syCckContent){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(syCckContent,config));
	}
	@Delete
	public Representation delete() {
		syCckContentDao.delete(sccGuid);
		return new StringRepresentation(sccGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		sccGuid =  form.getFirstValue("sccGuid");
		SyCckContent b= syCckContentDao.findById(sccGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}