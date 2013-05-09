/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */

package com.founder.sipbus.syweb.refmgmt.resource;

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
import com.founder.sipbus.syweb.refmgmt.dao.SyWidgetReferenceDaoImpl;
import com.founder.sipbus.syweb.refmgmt.po.SyWidgetReference;
import com.founder.sipbus.syweb.refmgmt.service.SyWidgetReferenceService;
import com.founder.sipbus.syweb.refmgmt.vo.SyWidgetReferenceSearchVO;

/**
 * @author zjl
 * urls = "/syWidgetReference" 
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/syWidgetReference")
public class SyWidgetReferencesResource extends SyBaseResource {

	private SyWidgetReferenceDaoImpl syWidgetReferenceDao;
	private SyWidgetReferenceService syWidgetReferenceService;

	public void setSyWidgetReferenceDao(
			SyWidgetReferenceDaoImpl syWidgetReferenceDao) {
		this.syWidgetReferenceDao = syWidgetReferenceDao;
	}

	private SyCodeService syCodeService;

	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity);
		SyWidgetReferenceSearchVO sVO = new SyWidgetReferenceSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO, getQueryMap());
		PageResponse p = syWidgetReferenceDao.findPage(getPageRequest(),
				fillDetachedCriteria(SyWidgetReference.class, sVO));

		if (p != null) {
			List<SyWidgetReference> l = p.getList();
			for (SyWidgetReference sct : l) {
				if (null != sct.getWidgetType()) {
					sct.setWidgetTypeLabel(syCodeService.getSyCodeName("1007",
							sct.getWidgetType().toString()));
				}

				if (null != sct.getAutocompleteConfig()) {
					sct.setAutocompleteConfigLabel(syCodeService.getSyCodeName(
							"1008", sct.getAutocompleteConfig().toString()));
				}
				if (StringUtils.isNotBlank(sct.getGroupname())) {
					sct.setGroupnameLabel(syCodeService.getSyCodeName("1001",
							sct.getGroupname()));
				}

			}
		}
		JSON jp = JSONSerializer.toJSON(getPageResponse(p), config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));
	}

	@Delete
	public Representation delete(Representation entity) {
		form = new Form(entity);
		String idsStr = form.getFirstValue("swrGuids");
		String[] ids = idsStr.split(",");
		for (String id : ids) {
			syWidgetReferenceService.clearCacheById(id);//先删缓存不然不能获得code
			syWidgetReferenceDao.delete(id);
			
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}

	@Post
	public Representation post(Representation entity) throws ResourceException {

		form = new Form(entity);
		SyWidgetReference syWidgetReference = new SyWidgetReference();
		PMGridCopyUtil.copyGridToDto(syWidgetReference, form.getValuesMap());
		String msg = syWidgetReferenceService.add(syWidgetReference);
		if (StringUtils.isBlank(msg)) {
			return getJsonGzipRepresentation(getDefaultAddReturnJson());
		} else {
			return getJsonGzipRepresentation(getErrorMessageReturnJson(msg));
		}

	}

	public SyWidgetReferenceService getSyWidgetReferenceService() {
		return syWidgetReferenceService;
	}

	public void setSyWidgetReferenceService(
			SyWidgetReferenceService syWidgetReferenceService) {
		this.syWidgetReferenceService = syWidgetReferenceService;
	}
}