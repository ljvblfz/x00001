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

import com.broadsoft.xmeeting.xmeeting.basic.dao.XmRoomInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmRoomInfo;
import com.broadsoft.xmeeting.xmeeting.basic.vo.XmRoomInfoSearchVO;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.service.SyCodeService;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmRoomInfo")
public class XmRoomInfosResource extends SyBaseResource {
	
	private XmRoomInfoDaoImpl xmRoomInfoDao;
	
	public void setXmRoomInfoDao(XmRoomInfoDaoImpl xmRoomInfoDao) {
		this.xmRoomInfoDao = xmRoomInfoDao;
	}
	private SyCodeService syCodeService; 
	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmRoomInfoSearchVO sVO=new XmRoomInfoSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmRoomInfoDao.findPage(getPageRequest(),fillDetachedCriteria(XmRoomInfo.class,sVO));
		List list = p.getList();
		for (int i = 0; i < list.size(); i++) {
			XmRoomInfo xmRoomInfo = (XmRoomInfo) list.get(i);
			xmRoomInfo.setXmriStatusLabel(syCodeService.getSyCodeName("3011", xmRoomInfo.getXmriStatus()));// 从码表读出对应名称  
		}
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmriGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmRoomInfoDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmRoomInfo xmRoomInfo = new XmRoomInfo();
		PMGridCopyUtil.copyGridToDto(xmRoomInfo, form.getValuesMap());
		xmRoomInfoDao.add(xmRoomInfo);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}