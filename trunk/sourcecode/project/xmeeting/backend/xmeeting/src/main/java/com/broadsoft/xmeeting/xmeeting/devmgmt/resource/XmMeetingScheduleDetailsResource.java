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
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingScheduleDetailDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingScheduleDetail;
import com.broadsoft.xmeeting.xmeeting.devmgmt.vo.XmMeetingScheduleDetailSearchVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingScheduleDetail")
public class XmMeetingScheduleDetailsResource extends SyBaseResource {
	
	private XmMeetingScheduleDetailDaoImpl xmMeetingScheduleDetailDao;
	
	public void setXmMeetingScheduleDetailDao(XmMeetingScheduleDetailDaoImpl xmMeetingScheduleDetailDao) {
		this.xmMeetingScheduleDetailDao = xmMeetingScheduleDetailDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmMeetingScheduleDetailSearchVO sVO=new XmMeetingScheduleDetailSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmMeetingScheduleDetailDao.findPage(getPageRequest(),fillDetachedCriteria(XmMeetingScheduleDetail.class,sVO));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmmsdGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmMeetingScheduleDetailDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmMeetingScheduleDetail xmMeetingScheduleDetail = new XmMeetingScheduleDetail();
		PMGridCopyUtil.copyGridToDto(xmMeetingScheduleDetail, form.getValuesMap());
		xmMeetingScheduleDetailDao.add(xmMeetingScheduleDetail);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}