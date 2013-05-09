/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.cck.resource;

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
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.cck.dao.SyCckContentDaoImpl;
import com.founder.sipbus.syweb.cck.po.SyCckContent;
import com.founder.sipbus.syweb.cck.vo.SyCckContentSearchPVO;

/***
 * 未使用
 * urls="/syCckContent"
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syCckContent")
public class SyCckContentsResource extends SyBaseResource {
	
	private SyCckContentDaoImpl syCckContentDao;
	
	public void setSyCckContentDao(SyCckContentDaoImpl syCckContentDao) {
		this.syCckContentDao = syCckContentDao;
	}
	
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		SyCckContentSearchPVO sVO=new SyCckContentSearchPVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = syCckContentDao.findPage(getPageRequest(),fillDetachedCriteria(SyCckContent.class,sVO));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("sccGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			syCckContentDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		SyCckContent syCckContent = new SyCckContent();
		PMGridCopyUtil.copyGridToDto(syCckContent, form.getValuesMap());
		syCckContentDao.add(syCckContent);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}