/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.au.dao;


import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;
import com.founder.sipbus.syweb.au.po.SyParams;

@Component
public class SyParamsDaoImpl extends DefaultBaseDaoImpl<SyParams,java.lang.String> {
	
	public SyParams getSyParamsByCode(String code){
		return this.findUniqueByHql("from SyParams where delFlag = 0 and code = ?", code);
	}
}
