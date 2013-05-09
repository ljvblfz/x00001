/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.script.resource;

import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.script.dao.SyScriptConfigDaoImpl;
import com.founder.sipbus.syweb.script.po.SyScriptConfig;
import com.founder.sipbus.syweb.script.vo.SyScriptConfigSearchVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.cck.po.SyCckType;

/***
 * 
 * @author zk
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syScriptConfig")
public class SyScriptConfigsResource extends SyBaseResource {
	
	private SyScriptConfigDaoImpl syScriptConfigDao;
	
	public void setSyScriptConfigDao(SyScriptConfigDaoImpl syScriptConfigDao) {
		this.syScriptConfigDao = syScriptConfigDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity);
		List<SyScriptConfig> list = syScriptConfigDao.findAll();
		JSON jp = JSONSerializer.toJSON(list, config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));
	}
}