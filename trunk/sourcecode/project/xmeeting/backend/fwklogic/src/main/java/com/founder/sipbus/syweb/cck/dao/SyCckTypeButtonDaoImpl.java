/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.cck.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;
import com.founder.sipbus.syweb.cck.po.SyCckTypeButton;
 

/**
 * SyCckTypeButton Dao
 * @author zjl
 *
 */
@Component
public class SyCckTypeButtonDaoImpl extends DefaultBaseDaoImpl<SyCckTypeButton,java.lang.String> {

	/**
	 * 获取 button by sctGuid 
	 * 	t.delFlag <> 1  order by t.buttonSortno asc
	 *  @param sctGuid
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2013-4-2 上午11:16:57 
	 */
	public List<SyCckTypeButton> findBySctGuid(String sctGuid) {
		 
		return findByHql("from SyCckTypeButton t where t.sctGuid = ? and t.delFlag <> 1  order by t.buttonSortno asc", sctGuid);
	}
}
