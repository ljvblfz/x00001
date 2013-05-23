/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.devmgmt.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmPadDevice;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.*;

/** no table comments对应DAO */
@Component
public class XmDownloadStatusDaoImpl extends DefaultBaseDaoImpl<XmDownloadStatus,java.lang.String> {
	
	
	
	/**
	 * 
	 * @param xmpdDeviceId
	 * @return
	 */
	public XmDownloadStatus findByXmpdGuidAndXmmiGuid(String xmpdGuid,String xmmiGuid){ 
		String hql=" from XmDownloadStatus a where a.xmpdGuid=? and a.xmmiGuid=? and a.delFlag=0  ";
		List<XmDownloadStatus> listOfXmDownloadStatus= super.findByHql(hql,xmpdGuid,xmmiGuid);
		
		if(null!=listOfXmDownloadStatus){
			return listOfXmDownloadStatus.get(0);
		}
		return null; 
	}//end of findByXmpdDeviceId
}
