/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.cck.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;
import com.founder.sipbus.syweb.cck.po.*;

/** SyCckTypeField对应DAO */
@Component
public class SyCckTypeFieldDaoImpl extends DefaultBaseDaoImpl<SyCckTypeField,java.lang.String> {

	public List<SyCckTypeField> findBySctGuid(  String ccktypeguid) {
		 
		return findByHql("from SyCckTypeField t where t.sctGuid = ? and ( t.delFlag = 0 or t.delFlag is null ) order by t.fieldListsortno asc ",ccktypeguid);
	}

	@SuppressWarnings("unchecked")
	public List<String> findBySctGuids(String[] sctfGuids) {
	return	 this.getSession().createQuery("select distinct t.sctGuid from SyCckTypeField t where t.sctfGuid in (:sctfGuids) ").setParameterList("sctfGuids", sctfGuids).list();

	}

	public int deleteBySctGuid(String id) {
	return	 this.getSession().createQuery("update  SyCckTypeField t set t.delFlag='1' where t.sctGuid = ?").setString(0, id).executeUpdate();
		
	}
}
