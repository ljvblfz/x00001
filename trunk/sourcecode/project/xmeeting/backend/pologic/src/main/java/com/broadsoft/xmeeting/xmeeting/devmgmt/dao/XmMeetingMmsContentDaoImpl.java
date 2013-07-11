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
public class XmMeetingMmsContentDaoImpl extends DefaultBaseDaoImpl<XmMeetingMmsContent,java.lang.String> {
	
	public List<XmMeetingMmsContent> findByXmmiGuid(String xmmiGuid){
		String hql="from XmMeetingMmsContent  a where a.xmmiGuid=?  and a.delFlag=0  ";
		List<XmMeetingMmsContent> listOfXmMeetingMmsContent=super.findByHql(hql, xmmiGuid);
		return listOfXmMeetingMmsContent; 
	}
	
}
