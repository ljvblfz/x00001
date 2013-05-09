/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.onsite.resource;

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

import com.broadsoft.xmeeting.xmeeting.basic.po.XmRoomInfo;
import com.broadsoft.xmeeting.xmeeting.onsite.dao.XmMeetingVoteDaoImpl;
import com.broadsoft.xmeeting.xmeeting.onsite.po.XmMeetingVote;
import com.broadsoft.xmeeting.xmeeting.onsite.vo.XmMeetingVoteSearchVO;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.service.SyCodeService;

@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingVote")
public class XmMeetingVotesResource extends SyBaseResource {
	
	private XmMeetingVoteDaoImpl xmMeetingVoteDao;
	
	public void setXmMeetingVoteDao(XmMeetingVoteDaoImpl xmMeetingVoteDao) {
		this.xmMeetingVoteDao = xmMeetingVoteDao;
	}
	private SyCodeService syCodeService; 
	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}


	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmMeetingVoteSearchVO sVO=new XmMeetingVoteSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmMeetingVoteDao.findPage(getPageRequest(),fillDetachedCriteria(XmMeetingVote.class,sVO));
		List list = p.getList();
		for (int i = 0; i < list.size(); i++) {
			XmMeetingVote xmMeetingVote = (XmMeetingVote) list.get(i);
			xmMeetingVote.setXmmvTypeLabel(syCodeService.getSyCodeName("3007", xmMeetingVote.getXmmvType()));// 从码表读出对应名称  
			xmMeetingVote.setXmmvIsanonymLabel(syCodeService.getSyCodeName("3008", xmMeetingVote.getXmmvIsanonym()));// 从码表读出对应名称  
		}
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmmvGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmMeetingVoteDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmMeetingVote xmMeetingVote = new XmMeetingVote();
		PMGridCopyUtil.copyGridToDto(xmMeetingVote, form.getValuesMap());
		xmMeetingVoteDao.add(xmMeetingVote);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}