/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingXpicture;
import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;

/** no table comments对应DAO */
@Component
public class XmMeetingXpictureDaoImpl extends DefaultBaseDaoImpl<XmMeetingXpicture,java.lang.String> {
	
	public List<XmMeetingXpicture> findByXmmiGuid(String xmmiGuid){
		String hql="from XmMeetingXpicture  a where a.xmmiGuid=?  and a.delFlag=0  ";
		List<XmMeetingXpicture> listOfXmMeetingPicture=super.findByHql(hql, xmmiGuid);
		return listOfXmMeetingPicture; 
	}
}
