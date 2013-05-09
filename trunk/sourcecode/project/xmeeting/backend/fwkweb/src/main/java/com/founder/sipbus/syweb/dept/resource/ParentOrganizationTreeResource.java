/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */

package com.founder.sipbus.syweb.dept.resource;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageRequest;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.dispatch.gis.dao.SysOrgDaoImpl;
import com.founder.sipbus.dispatch.gis.po.SysOrg;
import com.founder.sipbus.dispatch.gis.vo.SysOrgSearchVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.dept.vo.CompanyTreeVO;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/parentOrganizationTree")
public class ParentOrganizationTreeResource extends SyBaseResource {

	private SysOrgDaoImpl sysOrgDao;

	public void setSysOrgDao(SysOrgDaoImpl sysOrgDao)
	{
		this.sysOrgDao = sysOrgDao;
	}

	@Get
	public Representation get(Representation entity) {
		//form = new Form(entity); 
		SysOrgSearchVO sVO=new SysOrgSearchVO();
		PMGridCopyUtil.copyGridToDto(sVO,getQueryMap());
		PageRequest prq = getPageRequest();
		prq.setPageSize(100000);
		PageResponse p = sysOrgDao.findPage(prq,fillDetachedCriteria(SysOrg.class,sVO));
		List<SysOrg> lso=p.getList();
		List<CompanyTreeVO> listOfCompanyTreeVO = new ArrayList<CompanyTreeVO>();

		for (int i = 0; i < lso.size(); i++) {
			SysOrg po = lso.get(i);
			CompanyTreeVO treevo = new CompanyTreeVO();
			treevo.setId(po.getDeptid());
			treevo.setpId(po.getSupdeptid());
			treevo.setName(po.getOrgname());
			listOfCompanyTreeVO.add(treevo);
		}

		if (null == listOfCompanyTreeVO) {
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			return null;
		}

		JSON jp = JSONSerializer.toJSON(listOfCompanyTreeVO, config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));
	}
	
	
}