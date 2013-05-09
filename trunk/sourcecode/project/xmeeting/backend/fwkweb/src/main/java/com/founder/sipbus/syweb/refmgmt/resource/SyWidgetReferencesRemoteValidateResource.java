/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.refmgmt.resource;

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
import com.founder.sipbus.syweb.refmgmt.dao.SyWidgetReferenceDaoImpl;
import com.founder.sipbus.syweb.refmgmt.po.SyWidgetReference;
import com.founder.sipbus.syweb.refmgmt.service.SyWidgetReferenceService;

/**
 * 校验引用code是否唯一
 * urls="/syWidgetReference/validate/{code}"
 * @author zjl
 *
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syWidgetReference/validate/{code}")
public class SyWidgetReferencesRemoteValidateResource extends SyBaseResource {
	
	private SyWidgetReferenceDaoImpl syWidgetReferenceDao;
	private SyWidgetReferenceService syWidgetReferenceService;
	
	public void setSyWidgetReferenceDao(SyWidgetReferenceDaoImpl syWidgetReferenceDao) {
		this.syWidgetReferenceDao = syWidgetReferenceDao;
	}

	/**
	 *get 方法
	 * <p>方法说明:</p>
	 *	校验引用code是否唯一
	 *  @param entity
	 *  @return
	 *  @throws Exception
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 上午11:19:17 
	 */
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
	String code = getAttribute("code");
	JSON jp =null;
		if (StringUtils.isNotBlank(code)) { 
			SyWidgetReference a = syWidgetReferenceDao.findByCode(code);
			if (null==a) {
				  jp = JSONSerializer.toJSON( "{result:true}",config);
					return getJsonGzipRepresentation( jp );  
			}else {

				 jp = JSONSerializer.toJSON( "{result:false,message:\"Code " + code + " 已被使用。\"}",config);
				return getJsonGzipRepresentation( jp );   
			}
		}else {
			  jp = JSONSerializer.toJSON( "{result:false,message:\"请输入Code。\"}",config);
			return getJsonGzipRepresentation( jp );  
		}
	 
	}
	 

	public SyWidgetReferenceService getSyWidgetReferenceService() {
		return syWidgetReferenceService;
	}

	public void setSyWidgetReferenceService(SyWidgetReferenceService syWidgetReferenceService) {
		this.syWidgetReferenceService = syWidgetReferenceService;
	}
}