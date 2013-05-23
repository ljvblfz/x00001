/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.*;

/** no table comments对应DAO */
@Component
public class XmMeetingScheduleDetailDaoImpl extends DefaultBaseDaoImpl<XmMeetingScheduleDetail,java.lang.String> {
	
	public List<XmMeetingScheduleDetail> findByXmmsGuid(String xmmsGuid){
		String hql="from XmMeetingScheduleDetail  a where a.xmmsGuid=? and a.delFlag=0  order by a.xmmsdSortno";
		List<XmMeetingScheduleDetail> listOfXmMeetingScheduleDetail=super.findByHql(hql, xmmsGuid);
		return listOfXmMeetingScheduleDetail; 
	}
}
