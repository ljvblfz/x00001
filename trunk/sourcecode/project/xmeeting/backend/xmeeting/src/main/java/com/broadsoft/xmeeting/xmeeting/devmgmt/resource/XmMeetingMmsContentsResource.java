/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.devmgmt.resource;

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

import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingMmsContentDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingMmsContent;
import com.broadsoft.xmeeting.xmeeting.devmgmt.vo.XmMeetingMmsContentSearchVO;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingMmsContent")
public class XmMeetingMmsContentsResource extends SyBaseResource {
	
	private XmMeetingMmsContentDaoImpl xmMeetingMmsContentDao;
	
	public void setXmMeetingMmsContentDao(XmMeetingMmsContentDaoImpl xmMeetingMmsContentDao) {
		this.xmMeetingMmsContentDao = xmMeetingMmsContentDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmMeetingMmsContentSearchVO sVO=new XmMeetingMmsContentSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmMeetingMmsContentDao.findPage(getPageRequest(),fillDetachedCriteria(XmMeetingMmsContent.class,sVO));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmmcGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmMeetingMmsContentDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmMeetingMmsContent xmMeetingMmsContent = new XmMeetingMmsContent();
		PMGridCopyUtil.copyGridToDto(xmMeetingMmsContent, form.getValuesMap());
		xmMeetingMmsContentDao.add(xmMeetingMmsContent);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}