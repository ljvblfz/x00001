/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */

package com.founder.sipbus.syweb.cck.resource;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.cck.dao.SyCckTypeDaoImpl;
import com.founder.sipbus.syweb.cck.po.SyCckType;
import com.founder.sipbus.syweb.cck.service.SyCckService;
import com.founder.sipbus.syweb.cck.vo.SyCckTypeSearchPVO;

/***
 * 删除cck缓存
 *urls = "/syCckType/clearCache"
 * @author Founder
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/syCckType/clearCache")
public class SyCckClearCachesResource extends SyBaseResource {

	private SyCckTypeDaoImpl syCckTypeDao;
	private	SyCckService	syCckService;
	public SyCckService getSyCckService() {
		return syCckService;
	}

	public void setSyCckService(SyCckService syCckService) {
		this.syCckService = syCckService;
	}
	 

	public void setSyCckTypeDao(SyCckTypeDaoImpl syCckTypeDao) {
		this.syCckTypeDao = syCckTypeDao;
	}

	/**
	 *sctGuids为 ids
	 *  @param entity
	 *  @return
	 *  @throws Exception
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午5:00:23 
	 */
	@Delete
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity);
		form.getFirstValue("sctGuids");
		String idsStr = form.getFirstValue("sctGuids");
		if (StringUtils.isNotBlank(idsStr)) {
			String[] ids = idsStr.split(",");
			if (ids.length>0) {
				for (String id : ids) {
					syCckService.clearCache(id );
				}
			}
		}
		
		else{
		SyCckTypeSearchPVO sVO = new SyCckTypeSearchPVO();
		PMGridCopyUtil.copyGridToDto(sVO, getQueryMap());
		List<SyCckType> list = syCckTypeDao.findAll(); 
		for (SyCckType syCckType : list) {
			syCckService.clearCache(syCckType.getSctGuid());
		}
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}

		
	 
}