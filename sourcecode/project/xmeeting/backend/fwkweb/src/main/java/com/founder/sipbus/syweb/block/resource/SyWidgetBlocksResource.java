/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.block.resource;

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
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.service.SyCodeService;
import com.founder.sipbus.syweb.block.dao.SyWidgetBlockDaoImpl;
import com.founder.sipbus.syweb.block.po.SyWidgetBlock;
import com.founder.sipbus.syweb.block.vo.SyWidgetBlockSearchVO;

@Component
@Scope(value="prototype")
@RestletResource(urls="/syWidgetBlock")
public class SyWidgetBlocksResource extends SyBaseResource {
	
	private SyWidgetBlockDaoImpl syWidgetBlockDao;
	
	public void setSyWidgetBlockDao(SyWidgetBlockDaoImpl syWidgetBlockDao) {
		this.syWidgetBlockDao = syWidgetBlockDao;
	}
	private SyCodeService syCodeService;
	
	
	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		SyWidgetBlockSearchVO sVO=new SyWidgetBlockSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = syWidgetBlockDao.findPage(getPageRequest(),fillDetachedCriteria(SyWidgetBlock.class,sVO));
		if(p != null) {
			List<SyWidgetBlock> l = p.getList();
			for (SyWidgetBlock sct : l) {
				if(StringUtils.isNotBlank(sct.getSwbtype())){
					sct.setSwbtypelabel(syCodeService.getSyCodeName("1016", sct.getSwbtype())); 
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
		String idsStr=form.getFirstValue("swguids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			syWidgetBlockDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		SyWidgetBlock syWidgetBlock = new SyWidgetBlock();
		PMGridCopyUtil.copyGridToDto(syWidgetBlock, form.getValuesMap());
		syWidgetBlockDao.add(syWidgetBlock);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}