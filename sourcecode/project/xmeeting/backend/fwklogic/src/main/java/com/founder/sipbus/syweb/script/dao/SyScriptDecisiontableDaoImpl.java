/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.script.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;
import com.founder.sipbus.syweb.script.po.*;

/** no table comments对应DAO */
@Component
public class SyScriptDecisiontableDaoImpl extends DefaultBaseDaoImpl<SyScriptDecisiontable,java.lang.String> {  

	public List<SyScriptDecisiontable> findByScriptName(String scriptName){
		String hql="select dt from SyScriptDecisiontable dt, SyScript s where dt.gsguid=s.gsguid and s.scriptName=?";
		return this.findByHql(hql, scriptName);
	}
}
