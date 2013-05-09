/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.cck.resource;

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
import com.founder.sipbus.syweb.cck.dao.SyCckTypeButtonDaoImpl;
import com.founder.sipbus.syweb.cck.po.SyCckTypeButton;
import com.founder.sipbus.syweb.cck.service.SyCckService;

/***
 * cckbutton 的数据提供
 * urls="/syCckTypeButton/{sctGuid}"
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syCckTypeButton/{sctGuid}")
public class SyCckTypeButtonsAllResource extends SyBaseResource {
	
 
	private SyCckTypeButtonDaoImpl syCckTypeButtonDao;
	private SyCckService syCckService;
	public void setSyCckTypeButtonDao(SyCckTypeButtonDaoImpl syCckTypeButtonDao) {
		this.syCckTypeButtonDao = syCckTypeButtonDao;
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
		JSON jp =   syCckService.getButtonCache(sctGuid);
		if (jp==null) {
			List<SyCckTypeButton> list = syCckTypeButtonDao.findBySctGuid( sctGuid);
			for (SyCckTypeButton syCckTypeButton : list) {
				if ("5".equals(syCckTypeButton.getButtonType())) {
				String childrenSctGuid=	syCckService.getCachedCckTypeGuidForListByCode(syCckTypeButton.getButtonDetailTypeCode());
				syCckTypeButton.setChildrenSctGuid(childrenSctGuid);
				}
			}
		  jp = JSONSerializer.toJSON(list,config);
		  syCckService.setButtonCache(sctGuid, jp);
		}  
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}

	public SyCckService getSyCckService() {
		return syCckService;
	}

	public void setSyCckService(SyCckService syCckService) {
		this.syCckService = syCckService;
	}
	
	 
}