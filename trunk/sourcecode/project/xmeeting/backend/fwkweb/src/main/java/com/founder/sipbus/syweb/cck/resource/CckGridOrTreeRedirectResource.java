/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */

package com.founder.sipbus.syweb.cck.resource;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.cck.service.SyCckService;

/***
 *  根据cck sctGuid 判断list显示还是tree显示
 * /cckRedirector/gridOrTree/{sctGuid}
 * urls = "/cckRedirector/gridOrTree/{sctGuid}"
 * @author zjl
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/cckRedirector/gridOrTree/{sctGuid}")
public class CckGridOrTreeRedirectResource extends SyBaseResource {

	private SyCckService syCckService;
	private static String targetTree = "/syWeb/pages/system/cck/cckContentTreeNoButton.html";
	private static String targetGrid = "/syWeb/pages/system/cck/cckContentNoButton.html";

	@Get
	public void get(Representation entity) throws Exception {
		String sctGuid = getAttribute("sctGuid");
		if (StringUtils.isBlank(sctGuid)) {
			setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			return;
		}
		Map<String, Object> map = syCckService
				.getCachedCckTypeParamsForList(sctGuid);
		if (map == null) {
			setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			return;
		}
		String queryString = getHttpRequest().getQueryString();
		StringBuilder sBuilder = new StringBuilder();
		if ("1".equals(map.get("showType"))) {
			redirectTemporary(sBuilder.append(targetTree).append("?")
					.append(queryString).append("&sctGuid=").append(sctGuid).toString());
		} else {
			redirectTemporary(sBuilder.append(targetGrid).append("?")
					.append(queryString).append("&sctGuid=").append(sctGuid).toString());
		}

	}

	public SyCckService getSyCckService() {
		return syCckService;
	}

	public void setSyCckService(SyCckService syCckService) {
		this.syCckService = syCckService;
	}

}