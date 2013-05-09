/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.au.resource;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.dispatch.gis.dao.HrStaffrecordsDaoImpl;
import com.founder.sipbus.dispatch.gis.po.HrStaffrecords;
import com.founder.sipbus.mainten.basic.vo.HrStaffrecordsSearchVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/hrStaffs")
public class HrStaffrecordsInfoResource extends SyBaseResource {
	
	private HrStaffrecordsDaoImpl hrStaffrecordsDao;
	
	public void setHrStaffrecordsDao(HrStaffrecordsDaoImpl hrStaffrecordsDao) {
		this.hrStaffrecordsDao = hrStaffrecordsDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		HrStaffrecordsSearchVO sVO=new HrStaffrecordsSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageResponse p = hrStaffrecordsDao.findPage(getPageRequest(),fillDetachedCriteria(HrStaffrecords.class,sVO));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
}