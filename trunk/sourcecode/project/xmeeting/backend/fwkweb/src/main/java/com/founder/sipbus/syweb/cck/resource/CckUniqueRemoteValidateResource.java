/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.cck.resource;

import java.util.Map;

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
import com.founder.sipbus.syweb.cck.service.SyCckService;

/**
 * 对cck form中的 unique 字段检查唯一用resource
 * urls="/cckUniqueValidate/{code}"
 * @author zjl
 *
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/cckUniqueValidate/{code}")
public class CckUniqueRemoteValidateResource extends SyBaseResource {
 
	private SyCckService syCckService;
	 


	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
	String code = getAttribute("code");
	form.getValuesMap();
	Map map = getQueryMap();
	String sctGuid=(String) map.get("SCT_GUID");
	String elementname=(String) map.get("elementname");
	String id=(String) map.get("SCC_GUID");
	
	JSON jp =null;
		if (StringUtils.isNotBlank(code)) {
		
		
		boolean b = syCckService.checkUnique(sctGuid,id,map, code, elementname);
			if (b) {
				  jp = JSONSerializer.toJSON( "{result:true}",config);
					return getJsonGzipRepresentation( jp );  
			}else {

				 jp = JSONSerializer.toJSON( "{result:false,message:\"" + code + " 已被使用。\"}",config);
				return getJsonGzipRepresentation( jp );   
			}
		}else {
			  jp = JSONSerializer.toJSON( "{result:true }",config);
			return getJsonGzipRepresentation( jp );  
		}
	 
	}



	public SyCckService getSyCckService() {
		return syCckService;
	}



	public void setSyCckService(SyCckService syCckService) {
		this.syCckService = syCckService;
	}
	 

 
}