/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.basic.resource;

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
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingPictureDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingPicture;
import com.broadsoft.xmeeting.xmeeting.devmgmt.vo.XmMeetingPictureSearchVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingPicture")
public class XmMeetingPicturesResource extends SyBaseResource {
	
	private XmMeetingPictureDaoImpl xmMeetingPictureDao;
	
	public void setXmMeetingPictureDao(XmMeetingPictureDaoImpl xmMeetingPictureDao) {
		this.xmMeetingPictureDao = xmMeetingPictureDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmMeetingPictureSearchVO sVO=new XmMeetingPictureSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmMeetingPictureDao.findPage(getPageRequest(),fillDetachedCriteria(XmMeetingPicture.class,sVO));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmmpicGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmMeetingPictureDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmMeetingPicture xmMeetingPicture = new XmMeetingPicture();
		PMGridCopyUtil.copyGridToDto(xmMeetingPicture, form.getValuesMap());
		xmMeetingPictureDao.add(xmMeetingPicture);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}