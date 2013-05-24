/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
package com.broadsoft.xmeeting.xmeeting.basic.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.basic.po.XmCompanyInfo;
import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;

/** no table comments对应DAO */
@Component
public class XmCompanyInfoDaoImpl extends
		DefaultBaseDaoImpl<XmCompanyInfo, java.lang.String> {

	public List<XmCompanyInfo> findAllActivateInfo() {
		String hql = " from XmCompanyInfo a where a.xmciStatus='1'";
		List<XmCompanyInfo> listOfXmCompanyInfo = super.findByHql(hql);
		return listOfXmCompanyInfo;
	}
}
