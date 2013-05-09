/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.cck.resource;

import java.util.Map;

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
import com.founder.sipbus.syweb.cck.dao.SyCckTypeDaoImpl;
import com.founder.sipbus.syweb.cck.po.SyCckType;
import com.founder.sipbus.syweb.cck.service.SyCckService;



/***
 *  单条syCckType resource
 * urls="/syCckType/{sctGuid}"
 * @author zjl
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syCckType/{sctGuid}")
public class SyCckTypeResource extends SyBaseResource{
	
	private String sctGuid;
	
	private SyCckTypeDaoImpl syCckTypeDao;
	private	SyCckService	syCckService;
	public SyCckService getSyCckService() {
		return syCckService;
	}

	public void setSyCckService(SyCckService syCckService) {
		this.syCckService = syCckService;
	}
	public void setSyCckTypeDao(SyCckTypeDaoImpl syCckTypeDao) {
		this.syCckTypeDao = syCckTypeDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		sctGuid=getAttribute("sctGuid");
		SyCckType syCckType =  syCckTypeDao.findById(sctGuid);
		if(null==syCckType){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(syCckType,config));
	}
	@Delete
	public Representation delete() {

		syCckService.deleteType(sctGuid);
		syCckService.clearCache(sctGuid);
		return new StringRepresentation(sctGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		sctGuid =  form.getFirstValue("sctGuid");
		SyCckType b= syCckTypeDao.findById(sctGuid);
		Map map = form.getValuesMap();
		map.remove("typetable");
		PMGridCopyUtil.copyGridToDto(b,map );
		syCckService.clearCodeCache(b.getCode());
		syCckService.clearCache(sctGuid);
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}