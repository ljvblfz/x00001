/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */

package com.founder.sipbus.syweb.refmgmt.resource;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.refmgmt.service.SyWidgetReferenceService;

/***
 * 引用的缓存 by id
 * urls = "/syWidgetReferenceClearCache/id"
 * @author zjl
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/syWidgetReferenceClearCache/id")
public class SyWidgetReferenceClearCacheResource extends SyBaseResource {

	private SyWidgetReferenceService syWidgetReferenceService;

	public SyWidgetReferenceService getSyWidgetReferenceService() {
		return syWidgetReferenceService;
	}

	public void setSyWidgetReferenceService(
			SyWidgetReferenceService syWidgetReferenceService) {
		this.syWidgetReferenceService = syWidgetReferenceService;
	}


	/**
	 *delete 方法
	 *删除对应引用id 的缓存 支持，分割多个id
	 *swrGuids :ids
	 *  @param entity
	 *  @return
	 *  @throws Exception
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 上午11:15:37 
	 */
	@Delete
	public Representation delete(Representation entity) throws Exception {
		form = new Form(entity);
		String idsStr = form.getFirstValue("swrGuids");
		 
		String[] ids = idsStr.split(",");
		for (String id : ids) {
			syWidgetReferenceService.clearCacheById(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());

	}


	 
}