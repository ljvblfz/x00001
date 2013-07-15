/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.devmgmt.resource;

import java.util.List;

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
import com.broadsoft.xmeeting.xmeeting.basic.po.XmPadDevice;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingDocumentDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingDocument;
import com.broadsoft.xmeeting.xmeeting.devmgmt.vo.XmMeetingDocumentSearchVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.service.SyCodeService;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingDocument")
public class XmMeetingDocumentsResource extends SyBaseResource {
	
	private XmMeetingDocumentDaoImpl xmMeetingDocumentDao;
	
	public void setXmMeetingDocumentDao(XmMeetingDocumentDaoImpl xmMeetingDocumentDao) {
		this.xmMeetingDocumentDao = xmMeetingDocumentDao;
	}
	private SyCodeService syCodeService; 
	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}
	

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmMeetingDocumentSearchVO sVO=new XmMeetingDocumentSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmMeetingDocumentDao.findPage(getPageRequest(),fillDetachedCriteria(XmMeetingDocument.class,sVO));
		List list = p.getList();
		for (int i = 0; i < list.size(); i++) {
			XmMeetingDocument xmMeetingDocument = (XmMeetingDocument) list.get(i);
			xmMeetingDocument.setXmmdIsAllowedSentLabel(syCodeService.getSyCodeName("3016", xmMeetingDocument.getXmmdIsAllowedSent()));// 从码表读出对应名称  
		}
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmmdGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmMeetingDocumentDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmMeetingDocument xmMeetingDocument = new XmMeetingDocument();
		PMGridCopyUtil.copyGridToDto(xmMeetingDocument, form.getValuesMap());
		xmMeetingDocumentDao.add(xmMeetingDocument);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}