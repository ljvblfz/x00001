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
import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingPictureDetailDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingPictureDetail;
import com.broadsoft.xmeeting.xmeeting.devmgmt.vo.XmMeetingPictureDetailSearchVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingPictureDetail")
public class XmMeetingPictureDetailsResource extends SyBaseResource {
	
	private XmMeetingPictureDetailDaoImpl xmMeetingPictureDetailDao;
	
	public void setXmMeetingPictureDetailDao(XmMeetingPictureDetailDaoImpl xmMeetingPictureDetailDao) {
		this.xmMeetingPictureDetailDao = xmMeetingPictureDetailDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmMeetingPictureDetailSearchVO sVO=new XmMeetingPictureDetailSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmMeetingPictureDetailDao.findPage(getPageRequest(),fillDetachedCriteria(XmMeetingPictureDetail.class,sVO));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmmpicdGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmMeetingPictureDetailDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmMeetingPictureDetail xmMeetingPictureDetail = new XmMeetingPictureDetail();
		PMGridCopyUtil.copyGridToDto(xmMeetingPictureDetail, form.getValuesMap());
		xmMeetingPictureDetailDao.add(xmMeetingPictureDetail);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}