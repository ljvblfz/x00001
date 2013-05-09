/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.cck.resource;

import java.util.HashSet;
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
import com.founder.sipbus.syweb.cck.dao.SyCckTypeButtonDaoImpl;
import com.founder.sipbus.syweb.cck.po.SyCckTypeButton;
import com.founder.sipbus.syweb.cck.service.SyCckService;
import com.founder.sipbus.syweb.cck.vo.SyCckTypeButtonSearchVO;

/***
 * cckbutton 多条记录resource
 * urls="/syCckType/{sctGuid}/syCckTypeButton"
 * @author zjl
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syCckType/{sctGuid}/syCckTypeButton")
public class SyCckTypeButtonsResource extends SyBaseResource {
	
	private SyCckTypeButtonDaoImpl syCckTypeButtonDao;
	private SyCckService syCckService;
	public void setSyCckTypeButtonDao(SyCckTypeButtonDaoImpl syCckTypeButtonDao) {
		this.syCckTypeButtonDao = syCckTypeButtonDao;
	}
	private SyCodeService syCodeService;
	public void setSyCckService(SyCckService syCckService) {
		this.syCckService = syCckService;
	}
	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		SyCckTypeButtonSearchVO sVO=new SyCckTypeButtonSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		String sctGuid = getAttribute("sctGuid");
		
		sVO.setSctGuid(sctGuid);
		PageResponse<SyCckTypeButton> p = syCckTypeButtonDao.findPage(getPageRequest(),fillDetachedCriteria(SyCckTypeButton.class,sVO));
		List<SyCckTypeButton> list = p.getList();
		for (SyCckTypeButton syCckTypeButton : list) {
			syCckTypeButton.setButtonTypeString(syCodeService.getSyCodeName("1020", syCckTypeButton.getButtonType()));
		}
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("sctbGuids");
		String[] ids=idsStr.split(",");
		HashSet<String> sctGuidSet=new HashSet<String>();
		for(String id:ids){
			SyCckTypeButton b= syCckTypeButtonDao.findById(id);
			sctGuidSet.add(b.getSctGuid());
			syCckTypeButtonDao.delete(id);
			
		}
		for (String string : sctGuidSet) {
			syCckService.clearButtonCache(string);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		SyCckTypeButton syCckTypeButton = new SyCckTypeButton();
		PMGridCopyUtil.copyGridToDto(syCckTypeButton, form.getValuesMap());
		if ( null==syCckTypeButton.getButtonSortno() ) {
			syCckTypeButton.setButtonSortno(Integer.valueOf(0));
		}
		syCckTypeButtonDao.add(syCckTypeButton);
		syCckService.clearButtonCache(syCckTypeButton.getSctGuid());
		return getJsonGzipRepresentation(getDefaultAddReturnJson().accumulate("callBackMethod", getCallbackMethod()));   
	}
}