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
public class XmMeetingPictureDaoImpl extends DefaultBaseDaoImpl<XmMeetingPicture,java.lang.String> {
	
	public List<XmMeetingPicture> findByXmmiGuid(String xmmiGuid){
		String hql="from XmMeetingPicture  a where a.xmmiGuid=?   ";
		List<XmMeetingPicture> listOfXmMeetingPicture=super.findByHql(hql, xmmiGuid);
		return listOfXmMeetingPicture; 
	}
}