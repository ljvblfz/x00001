/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */

package com.founder.sipbus.syweb.au.resource;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import net.sf.json.JSONSerializer;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.dao.SyCodeTypeDaoImpl;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/validateExists/{tableName}/{columnName}/{typeName}")
public class ValidateExistsResource extends SyBaseResource {

	private String typeName;
	
	private String tableName;
	
	private String columnName;

	private SyCodeTypeDaoImpl syCodeTypeDao;

	public void setSyCodeTypeDao(SyCodeTypeDaoImpl syCodeTypeDao) {
		this.syCodeTypeDao = syCodeTypeDao;
	}

	@Override
	protected void doInit() throws ResourceException {
		try {
			tableName = URLDecoder.decode((String)getRequestAttributes().get("tableName"),"UTF-8");
			columnName = URLDecoder.decode((String)getRequestAttributes().get("columnName"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Get
	public Representation get(Representation entity) {
		form = new Form(entity);
		typeName = ((String) this.getQueryMap().get("typeName")).trim();
		Boolean flag = syCodeTypeDao.checkIfExistsTypeName(tableName,columnName,typeName);
		if(flag){
			return getJsonGzipRepresentation(JSONSerializer.toJSON("{result:false,message:\" " + typeName + " 已存在\"}", config));
		}else {
			return getJsonGzipRepresentation(JSONSerializer.toJSON("{result:true}", config));
		}
	}

}