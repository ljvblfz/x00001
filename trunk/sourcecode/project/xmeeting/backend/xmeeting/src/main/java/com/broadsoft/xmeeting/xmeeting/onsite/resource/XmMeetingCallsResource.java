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

import com.broadsoft.xmeeting.xmeeting.meeting.dao.XmeetingOnsiteDaoImpl;
import com.broadsoft.xmeeting.xmeeting.meeting.vo.XmMeetingCallIVO;
import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingCallDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.po.XmMeetingCall;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingCall")
public class XmMeetingCallsResource extends SyBaseResource {
	
	private XmMeetingCallDaoImpl xmMeetingCallDao;
	
	public void setXmMeetingCallDao(XmMeetingCallDaoImpl xmMeetingCallDao) {
		this.xmMeetingCallDao = xmMeetingCallDao;
	}
	

	private XmeetingOnsiteDaoImpl xmeetingOnsiteDao;

	public void setXmeetingOnsiteDao(XmeetingOnsiteDaoImpl xmeetingOnsiteDao) {
		this.xmeetingOnsiteDao = xmeetingOnsiteDao;
	}
	
	
	

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
//		XmMeetingCallSearchVO sVO=new XmMeetingCallSearchVO();
//		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
//		PageResponse p = xmMeetingCallDao.findPage(getPageRequest(),fillDetachedCriteria(XmMeetingCall.class,sVO));
//		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		

		PageResponse<XmMeetingCallIVO> p = xmeetingOnsiteDao.searchMeetingCallByName(getPageRequest(), getQueryMap());
	 
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmmcallGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmMeetingCallDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmMeetingCall xmMeetingCall = new XmMeetingCall();
		PMGridCopyUtil.copyGridToDto(xmMeetingCall, form.getValuesMap());
		xmMeetingCallDao.add(xmMeetingCall);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}