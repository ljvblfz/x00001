/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.view.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;
import com.founder.sipbus.syweb.view.po.SyViewTypeButton;
 

@Component
public class SyViewTypeButtonDaoImpl extends DefaultBaseDaoImpl<SyViewTypeButton,java.lang.String> {

	public List<SyViewTypeButton> findBySctGuid(String sctGuid) {
		 
		return findByHql("from SyViewTypeButton t where t.sctGuid = ? and t.delFlag <> 1  order by t.buttonSortno asc", sctGuid);
	}
}
