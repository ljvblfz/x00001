/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.onsite.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.po.*;

/** no table comments对应DAO */
@Component
public class XmMeetingVoteDetailDaoImpl extends DefaultBaseDaoImpl<XmMeetingVoteDetail,java.lang.String> {
	
	public List<XmMeetingVoteDetail> findByXmmvGuid(String xmmvGuid){
		String hql="from XmMeetingVoteDetail  a where a.xmmvGuid=? and a.delFlag=0  order by a.xmmvdGuid ";
		List<XmMeetingVoteDetail> listOfXmMeetingVoteDetail=super.findByHql(hql, xmmvGuid);
		return listOfXmMeetingVoteDetail; 
	}
}
