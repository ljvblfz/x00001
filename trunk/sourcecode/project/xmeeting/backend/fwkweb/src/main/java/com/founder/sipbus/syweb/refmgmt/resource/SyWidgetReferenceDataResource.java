/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */

package com.founder.sipbus.syweb.refmgmt.resource;

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
import com.founder.sipbus.syweb.refmgmt.po.SyWidgetReference;
import com.founder.sipbus.syweb.refmgmt.service.SyWidgetReferenceService;

/***
 * 获取引用数据类
 * urls = "/syWidgetReferenceData/{code}
 * @author Founder
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/syWidgetReferenceData/{code}")
public class SyWidgetReferenceDataResource extends SyBaseResource {
	private CckIDaoImpl cckIDao;
	private SyCckTypeDaoImpl syCckTypeDao;
	private SyWidgetReferenceService syWidgetReferenceService;

	public CckIDaoImpl getCckIDao() {
		return cckIDao;
	}

	public SyWidgetReferenceService getSyWidgetReferenceService() {
		return syWidgetReferenceService;
	}

	public void setSyWidgetReferenceService(
			SyWidgetReferenceService syWidgetReferenceService) {
		this.syWidgetReferenceService = syWidgetReferenceService;
	}

	public void setCckIDao(CckIDaoImpl cckIDao) {
		this.cckIDao = cckIDao;
	}

	/**
	 *获取引用的数据
	 *code：引用编码
	 *q:Autocomplete查询参数
	 *limit：Autocomplete结果长度
	 *
	 *调用 {@link SyWidgetReferenceService#getCache(String)}
	 *  @param entity
	 *  @return
	 *  @throws Exception
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 上午11:17:31 
	 */
	@Get
	public Representation get(Representation entity) throws Exception {
		Map map = getQueryMap();

		String q = (String) map.get("q");
		String limit = (String) map.get("limit");
		if (StringUtils.isBlank(limit)) {
			map.put("limit", "30");
		}
		String code = getAttribute("code");

		if (StringUtils.isNotBlank(code)) {
			Map<String, Object> SQLmap = syWidgetReferenceService
					.getCache(code);
			if (null != SQLmap) {
				SyWidgetReference bean = (SyWidgetReference) SQLmap.get("bean");
				if (bean != null) {
					List<Map<String, Object>> list = null;
					if (bean.getWidgetType() == null) {
						setStatus(Status.CLIENT_ERROR_NOT_FOUND);
						return getJsonGzipRepresentation(JsonUtils
								.genSuccessReturnJson(null));
					}
					int typeint = bean.getWidgetType().intValue();
					if (typeint == 1) {
						SQLmap.put("q", q);
						SQLmap.put("limit", limit);
						list = syWidgetReferenceService.getAutocomplete( 
								SQLmap);
					} else {
						list = syWidgetReferenceService.getReference(bean
								.getReferenceValue());
 
						for (Map<String, Object> object : list) {
							Object id = object.get("ID");
							if (id == null) {
								continue;
							}
							if ((id.equals(object.get("PID")))
									|| null == object.get("PID")) {
								object.put("PID", "0");
							}
						}
 
					}

					JSON jp = JSONSerializer.toJSON(list, config);
					 
					return getJsonGzipRepresentation(JsonUtils
							.genSuccessReturnJson(jp));
				}

			} else {
				setStatus(Status.CLIENT_ERROR_NOT_FOUND);
				return getJsonGzipRepresentation(JsonUtils
						.genSuccessReturnJson(null));
			}

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
 
}