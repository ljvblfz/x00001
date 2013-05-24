/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.open.resource;

import java.util.List;

import net.sf.json.JSONObject;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.basic.dao.XmCompanyInfoDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmCompanyInfo;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;



/**
 * 此服务为pad展示所需的信息
 * @author lu.zhen
 *
 */

@Component
@Scope(value="prototype")
@RestletResource(urls="/open/companyinfo/download")
public class XmCompanyInfoDownloadResource extends SyBaseResource{
	  
	 
	@Override
    protected void doInit() throws ResourceException {  
	}
	
	@Get
	public Representation get(Representation entity) throws ResourceException { 
		JSONObject allInfo=new JSONObject();
		//  
		List<XmCompanyInfo> listXmCompanyInfo=xmCompanyInfoDao.findAllActivateInfo();
		for(XmCompanyInfo xmCompanyInfo:listXmCompanyInfo){
			String xmciType=xmCompanyInfo.getXmciType();
			if("1".equals(xmciType)){
				allInfo.put("companyinfo", xmCompanyInfo);
			}else if("2".equals(xmciType)){
				allInfo.put("leaderinfo", xmCompanyInfo); 
			}else if("3".equals(xmciType)){
				allInfo.put("orginfo", xmCompanyInfo); 
			}
		}//end of for
		//
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(allInfo));    
	}
	
	
	//=============IOC================================= 
	
	private XmCompanyInfoDaoImpl xmCompanyInfoDao;
	
	public void setXmCompanyInfoDao(XmCompanyInfoDaoImpl xmCompanyInfoDao) {
		this.xmCompanyInfoDao = xmCompanyInfoDao;
	}
}