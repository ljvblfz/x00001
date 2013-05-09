/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.au.resource;


import java.util.List;
import java.util.UUID;

import net.sf.json.*;
import org.restlet.data.*;
import org.restlet.representation.*;
import org.restlet.resource.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.dao.SysMemberofroleDaoImpl;
import com.founder.sipbus.syweb.au.po.SysMemberofrole;




/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/sysUser/{userId}/assignRole")
public class SysUserAssignRolesResource extends SyBaseResource{
	
	private String userId;
	
	
	private SysMemberofroleDaoImpl sysMemberofroleDao;
	


	@Override
    protected void doInit() throws ResourceException {
	}
	

	@Post
	public Representation post(Representation entity)
			throws ResourceException {
		userId =getAttribute("userId");
		form = new Form(entity);
		List<SysMemberofrole> roles = sysMemberofroleDao.queryRolesByUserId(userId);
		if(roles!=null){
			for(SysMemberofrole role:roles){
				sysMemberofroleDao.delete(role);
			}
		}
		sysMemberofroleDao.getSessionFactory().getCurrentSession().flush();
		String [] rolenames =  form.getValuesArray("rolename");
		if(rolenames!=null){
			for(String rolename:rolenames){
				if(rolename!=null && !"".equals(rolename)){
					SysMemberofrole sysMemberofrole = new SysMemberofrole();
					sysMemberofrole.setGuid(UUID.randomUUID().toString());
					sysMemberofrole.setMember(userId);
					sysMemberofrole.setRolename(rolename);
					sysMemberofroleDao.insert(sysMemberofrole);
				}
			}
		}
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}

	public void setSysMemberofroleDao(SysMemberofroleDaoImpl sysMemberofroleDao) {
		this.sysMemberofroleDao = sysMemberofroleDao;
	}
	
}