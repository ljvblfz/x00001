/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.basic.resource;

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
import com.broadsoft.xmeeting.xmeeting.basic.dao.XmPadDeviceDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.dao.XmRoomInfoDetailDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmPadDevice;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmRoomInfoDetail;
import com.broadsoft.xmeeting.xmeeting.basic.vo.XmRoomInfoDetailSearchVO;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmDownloadStatus;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingInfo;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmRoomInfoDetail")
public class XmRoomInfoDetailsResource extends SyBaseResource {
	
	private XmRoomInfoDetailDaoImpl xmRoomInfoDetailDao;
	
	public void setXmRoomInfoDetailDao(XmRoomInfoDetailDaoImpl xmRoomInfoDetailDao) {
		this.xmRoomInfoDetailDao = xmRoomInfoDetailDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmRoomInfoDetailSearchVO sVO=new XmRoomInfoDetailSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmRoomInfoDetailDao.findPage(getPageRequest(),fillDetachedCriteria(XmRoomInfoDetail.class,sVO));
		
		List list = p.getList();
		for (int i = 0; i < list.size(); i++) {
			XmRoomInfoDetail xmRoomInfoDetail = (XmRoomInfoDetail) list.get(i);  
			String xmpdGuid=xmRoomInfoDetail.getXmpdGuid();
			if(null!=xmpdGuid&&!"".equals(xmpdGuid)){
				XmPadDevice xmPadDevice=xmPadDeviceDao.findById(xmpdGuid); 
				if(null!=xmPadDevice){
					xmRoomInfoDetail.setXmpdGuidLabel(xmPadDevice.getXmpdCode());
				}
			}
		}//end of for
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmridGuids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			xmRoomInfoDetailDao.delete(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmRoomInfoDetail xmRoomInfoDetail = new XmRoomInfoDetail();
		PMGridCopyUtil.copyGridToDto(xmRoomInfoDetail, form.getValuesMap());
		xmRoomInfoDetailDao.add(xmRoomInfoDetail);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
	//========================
	private XmPadDeviceDaoImpl xmPadDeviceDao; 
	public void setXmPadDeviceDao(XmPadDeviceDaoImpl xmPadDeviceDao) {
		this.xmPadDeviceDao = xmPadDeviceDao;
	}
}