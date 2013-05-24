/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.open.resource;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmDownloadStatusDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmDownloadStatus;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/open/downloadstatus/save")
public class XmPadDownloadStatusSaveResource extends SyBaseResource {
	
	private XmDownloadStatusDaoImpl xmDownloadStatusDao;
	
	public void setXmDownloadStatusDao(XmDownloadStatusDaoImpl xmDownloadStatusDao) {
		this.xmDownloadStatusDao = xmDownloadStatusDao;
	} 
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException { 
		form = new Form(entity);
		String xmdsGuid =  form.getFirstValue("xmdsGuid"); 
		if(xmdsGuid==null||xmdsGuid.trim().equals("")){
			XmDownloadStatus xmDownloadStatusInsert = new XmDownloadStatus();
			PMGridCopyUtil.copyGridToDto(xmDownloadStatusInsert, form.getValuesMap());
			xmDownloadStatusDao.add(xmDownloadStatusInsert); 
		}else{ 
			XmDownloadStatus xmDownloadStatusModify= xmDownloadStatusDao.findById(xmdsGuid);
			PMGridCopyUtil.copyGridToDto(xmDownloadStatusModify, form.getValuesMap()); 
		}
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}//end of post
	
	
}//end of XmPadDownloadStatusSaveResource