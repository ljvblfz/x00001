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
 * 引用的缓存 by code
 * swrCodes:codes
 * urls = "/syWidgetReferenceClearCache/code"
 * @author zjl
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/syWidgetReferenceClearCache/code")
public class SyWidgetReferenceClearCacheByCodeResource extends SyBaseResource {

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
	 * <p>方法说明:</p>
	 *	删除对应引用code 的缓存 支持，分割多个code
	 *  @param entity
	 *  @return
	 *  @throws Exception
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 上午11:11:54 
	 */
	@Delete
	public Representation delete(Representation entity) throws Exception {
		form = new Form(entity);
		String codeStr = form.getFirstValue("swrCodes");
	 
		String[] codes = codeStr.split(",");
		for (String code : codes) {
			syWidgetReferenceService.clearCache(code);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());

	}


	 
}