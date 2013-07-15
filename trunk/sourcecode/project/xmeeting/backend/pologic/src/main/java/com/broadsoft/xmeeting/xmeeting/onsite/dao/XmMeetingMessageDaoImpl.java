/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.onsite.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingPicture;
import com.broadsoft.xmeeting.xmeeting.onsite.po.*;

/** no table comments对应DAO */
@Component
public class XmMeetingMessageDaoImpl extends DefaultBaseDaoImpl<XmMeetingMessage,java.lang.String> {
	public List<XmMeetingMessage> findByXmmiGuid(String xmmiGuid){
		String hql="from XmMeetingMessage  a where a.xmmiGuid=?  and a.delFlag=0  ";
		List<XmMeetingMessage> listOfXmMeetingMessage=super.findByHql(hql, xmmiGuid);
		return listOfXmMeetingMessage; 
	}
}
