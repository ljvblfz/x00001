/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */

package com.founder.sipbus.syweb.codeprinciple.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
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
import com.founder.sipbus.syweb.codeprinciple.dao.UtilCodePrincipleDaoImpl;
import com.founder.sipbus.syweb.codeprinciple.po.UtilCodePrinciple;
import com.founder.sipbus.syweb.codeprinciple.po.UtilCodePrincipleDetail;
import com.founder.sipbus.syweb.codeprinciple.pvo.UtilCodePrinciplePVO;
import com.founder.sipbus.syweb.codeprinciple.service.UtilCodePrincipleService;
import com.founder.sipbus.syweb.codeprinciple.util.ItemsDetailUtil;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/mwBasicEncodePrinciple")
public class MwBasicEncodePrinciplesResource extends SyBaseResource {

	private UtilCodePrincipleDaoImpl utilCodePrincipleDao;
	private UtilCodePrincipleService utilCodePrincipleService;
	 
	private SyCodeService syCodeService;

	public UtilCodePrincipleDaoImpl getUtilCodePrincipleDao() {
		return utilCodePrincipleDao;
	}

	public void setUtilCodePrincipleDao(
			UtilCodePrincipleDaoImpl utilCodePrincipleDao) {
		this.utilCodePrincipleDao = utilCodePrincipleDao;
	}

	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}

 
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity);
		UtilCodePrinciplePVO pvo = new UtilCodePrinciplePVO();
		PMGridCopyUtil.copyGridToDto(pvo, getQueryMap());
	DetachedCriteria dc = fillDetachedCriteria(UtilCodePrinciple.class, pvo);
	dc.addOrder(Order.asc("ucpType"));
		@SuppressWarnings("rawtypes")
		PageResponse pageResponse = utilCodePrincipleDao.findPage(
				getPageRequest(),
				dc);
		List<UtilCodePrinciple> list = pageResponse.getList();
		for (UtilCodePrinciple ucp: list) {
			ucp.setUcpTypeName(syCodeService.getSyCodeName("7009", ucp.getUcpType()));
		}
		JSON jp = JSONSerializer.toJSON(getPageResponse(pageResponse), config);

		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));
	}

	@Delete
	public Representation delete(Representation entity) {
		form = new Form(entity);
		String idsStr = form.getFirstValue("ucpGuids");
		String[] ids = idsStr.split(",");
		utilCodePrincipleService.deleteUcpAndUcpd(ids);
	
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}

	@Post
	public Representation post(Representation entity) throws ResourceException {
		form = new Form(entity);

		HashMap<String, ArrayList<Map>> map = ItemsDetailUtil.getDetails(form);// 获取子表内容
		ArrayList<UtilCodePrincipleDetail> details = new ArrayList<UtilCodePrincipleDetail>();
		UtilCodePrinciple po = new UtilCodePrinciple();
		PMGridCopyUtil.copyGridToDto(po, form.getValuesMap());
		ArrayList<Map> itemsMap = map.get("items");
		po.setUcpCodeLength("0");
		if (itemsMap != null) {
			String length= (String) itemsMap.get(itemsMap.size()-1).get("ucpdEnd");
			if (StringUtils.isNumericSpace(length)) {
				po.setUcpCodeLength(length);
				
			}
			for (Map map2 : itemsMap) {
				UtilCodePrincipleDetail detail = new UtilCodePrincipleDetail();
				PMGridCopyUtil.copyGridToDto(detail, map2);
				details.add(detail);
			}
		}
	
		utilCodePrincipleDao.add(po);
		String ucpGuid = po.getUcpGuid();
		for (UtilCodePrincipleDetail detail : details) {
			detail.setUcpGuid(ucpGuid);
		}
		utilCodePrincipleService.addAllUtilCodePrincipleDetail(details);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());

	}


	public UtilCodePrincipleService getUtilCodePrincipleService() {
		return utilCodePrincipleService;
	}

	public void setUtilCodePrincipleService(UtilCodePrincipleService utilCodePrincipleService) {
		this.utilCodePrincipleService = utilCodePrincipleService;
	}

}