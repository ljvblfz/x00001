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
public class XmMeetingEmailDaoImpl extends DefaultBaseDaoImpl<XmMeetingEmail,java.lang.String> {
	public List<XmMeetingEmail> findByXmmiGuid(String xmmiGuid){
		String hql="from XmMeetingEmail  a where a.xmmiGuid=?  and a.delFlag=0  ";
		List<XmMeetingEmail> listOfXmMeetingEmail=super.findByHql(hql, xmmiGuid);
		return listOfXmMeetingEmail; 
	}
	
}
