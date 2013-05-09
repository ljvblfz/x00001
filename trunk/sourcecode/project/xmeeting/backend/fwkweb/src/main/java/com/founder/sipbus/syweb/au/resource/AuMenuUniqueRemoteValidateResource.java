/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.au.resource;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.dao.AuMenuDaoImpl;

/**
 * 对menuId 检查唯一用resource
 * urls="/cckUniqueValidate/{code}"
 * @author zjl
 *
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/validate/auMenuId/{menuId}")
public class AuMenuUniqueRemoteValidateResource extends SyBaseResource {
 
	private AuMenuDaoImpl auMenuDao ;
	 


	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
	String menuId = getAttribute("menuId");
//	form.getValuesMap();
//	Map map = getQueryMap();
//	String sctGuid=(String) map.get("SCT_GUID");
//	String elementname=(String) map.get("elementname");
//	String id=(String) map.get("SCC_GUID");
	
	JSON jp =null;
		if (StringUtils.isNotBlank(menuId)) {
		
		
		boolean b = auMenuDao.isUnique(menuId);
			if (b) {
				  jp = JSONSerializer.toJSON( "{result:true}",config);
					return getJsonGzipRepresentation( jp );  
			}else {

				 jp = JSONSerializer.toJSON( "{result:false,message:\"" + menuId + " 已被使用。\"}",config);
				return getJsonGzipRepresentation( jp );   
			}
		}else {
			  jp = JSONSerializer.toJSON( "{result:true }",config);
			return getJsonGzipRepresentation( jp );  
		}
	 
	}

	public AuMenuDaoImpl getAuMenuDao() {
		return auMenuDao;
	}

	public void setAuMenuDao(AuMenuDaoImpl auMenuDao) {
		this.auMenuDao = auMenuDao;
	}
	 

 
}