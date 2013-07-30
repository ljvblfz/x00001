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
		String hql="from XmMeetingPictureDetail  a where a.xmmpicGuid=?   and a.delFlag=0  order by xmmpicSortno";
		List<XmMeetingPictureDetail> listOfXmMeetingPictureDetail=super.findByHql(hql, xmmpicGuid);
		return listOfXmMeetingPictureDetail; 
	}
	
	
	public Integer getMaxSeqnoByXmmpicGuid(String xmmpicGuid){
		String hql="select max(xmmpicSortno) as  maxXmmpicSortno from XmMeetingPictureDetail  a where a.xmmpicGuid=?   and a.delFlag=0  ";
		Object maxObject =super.findByHqlNoEntityType(hql, xmmpicGuid);
		if(null!=maxObject){
			List<Integer> listOfMax =(List<Integer>)super.findByHqlNoEntityType(hql, xmmpicGuid);
			if(listOfMax.size()>0){
				Object value=listOfMax.get(0); 
				if(null!=value){
					return (Integer)value;
				}
			}
		}
		return 0;
	}
}
