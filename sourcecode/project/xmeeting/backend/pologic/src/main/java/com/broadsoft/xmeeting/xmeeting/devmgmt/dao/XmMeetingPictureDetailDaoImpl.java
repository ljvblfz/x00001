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
public class XmMeetingPictureDetailDaoImpl extends DefaultBaseDaoImpl<XmMeetingPictureDetail,java.lang.String> {
	
	public List<XmMeetingPictureDetail> findByXmmpicGuid(String xmmpicGuid){
		String hql="from XmMeetingPictureDetail  a where a.xmmpicGuid=?   ";
		List<XmMeetingPictureDetail> listOfXmMeetingPictureDetail=super.findByHql(hql, xmmpicGuid);
		return listOfXmMeetingPictureDetail; 
	}
}
