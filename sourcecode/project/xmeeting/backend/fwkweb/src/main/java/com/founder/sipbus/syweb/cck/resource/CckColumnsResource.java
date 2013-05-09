/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */

package com.founder.sipbus.syweb.cck.resource;

import java.util.ArrayList;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.sys.cck.dao.CckIDaoImpl;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.cck.dao.SyCckTypeDaoImpl;
import com.founder.sipbus.syweb.cck.po.SyCckType;

/***
 * 获取combox 的columns
 * urls = "/cckColumns/{sctGuid}
 * @author zjl
 * 
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/cckColumns/{sctGuid}")
public class CckColumnsResource extends SyBaseResource {

	private CckIDaoImpl cckIDao;
	private SyCckTypeDaoImpl syCckTypeDao;

	public CckIDaoImpl getCckIDao() {
		return cckIDao;
	}

	public void setCckIDao(CckIDaoImpl cckIDao) {
		this.cckIDao = cckIDao;
	}
	/***
	 * 获取combox 的columns
	 * {@link CckIDaoImpl#getTableColumns(String)}
	 * @author Founder
	 */
	@Get
	public Representation get(Representation entity) throws Exception {
		String sctGuid = getAttribute("sctGuid");
		if (StringUtils.isNotBlank(sctGuid)) {
			SyCckType syCckType = syCckTypeDao.findById(sctGuid);
			if (null != syCckType
					&& StringUtils.isNotBlank(syCckType.getTypetable())) {
				ArrayList<String> list = cckIDao.getTableColumns(syCckType
						.getTypetable());
				JSON jp = JSONSerializer.toJSON(list, config);
				return getJsonGzipRepresentation(JsonUtils
						.genSuccessReturnJson(jp));
			}
		}

		setStatus(Status.CLIENT_ERROR_NOT_FOUND);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(null));

	}

	public SyCckTypeDaoImpl getSyCckTypeDao() {
		return syCckTypeDao;
	}

	public void setSyCckTypeDao(SyCckTypeDaoImpl syCckTypeDao) {
		this.syCckTypeDao = syCckTypeDao;
	}

}