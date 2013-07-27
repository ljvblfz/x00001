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

import com.broadsoft.xmeeting.xmeeting.basic.po.XmPadDevice;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingPictureDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingXpictureDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingPicture;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingXpicture;
import com.broadsoft.xmeeting.xmeeting.devmgmt.vo.XmMeetingXpictureSearchVO;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingXpicture")
public class XmMeetingXpicturesResource extends SyBaseResource {
	
	private XmMeetingXpictureDaoImpl xmMeetingXpictureDao;
	
	public void setXmMeetingXpictureDao(XmMeetingXpictureDaoImpl xmMeetingXpictureDao) {
		this.xmMeetingXpictureDao = xmMeetingXpictureDao;
	}
	
	private XmMeetingPictureDaoImpl xmMeetingPictureDao;
	
	public void setXmMeetingPictureDao(XmMeetingPictureDaoImpl xmMeetingPictureDao) {
		this.xmMeetingPictureDao = xmMeetingPictureDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmMeetingXpictureSearchVO sVO=new XmMeetingXpictureSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmMeetingXpictureDao.findPage(getPageRequest(),fillDetachedCriteria(XmMeetingXpicture.class,sVO)); 
		List list = p.getList();
		for (int i = 0; i < list.size(); i++) {
			XmMeetingXpicture xmMeetingXpicture = (XmMeetingXpicture) list.get(i); 
			String xmmpicGuid=xmMeetingXpicture.getXmmpicGuid();
			XmMeetingPicture xmMeetingPicture=xmMeetingPictureDao.findById(xmmpicGuid);
			xmMeetingXpicture.setXmmpicGuidLabel(xmMeetingPicture.getXmmpicImageTitle());
		}
		
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmmxpicGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmMeetingXpictureDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmMeetingXpicture xmMeetingXpicture = new XmMeetingXpicture();
		PMGridCopyUtil.copyGridToDto(xmMeetingXpicture, form.getValuesMap());
		xmMeetingXpictureDao.add(xmMeetingXpicture);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}