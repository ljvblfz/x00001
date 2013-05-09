/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.au.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.*;

import org.restlet.data.*;
import org.restlet.representation.*;
import org.restlet.resource.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.*;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.dao.SysMemberofroleDaoImpl;
import com.founder.sipbus.syweb.au.dao.SysUserDaoImpl;
import com.founder.sipbus.syweb.au.po.SysMemberofrole;
import com.founder.sipbus.syweb.au.po.SysUser;



/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/sysUser/{username}")
public class SysUserResource extends SyBaseResource{
	
	private String username;
	
	private SysUserDaoImpl sysUserDao;
	
	private SysMemberofroleDaoImpl sysMemberofroleDao;
	
	public void setSysUserDao(SysUserDaoImpl sysUserDao) {
		this.sysUserDao = sysUserDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		username=getAttribute("username");
		SysUser sysUser =  sysUserDao.findById(username);
		Map result = new HashMap();
		if(null==sysUser){
			return getJsonGzipRepresentation(JSONSerializer.toJSON(result,config));
		}
		List<SysMemberofrole> list = sysMemberofroleDao.queryRolesByUserId(sysUser.getUserid());
		
		result.put("user", sysUser);
		result.put("roles", list);
		return getJsonGzipRepresentation(JSONSerializer.toJSON(result,config));
	}
	@Delete
	public Representation delete() {
		sysUserDao.delete(username);
		return new StringRepresentation(username);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		username =  form.getFirstValue("username");
		SysUser b= sysUserDao.findById(username);
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}

	public void setSysMemberofroleDao(SysMemberofroleDaoImpl sysMemberofroleDao) {
		this.sysMemberofroleDao = sysMemberofroleDao;
	}
	
}