/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.devmgmt.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingBusDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingContactDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingScheduleDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingScheduleDetailDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingWeatherDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingBus;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingContact;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingInfo;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingSchedule;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingScheduleDetail;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingWeather;
import com.broadsoft.xmeeting.xmeeting.meeting.dao.XmeetingDaoImpl;
import com.broadsoft.xmeeting.xmeeting.meeting.vo.XmMeetingPersonnelSeatPadIVO;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageRequest;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.TimeUtils;
import com.founder.sipbus.fwk.regex.HtmlRegexSupport;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingMmsGenContent/xmmiGuid/{xmmiGuid}")
public class XmMeetingMmsGenContentResource extends SyBaseResource{
	
	private String xmmiGuid; 
	
	@Override
    protected void doInit() throws ResourceException {
		this.xmmiGuid=this.getAttribute("xmmiGuid");
	}
	
	@Get
	public Representation get(Representation entity) {  
		StringBuilder sb=new StringBuilder(); 
		//会议基本信息
		XmMeetingInfo xmMeetingInfo=xmMeetingInfoDao.findById(xmmiGuid); 
		sb.append("会议名称: "+xmMeetingInfo.getXmmiName()+"("+TimeUtils.date2String(null,xmMeetingInfo.getXmmiBeginDate())+"-"+TimeUtils.date2String(null,xmMeetingInfo.getXmmiEndDate())+")"); 
		//会议日程
		sb.append("\n");
		sb.append("*********会议日程***************\n"); 
		List<XmMeetingSchedule> listOfXmMeetingSchedule=xmMeetingScheduleDao.findByXmmiGuid(xmmiGuid);
		for(XmMeetingSchedule xmMeetingSchedule:listOfXmMeetingSchedule){ 
			String xmmsGuid=xmMeetingSchedule.getXmmsGuid();
			sb.append(""+xmMeetingSchedule.getXmmsTitle()+"\n");  
			List<XmMeetingScheduleDetail> listOfXmMeetingScheduleDetail=xmMeetingScheduleDetailDao.findByXmmsGuid(xmmsGuid); 
			for(XmMeetingScheduleDetail xmMeetingScheduleDetail:listOfXmMeetingScheduleDetail){
				sb.append("    "+xmMeetingScheduleDetail.getXmmsdTime()+"("+xmMeetingScheduleDetail.getXmmsdTitle()+")"+"\n");  
				sb.append("    "+xmMeetingScheduleDetail.getXmmsdDescription()+"\n");  
				
			}
		} 
		//人员名单
		sb.append("\n");
		sb.append("*********人员名单***************\n");
		PageRequest pageRequest=new PageRequest();
		pageRequest.setPageNumber(1);
		pageRequest.setPageSize(200); 
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("xmmiGuid", xmmiGuid);  
		PageResponse<XmMeetingPersonnelSeatPadIVO>  responseOfXmMeetingPersonnelSeatPadIVO=xmeetingDao.findMeetingPersonnelSeatPadByMeetingID(pageRequest, paramMap); 
		List<XmMeetingPersonnelSeatPadIVO> listOfXmMeetingPersonnelSeatPadIVO= responseOfXmMeetingPersonnelSeatPadIVO.getList(); 
		//--格式化数据
		String xmpiDeptinfo1="";
		String xmpiDeptinfo2="";
		List<String> memberList1=new ArrayList<String>();
		List<String> memberList2=new ArrayList<String>();
		for(XmMeetingPersonnelSeatPadIVO xmMeetingPersonnelSeatPadIVO:listOfXmMeetingPersonnelSeatPadIVO){
			String xmpiDeptinfo=xmMeetingPersonnelSeatPadIVO.getXmpiDeptinfo();
			String xmpiName=xmMeetingPersonnelSeatPadIVO.getXmpiName();
			String xmpiTitle=xmMeetingPersonnelSeatPadIVO.getXmpiTitle();
			//
			if("".equals(xmpiDeptinfo1)){
				xmpiDeptinfo1=xmpiDeptinfo;
			}
			if(xmpiDeptinfo.equals(xmpiDeptinfo1)){
				memberList1.add(xmpiName+"          "+xmpiTitle);
			}else if("".equals(xmpiDeptinfo2)){
				xmpiDeptinfo2=xmpiDeptinfo;
			}  
			if(xmpiDeptinfo.equals(xmpiDeptinfo2)){
				memberList2.add(xmpiName+"          "+xmpiTitle); 
			}
		}//end of for 
		sb.append(xmpiDeptinfo1+"名单\n"); 
		sb.append("姓名          职务\n");
		for(String item:memberList1){
			sb.append(item+"\n");
			
		}
		sb.append("\n");
		sb.append(xmpiDeptinfo2+"名单\n");
		sb.append("姓名          职务\n");
		for(String item:memberList2){
			sb.append(item+"\n");
		}
		
		//车辆安排
		sb.append("\n");
		sb.append("*********车辆安排***************\n"); 
		List<XmMeetingBus> listOfXmMeetingBus=xmMeetingBusDao.findByXmmiGuid(xmmiGuid); 
		if(null!=listOfXmMeetingBus&&listOfXmMeetingBus.size()>0){
			for(XmMeetingBus xmMeetingBus:listOfXmMeetingBus){
				sb.append(""+xmMeetingBus.getXmmbCode()+"("+xmMeetingBus.getXmmbCard()+")"+"\n");
				sb.append("    "+xmMeetingBus.getXmmbDesc()+"\n");
			}
		}
		//会议天气
		sb.append("\n");
		sb.append("*********天气情况***************\n"); 
		List<XmMeetingWeather> listOfXmMeetingWeather=xmMeetingWeatherDao.findByXmmiGuid(xmmiGuid);
		if(null!=listOfXmMeetingWeather&&listOfXmMeetingWeather.size()>0){
			for(XmMeetingWeather xmMeetingWeather:listOfXmMeetingWeather){
				sb.append(""+xmMeetingWeather.getXmmwDescription()+"\n");
			}
		}
		//联系方式
		sb.append("\n");
		sb.append("*********联系方式***************\n"); 
		List<XmMeetingContact> listOfXmMeetingContact=xmMeetingContactDao.findByXmmiGuid(xmmiGuid);
		if(null!=listOfXmMeetingContact&&listOfXmMeetingContact.size()>0){
			for(XmMeetingContact xmMeetingContact:listOfXmMeetingContact){
				sb.append(""+xmMeetingContact.getXmmcDescription()+"\n");
			}
		}
		
		//彩信结束
		sb.append("\n");
		sb.append("*********彩信结束***************\n");
		
		
		
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(HtmlRegexSupport.replaceHtmlTag(sb.toString())));
	} 
	//=========================================

	private XmMeetingInfoDaoImpl xmMeetingInfoDao;
	
	public void setXmMeetingInfoDao(XmMeetingInfoDaoImpl xmMeetingInfoDao) {
		this.xmMeetingInfoDao = xmMeetingInfoDao;
	}

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
	
	private XmeetingDaoImpl xmeetingDao;

	public void setXmeetingDao(XmeetingDaoImpl xmeetingDao) {
		this.xmeetingDao = xmeetingDao;
	} 
}