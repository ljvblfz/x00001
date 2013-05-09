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
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingScheduleDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingSchedule;
import com.broadsoft.xmeeting.xmeeting.devmgmt.vo.XmMeetingScheduleSearchVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingSchedule")
public class XmMeetingSchedulesResource extends SyBaseResource {
	
	private XmMeetingScheduleDaoImpl xmMeetingScheduleDao;
	
	public void setXmMeetingScheduleDao(XmMeetingScheduleDaoImpl xmMeetingScheduleDao) {
		this.xmMeetingScheduleDao = xmMeetingScheduleDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmMeetingScheduleSearchVO sVO=new XmMeetingScheduleSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmMeetingScheduleDao.findPage(getPageRequest(),fillDetachedCriteria(XmMeetingSchedule.class,sVO));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmmsGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmMeetingScheduleDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmMeetingSchedule xmMeetingSchedule = new XmMeetingSchedule();
		PMGridCopyUtil.copyGridToDto(xmMeetingSchedule, form.getValuesMap());
		xmMeetingScheduleDao.add(xmMeetingSchedule);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}