/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.onsite.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingWeather;
import com.broadsoft.xmeeting.xmeeting.onsite.po.*;

/** no table comments对应DAO */
@Component
public class XmMeetingVoteDaoImpl extends DefaultBaseDaoImpl<XmMeetingVote,java.lang.String> {
	
	public List<XmMeetingVote> findByXmmiGuid(String xmmiGuid){
		String hql="from XmMeetingVote  a where a.xmmiGuid=? and a.delFlag=0  order by a.xmmvGuid ";
		List<XmMeetingVote> listOfXmMeetingVote=super.findByHql(hql, xmmiGuid);
		return listOfXmMeetingVote; 
	}
}
