/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.cck.resource;

import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.cck.dao.SyCckTypeFieldDaoImpl;
import com.founder.sipbus.syweb.cck.po.SyCckTypeField;
import com.founder.sipbus.syweb.cck.service.SyCckService;
import com.founder.sipbus.syweb.cck.util.SyCckUtil;



/***
 * syCckTypeField 单条记录resource
 * urls="/syCckType/{sctGuid}/syCckTypeField/{sctfGuid}"
 * @author zjl
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syCckType/{sctGuid}/syCckTypeField/{sctfGuid}")
public class SyCckTypeFieldResource extends SyBaseResource{
	
	private String sctfGuid;
	private SyCckService syCckService;
	private SyCckTypeFieldDaoImpl syCckTypeFieldDao;

	private String sctGuid;
	
	public void setSyCckTypeFieldDao(SyCckTypeFieldDaoImpl syCckTypeFieldDao) {
		this.syCckTypeFieldDao = syCckTypeFieldDao;
	}

	@Override
    protected void doInit() throws ResourceException {
		   sctGuid = getAttribute("sctGuid");
	}
	
	@Get
	public Representation get(Representation entity) {
		sctfGuid=getAttribute("sctfGuid");
		SyCckTypeField syCckTypeField =  syCckTypeFieldDao.findById(sctfGuid);
		if(null==syCckTypeField){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		return getJsonGzipRepresentation(JSONSerializer.toJSON(syCckTypeField,config));
	}
	@Delete
	public Representation delete() {
		syCckTypeFieldDao.delete(sctfGuid);
		syCckService.clearCacheBySctfGuids(new String[]{sctfGuid});
		return new StringRepresentation(sctfGuid);
	}

	@Put
	public Representation put(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		 
		Map  map = form.getValuesMap();
		sctfGuid =  form.getFirstValue("sctfGuid");
		SyCckUtil.changeCheckBox(map);

		map.put("sctGuid", sctGuid);
		map.put("fieldColumn", StringUtils.upperCase((String) map.get("fieldColumn")));
		map.put("fieldAlias", StringUtils.upperCase((String) map.get("fieldAlias")));
		SyCckTypeField b= syCckTypeFieldDao.findById(sctfGuid);
		String isUnique= map.get("fieldIsunique").toString();
		if ("1".equals(isUnique)) {
			map.put("fieldIsrequired", "1");
		}
		PMGridCopyUtil.copyGridToDto(b,map );
		syCckService.clearCache(sctGuid);
		JSONObject jo = getDefaultEditReturnJson();
		return getJsonGzipRepresentation(jo);
	}

	public SyCckService getSyCckService() {
		return syCckService;
	}

	public void setSyCckService(SyCckService syCckService) {
		this.syCckService = syCckService;
	}
}