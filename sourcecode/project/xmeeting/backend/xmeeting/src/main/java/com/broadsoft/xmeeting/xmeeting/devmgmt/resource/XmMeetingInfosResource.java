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

import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingInfo;
import com.broadsoft.xmeeting.xmeeting.meeting.dao.XmeetingDaoImpl;
import com.broadsoft.xmeeting.xmeeting.meeting.vo.XmMeetingInfoIVO;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.service.SyCodeService;
@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingInfo")
public class XmMeetingInfosResource extends SyBaseResource {
	
	private XmMeetingInfoDaoImpl xmMeetingInfoDao;
	public void setXmMeetingInfoDao(XmMeetingInfoDaoImpl xmMeetingInfoDao) {
		this.xmMeetingInfoDao = xmMeetingInfoDao;
	}
	
	private SyCodeService syCodeService; 
	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}
	

	private XmeetingDaoImpl xmeetingDao; 
	public void setXmeetingDao(XmeetingDaoImpl xmeetingDao) {
		this.xmeetingDao = xmeetingDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
//		form = new Form(entity); 
//		XmMeetingInfoSearchVO sVO=new XmMeetingInfoSearchVO();
//		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
//		PageResponse p = xmMeetingInfoDao.findPage(getPageRequest(),fillDetachedCriteria(XmMeetingInfo.class,sVO));
//		List list = p.getList();
//		for (int i = 0; i < list.size(); i++) {
//			XmMeetingInfo xmMeetingInfo = (XmMeetingInfo) list.get(i);
//			xmMeetingInfo.setXmmiStatusLabel(syCodeService.getSyCodeName("3006", xmMeetingInfo.getXmmiStatus()));// 从码表读出对应名称  
//		}

		PageResponse<XmMeetingInfoIVO> p = xmeetingDao.searchMeetingInfoByName(getPageRequest(), getQueryMap());
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmmiGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmMeetingInfoDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmMeetingInfo xmMeetingInfo = new XmMeetingInfo();
		PMGridCopyUtil.copyGridToDto(xmMeetingInfo, form.getValuesMap());
		xmMeetingInfoDao.add(xmMeetingInfo);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}