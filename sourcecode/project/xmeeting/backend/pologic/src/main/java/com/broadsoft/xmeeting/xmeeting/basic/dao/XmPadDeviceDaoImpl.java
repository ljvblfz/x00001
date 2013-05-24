/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.basic.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.basic.po.XmPadDevice;
import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;

/** no table comments对应DAO */
@Component
public class XmPadDeviceDaoImpl extends DefaultBaseDaoImpl<XmPadDevice,java.lang.String> {
	
	/**
	 * 
	 * @param xmpdDeviceId
	 * @return
	 */
	public XmPadDevice findByXmpdDeviceId(String xmpdDeviceId){ 
		String hql=" from XmPadDevice a where a.xmpdDeviceId=? ";
		List<XmPadDevice> listOfXmPadDevice= super.findByHql(hql,xmpdDeviceId);
		
		if(null!=listOfXmPadDevice&&!listOfXmPadDevice.isEmpty()){
			return listOfXmPadDevice.get(0);
		}
		return null; 
	}//end of findByXmpdDeviceId
	
}
