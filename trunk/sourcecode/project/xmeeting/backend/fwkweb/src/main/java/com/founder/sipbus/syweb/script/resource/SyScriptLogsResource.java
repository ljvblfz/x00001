/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.script.resource;

import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.sys.script.dao.ScriptIDaoImpl;
import com.founder.sipbus.sys.script.vo.ScriptIVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.service.SyCodeService;
import com.founder.sipbus.syweb.script.dao.SyScriptLogDaoImpl;
import com.founder.sipbus.syweb.script.po.SyScriptLog;
import com.founder.sipbus.syweb.script.vo.SyScriptLogSearchVO;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syScriptLog")
public class SyScriptLogsResource extends SyBaseResource {
	
	private SyScriptLogDaoImpl syScriptLogDao;
	
	private ScriptIDaoImpl scriptIDao;
	
	private SyCodeService syCodeService;
	
	
	
	
	
	
	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}

	public SyCodeService getSyCodeService() {
		return syCodeService;
	}

	public void setScriptIDao(ScriptIDaoImpl scriptIDao) {
		this.scriptIDao = scriptIDao;
	}

	public void setSyScriptLogDao(SyScriptLogDaoImpl syScriptLogDao) {
		this.syScriptLogDao = syScriptLogDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		Map map = getQueryMap();
		PageResponse p = scriptIDao.queryScriptLogInfoByPage(getPageRequest(), map);
		List<ScriptIVO> syScriptList = p.getList();
		for(ScriptIVO si:syScriptList){
			si.setScriptTypeString(syCodeService.getSyCodeName("1004",
					si.getScriptType()));
			si.setGroupNameString(syCodeService.getSyCodeName("1005", si.getGroupName()));
		}
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("gslguids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			syScriptLogDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		SyScriptLog syScriptLog = new SyScriptLog();
		PMGridCopyUtil.copyGridToDto(syScriptLog, form.getValuesMap());
		syScriptLogDao.add(syScriptLog);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}