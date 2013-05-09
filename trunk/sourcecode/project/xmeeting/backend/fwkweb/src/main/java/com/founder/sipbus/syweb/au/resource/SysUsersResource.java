/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.au.resource;


import java.util.UUID;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.dao.SysUserDaoImpl;
import com.founder.sipbus.syweb.au.po.SysUser;
import com.founder.sipbus.syweb.au.vo.SysUserSearchVO;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/sysUser")
public class SysUsersResource extends SyBaseResource {
	
	private final String PASSWORD = "423873";
	private SysUserDaoImpl sysUserDao;
	
	public void setSysUserDao(SysUserDaoImpl sysUserDao) {
		this.sysUserDao = sysUserDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		SysUserSearchVO sVO=new SysUserSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = sysUserDao.findPage(getPageRequest(),fillDetachedCriteria(SysUser.class,sVO));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("usernames");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			sysUserDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Put
	public Representation put(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("usernames");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			SysUser sysUser = sysUserDao.findById(id);
			sysUser.setPassword(PASSWORD);
			sysUser.setConfPassword(PASSWORD);
		}
		return getJsonGzipRepresentation(getMessageReturnJson("操作成功"));
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		SysUser sysUser = new SysUser();
		PMGridCopyUtil.copyGridToDto(sysUser, form.getValuesMap());
		String uuid = UUID.randomUUID().toString();
		sysUser.setGuid(uuid);
		sysUser.setIsadmin(0);
		sysUser.setIsenable(1);
		sysUser.setIson(1L);
		sysUser.setPassword(PASSWORD);
		sysUser.setConfPassword(PASSWORD);
		sysUserDao.add(sysUser);		 
		return getSuccessAndCloseCurrentRepresentation();   
	}
}