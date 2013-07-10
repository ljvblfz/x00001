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

import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingMmsHistoryLogDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingMmsHistoryLog;
import com.broadsoft.xmeeting.xmeeting.devmgmt.vo.XmMeetingMmsHistoryLogSearchVO;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingMmsHistoryLog")
public class XmMeetingMmsHistoryLogsResource extends SyBaseResource {
	
	private XmMeetingMmsHistoryLogDaoImpl xmMeetingMmsHistoryLogDao;
	
	public void setXmMeetingMmsHistoryLogDao(XmMeetingMmsHistoryLogDaoImpl xmMeetingMmsHistoryLogDao) {
		this.xmMeetingMmsHistoryLogDao = xmMeetingMmsHistoryLogDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmMeetingMmsHistoryLogSearchVO sVO=new XmMeetingMmsHistoryLogSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmMeetingMmsHistoryLogDao.findPage(getPageRequest(),fillDetachedCriteria(XmMeetingMmsHistoryLog.class,sVO));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmmmhlGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmMeetingMmsHistoryLogDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmMeetingMmsHistoryLog xmMeetingMmsHistoryLog = new XmMeetingMmsHistoryLog();
		PMGridCopyUtil.copyGridToDto(xmMeetingMmsHistoryLog, form.getValuesMap());
		xmMeetingMmsHistoryLogDao.add(xmMeetingMmsHistoryLog);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}