/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.onsite.resource;

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
import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingVoteDetailDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.po.XmMeetingVoteDetail;
import com.broadsoft.xmeeting.xmeeting.onsite.vo.XmMeetingVoteDetailSearchVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingVoteDetail")
public class XmMeetingVoteDetailsResource extends SyBaseResource {
	
	private XmMeetingVoteDetailDaoImpl xmMeetingVoteDetailDao;
	
	public void setXmMeetingVoteDetailDao(XmMeetingVoteDetailDaoImpl xmMeetingVoteDetailDao) {
		this.xmMeetingVoteDetailDao = xmMeetingVoteDetailDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmMeetingVoteDetailSearchVO sVO=new XmMeetingVoteDetailSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmMeetingVoteDetailDao.findPage(getPageRequest(),fillDetachedCriteria(XmMeetingVoteDetail.class,sVO));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmmvdGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmMeetingVoteDetailDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmMeetingVoteDetail xmMeetingVoteDetail = new XmMeetingVoteDetail();
		PMGridCopyUtil.copyGridToDto(xmMeetingVoteDetail, form.getValuesMap());
		xmMeetingVoteDetailDao.add(xmMeetingVoteDetail);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}