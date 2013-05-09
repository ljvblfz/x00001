/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.au.resource;

import java.util.Date;
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
import com.founder.sipbus.syweb.au.dao.SyCodeDaoImpl;
import com.founder.sipbus.syweb.au.dao.SyCodeTypeDaoImpl;
import com.founder.sipbus.syweb.au.po.SyCodeType;
import com.founder.sipbus.syweb.au.service.SyCodeService;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syCodeType")
public class SyCodeTypesResource extends SyBaseResource {
	
	private SyCodeTypeDaoImpl syCodeTypeDao;

	public void setSyCodeTypeDao(SyCodeTypeDaoImpl syCodeTypeDao) {
		this.syCodeTypeDao = syCodeTypeDao;
	}

	private SyCodeService syCodeService;
	
	
	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		SyCodeType type = new SyCodeType();
		PMGridCopyUtil.copyGridToDto(type, getQueryMap());
		PageResponse p = syCodeTypeDao.findPage(getPageRequest(),fillDetachedCriteria(SyCodeType.class,type));
		if(p != null) {
			List<SyCodeType> l = p.getList();
			for (SyCodeType sct : l) {
				if(StringUtils.isNotBlank(sct.getRedOnyFlg())){
					sct.setRedOnlyFlaStr(syCodeService.getSyCodeName("1002", sct.getRedOnyFlg()));
//					sct.setRedOnlyFlaStr(syCodeDao.getSyCodeName("1002", sct.getRedOnyFlg()));
				}
				
				if(StringUtils.isNotBlank(sct.getDomain())){
					sct.setDomainStr(syCodeService.getSyCodeName("1001", sct.getDomain()));
				}
			}
		}
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("typeIds");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			syCodeService.deleteSyCodeType(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		SyCodeType syCodeType = new SyCodeType();
		PMGridCopyUtil.copyGridToDto(syCodeType, form.getValuesMap());
		syCodeType.setCreateDt(new Date());
		syCodeType.setUpdateDt(new Date());
		syCodeType.setCreateBy(getLoginUser().getUserid());
		syCodeType.setUpdateBy(getLoginUser().getUserid());
		syCodeTypeDao.add(syCodeType);
		syCodeService.reflushCodeType(syCodeType.getTypeId());
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}