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

import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingEmailDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingEmail;
import com.broadsoft.xmeeting.xmeeting.devmgmt.vo.XmMeetingEmailSearchVO;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingEmail")
public class XmMeetingEmailsResource extends SyBaseResource {
	
	private XmMeetingEmailDaoImpl xmMeetingEmailDao;
	
	public void setXmMeetingEmailDao(XmMeetingEmailDaoImpl xmMeetingEmailDao) {
		this.xmMeetingEmailDao = xmMeetingEmailDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmMeetingEmailSearchVO sVO=new XmMeetingEmailSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmMeetingEmailDao.findPage(getPageRequest(),fillDetachedCriteria(XmMeetingEmail.class,sVO));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmmeGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmMeetingEmailDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmMeetingEmail xmMeetingEmail = new XmMeetingEmail();
		PMGridCopyUtil.copyGridToDto(xmMeetingEmail, form.getValuesMap());
		xmMeetingEmailDao.add(xmMeetingEmail);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}