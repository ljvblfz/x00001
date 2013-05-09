/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */

package com.founder.sipbus.syweb.codeprinciple.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.service.SyCodeService;
import com.founder.sipbus.syweb.codeprinciple.dao.UtilCodePrincipleDaoImpl;
import com.founder.sipbus.syweb.codeprinciple.po.UtilCodePrinciple;
import com.founder.sipbus.syweb.codeprinciple.po.UtilCodePrincipleDetail;
import com.founder.sipbus.syweb.codeprinciple.service.UtilCodePrincipleService;
import com.founder.sipbus.syweb.codeprinciple.util.ItemsDetailUtil;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/mwBasicEncodePrinciple/{id}")
public class MwBasicEncodePrincipleResource extends SyBaseResource {

	private String id;

	private UtilCodePrincipleDaoImpl utilCodePrincipleDao;
	private UtilCodePrincipleService utilCodePrincipleService;
	private SyCodeService syCodeService;

	public UtilCodePrincipleService getUtilCodePrincipleService() {
		return utilCodePrincipleService;
	}

	public void setUtilCodePrincipleService(
			UtilCodePrincipleService utilCodePrincipleService) {
		this.utilCodePrincipleService = utilCodePrincipleService;
	}

	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}

	@Override
	protected void doInit() throws ResourceException {
	}

	@Get
	public Representation get(Representation entity) {
		id = getAttribute("id");
		UtilCodePrinciple bean = utilCodePrincipleDao.findById(id);

		if (null == bean) {
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			return null;
		}
		bean.setUcpTypeName(syCodeService.getSyCodeName("7009",
				bean.getUcpType()));
		JSONObject jsonObject = JSONObject.fromObject(bean, config);

		return getJsonGzipRepresentation(jsonObject);
	}

	@Delete
	public Representation delete() {
		utilCodePrincipleDao.delete(id);
		return new StringRepresentation(id);
	}

	@Put
	public Representation put(Representation entity) throws ResourceException {
		form = new Form(entity);
		id = form.getFirstValue("ucpGuid");
		UtilCodePrinciple mwMaterialInfo = utilCodePrincipleDao.findById(id);
		PMGridCopyUtil.copyGridToDto(mwMaterialInfo, form.getValuesMap());

		HashMap<String, ArrayList<Map>> map = ItemsDetailUtil.getDetails(form);
		ArrayList<UtilCodePrincipleDetail> details = new ArrayList<UtilCodePrincipleDetail>();
		UtilCodePrinciple po = new UtilCodePrinciple();
		PMGridCopyUtil.copyGridToDto(po, form.getValuesMap());
		ArrayList<Map> itemsMap = map.get("items");
		if (itemsMap != null) {
			for (Map map2 : itemsMap) {
				UtilCodePrincipleDetail detail = new UtilCodePrincipleDetail();
				PMGridCopyUtil.copyGridToDto(detail, map2);
				details.add(detail);
			}
		}

		utilCodePrincipleService.updateDetailByucpGuid(id, details);
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}

	public UtilCodePrincipleDaoImpl getUtilCodePrincipleDao() {
		return utilCodePrincipleDao;
	}

	public void setUtilCodePrincipleDao(
			UtilCodePrincipleDaoImpl utilCodePrincipleDao) {
		this.utilCodePrincipleDao = utilCodePrincipleDao;
	}

	// //利用post来完成多个发动机的报废工作
	// @Post
	// public Representation post(Representation entity)
	// throws ResourceException {
	// form = new Form(entity);
	// String idsStr=form.getFirstValue("engineIds");
	// String[] ids=idsStr.split(",");
	//
	// JSONObject jo =getMessageReturnJson("发动机报废成功！");
	// return getJsonGzipRepresentation(jo);
	// }

}