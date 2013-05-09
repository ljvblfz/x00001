/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.basic.resource;

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
import com.broadsoft.xmeeting.xmeeting.basic.dao.XmPersonnelInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmPersonnelInfo;
import com.broadsoft.xmeeting.xmeeting.basic.vo.XmPersonnelInfoSearchVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmPersonnelInfo")
public class XmPersonnelInfosResource extends SyBaseResource {
	
	private XmPersonnelInfoDaoImpl xmPersonnelInfoDao;
	
	public void setXmPersonnelInfoDao(XmPersonnelInfoDaoImpl xmPersonnelInfoDao) {
		this.xmPersonnelInfoDao = xmPersonnelInfoDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmPersonnelInfoSearchVO sVO=new XmPersonnelInfoSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmPersonnelInfoDao.findPage(getPageRequest(),fillDetachedCriteria(XmPersonnelInfo.class,sVO));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmpiGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmPersonnelInfoDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmPersonnelInfo xmPersonnelInfo = new XmPersonnelInfo();
		PMGridCopyUtil.copyGridToDto(xmPersonnelInfo, form.getValuesMap());
		xmPersonnelInfoDao.add(xmPersonnelInfo);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}