/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.refmgmt.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;
import com.founder.sipbus.syweb.refmgmt.po.*;

/** no table comments对应DAO */
@Component
public class SyWidgetReferenceDaoImpl extends DefaultBaseDaoImpl<SyWidgetReference,java.lang.String> {

	public SyWidgetReference findByCode(String code) {
	List<SyWidgetReference> list = findByHql("from SyWidgetReference t where t.swrCode = ? ",code);
	if (list!=null&&list.size()>0) {
		return list.get(0);
	}	else
	return null;
		
		
	}
}
