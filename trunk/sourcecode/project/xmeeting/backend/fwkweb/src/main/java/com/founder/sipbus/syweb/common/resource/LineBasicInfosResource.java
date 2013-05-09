/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */

package com.founder.sipbus.syweb.common.resource;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.dispatch.base.vo.LineLineBasicInfoVO;
import com.founder.sipbus.dispatch.gis.dao.LineLinebasicinfoDaoImpl;
import com.founder.sipbus.dispatch.gis.po.LineLinebasicinfo;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/basicLine")
public class LineBasicInfosResource extends SyBaseResource {


	private LineLinebasicinfoDaoImpl lineLinebasicinfoDao;

	private Log logger = LogFactory.getLog(LineBasicInfosResource.class);

	@Override
	protected void doInit() throws ResourceException {
	}

	@Get
	public Representation get(Representation entity) {
		List<LineLinebasicinfo> list = lineLinebasicinfoDao.getAllLineBasicInfo();
		List<LineLineBasicInfoVO> voList=new ArrayList<LineLineBasicInfoVO>();
		LineLineBasicInfoVO vo;
		for(LineLinebasicinfo po:list){
			vo=new LineLineBasicInfoVO();
			BeanUtils.copyProperties(po,vo);
			voList.add(vo);
		}
		JSON jp = JSONSerializer.toJSON(voList, config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));

	}

	public void setLineLinebasicinfoDao(
			LineLinebasicinfoDaoImpl lineLinebasicinfoDao) {
		this.lineLinebasicinfoDao = lineLinebasicinfoDao;
	}

}