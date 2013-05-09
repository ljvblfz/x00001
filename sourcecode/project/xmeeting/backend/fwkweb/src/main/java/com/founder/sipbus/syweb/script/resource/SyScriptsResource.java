/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.script.resource;

import java.util.HashSet;
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
import com.founder.sipbus.syweb.script.dao.SyScriptDaoImpl;
import com.founder.sipbus.syweb.script.po.SyScript;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syScript")
public class SyScriptsResource extends SyBaseResource {
	
	private SyScriptDaoImpl syScriptDao;
	
	private ScriptIDaoImpl scriptIDao;
	
	private SyCodeService syCodeService;
	
	
	
	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}

	private static HashSet<String> orderFieldSet=new HashSet<String>();
	
	static{
		orderFieldSet.add("beanName");
		orderFieldSet.add("groupName");
		orderFieldSet.add("status");
		orderFieldSet.add("scriptType");
		}
	
	
	public void setScriptIDao(ScriptIDaoImpl scriptIDao) {
		this.scriptIDao = scriptIDao;
	}

	public void setSyScriptDao(SyScriptDaoImpl syScriptDao) {
		this.syScriptDao = syScriptDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		String orderField = this.getOrderField();
		String orderDirection = this.getOrderDirection();
		Map<String, String> map = getQueryMap();
		if (null!=orderField&&!orderFieldSet.contains(orderField.toLowerCase())) {
			map.remove("orderField");
			map.remove("orderDirection");
		}
		if (!("asc".equalsIgnoreCase(orderDirection)||"desc".equalsIgnoreCase(orderDirection)))  {
			map.remove("orderDirection");
		}
		PageResponse p = scriptIDao.queryScriptInfoByPage(getPageRequest(), map);
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
		String idsStr=form.getFirstValue("gsguids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			SyScript sg = syScriptDao.findById(id);
			sg.setDelFlag(1);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		SyScript syScript = new SyScript();
		PMGridCopyUtil.copyGridToDto(syScript, form.getValuesMap());
		
		syScript.setDelFlag(0);
		syScript.setStatus("0");
		syScript.setCreateDt(new java.util.Date());
		syScript.setCreateBy(super.getLoginUser().getUserid());
		syScript.setUpdateDt(new java.util.Date());
		syScript.setUpdateBy(super.getLoginUser().getUserid());
		syScriptDao.add(syScript);
		return getJsonGzipRepresentation(getDefaultAddReturnJson().accumulate(
				"callBackMethod", getCallbackMethod()));   
	}
}