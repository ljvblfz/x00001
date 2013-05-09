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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.broadsoft.xmeeting.xmeeting.basic.dao.XmPadDeviceDaoImpl;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmCompanyInfo;
import com.broadsoft.xmeeting.xmeeting.basic.po.XmPadDevice;
import com.broadsoft.xmeeting.xmeeting.basic.vo.XmPadDeviceSearchVO;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

import com.founder.sipbus.syweb.au.service.SyCodeService;
@Component
@Scope(value="prototype")
@RestletResource(urls="/xmPadDevice")
public class XmPadDevicesResource extends SyBaseResource {

	private Logger logger = LoggerFactory.getLogger(XmPadDevicesResource.class);
	private XmPadDeviceDaoImpl xmPadDeviceDao; 
	public void setXmPadDeviceDao(XmPadDeviceDaoImpl xmPadDeviceDao) {
		this.xmPadDeviceDao = xmPadDeviceDao;
	}

	private SyCodeService syCodeService; 
	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}
	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		XmPadDeviceSearchVO sVO=new XmPadDeviceSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = xmPadDeviceDao.findPage(getPageRequest(),fillDetachedCriteria(XmPadDevice.class,sVO));
		List list = p.getList();
		for (int i = 0; i < list.size(); i++) {
			XmPadDevice xmPadDevice = (XmPadDevice) list.get(i);
			xmPadDevice.setXmpdTypeLabel(syCodeService.getSyCodeName("3003", xmPadDevice.getXmpdType()));// 从码表读出对应名称 
			xmPadDevice.setXmpdStatusLabel(syCodeService.getSyCodeName("3004", xmPadDevice.getXmpdStatus()));// 从码表读出对应名称 
		}
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		if(logger.isTraceEnabled()){
			logger.trace("delete begin");
		}
		form = new Form(entity); 
		String idsStr=form.getFirstValue("xmpdGuids");
		if(logger.isTraceEnabled()){
			logger.trace("xmpdGuids is: {}",idsStr);
		}
		String[] ids=idsStr.split(",");
		for(String id:ids){
//			xmPadDeviceDao.delete(id); 
			XmPadDevice xmPadDevice=xmPadDeviceDao.findById(id);
			xmPadDevice.setDelFlag(1); 
		}
		if(logger.isTraceEnabled()){
			logger.trace("delete end");
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		XmPadDevice xmPadDevice = new XmPadDevice();
		PMGridCopyUtil.copyGridToDto(xmPadDevice, form.getValuesMap());
		xmPadDeviceDao.add(xmPadDevice);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());   
	}
}