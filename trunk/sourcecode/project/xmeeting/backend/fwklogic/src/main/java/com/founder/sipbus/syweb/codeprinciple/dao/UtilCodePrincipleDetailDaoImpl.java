/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.codeprinciple.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;
import com.founder.sipbus.syweb.codeprinciple.po.UtilCodePrincipleDetail;

@Component
public class UtilCodePrincipleDetailDaoImpl extends DefaultBaseDaoImpl<UtilCodePrincipleDetail,java.lang.String> {

	public void addAll(ArrayList<UtilCodePrincipleDetail> details) {
		 for (UtilCodePrincipleDetail utilCodePrincipleDetail : details) {
			this.add(utilCodePrincipleDetail);
		}
	}

	public  List<UtilCodePrincipleDetail>  findDetailByUcpGuid(String ucpGuid) {
		return	( List<UtilCodePrincipleDetail>) this.findByHql("from UtilCodePrincipleDetail t where t.ucpGuid = ? order by t.ucpdOrder ", ucpGuid);
	 
		
	}
	public boolean  deleteByucpGuid(String ucpGuid) {
		
		this.getSession().createQuery("delete from UtilCodePrincipleDetail t where t.ucpGuid = ? ").setString(0, ucpGuid).executeUpdate();
		return true;
		
	}
}
