/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.broadsoft.xmeeting.xmeeting.basic.resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.devmgmt.dao.XmMeetingPictureDetailDaoImpl;
import com.broadsoft.xmeeting.xmeeting.devmgmt.po.XmMeetingPictureDetail;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.syweb.au.base.SyBaseResource;


@Component
@Scope(value="prototype")
@RestletResource(urls="/xmMeetingPictureMultipleUpload")
public class XmMeetingPictureMultipleUploadResource extends SyBaseResource{
	 
	
	private XmMeetingPictureDetailDaoImpl xmMeetingPictureDetailDao;
	
	public void setXmMeetingPictureDetailDao(XmMeetingPictureDetailDaoImpl xmMeetingPictureDetailDao) {
		this.xmMeetingPictureDetailDao = xmMeetingPictureDetailDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	@Post
	public Representation post(Representation entity)
			throws ResourceException { 
		form = new Form(entity);
		String xmmpicGuid=form.getFirstValue("xmmpicGuid");
		String xmmpicJsonData=form.getFirstValue("xmmpicJsonData");
		Integer maxNo=xmMeetingPictureDetailDao.getMaxSeqnoByXmmpicGuid(xmmpicGuid);
		JSONArray jsonArray=JSONArray.fromObject(xmmpicJsonData);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			String xmmpicSortno=jsonObject.getString("xmmpicSortno");
			String xmmpicImageFile=jsonObject.getString("xmmpicImageFile");
			String xmmpicImageDesc=jsonObject.getString("xmmpicImageDesc").split("\\.")[0];
			XmMeetingPictureDetail xmMeetingPictureDetail = new XmMeetingPictureDetail(); 
			
			xmMeetingPictureDetail.setXmmpicGuid(xmmpicGuid);
			xmMeetingPictureDetail.setXmmpicImageDesc(xmmpicImageDesc);
			xmMeetingPictureDetail.setXmmpicImageFile(xmmpicImageFile);
			Integer ixmmpicSortno=Integer.valueOf(xmmpicSortno);
			xmMeetingPictureDetail.setXmmpicSortno(ixmmpicSortno+maxNo); 
			xmMeetingPictureDetailDao.add(xmMeetingPictureDetail);
		}//end of for 
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}

