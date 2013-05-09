/*
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.au.dao;


import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;
import com.founder.sipbus.syweb.au.po.AuMenu;

@Component
public class AuMenuDaoImpl extends DefaultBaseDaoImpl<AuMenu,String> {
	
	public JSONArray findMainMenuListByUserName(String loginUserId){
		List<AuMenu> list = new java.util.ArrayList<AuMenu>();
		try {
			String hql = "from AuMenu t where 1=1 and t.delFlag=0 and t.parentid = 0 and t.isDisplay=1 and "+
			  " exists"+
			  " ( "+
				"	select ara.authorityId from AuRolesAuthorities ara where 1=1 and ara.authorityId =t.authorityId"+
				"	 and exists "+
					" 	 ("+
					"	  		select sm.member from SysMemberofrole sm,AuRoles sr,SysUser su  where sm.rolename=sr.name "+
					"			and su.userid=? and su.username =sm.member and ara.roleId=sr.guid"+
					"	) "+
					"	   )" +
					" order by t.parentid asc , t.menuOrder asc";
				

			list=findByHql(hql,new Object[]{loginUserId});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONArray.fromObject(list);
	}
	
	
	
	public void physicalRemove(String menuId){
		String hql=" delete from AuMenu where menuId=?";
		this.excuteUpdate(hql, menuId);
		
	}
	 
	public boolean isUnique(String menuId){
		 
		AuMenu b = findById(menuId);
		if (null==b) {
			return true;
		} else {
			return false;
		}
		
	}
	
}
