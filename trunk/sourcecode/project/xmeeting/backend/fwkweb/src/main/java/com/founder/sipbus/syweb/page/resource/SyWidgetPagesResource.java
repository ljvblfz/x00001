/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.page.resource;

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
import com.founder.sipbus.syweb.page.dao.SyWidgetPageDaoImpl;
import com.founder.sipbus.syweb.page.po.SyWidgetPage;
import com.founder.sipbus.syweb.page.vo.SyWidgetPageSearchVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.service.SyCodeService;
import com.founder.sipbus.syweb.block.po.SyWidgetBlock;

@Component
@Scope(value="prototype")
@RestletResource(urls="/syWidgetPage")
public class SyWidgetPagesResource extends SyBaseResource {
	
	private SyWidgetPageDaoImpl syWidgetPageDao;
	
	public void setSyWidgetPageDao(SyWidgetPageDaoImpl syWidgetPageDao) {
		this.syWidgetPageDao = syWidgetPageDao;
	}
	private SyCodeService syCodeService;
	
	
	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		SyWidgetPageSearchVO sVO=new SyWidgetPageSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = syWidgetPageDao.findPage(getPageRequest(),fillDetachedCriteria(SyWidgetPage.class,sVO));
		if(p != null) {
			List<SyWidgetPage> l = p.getList();
			for (SyWidgetPage sct : l) {
				if(StringUtils.isNotBlank(sct.getSptype())){
					sct.setSptypelabel(syCodeService.getSyCodeName("1016", sct.getSptype())); 
				}

				if(StringUtils.isNotBlank(sct.getStatus())){ 
					sct.setStatuslabel(syCodeService.getSyCodeName("1017", sct.getStatus()));
				}
				if(StringUtils.isNotBlank(sct.getGroupname())){
					sct.setGroupnamelabel(syCodeService.getSyCodeName("1001", sct.getGroupname()));
				}
				

			}
		} 
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("spguids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			syWidgetPageDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		SyWidgetPage syWidgetPage = new SyWidgetPage();
		PMGridCopyUtil.copyGridToDto(syWidgetPage, form.getValuesMap());
		syWidgetPageDao.add(syWidgetPage);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}