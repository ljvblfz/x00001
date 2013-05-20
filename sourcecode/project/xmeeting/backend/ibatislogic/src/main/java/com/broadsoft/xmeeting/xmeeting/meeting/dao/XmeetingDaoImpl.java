package com.broadsoft.xmeeting.xmeeting.meeting.dao;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.meeting.vo.XmMeetingInfoIVO;
import com.broadsoft.xmeeting.xmeeting.meeting.vo.XmMeetingPadIVO;
import com.broadsoft.xmeeting.xmeeting.meeting.vo.XmMeetingPersonnelSeatPadIVO;
import com.broadsoft.xmeeting.xmeeting.meeting.vo.XmMeetingServicePersonnelIVO;
import com.founder.sipbus.common.dao.BaseIDao;
import com.founder.sipbus.common.page.PageRequest;
import com.founder.sipbus.common.page.PageResponse;

@Component
public class XmeetingDaoImpl extends BaseIDao { 
	

	private Logger logger = LoggerFactory.getLogger(XmeetingDaoImpl.class);
	
	

	/**
	 * 
	 * @param pageRequest
	 * @param map
	 * @return
	 */
	public PageResponse<XmMeetingInfoIVO> searchMeetingInfoByName(PageRequest pageRequest, Map<String, String> map) { 
		
		if(logger.isTraceEnabled()){ 
			logger.trace("The value of[xmmiName] is  {}.",map.get("xmmiName"));
		}
		
		return this.queryPageAuto(pageRequest, map,"xmeeting.xmeeting.searchMeetingInfoByName");
	}
	
	/**
	 * 
	 * @param pageRequest
	 * @param map
	 * @return
	 */
	public PageResponse<XmMeetingPersonnelSeatPadIVO> findMeetingPersonnelSeatPadByMeetingID(PageRequest pageRequest, Map<String, String> map) { 
		
		if(logger.isTraceEnabled()){ 
			logger.trace("The value of[xmmiGuid] is  {}.",map.get("xmmiGuid"));
		}
		
		return this.queryPageAuto(pageRequest, map,"xmeeting.xmeeting.findPersonnelSeatPadByMeetingID");
	}
	
	

	/**
	 * 
	 * @param pageRequest
	 * @param map
	 * @return
	 */
	public PageResponse<XmMeetingServicePersonnelIVO> findServicePersonnelByMeetingID(PageRequest pageRequest, Map<String, String> map) { 
		
		if(logger.isTraceEnabled()){ 
			logger.trace("The value of[xmmiGuid] is  {}.",map.get("xmmiGuid"));
		}
		
		return this.queryPageAuto(pageRequest, map,"xmeeting.xmeeting.findServicePersonnelByMeetingID");
	}
	
	
	
	/**
	 * 
	 * @param pageRequest
	 * @param map
	 * @return
	 */
	public PageResponse<XmMeetingPadIVO> findPadInfoByMeetingID(PageRequest pageRequest, Map<String, String> map) { 
		
		if(logger.isTraceEnabled()){ 
			logger.trace("The value of[xmmiGuid] is  {}.",map.get("xmmiGuid"));
		}
		
		return this.queryPageAuto(pageRequest, map,"xmeeting.xmeeting.findPadInfoByMeetingID");
	}

}//end of XmeetingDaoImpl
