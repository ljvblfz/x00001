/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.refmgmt.resource;

import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
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
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.refmgmt.dao.SyWidgetReferenceDaoImpl;
import com.founder.sipbus.syweb.refmgmt.po.SyWidgetReference;
import com.founder.sipbus.syweb.refmgmt.service.SyWidgetReferenceService;


/**
 * @author zjl
 * urls="/syWidgetReference/{swrGuid}" 

 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syWidgetReference/{swrGuid}")
public class SyWidgetReferenceResource extends SyBaseResource{
	
	private String swrGuid;
	
	private SyWidgetReferenceDaoImpl syWidgetReferenceDao;
	private SyWidgetReferenceService syWidgetReferenceService;
	public void setSyWidgetReferenceDao(SyWidgetReferenceDaoImpl syWidgetReferenceDao) {
		this.syWidgetReferenceDao = syWidgetReferenceDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	/**
	 * 获取单个syWidgetReference数据
	 *	swrGuid:id
	 *  @param entity
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:10:17 
	 */
	@Get
	public Representation get(Representation entity) {
		swrGuid=getAttribute("swrGuid");
		SyWidgetReference syWidgetReference =  syWidgetReferenceDao.findById(swrGuid);
		if(null==syWidgetReference){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(syWidgetReference,config));
	}
	@Delete
	public Representation delete() {
		syWidgetReferenceService.clearCacheById(swrGuid);
		syWidgetReferenceDao.delete(swrGuid);
		
		return new StringRepresentation(swrGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		swrGuid =  form.getFirstValue("swrGuid");
		Map map=form.getValuesMap();
		if (StringUtils.isBlank(swrGuid)||StringUtils.isBlank((String) map.get("referenceValue"))) {
			setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			JSONObject jo = getErrorMessageReturnJson("缺少参数！");
			return getJsonGzipRepresentation(jo);
		}
	String msg=	syWidgetReferenceService.update(swrGuid,map);
		if (null==msg) {
			JSONObject jo = getDefaultEditReturnJson();
			return getJsonGzipRepresentation(jo);
		}else {
			JSONObject jo = getErrorMessageReturnJson(msg);
			return getJsonGzipRepresentation(jo);
		}
		
		
	}

	public SyWidgetReferenceService getSyWidgetReferenceService() {
		return syWidgetReferenceService;
	}

	public void setSyWidgetReferenceService(SyWidgetReferenceService syWidgetReferenceService) {
		this.syWidgetReferenceService = syWidgetReferenceService;
	}
}