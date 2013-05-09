/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.script.resource;

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
import com.founder.sipbus.syweb.script.dao.SyScriptDecisiontableDaoImpl;
import com.founder.sipbus.syweb.script.po.SyScriptDecisiontable;
import com.founder.sipbus.syweb.script.vo.SyScriptDecisiontableSearchVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syScriptDecisiontable")
public class SyScriptDecisiontablesResource extends SyBaseResource {
	
	private SyScriptDecisiontableDaoImpl syScriptDecisiontableDao;
	
	public void setSyScriptDecisiontableDao(SyScriptDecisiontableDaoImpl syScriptDecisiontableDao) {
		this.syScriptDecisiontableDao = syScriptDecisiontableDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		SyScriptDecisiontableSearchVO sVO=new SyScriptDecisiontableSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = syScriptDecisiontableDao.findPage(getPageRequest(),fillDetachedCriteria(SyScriptDecisiontable.class,sVO));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("gsdtguids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			syScriptDecisiontableDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		SyScriptDecisiontable syScriptDecisiontable = new SyScriptDecisiontable();
		PMGridCopyUtil.copyGridToDto(syScriptDecisiontable, form.getValuesMap());
		syScriptDecisiontableDao.add(syScriptDecisiontable);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}