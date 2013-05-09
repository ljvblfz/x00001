/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.au.resource;

import java.util.Date;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
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
import com.founder.sipbus.syweb.au.po.SyCode;
import com.founder.sipbus.syweb.au.service.SyCodeService;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syCode")
public class SyCodesResource extends SyBaseResource {
	
	private SyCodeDaoImpl syCodeDao;
	
	public void setSyCodeDao(SyCodeDaoImpl syCodeDao) {
		this.syCodeDao = syCodeDao;
	}


	private SyCodeService syCodeService;
	
	
	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
//		SyCodeSearchVO sVO=new SyCodeSearchVO();
		SyCode code = new SyCode();
		PMGridCopyUtil.copyGridToDto(code,getQueryMap());
//		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		//PageResponse p = syCodeDao.findPage(getPageRequest(),fillDetachedCriteria(SyCode.class,code));
//		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		DetachedCriteria dra = fillDetachedCriteria(SyCode.class,code);
		dra.addOrder(Order.asc(SyCode.CODE_SEQ));
		List<SyCode> list = syCodeDao.find(dra);
		PageResponse p = new PageResponse();
		p.setList(list);
		
		JSON jp = JSONSerializer.toJSON(p,config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("codeIds");
		String[] ids=idsStr.split(",");
		SyCode b=null;
		for(String id:ids){
//			syCodeDao.delete(id);
			b= syCodeDao.findById(id);
			syCodeDao.delete(b);
		}
		syCodeService.reflushCodeType(b.getTypeId());
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		SyCode syCode = new SyCode();
		PMGridCopyUtil.copyGridToDto(syCode, form.getValuesMap());
		syCode.setCreateDt(new Date());
		syCode.setUpdateDt(new Date());
		syCode.setCreateBy(getLoginUser().getUserid());
		syCode.setUpdateBy(getLoginUser().getUserid());
		syCodeDao.add(syCode);
		syCodeService.reflushCodeType(syCode.getTypeId());
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}