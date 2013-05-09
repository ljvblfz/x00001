/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */

package com.founder.sipbus.syweb.cck.resource;

import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.sys.cck.dao.CckIDaoImpl;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.cck.dao.SyCckTypeDaoImpl;
import com.founder.sipbus.syweb.cck.service.SyCckService;

/***
 *  获取Autocomplete 数据
 * @author zjl
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/cckAutocomplete/{sctGuid}/{sctfGuid}")
@Deprecated
public class CckAutocompleteResource extends SyBaseResource {

	private CckIDaoImpl cckIDao;
	private SyCckTypeDaoImpl syCckTypeDao;
	private SyCckService syCckService;
	public CckIDaoImpl getCckIDao() {
		return cckIDao;
	}

	public void setCckIDao(CckIDaoImpl cckIDao) {
		this.cckIDao = cckIDao;
	}

	@Get
	@Deprecated
	public Representation get(Representation entity) throws Exception {
		Map map = getQueryMap();
		
		String q =(String) map.get("q");
		String limit=(String) map.get("limit");
		if (StringUtils.isBlank(limit)) {
			map.put("limit","30");
		}
		String sctGuid = getAttribute("sctGuid");
		String sctfGuid = getAttribute("sctfGuid");
		if (StringUtils.isNotBlank(sctGuid)&&StringUtils.isNotBlank(sctfGuid)) {
			List<Map<String, Object>> list = syCckService.getAutocomplete(sctGuid,sctfGuid,map);
			for (Map<String, Object> object : list) {
				if (object.get("ID")!=null&&object.get("ID").equals(object.get("PID")) ) {
					object.put("PID", "0");
				}
			}
			JSON jp = JSONSerializer.toJSON(list, config);
			return getJsonGzipRepresentation(JsonUtils
					.genSuccessReturnJson(jp));
		}

		setStatus(Status.CLIENT_ERROR_NOT_FOUND);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(null));

	}

	public SyCckTypeDaoImpl getSyCckTypeDao() {
		return syCckTypeDao;
	}

	public void setSyCckTypeDao(SyCckTypeDaoImpl syCckTypeDao) {
		this.syCckTypeDao = syCckTypeDao;
	}

	public SyCckService getSyCckService() {
		return syCckService;
	}

	public void setSyCckService(SyCckService syCckService) {
		this.syCckService = syCckService;
	}

}