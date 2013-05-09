/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.script.resource;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.script.dao.SyScriptDaoImpl;
import com.founder.sipbus.syweb.script.dao.SyScriptLogDaoImpl;
import com.founder.sipbus.syweb.script.po.SyScript;
import com.founder.sipbus.syweb.script.po.SyScriptLog;



/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syScript/{gsguid}")
public class SyScriptResource extends SyBaseResource{
	
	private String gsguid;
	
	private SyScriptDaoImpl syScriptDao;
	
	private SyScriptLogDaoImpl syScriptLogDao;
	
	
	
	public void setSyScriptLogDao(SyScriptLogDaoImpl syScriptLogDao) {
		this.syScriptLogDao = syScriptLogDao;
	}

	public void setSyScriptDao(SyScriptDaoImpl syScriptDao) {
		this.syScriptDao = syScriptDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		gsguid=getAttribute("gsguid");
		SyScript syScript =  syScriptDao.findById(gsguid);
		String status = syScript.getStatus();
		syScript.setNewSpringBeanName(syScript.getBeanName());
		//System.out.println("sadfasdfas"+status);
		if(status.equals("0")){
			syScript.setStatusString("草稿");
		}else{
			syScript.setStatusString("发布");
		}
		if(null==syScript){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(syScript,config));
	}
	@Delete
	public Representation delete() {
		syScriptDao.delete(gsguid);
		return new StringRepresentation(gsguid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		gsguid =  form.getFirstValue("gsguid");
		SyScript b= syScriptDao.findById(gsguid);
		SyScriptLog syScriptLog = new SyScriptLog();
		PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
		b.setUpdateDt(new java.util.Date());
		b.setUpdateBy(super.getLoginUser().getUserid());
		b.setNewSpringBeanName(b.getBeanName());
		PMGridCopyUtil.copyGridToDto(syScriptLog, form.getValuesMap());
		syScriptLog.setCreateDt(new java.util.Date());
		syScriptLog.setCreateBy(super.getLoginUser().getUserid());
		syScriptLog.setUpdateDt(new java.util.Date());
		syScriptLog.setUpdateBy(super.getLoginUser().getUserid());
		syScriptLog.setGsguid(b.getGsguid());
		int count = syScriptLogDao.findCountByGsguid(b.getGsguid());
		syScriptLog.setScriptVersion(count);
		
		//更新log表
		syScriptLogDao.add(syScriptLog);
		
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}
}