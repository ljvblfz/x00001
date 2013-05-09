/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.script.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.AbstractBaseDaoImpl;
import com.founder.sipbus.syweb.script.po.SyScriptLog;

@Component
public class SyScriptLogDaoImpl extends AbstractBaseDaoImpl<SyScriptLog,java.lang.String> {
	public int findCountByGsguid(String gsguid){
		String hql ="select nvl(max(t.scriptVersion),0) from SyScriptLog t where t.gsguid = ?";
		List list2 = findByHql(hql, gsguid);
		int count = (Integer) list2.get(0);
		return count+1;
	}

}
