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

import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingPersonnelSeatPadDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingPersonnelSeatPad;
import com.broadsoft.xmeeting.xmeeting.meeting.dao.XmeetingDaoImpl;
import com.broadsoft.xmeeting.xmeeting.meeting.vo.XmMeetingPersonnelSeatPadIVO;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingPersonnelSeatPad")
public class XmMeetingPersonnelSeatPadsResource extends SyBaseResource {
	
	private XmMeetingPersonnelSeatPadDaoImpl xmMeetingPersonnelSeatPadDao;
	
	public void setXmMeetingPersonnelSeatPadDao(XmMeetingPersonnelSeatPadDaoImpl xmMeetingPersonnelSeatPadDao) {
		this.xmMeetingPersonnelSeatPadDao = xmMeetingPersonnelSeatPadDao;
	}
	
	private XmeetingDaoImpl xmeetingDao;
 

	public void setXmeetingDao(XmeetingDaoImpl xmeetingDao) {
		this.xmeetingDao = xmeetingDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
//		XmMeetingPersonnelSeatPadSearchVO sVO=new XmMeetingPersonnelSeatPadSearchVO();
//		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
//		PageResponse p = xmMeetingPersonnelSeatPadDao.findPage(getPageRequest(),fillDetachedCriteria(XmMeetingPersonnelSeatPad.class,sVO));
		

		PageResponse<XmMeetingPersonnelSeatPadIVO> p = xmeetingDao.findMeetingPersonnelSeatPadByMeetingID(getPageRequest(), getQueryMap());
	 
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmmpspGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmMeetingPersonnelSeatPadDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmMeetingPersonnelSeatPad xmMeetingPersonnelSeatPad = new XmMeetingPersonnelSeatPad();
		PMGridCopyUtil.copyGridToDto(xmMeetingPersonnelSeatPad, form.getValuesMap());
		xmMeetingPersonnelSeatPadDao.add(xmMeetingPersonnelSeatPad);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}