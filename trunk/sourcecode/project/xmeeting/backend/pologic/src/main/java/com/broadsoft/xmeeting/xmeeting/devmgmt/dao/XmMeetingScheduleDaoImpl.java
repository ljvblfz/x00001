/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingSchedule;
import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;

/** no table comments对应DAO */
@Component
public class XmMeetingScheduleDaoImpl extends DefaultBaseDaoImpl<XmMeetingSchedule,java.lang.String> {
	
	
	public List<XmMeetingSchedule> findByXmmiGuid(String xmmiGuid){
		String hql="from XmMeetingSchedule  a where a.xmmiGuid=? and a.delFlag=0 order by a.xmmsSortno";
		 
		List<XmMeetingSchedule> listOfXmMeetingSchedule=super.findByHql(hql, xmmiGuid);
		return listOfXmMeetingSchedule; 
	}
	
	
	
}
