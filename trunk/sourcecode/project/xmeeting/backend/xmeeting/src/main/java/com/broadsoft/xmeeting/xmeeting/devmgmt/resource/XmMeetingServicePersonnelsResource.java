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

import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingServicePersonnelDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingServicePersonnel;
import com.broadsoft.xmeeting.xmeeting.meeting.dao.XmeetingDaoImpl;
import com.broadsoft.xmeeting.xmeeting.meeting.vo.XmMeetingServicePersonnelIVO;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingServicePersonnel")
public class XmMeetingServicePersonnelsResource extends SyBaseResource {
	
	private XmMeetingServicePersonnelDaoImpl xmMeetingServicePersonnelDao;
	
	public void setXmMeetingServicePersonnelDao(XmMeetingServicePersonnelDaoImpl xmMeetingServicePersonnelDao) {
		this.xmMeetingServicePersonnelDao = xmMeetingServicePersonnelDao;
	}
	
	
	private XmeetingDaoImpl xmeetingDao; 
	public void setXmeetingDao(XmeetingDaoImpl xmeetingDao) {
		this.xmeetingDao = xmeetingDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
//		XmMeetingServicePersonnelSearchVO sVO=new XmMeetingServicePersonnelSearchVO();
//		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
//		PageResponse p = xmMeetingServicePersonnelDao.findPage(getPageRequest(),fillDetachedCriteria(XmMeetingServicePersonnel.class,sVO));
		

		PageResponse<XmMeetingServicePersonnelIVO> p = xmeetingDao.findServicePersonnelByMeetingID(getPageRequest(), getQueryMap());
	 
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmmspGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmMeetingServicePersonnelDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmMeetingServicePersonnel xmMeetingServicePersonnel = new XmMeetingServicePersonnel();
		PMGridCopyUtil.copyGridToDto(xmMeetingServicePersonnel, form.getValuesMap());
		xmMeetingServicePersonnelDao.add(xmMeetingServicePersonnel);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}