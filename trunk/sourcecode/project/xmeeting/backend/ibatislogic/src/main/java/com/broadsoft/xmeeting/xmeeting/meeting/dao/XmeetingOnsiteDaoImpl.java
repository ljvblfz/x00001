package com.broadsoft.xmeeting.xmeeting.meeting.dao;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.meeting.vo.XmMeetingCallIVO;
import com.broadsoft.xmeeting.xmeeting.meeting.vo.XmMeetingMessageIVO;
import com.broadsoft.xmeeting.xmeeting.meeting.vo.XmMeetingSigninlIVO;
import com.founder.sipbus.common.dao.BaseIDao;
import com.founder.sipbus.common.page.PageRequest;
import com.founder.sipbus.common.page.PageResponse;

@Component
public class XmeetingOnsiteDaoImpl extends BaseIDao { 
	

	private Logger logger = LoggerFactory.getLogger(XmeetingOnsiteDaoImpl.class);
	
	

	/**
	 * 
	 * @param pageRequest
	 * @param map
	 * @return
	 */
	public PageResponse<XmMeetingSigninlIVO> searchMeetingSigninByName(PageRequest pageRequest, Map<String, String> map) { 
		
		if(logger.isTraceEnabled()){ 
			logger.trace("The value of[xmmiName] is  {}.",map.get("xmmiName"));
		}
		
		return this.queryPageAuto(pageRequest, map,"xmeeting.onsite.searchMeetingSigninByName");
	}
	


	/**
	 * 
	 * @param pageRequest
	 * @param map
	 * @return
	 */
	public PageResponse<XmMeetingCallIVO> searchMeetingCallByName(PageRequest pageRequest, Map<String, String> map) { 
		
		if(logger.isTraceEnabled()){ 
			logger.trace("The value of[xmmiName] is  {}.",map.get("xmmiName"));
		}
		
		return this.queryPageAuto(pageRequest, map,"xmeeting.onsite.searchMeetingCallByName");
	}
	
	


	/**
	 * 
	 * @param pageRequest
	 * @param map
	 * @return
	 */
	public PageResponse<XmMeetingMessageIVO> searchMeetingMessageByName(PageRequest pageRequest, Map<String, String> map) { 
		
		if(logger.isTraceEnabled()){ 
			logger.trace("The value of[xmmiName] is  {}.",map.get("xmmiName"));
		}
		
		return this.queryPageAuto(pageRequest, map,"xmeeting.onsite.searchMeetingMessageByName");
	}
	 

}//end of XmeetingOnsiteDaoImpl
