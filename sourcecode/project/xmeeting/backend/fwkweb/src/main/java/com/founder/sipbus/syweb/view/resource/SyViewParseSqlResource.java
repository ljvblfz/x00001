/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.view.resource;

import net.sf.json.JSONObject;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.fwk.jsqlparser.ViewSqlParserSupport;
import com.founder.sipbus.syweb.au.base.SyBaseResource;



/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/system/view/parsesql")
public class SyViewParseSqlResource extends SyBaseResource{   
	
	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException { 
		form = new Form(entity);  
		String sqlSource=form.getFirstValue("sqlSource");
		JSONObject jsonObject=ViewSqlParserSupport.parseSqlTextWithSql(sqlSource);  
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jsonObject));   
	}
}