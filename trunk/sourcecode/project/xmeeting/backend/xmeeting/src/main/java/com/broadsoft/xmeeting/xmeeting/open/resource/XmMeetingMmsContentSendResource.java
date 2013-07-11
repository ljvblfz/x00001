/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.open.resource;

import java.util.List;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadsoft.integration.mms.MmsSupport;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingMmsContentDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingMmsHistoryLogDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingInfo;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingMmsContent;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingMmsHistoryLog;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/open/sendmms/xmmiGuid/{xmmiGuid}/mT0/{mTo}/fromName/{fromName}")
public class XmMeetingMmsContentSendResource extends SyBaseResource{

	private Logger logger = LoggerFactory .getLogger(XmMeetingMmsContentSendResource.class);
	private String xmmiGuid;
	private String mTo;
	private String fromName;
	
	private XmMeetingMmsContentDaoImpl xmMeetingMmsContentDao;
	
	public void setXmMeetingMmsContentDao(XmMeetingMmsContentDaoImpl xmMeetingMmsContentDao) {
		this.xmMeetingMmsContentDao = xmMeetingMmsContentDao;
	}

	private XmMeetingMmsHistoryLogDaoImpl xmMeetingMmsHistoryLogDao;
	
	public void setXmMeetingMmsHistoryLogDao(XmMeetingMmsHistoryLogDaoImpl xmMeetingMmsHistoryLogDao) {
		this.xmMeetingMmsHistoryLogDao = xmMeetingMmsHistoryLogDao;
	}  
	private XmMeetingInfoDaoImpl xmMeetingInfoDao;
	
	public void setXmMeetingInfoDao(XmMeetingInfoDaoImpl xmMeetingInfoDao) {
		this.xmMeetingInfoDao = xmMeetingInfoDao;
	}
	@Override
    protected void doInit() throws ResourceException {
		this.xmmiGuid=this.getAttribute("xmmiGuid");
		this.mTo=this.getAttribute("mTo");
		this.fromName=this.getAttribute("fromName");
	} 
	
	@Get
	public Representation get(Representation entity) { 
		//
		List<XmMeetingMmsContent> listOfXmMeetingMmsContent=xmMeetingMmsContentDao.findByXmmiGuid(xmmiGuid);
		XmMeetingMmsContent xmMeetingMmsContent=listOfXmMeetingMmsContent.get(0);
		String mmsContent=xmMeetingMmsContent.getXmmcDescription1();
		String xmmiGuid=xmMeetingMmsContent.getXmmiGuid(); 
		XmMeetingInfo xmMeetingInfo=xmMeetingInfoDao.findById(xmmiGuid); 
		XmMeetingMmsHistoryLog xmMeetingMmsHistoryLog=new XmMeetingMmsHistoryLog();
		xmMeetingMmsHistoryLog.setXmmiGuid(xmMeetingInfo.getXmmiName());
		xmMeetingMmsHistoryLog.setXmmmhlFrom(fromName);
		xmMeetingMmsHistoryLog.setXmmmhlTo(mTo);
		try {
			String response=MmsSupport.sendMms(mTo, mmsContent, null);
			xmMeetingMmsHistoryLog.setXmmmhlStatus(response);
		} catch (Exception e) { 
			e.printStackTrace();
			logger.error("Send Mms raised the error:  ", e);
			xmMeetingMmsHistoryLog.setXmmmhlStatus("发送异常");
			return getJsonGzipRepresentation(JsonUtils.genFailureReturnJson(null, null));
		}
		xmMeetingMmsHistoryLogDao.insert(xmMeetingMmsHistoryLog);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(null));
	} 
}