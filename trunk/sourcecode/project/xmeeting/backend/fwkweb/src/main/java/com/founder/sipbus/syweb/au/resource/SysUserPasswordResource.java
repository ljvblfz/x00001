/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.au.resource;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.*;

import org.restlet.data.*;
import org.restlet.representation.*;
import org.restlet.resource.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.ESCode;
import com.founder.sipbus.common.util.ESDecode;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.dao.SysUserDaoImpl;
import com.founder.sipbus.syweb.au.po.SysUser;



/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/sysUserPaswword")
public class SysUserPasswordResource extends SyBaseResource{
	

	
	private SysUserDaoImpl sysUserDao;
	
	
	public void setSysUserDao(SysUserDaoImpl sysUserDao) {
		this.sysUserDao = sysUserDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		String password = this.getParameter("password");
		SysUser sysUser =  this.getLoginUser();	
		boolean isEqual = false;
		Map<String,Object>  result = new HashMap<String,Object>();
		if(ESDecode.Decode2(sysUser.getPassword()).equals(password)){
			isEqual = true;
		}
		result.put("isEqual", isEqual);
		JSON jp = JSONSerializer.toJSON(result,config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp)); 
	}


	@Post
	public Representation post(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		String newpassword = form.getFirstValue("newpassword");
		newpassword = ESCode.Encode2(newpassword);
		SysUser sysUser = sysUserDao.findById(this.getLoginUser().getUsername());
		sysUser.setPassword(newpassword);
		sysUser.setConfPassword(newpassword);
		
		this.getLoginUser().setPassword(newpassword);
		this.getLoginUser().setConfPassword(newpassword);

		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}

	
}