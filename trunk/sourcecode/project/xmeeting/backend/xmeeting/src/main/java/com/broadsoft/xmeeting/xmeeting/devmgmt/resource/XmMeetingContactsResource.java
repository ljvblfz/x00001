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

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingContactDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingContact;
import com.broadsoft.xmeeting.xmeeting.devmgmt.vo.XmMeetingContactSearchVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingContact")
public class XmMeetingContactsResource extends SyBaseResource {
	
	private XmMeetingContactDaoImpl xmMeetingContactDao;
	
	public void setXmMeetingContactDao(XmMeetingContactDaoImpl xmMeetingContactDao) {
		this.xmMeetingContactDao = xmMeetingContactDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmMeetingContactSearchVO sVO=new XmMeetingContactSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmMeetingContactDao.findPage(getPageRequest(),fillDetachedCriteria(XmMeetingContact.class,sVO));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmmcGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmMeetingContactDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmMeetingContact xmMeetingContact = new XmMeetingContact();
		PMGridCopyUtil.copyGridToDto(xmMeetingContact, form.getValuesMap());
		xmMeetingContactDao.add(xmMeetingContact);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}