/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.open.resource;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingBusDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingContactDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingDocumentDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingPictureDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingPictureDetailDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingScheduleDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingScheduleDetailDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingVideoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingWeatherDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingBus;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingContact;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingDocument;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingInfo;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingPicture;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingPictureDetail;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingSchedule;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingScheduleDetail;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingVideo;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingWeather;
import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingVoteDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingVoteDetailDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.po.XmMeetingVote;
import com.broadsoft.xmeeting.xmeeting.onsite.po.XmMeetingVoteDetail;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;



/**
 * 此服务为pad展示所需的信息
 * @author lu.zhen
 *
 */

@Component
@Scope(value="prototype")
@RestletResource(urls="/open/meetingallinfo/download/xmmiGuid/{xmmiGuid}")
public class XmMeetingInfoDownloadResource extends SyBaseResource{
	 
	//会议Pid
	private String xmmiGuid;
	 
	@Override
    protected void doInit() throws ResourceException { 
		xmmiGuid=getAttribute("xmmiGuid");
	}
	
	@Get
	public Representation get(Representation entity) throws ResourceException { 
		JSONObject allInfo=new JSONObject();
		//会议基本信息
		XmMeetingInfo xmMeetingInfo=xmMeetingInfoDao.findById(xmmiGuid); 
		xmMeetingInfoDao.evict(xmMeetingInfo); 
		allInfo.put("xmMeetingInfo", xmMeetingInfo);
		//会议日程
		List<XmMeetingSchedule> listOfXmMeetingSchedule=xmMeetingScheduleDao.findByXmmiGuid(xmmiGuid);
		for(XmMeetingSchedule xmMeetingSchedule:listOfXmMeetingSchedule){
			xmMeetingScheduleDao.evict(xmMeetingSchedule);
			String xmmsGuid=xmMeetingSchedule.getXmmsGuid();
			List<XmMeetingScheduleDetail> listOfXmMeetingScheduleDetail=xmMeetingScheduleDetailDao.findByXmmsGuid(xmmsGuid);
			xmMeetingSchedule.setListOfXmMeetingScheduleDetail(listOfXmMeetingScheduleDetail);
		}
		allInfo.put("listOfXmMeetingSchedule", listOfXmMeetingSchedule);
		//车辆安排
		List<XmMeetingBus> listOfXmMeetingBus=xmMeetingBusDao.findByXmmiGuid(xmmiGuid);
		allInfo.put("listOfXmMeetingBus", listOfXmMeetingBus);
		//会议天气
		List<XmMeetingWeather> listOfXmMeetingWeather=xmMeetingWeatherDao.findByXmmiGuid(xmmiGuid);
		allInfo.put("listOfXmMeetingWeather", listOfXmMeetingWeather);
		//联系方式
		List<XmMeetingContact> listOfXmMeetingContact=xmMeetingContactDao.findByXmmiGuid(xmmiGuid);
		allInfo.put("listOfXmMeetingContact", listOfXmMeetingContact);
		//会议文稿
		List<XmMeetingDocument> listOfXmMeetingDocument=xmMeetingDocumentDao.findByXmmiGuid(xmmiGuid);
		allInfo.put("listOfXmMeetingDocument", listOfXmMeetingDocument);
		//会议视频
		List<XmMeetingVideo> listOfXmMeetingVideo=xmMeetingVideoDao.findByXmmiGuid(xmmiGuid);
		allInfo.put("listOfXmMeetingVideo", listOfXmMeetingVideo);
		//会议图片
		List<XmMeetingPicture> listOfXmMeetingPicture=xmMeetingPictureDao.findByXmmiGuid(xmmiGuid);
		for(XmMeetingPicture xmMeetingPicture:listOfXmMeetingPicture){
			xmMeetingPictureDao.evict(xmMeetingPicture);
			String xmmpicGuid=xmMeetingPicture.getXmmpicGuid();
			List<XmMeetingPictureDetail> listOfXmMeetingPictureDetail=xmMeetingPictureDetailDao.findByXmmpicGuid(xmmpicGuid);
			xmMeetingPicture.setListOfXmMeetingPictureDetail(listOfXmMeetingPictureDetail);
		}
		allInfo.put("listOfXmMeetingPicture", listOfXmMeetingPicture);
		//会议投票
		List<XmMeetingVote> listOfXmMeetingVote=xmMeetingVoteDao.findByXmmiGuid(xmmiGuid);
		for(XmMeetingVote xmMeetingVote:listOfXmMeetingVote){
			xmMeetingVoteDao.evict(xmMeetingVote);
			String xmmvGuid=xmMeetingVote.getXmmvGuid();
			List<XmMeetingVoteDetail> listOfXmMeetingVoteDetail=xmMeetingVoteDetailDao.findByXmmvGuid(xmmvGuid);
			xmMeetingVote.setListOfXmMeetingVoteDetail(listOfXmMeetingVoteDetail);
		}
		
		
		allInfo.put("listOfXmMeetingVote", listOfXmMeetingVote);
		
		//
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(allInfo));   
		 
	}
	
	
	//=============IOC=================================
	private XmMeetingInfoDaoImpl xmMeetingInfoDao;
	
	public void setXmMeetingInfoDao(XmMeetingInfoDaoImpl xmMeetingInfoDao) {
		this.xmMeetingInfoDao = xmMeetingInfoDao;
	}
 
	//会议日程
	private XmMeetingScheduleDaoImpl xmMeetingScheduleDao;
	
	public void setXmMeetingScheduleDao(XmMeetingScheduleDaoImpl xmMeetingScheduleDao) {
		this.xmMeetingScheduleDao = xmMeetingScheduleDao;
	}
	private XmMeetingScheduleDetailDaoImpl xmMeetingScheduleDetailDao;
	
	public void setXmMeetingScheduleDetailDao(XmMeetingScheduleDetailDaoImpl xmMeetingScheduleDetailDao) {
		this.xmMeetingScheduleDetailDao = xmMeetingScheduleDetailDao;
	}
	//
	private XmMeetingBusDaoImpl xmMeetingBusDao;
	
	public void setXmMeetingBusDao(XmMeetingBusDaoImpl xmMeetingBusDao) {
		this.xmMeetingBusDao = xmMeetingBusDao;
	}	

	private XmMeetingWeatherDaoImpl xmMeetingWeatherDao;
	
	public void setXmMeetingWeatherDao(XmMeetingWeatherDaoImpl xmMeetingWeatherDao) {
		this.xmMeetingWeatherDao = xmMeetingWeatherDao;
	}
	
	private XmMeetingContactDaoImpl xmMeetingContactDao;
	public void setXmMeetingContactDao(XmMeetingContactDaoImpl xmMeetingContactDao) {
		this.xmMeetingContactDao = xmMeetingContactDao;
	}
	
	//会议文稿
	private XmMeetingDocumentDaoImpl xmMeetingDocumentDao; 
	public void setXmMeetingDocumentDao(XmMeetingDocumentDaoImpl xmMeetingDocumentDao) {
		this.xmMeetingDocumentDao = xmMeetingDocumentDao;
	}	

	private XmMeetingVideoDaoImpl xmMeetingVideoDao; 
	public void setXmMeetingVideoDao(XmMeetingVideoDaoImpl xmMeetingVideoDao) {
		this.xmMeetingVideoDao = xmMeetingVideoDao;
	}
	
	
	private XmMeetingPictureDaoImpl xmMeetingPictureDao; 
	public void setXmMeetingPictureDao(XmMeetingPictureDaoImpl xmMeetingPictureDao) {
		this.xmMeetingPictureDao = xmMeetingPictureDao;
	}
	private XmMeetingPictureDetailDaoImpl xmMeetingPictureDetailDao; 
	public void setXmMeetingPictureDetailDao(XmMeetingPictureDetailDaoImpl xmMeetingPictureDetailDao) {
		this.xmMeetingPictureDetailDao = xmMeetingPictureDetailDao;
	}
	
	

	private XmMeetingVoteDaoImpl xmMeetingVoteDao; 
	public void setXmMeetingVoteDao(XmMeetingVoteDaoImpl xmMeetingVoteDao) {
		this.xmMeetingVoteDao = xmMeetingVoteDao;
	}
	private XmMeetingVoteDetailDaoImpl xmMeetingVoteDetailDao; 
	public void setXmMeetingVoteDetailDao(XmMeetingVoteDetailDaoImpl xmMeetingVoteDetailDao) {
		this.xmMeetingVoteDetailDao = xmMeetingVoteDetailDao;
	}

	
}