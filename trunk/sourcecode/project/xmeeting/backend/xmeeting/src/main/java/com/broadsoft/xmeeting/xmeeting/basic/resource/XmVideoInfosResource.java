/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.basic.resource;

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

import com.broadsoft.xmeeting.xmeeting.basic.dao.XmVideoInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmPadDevice;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmVideoInfo;
import com.broadsoft.xmeeting.xmeeting.basic.vo.XmVideoInfoSearchVO;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.service.SyCodeService;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmVideoInfo")
public class XmVideoInfosResource extends SyBaseResource {
	
	private XmVideoInfoDaoImpl xmVideoInfoDao;
	
	public void setXmVideoInfoDao(XmVideoInfoDaoImpl xmVideoInfoDao) {
		this.xmVideoInfoDao = xmVideoInfoDao;
	}
	
	

	private SyCodeService syCodeService; 
	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmVideoInfoSearchVO sVO=new XmVideoInfoSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmVideoInfoDao.findPage(getPageRequest(),fillDetachedCriteria(XmVideoInfo.class,sVO));
		List list = p.getList();
		for (int i = 0; i < list.size(); i++) {
			XmVideoInfo xmVideoInfo = (XmVideoInfo) list.get(i);
			xmVideoInfo.setXmviStatusLabel(syCodeService.getSyCodeName("3015", xmVideoInfo.getXmviStatus()));// 从码表读出对应名称 
			 
		}
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmviGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmVideoInfoDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmVideoInfo xmVideoInfo = new XmVideoInfo();
		PMGridCopyUtil.copyGridToDto(xmVideoInfo, form.getValuesMap());
		xmVideoInfoDao.add(xmVideoInfo);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}