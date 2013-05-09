/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.codeprinciple.resource;

import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

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
import com.founder.sipbus.syweb.codeprinciple.dao.UtilCodePrincipleDetailDaoImpl;
import com.founder.sipbus.syweb.codeprinciple.po.UtilCodePrincipleDetail;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/utilCodePrinciple/{parentId}/detail")
public class UtilCodePrincipleDetailsResource extends SyBaseResource {
	private SyCodeService syCodeService;
	private UtilCodePrincipleDetailDaoImpl utilCodePrincipleDetailDao;
	
	public void setUtilCodePrincipleDetailDao(UtilCodePrincipleDetailDaoImpl utilCodePrincipleDetailDao) {
		this.utilCodePrincipleDetailDao = utilCodePrincipleDetailDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		String ucpGuid = getAttribute("parentId");
		//UtilCodePrincipleDetailPVO sVO=new UtilCodePrincipleDetailPVO();
	//	PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
	//	PageResponse p = utilCodePrincipleDetailDao.findPage(getPageRequest(),fillDetachedCriteria(UtilCodePrincipleDetail.class,sVO));
	List<UtilCodePrincipleDetail> list = utilCodePrincipleDetailDao.findDetailByUcpGuid(ucpGuid);
	for (UtilCodePrincipleDetail utilCodePrincipleDetail : list) {
		utilCodePrincipleDetail.setUcpdFieldName(  syCodeService.getSyCodeName("7010",utilCodePrincipleDetail.getUcpdFieldId()) );
		utilCodePrincipleDetail.setUcpdFormatName(   syCodeService.getSyCodeName("7011",utilCodePrincipleDetail.getUcpdFormat()) );
		
	}
	PageResponse<UtilCodePrincipleDetail> pageResponse=new PageResponse<UtilCodePrincipleDetail>();
	pageResponse.setList(list);
		JSON jp = JSONSerializer.toJSON( pageResponse ,config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("ucpdGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			utilCodePrincipleDetailDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		UtilCodePrincipleDetail utilCodePrincipleDetail = new UtilCodePrincipleDetail();
		PMGridCopyUtil.copyGridToDto(utilCodePrincipleDetail, form.getValuesMap());
		utilCodePrincipleDetailDao.add(utilCodePrincipleDetail);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}

	public SyCodeService getSyCodeService() {
		return syCodeService;
	}

	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}
}