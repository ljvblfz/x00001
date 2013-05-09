/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.page.resource;

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
import com.founder.sipbus.syweb.page.dao.SyWidgetPageRegionDaoImpl;
import com.founder.sipbus.syweb.page.po.SyWidgetPageRegion;
import com.founder.sipbus.syweb.page.vo.SyWidgetPageRegionSearchVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/syWidgetPageRegion")
public class SyWidgetPageRegionsResource extends SyBaseResource {
	
	private SyWidgetPageRegionDaoImpl syWidgetPageRegionDao;
	
	public void setSyWidgetPageRegionDao(SyWidgetPageRegionDaoImpl syWidgetPageRegionDao) {
		this.syWidgetPageRegionDao = syWidgetPageRegionDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		SyWidgetPageRegionSearchVO sVO=new SyWidgetPageRegionSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = syWidgetPageRegionDao.findPage(getPageRequest(),fillDetachedCriteria(SyWidgetPageRegion.class,sVO));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("sprguids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			syWidgetPageRegionDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		SyWidgetPageRegion syWidgetPageRegion = new SyWidgetPageRegion();
		PMGridCopyUtil.copyGridToDto(syWidgetPageRegion, form.getValuesMap());
		syWidgetPageRegionDao.add(syWidgetPageRegion);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}