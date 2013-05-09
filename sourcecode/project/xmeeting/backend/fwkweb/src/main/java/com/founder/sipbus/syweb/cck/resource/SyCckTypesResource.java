/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */

package com.founder.sipbus.syweb.cck.resource;

import java.util.HashSet;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
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
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.service.SyCodeService;
import com.founder.sipbus.syweb.cck.dao.SyCckTypeDaoImpl;
import com.founder.sipbus.syweb.cck.po.SyCckType;
import com.founder.sipbus.syweb.cck.service.SyCckService;
import com.founder.sipbus.syweb.cck.vo.SyCckTypeSearchPVO;

/***
 * 多条 syCckType resource
 * urls = "/syCckType"
 * @author zjl
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/syCckType")
public class SyCckTypesResource extends SyBaseResource {
private static final String TREE="树状显示"; 
private static final String GRID="列表显示"; 
	private SyCodeService syCodeService;

	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}
	private SyCckTypeDaoImpl syCckTypeDao;
	private	SyCckService	syCckService;
	public SyCckService getSyCckService() {
		return syCckService;
	}

	public void setSyCckService(SyCckService syCckService) {
		this.syCckService = syCckService;
	}
	static {
		HashSet<String> orderFieldSet = new HashSet<String>(7);
		orderFieldSet.add("groupname");
		orderFieldSet.add("typetable");
		orderFieldSet.add("code");
		orderFieldSet.add("name");
		orderFieldSet.add("authority");
		orderFieldSet.add("description");
		orderFieldSet.add("status");
	}

	public void setSyCckTypeDao(SyCckTypeDaoImpl syCckTypeDao) {
		this.syCckTypeDao = syCckTypeDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity);
		SyCckTypeSearchPVO sVO = new SyCckTypeSearchPVO();
		PMGridCopyUtil.copyGridToDto(sVO, getQueryMap());
		PageResponse p = syCckTypeDao.findPage(getPageRequest(),
				fillDetachedCriteria(SyCckType.class, sVO));
	List<SyCckType> list = p.getList();
	for (SyCckType type : list) {
		type.setGroupnameString(syCodeService.getSyCodeName("1010",
				type.getGroupname()));
		type.setAuthorityString(syCodeService.getSyCodeName("1011",
				type.getAuthority()));
		if ("1".equals(type.getShowType())) {
			type.setShowTypeString(TREE);
		}else {
			type.setShowTypeString(GRID);

		}
	
	}
		JSON jp = JSONSerializer.toJSON(getPageResponse(p), config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));
	}

	@Delete
	public Representation delete(Representation entity) {
		form = new Form(entity);
		String idsStr = form.getFirstValue("sctGuids");
		String[] ids = idsStr.split(",");
		for (String id : ids) {
			syCckService.deleteType(id);
//			syCckTypeDao.delete(id);
		}
		for (String id : ids) {
			syCckService.clearCache(id);
		}
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}

	@Post
	public Representation post(Representation entity) throws ResourceException {

		form = new Form(entity);
		SyCckType syCckType = new SyCckType();
		PMGridCopyUtil.copyGridToDto(syCckType, form.getValuesMap());
	syCckType.setTypetable(StringUtils.replace(StringUtils.upperCase(syCckType.getTypetable()), " ", ""));	
	//过滤
//		syCckTypeDao.add(syCckType);
		String msg= null;//
		 
		syCckService.addCckType(syCckType);//TODO 新方式
		if (msg==null) {
			return getJsonGzipRepresentation(getDefaultAddReturnJson());
		} else {
			return getJsonGzipRepresentation(JsonUtils.genFailureReturnJson(msg, syCckType));
		}
		
	}
}