/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.codeprinciple.resource;

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
import com.founder.sipbus.syweb.codeprinciple.dao.UtilCodePrincipleDetailDaoImpl;
import com.founder.sipbus.syweb.codeprinciple.po.UtilCodePrincipleDetail;



/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/utilCodePrincipleDetail/{ucpdGuid}")
public class UtilCodePrincipleDetailResource extends SyBaseResource{
	
	private String ucpdGuid;
	
	private UtilCodePrincipleDetailDaoImpl utilCodePrincipleDetailDao;
	
	public void setUtilCodePrincipleDetailDao(UtilCodePrincipleDetailDaoImpl utilCodePrincipleDetailDao) {
		this.utilCodePrincipleDetailDao = utilCodePrincipleDetailDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		ucpdGuid=getAttribute("ucpdGuid");
		UtilCodePrincipleDetail utilCodePrincipleDetail =  utilCodePrincipleDetailDao.findById(ucpdGuid);
		if(null==utilCodePrincipleDetail){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(utilCodePrincipleDetail,config));
	}
	@Delete
	public Representation delete() {
		utilCodePrincipleDetailDao.delete(ucpdGuid);
		return new StringRepresentation(ucpdGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		ucpdGuid =  form.getFirstValue("ucpdGuid");
		UtilCodePrincipleDetail b= utilCodePrincipleDetailDao.findById(ucpdGuid);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}