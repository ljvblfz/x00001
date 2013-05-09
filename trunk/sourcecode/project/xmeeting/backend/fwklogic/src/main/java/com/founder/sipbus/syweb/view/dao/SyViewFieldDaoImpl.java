/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.view.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;
import com.founder.sipbus.syweb.view.po.*;

/** no table comments对应DAO */
@Component
public class SyViewFieldDaoImpl extends DefaultBaseDaoImpl<SyViewField,java.lang.String> {
	public List<SyViewField> findSearchConditionFieldsBySvguid(String svGuid){
		String hql =" from SyViewField t where t.svGuid = ? and t.fieldCategory='1' and delFlag=0 order by fieldOrder asc,createDt desc";
		List<SyViewField> list2 = findByHql(hql, svGuid); 
		return list2;
	}
	
	public List<SyViewField> findSearchResultFieldsBySvguid(String svGuid){
		String hql =" from SyViewField t where t.svGuid = ? and t.fieldCategory='2' and delFlag=0 order by  fieldOrder asc,createDt desc";
		List<SyViewField> list2 = findByHql(hql, svGuid); 
		return list2;
	}
	
	
	public void deleteBySvguid(String svGuid){
		String hql="delete SyViewField where svGuid = ?";
		this.excuteUpdate(hql, svGuid);
	}
	
}
