/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.view.resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.fwk.jsqlparser.ViewSqlParserSupport;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.view.dao.SyViewDaoImpl;
import com.founder.sipbus.syweb.view.dao.SyViewFieldDaoImpl;
import com.founder.sipbus.syweb.view.po.SyViewField;



/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/system/view/parsesqlandsave")
public class SyViewParseSqlAndSaveResource extends SyBaseResource{   

	
	@Override
    protected void doInit() throws ResourceException {
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException { 
		form = new Form(entity);  
		String sqlSource=form.getFirstValue("sqlSource");
		String svGuid=form.getFirstValue("svGuid");
		JSONObject jsonObject=ViewSqlParserSupport.parseSqlTextWithSql(sqlSource);  
		//清空已有数据
		syViewFieldDao.deleteBySvguid(svGuid);
		//select result items

		if(jsonObject.has("listOfSelectItem")){ 
			JSONArray jsonSelectItemArray=(JSONArray)jsonObject.get("listOfSelectItem");
			for(int i=0;i<jsonSelectItemArray.size();i++){
				JSONObject jsonObjectItem=(JSONObject)jsonSelectItemArray.get(i);
				SyViewField syViewField=new SyViewField();
				syViewField.setSvGuid(svGuid);
				syViewField.setDelFlag(0);
				syViewField.setFieldCategory("2");
				syViewField.setFieldType("1");
				if(jsonObjectItem.has("columnName")){
					syViewField.setFieldLabel(jsonObjectItem.getString("columnName"));
					syViewField.setFieldColumn(jsonObjectItem.getString("columnName")); 
				} 
	//			else{
	//				if(jsonObjectItem.has("alias")){
	//					syViewField.setFieldLabel(jsonObjectItem.getString("alias"));
	//					syViewField.setFieldColumn(jsonObjectItem.getString("alias"));
	//					
	//				}
	//			}
				if(jsonObjectItem.has("alias")){
					Object alias=jsonObjectItem.get("alias");
					if(null!=alias){
						syViewField.setFieldColumnAlias((String)alias); 
					} 
				}
				syViewFieldDao.insert(syViewField); 
			}//end of for
		}//end of if
		//select condition items  
		if(jsonObject.has("listOfConditionItem")){ 
			JSONArray jsonConditionItemArray=(JSONArray)jsonObject.get("listOfConditionItem");
			for(int i=0;i<jsonConditionItemArray.size();i++){
				String strConditionItem=(String)jsonConditionItemArray.get(i);
				SyViewField syViewField=new SyViewField();
				syViewField.setSvGuid(svGuid);
				syViewField.setFieldCategory("1");
				syViewField.setFieldType("1");//input type
				syViewField.setDelFlag(0);
				syViewField.setFieldLabel(strConditionItem);
				syViewField.setFieldColumn(strConditionItem); 
				syViewFieldDao.insert(syViewField);
			}//end of for
		}//end of if
		
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jsonObject));   
	}
	
	
	//==============IoC=================
	private SyViewDaoImpl syViewDao;
	private SyViewFieldDaoImpl syViewFieldDao;
	 

	public void setSyViewDao(SyViewDaoImpl syViewDao) {
		this.syViewDao = syViewDao;
	}

	 

	public void setSyViewFieldDao(SyViewFieldDaoImpl syViewFieldDao) {
		this.syViewFieldDao = syViewFieldDao;
	}
	
	
	
}