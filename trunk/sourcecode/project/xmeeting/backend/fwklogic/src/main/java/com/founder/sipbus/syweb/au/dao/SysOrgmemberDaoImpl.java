package com.founder.sipbus.syweb.au.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.AbstractBaseDaoImpl;
import com.founder.sipbus.syweb.au.po.SysOrgmember;
@Component
public class SysOrgmemberDaoImpl extends AbstractBaseDaoImpl<SysOrgmember, String> {
	
	/**
	 * 根据工号获取部门
	 * @author Tracy 2012-10-17
	 * @param userid
	 * @return
	 */
	public String getUserOrg(String jobnumber){
		List<SysOrgmember> l = findByHql("from SysOrgmember t where t.member=?", jobnumber);
		
		if(null!=l && l.size()>0)
			return l.get(0).getOrg();
		return null;
	}
}
