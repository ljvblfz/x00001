/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.view.resource;

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
import com.founder.sipbus.syweb.view.dao.SyViewFieldDaoImpl;
import com.founder.sipbus.syweb.view.po.SyViewField;
import com.founder.sipbus.syweb.view.vo.SyViewFieldSearchVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/syViewField")
public class SyViewFieldsResource extends SyBaseResource {
	
	private SyViewFieldDaoImpl syViewFieldDao;
	
	public void setSyViewFieldDao(SyViewFieldDaoImpl syViewFieldDao) {
		this.syViewFieldDao = syViewFieldDao;
	}

	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		SyViewFieldSearchVO sVO=new SyViewFieldSearchVO();
		
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap()); 
		System.out.println("sVO-------->"+sVO.getFieldCategory());
		PageResponse p = syViewFieldDao.findPage(getPageRequest(),fillDetachedCriteria(SyViewField.class,sVO));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("svfGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			syViewFieldDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		SyViewField syViewField = new SyViewField();
		PMGridCopyUtil.copyGridToDto(syViewField, form.getValuesMap());
		syViewFieldDao.add(syViewField);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}