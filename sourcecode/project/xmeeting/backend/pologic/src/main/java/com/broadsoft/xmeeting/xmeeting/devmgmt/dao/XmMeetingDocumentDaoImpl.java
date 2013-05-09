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
public class XmMeetingDocumentDaoImpl extends DefaultBaseDaoImpl<XmMeetingDocument,java.lang.String> {
	
	public List<XmMeetingDocument> findByXmmiGuid(String xmmiGuid){
		String hql="from XmMeetingDocument  a where a.xmmiGuid=?   ";
		List<XmMeetingDocument> listOfXmMeetingDocument=super.findByHql(hql, xmmiGuid);
		return listOfXmMeetingDocument; 
	}
}
