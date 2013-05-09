/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.view.resource;

import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
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
import com.founder.sipbus.syweb.refmgmt.po.SyWidgetReference;
import com.founder.sipbus.syweb.view.dao.SyViewDaoImpl;
import com.founder.sipbus.syweb.view.po.SyView;
import com.founder.sipbus.syweb.view.vo.SyViewSearchVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.service.SyCodeService;

@Component
@Scope(value="prototype")
@RestletResource(urls="/syView")
public class SyViewsResource extends SyBaseResource {
	
	private SyViewDaoImpl syViewDao;
	
	public void setSyViewDao(SyViewDaoImpl syViewDao) {
		this.syViewDao = syViewDao;
	}

	private SyCodeService syCodeService;

	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}
	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		SyViewSearchVO sVO=new SyViewSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = syViewDao.findPage(getPageRequest(),fillDetachedCriteria(SyView.class,sVO));
		if (p != null) {
			List<SyView> list = p.getList();
			for (SyView syView : list) {
				if (null != syView.getStatus()) {
					syView.setStatusLabel(syCodeService.getSyCodeName("1013",syView.getStatus()));
				} 

				if (null != syView.getGroupName()) {
					syView.setGroupNameLabel(syCodeService.getSyCodeName("1012",syView.getGroupName()));
				} 

				if (null != syView.getSqltype()) {
					syView.setSqltypeLabel(syCodeService.getSyCodeName("1024",syView.getSqltype()));
				}  
			}
		}
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("svGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			syViewDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		SyView syView = new SyView();
		PMGridCopyUtil.copyGridToDto(syView, form.getValuesMap());
		syViewDao.add(syView);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}