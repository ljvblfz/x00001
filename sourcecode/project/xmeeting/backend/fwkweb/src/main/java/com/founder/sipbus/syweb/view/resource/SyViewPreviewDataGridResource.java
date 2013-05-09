/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */

package com.founder.sipbus.syweb.view.resource;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.DateJsonValueProcessor;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.fwk.groovysql.GroovySqlSupport;
import com.founder.sipbus.fwk.jsqlparser.IBatisDynamicSqlParserSupport;
import com.founder.sipbus.sys.view.dao.ViewDynamicSqlIDaoImpl;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.view.dao.SyViewDaoImpl;
import com.founder.sipbus.syweb.view.dao.SyViewFieldDaoImpl;
import com.founder.sipbus.syweb.view.po.SyView;
import com.founder.sipbus.syweb.view.po.SyViewField;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/system/view/previewdatagrid/{svGuid}")
public class SyViewPreviewDataGridResource extends SyBaseResource {
	private Logger logger = LoggerFactory.getLogger(SyViewPreviewDataGridResource.class);

	private static String countSelectSqlTemplate=" select count(1) from ( {0}  ) "; 
	
	
	private String svGuid;

	@Override
	protected void doInit() throws ResourceException {
		this.svGuid = getAttribute("svGuid");
	}

	@Get
	public Representation get(Representation entity) throws ResourceException {
//		form = new Form(entity);

		 
		Map<String,String> param=getQueryMap();; 
		 
		String[] arrString=findSqlBySvGuid();
		String sqltype=arrString[0];
		String originalsqltext=arrString[1];
		PageResponse pageResponse = queryDataGridByPage(param, sqltype,originalsqltext);
		
		
		//
		config.registerJsonValueProcessor(java.sql.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));
		JSON jp = JSONSerializer.toJSON(getPageResponse(pageResponse), config);
		JSONObject jsonObject = JsonUtils.genSuccessReturnJson(jp);
		jsonObject.accumulate("fields", findTableHeaderFieldsBySvGuid());
		jsonObject.accumulate("searchs", findSearchConditonFieldsBySvGuid());
		jsonObject.accumulate("primaryKey", null);
//		jsonObject.accumulate("sqltext", sqltext);
//		jsonObject.accumulate("countsqltext", countsqltext);
		return getJsonGzipRepresentation(jsonObject);
	}

	protected PageResponse queryDataGridByPage(Map<String, String> param,
			String sqltype, String originalsqltext) {
		PageResponse pageResponse =null;
		if("1".equals(sqltype)){//pure sql
			JSONObject jsonObjectSql=IBatisDynamicSqlParserSupport.genIbatisSelectSqlWithParam(originalsqltext, param);
			String sqltext=(String)jsonObjectSql.getString("sqltext");
			String countsqltext=(String)jsonObjectSql.getString("countsqltext");
			param.put("sqltext", sqltext);
			param.put("countsqltext", countsqltext);  
			if(logger.isTraceEnabled()){
				logger.trace("1-->param is {}.",param);
				logger.trace("1-->sqltext is {}.",sqltext);
				logger.trace("1-->countsqltext is {}.",countsqltext);
			} 
			pageResponse =viewDynamicSqlIDao.queryDataGridByPage(getPageRequest(), param);
		}else if("2".equals(sqltype)){//ibatis sql
			 if(logger.isTraceEnabled()){
				logger.trace("2-->param is {}.",param);
				logger.trace("2-->originalsqltext is {}.",originalsqltext); 
			 } 
			pageResponse =viewDynamicSqlIDao.queryDataGridByPage(getPageRequest(), param,originalsqltext);
		}else if("3".equals(sqltype)){//groovy sql
			 if(logger.isTraceEnabled()){
				logger.trace("3-->param is {}.",param);
				logger.trace("3-->originalsqltext is {}.",originalsqltext); 
			 } 
			 String sqltext=GroovySqlSupport.parseGroovySql(originalsqltext, param); 
			 String[] variablesx={sqltext};
			 String countsqltext = MessageFormat.format( countSelectSqlTemplate, variablesx );   
			 param.put("sqltext", sqltext);
			 param.put("countsqltext", countsqltext);  
			 if(logger.isTraceEnabled()){
				logger.trace("3-->sqltext is {}.",sqltext);
				logger.trace("3-->countsqltext is {}.",countsqltext);
			 } 
			 pageResponse =viewDynamicSqlIDao.queryDataGridByPage(getPageRequest(), param);
		}
		if(logger.isTraceEnabled()){
			 logger.trace("pageResponse is {}.",pageResponse);
		}
		
		return pageResponse;
	}

	/**
	 * @param svGuid
	 * @return
	 */
	public String[] findSqlBySvGuid() {
		SyView syView = syViewDao.findById(svGuid);
		String sqlText = syView.getSqlSource();
		String sqlType=syView.getSqltype();
		String[] arrString={sqlType,sqlText}; 
		return arrString;
	}
	
	

	public JSONArray findTableHeaderFieldsBySvGuid() {
		JSONArray array = new JSONArray();
		List<SyViewField> list = syViewFieldDao
				.findSearchResultFieldsBySvguid(svGuid);
		for (SyViewField syViewField : list) {
			array.add(JSONObject.fromObject(syViewField));
		}
		return array;
	}

	public JSONArray findSearchConditonFieldsBySvGuid() {
		JSONArray array = new JSONArray();
		List<SyViewField> list = syViewFieldDao
				.findSearchConditionFieldsBySvguid(svGuid);
		for (SyViewField syViewField : list) {
			array.add(JSONObject.fromObject(syViewField));

		}
		return array;
	}

	// ===========================> IOC
	private SyViewFieldDaoImpl syViewFieldDao;
	private SyViewDaoImpl syViewDao;

	public void setSyViewFieldDao(SyViewFieldDaoImpl syViewFieldDao) {
		this.syViewFieldDao = syViewFieldDao;
	}

	public void setSyViewDao(SyViewDaoImpl syViewDao) {
		this.syViewDao = syViewDao;
	}
	
	private ViewDynamicSqlIDaoImpl viewDynamicSqlIDao;

	public void setViewDynamicSqlIDao(ViewDynamicSqlIDaoImpl viewDynamicSqlIDao) {
		this.viewDynamicSqlIDao = viewDynamicSqlIDao;
	}

}