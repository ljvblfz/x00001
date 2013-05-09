/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingPersonnelSeatPad;
import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;

/** no table comments对应DAO */
@Component
public class XmMeetingPersonnelSeatPadDaoImpl extends DefaultBaseDaoImpl<XmMeetingPersonnelSeatPad,java.lang.String> {
	
	
	public XmMeetingPersonnelSeatPad findByXmpdGuid(String xmpdGuid){
		String hql="select a from XmMeetingPersonnelSeatPad a ,XmMeetingInfo b where a.xmmiGuid=b.xmmiGuid and a.xmpdGuid=? and b.xmmiStatus='1'";
		
		List<XmMeetingPersonnelSeatPad> listOfXmMeetingPersonnelSeatPad=super.findByHql(hql, xmpdGuid);
		
		if(null!=listOfXmMeetingPersonnelSeatPad&&!listOfXmMeetingPersonnelSeatPad.isEmpty()){
			return listOfXmMeetingPersonnelSeatPad.get(0);
		}
		return null; 
	}
	
	
 
	
	
}
