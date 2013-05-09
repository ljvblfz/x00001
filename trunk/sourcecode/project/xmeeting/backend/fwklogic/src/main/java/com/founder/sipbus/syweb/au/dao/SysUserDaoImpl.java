/*
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.au.dao;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.AbstractBaseDaoImpl;
import com.founder.sipbus.syweb.au.po.AuAuthorities;
import com.founder.sipbus.syweb.au.po.SysUser;

@Component
public class SysUserDaoImpl extends AbstractBaseDaoImpl<SysUser,java.lang.String> {
	
	/**
	 * 
	 * @author founder 2012-3-12
	 * @param userName 用户登录名
	 * @param password 用户登录密码
	 * @return
	 */
	public SysUser getUserById(String userId){
		
//		DetachedCriteria dcri=DetachedCriteria.forClass(SysUser.class);
//		dcri.add(Restrictions.eq(SysUser.USERNAME,userName)).add(Restrictions.eq(SysUser.PASSWORD,password));
		String hql="from SysUser where "+SysUser.USERID+"=? ";
		
		return findUniqueByHql(hql,new Object[]{userId});
	}
	
	/**
	 * 
	 * @author founder 2012-3-12
	 * @param userName 用户登录名
	 * @param password 用户登录密码
	 * @return
	 */
	public SysUser getUserByName(String userName){
		
//		DetachedCriteria dcri=DetachedCriteria.forClass(SysUser.class);
//		dcri.add(Restrictions.eq(SysUser.USERNAME,userName)).add(Restrictions.eq(SysUser.PASSWORD,password));
		String hql="from SysUser where "+SysUser.USERNAME+"=? ";
		
		return findUniqueByHql(hql,new Object[]{userName});
	}
	
	
	/**
	 * 根据用户名查找所有的权限列表
	 * @author founder 2012-3-13
	 * @param userName
	 * @return
	 */
	public List<AuAuthorities> getAuListByUserId(String userId){
		String hql = "from AuAuthorities t where 1=1 and t.delFlag=0 and  "+
		  " exists"+
		  " ( "+
			"	select ara.authorityId from AuRolesAuthorities ara where 1=1 and ara.authorityId =t.authorityId"+
			"	 and exists "+
				" 	 ("+
				"	  		select sm.member from SysMemberofrole sm,AuRoles sr,SysUser su  where sm.rolename=sr.name "+
				"			and su.userid=? and su.username =sm.member and ara.roleId=sr.guid"+
				"	) "+
				"	   )" +
				" order by t.parentId asc";
		
		return (List<AuAuthorities>)findByHqlNoEntityType(hql,new Object[]{userId});
	}
	
	
	
	/**
	 * 根据用户名查找所有的权限列表
	 * @author founder 2012-3-13
	 * @param userName
	 * @return
	 */
	public boolean hasAuth(String userId , String url , String method){
		String hql = "select count(af.functionId) from AuFunction af where af.url=?  and af.urlMethod=?   and  "+
		  " exists"+
		  " ( "+
			"	select ara.authorityId from AuRolesAuthorities ara where 1=1 and ara.authorityId =af.auAuthorities.authorityId"+
			"	 and exists "+
				" 	 ("+
				"	  		select sm.member from SysMemberofrole sm,AuRoles sr,SysUser su  where sm.rolename=sr.name "+
				"			and su.userid=? and su.username =sm.member and ara.roleId=sr.guid"+
				"	) "+
				"	   )" ;
		Long b=(Long)findUniqueByHqlNoEntityType(hql, new Object[]{url,method,userId});
		return b.intValue()>0;
	}
}
