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
public class XmMeetingWeatherDaoImpl extends DefaultBaseDaoImpl<XmMeetingWeather,java.lang.String> {
	
	public List<XmMeetingWeather> findByXmmiGuid(String xmmiGuid){
		String hql="from XmMeetingWeather  a where a.xmmiGuid=?   ";
		List<XmMeetingWeather> listOfXmMeetingWeather=super.findByHql(hql, xmmiGuid);
		return listOfXmMeetingWeather; 
	}
}
