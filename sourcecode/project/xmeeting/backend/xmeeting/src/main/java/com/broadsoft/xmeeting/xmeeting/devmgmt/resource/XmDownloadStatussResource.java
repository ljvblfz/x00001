/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.devmgmt.resource;

import java.util.List;

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

import com.broadsoft.xmeeting.xmeeting.basic.dao.XmPadDeviceDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmPadDevice;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmDownloadStatusDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmDownloadStatus;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingInfo;
import com.broadsoft.xmeeting.xmeeting.devmgmt.vo.XmDownloadStatusSearchVO;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.service.SyCodeService;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmDownloadStatus")
public class XmDownloadStatussResource extends SyBaseResource {
	
	private XmDownloadStatusDaoImpl xmDownloadStatusDao;
	
	public void setXmDownloadStatusDao(XmDownloadStatusDaoImpl xmDownloadStatusDao) {
		this.xmDownloadStatusDao = xmDownloadStatusDao;
	}
	
	private SyCodeService syCodeService; 
	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmDownloadStatusSearchVO sVO=new XmDownloadStatusSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmDownloadStatusDao.findPage(getPageRequest(),fillDetachedCriteria(XmDownloadStatus.class,sVO)); 
		
		List list = p.getList();
		for (int i = 0; i < list.size(); i++) {
			XmDownloadStatus xmDownloadStatus = (XmDownloadStatus) list.get(i);
			String xmpdGuid=xmDownloadStatus.getXmpdGuid(); 
			String xmmiGuid=xmDownloadStatus.getXmmiGuid();
			XmPadDevice xmPadDevice=xmPadDeviceDao.findById(xmpdGuid);
			XmMeetingInfo xmMeetingInfo=xmMeetingInfoDao.findById(xmmiGuid);
			xmDownloadStatus.setXmmiGuidLabel(xmMeetingInfo.getXmmiName());
			if(null!=xmPadDevice){
				xmDownloadStatus.setXmpdGuidLabel(xmPadDevice.getXmpdCode()); 
			}
			//
			xmDownloadStatus.setXmdsMeetingScheduleLabel(syCodeService.getSyCodeName("3019", xmDownloadStatus.getXmdsMeetingSchedule()));
			xmDownloadStatus.setXmdsDocumentLabel(syCodeService.getSyCodeName("3019", xmDownloadStatus.getXmdsDocument()));
			xmDownloadStatus.setXmdsVideoLabel(syCodeService.getSyCodeName("3019", xmDownloadStatus.getXmdsVideo()));
			xmDownloadStatus.setXmdsImageLabel(syCodeService.getSyCodeName("3019", xmDownloadStatus.getXmdsImage()));
		}//end of for 
		
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmdsGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmDownloadStatusDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmDownloadStatus xmDownloadStatus = new XmDownloadStatus();
		PMGridCopyUtil.copyGridToDto(xmDownloadStatus, form.getValuesMap());
		xmDownloadStatusDao.add(xmDownloadStatus);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
	//
	private XmMeetingInfoDaoImpl xmMeetingInfoDao; 
	public void setXmMeetingInfoDao(XmMeetingInfoDaoImpl xmMeetingInfoDao) {
		this.xmMeetingInfoDao = xmMeetingInfoDao;
	}
	private XmPadDeviceDaoImpl xmPadDeviceDao; 
	public void setXmPadDeviceDao(XmPadDeviceDaoImpl xmPadDeviceDao) {
		this.xmPadDeviceDao = xmPadDeviceDao;
	}
}