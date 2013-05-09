/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.view.resource;

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
import com.founder.sipbus.syweb.cck.service.SyCckService;
import com.founder.sipbus.syweb.view.dao.SyViewTypeButtonDaoImpl;
import com.founder.sipbus.syweb.view.po.SyViewTypeButton;
import com.founder.sipbus.syweb.view.vo.SyViewTypeButtonSearchVO;

/***
 * cckbutton 多条记录resource
 * urls="/syViewType/{sctGuid}/syViewTypeButton"
 * @author zjl
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syViewType/{sctGuid}/syViewTypeButton")
public class SyViewTypeButtonsResource extends SyBaseResource {
	
	private SyViewTypeButtonDaoImpl syViewTypeButtonDao;
	private SyCckService syCckService;
	public void setSyViewTypeButtonDao(SyViewTypeButtonDaoImpl syViewTypeButtonDao) {
		this.syViewTypeButtonDao = syViewTypeButtonDao;
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
		SyViewTypeButtonSearchVO sVO=new SyViewTypeButtonSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		String sctGuid = getAttribute("sctGuid");
		
		sVO.setSctGuid(sctGuid);
		PageResponse<SyViewTypeButton> p = syViewTypeButtonDao.findPage(getPageRequest(),fillDetachedCriteria(SyViewTypeButton.class,sVO));
		List<SyViewTypeButton> list = p.getList();
		for (SyViewTypeButton syViewTypeButton : list) {
			syViewTypeButton.setButtonTypeString(syCodeService.getSyCodeName("1019", syViewTypeButton.getButtonType()));
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
			SyViewTypeButton b= syViewTypeButtonDao.findById(id);
			sctGuidSet.add(b.getSctGuid());
			syViewTypeButtonDao.delete(id);
			
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
		SyViewTypeButton syViewTypeButton = new SyViewTypeButton();
		PMGridCopyUtil.copyGridToDto(syViewTypeButton, form.getValuesMap());
		if ( null==syViewTypeButton.getButtonSortno() ) {
			syViewTypeButton.setButtonSortno(Integer.valueOf(0));
		}
		syViewTypeButtonDao.add(syViewTypeButton);
		syCckService.clearButtonCache(syViewTypeButton.getSctGuid());
		return getJsonGzipRepresentation(getDefaultAddReturnJson().accumulate("callBackMethod", getCallbackMethod()));   
	}
}