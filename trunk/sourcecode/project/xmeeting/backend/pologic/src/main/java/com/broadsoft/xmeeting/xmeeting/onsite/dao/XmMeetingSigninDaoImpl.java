/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.broadsoft.xmeeting.xmeeting.onsite.dao;


import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.onsite.po.XmMeetingSignin;
import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;

/** no table comments对应DAO */
@Component
public class XmMeetingSigninDaoImpl extends DefaultBaseDaoImpl<XmMeetingSignin,java.lang.String> {
	
	
	public int countByXmpiGuidAndXmmiGuid(String xmpiGuid,String xmmiGuid){
		String hql="select count(*) from XmMeetingSignin a where a.xmmsPersonnel=? and a.xmmiGuid=?";
		Object obj=this.findUniqueByHqlNoEntityType(hql,xmpiGuid,xmmiGuid);
		if(obj!=null){
			Long count=Long.parseLong(obj.toString()); 
			return count.intValue();
		}
		return 0;
	}
}
