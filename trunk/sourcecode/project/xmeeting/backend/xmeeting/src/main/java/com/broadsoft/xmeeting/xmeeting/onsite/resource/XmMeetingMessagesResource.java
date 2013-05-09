/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.onsite.resource;

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
import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingMessageDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.po.XmMeetingMessage;
import com.broadsoft.xmeeting.xmeeting.onsite.vo.XmMeetingMessageSearchVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingMessage")
public class XmMeetingMessagesResource extends SyBaseResource {
	
	private XmMeetingMessageDaoImpl xmMeetingMessageDao;
	
	public void setXmMeetingMessageDao(XmMeetingMessageDaoImpl xmMeetingMessageDao) {
		this.xmMeetingMessageDao = xmMeetingMessageDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmMeetingMessageSearchVO sVO=new XmMeetingMessageSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmMeetingMessageDao.findPage(getPageRequest(),fillDetachedCriteria(XmMeetingMessage.class,sVO));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmmmGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmMeetingMessageDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmMeetingMessage xmMeetingMessage = new XmMeetingMessage();
		PMGridCopyUtil.copyGridToDto(xmMeetingMessage, form.getValuesMap());
		xmMeetingMessageDao.add(xmMeetingMessage);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}