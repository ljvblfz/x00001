/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.cck.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;
import com.founder.sipbus.syweb.cck.po.*;

/** SyCckType对应DAO */
@Component
public class SyCckTypeDaoImpl extends DefaultBaseDaoImpl<SyCckType,java.lang.String> {
	/**
	 *	
	 *  @param typetable
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2013-4-2 上午11:21:53 
	 */
	public List searchCckTypeByTypetable(String typetable) {
		String hql = "from SyCckType c where c.typetable=?";
		return findByHql(hql, typetable);
	}

}
