/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.view.resource;

import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.view.dao.SyViewTypeButtonDaoImpl;
import com.founder.sipbus.syweb.view.po.SyViewTypeButton;
import com.founder.sipbus.syweb.view.service.SyViewService;

/***
 * View button 的数据提供
 * urls="/syViewTypeButton/{sctGuid}"
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syViewTypeButton/{sctGuid}")
public class SyViewTypeButtonsAllResource extends SyBaseResource {
	
 
	private SyViewTypeButtonDaoImpl syViewTypeButtonDao;
	private SyViewService syViewService;
	public void setSyViewTypeButtonDao(SyViewTypeButtonDaoImpl syViewTypeButtonDao) {
		this.syViewTypeButtonDao = syViewTypeButtonDao;
	}
 
	/**
	 *	获取 对应 sctGuid 的所有button
	 *  @param entity
	 *  @return
	 *  @throws Exception
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午5:13:04 
	 */
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		String sctGuid = getAttribute("sctGuid");
		JSON jp =   syViewService.getButtonCache(sctGuid);
		if (jp==null) {
			List<SyViewTypeButton> list = syViewTypeButtonDao.findBySctGuid( sctGuid);
//			for (SyViewTypeButton syViewTypeButton : list) {
//				if ("5".equals(syViewTypeButton.getButtonType())) {
//				String childrenSctGuid=	syViewService.getCachedViewTypeGuidForListByCode(syViewTypeButton.getButtonDetailTypeCode());
//				syViewTypeButton.setChildrenSctGuid(childrenSctGuid);
//				}
//			}
		  jp = JSONSerializer.toJSON(list,config);
		  syViewService.setButtonCache(sctGuid, jp);
		}  
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}

	public SyViewService getSyViewService() {
		return syViewService;
	}

	public void setSyViewService(SyViewService syViewService) {
		this.syViewService = syViewService;
	}
	
	 
}