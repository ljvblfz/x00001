/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.cck.resource;

import java.util.Date;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.cck.dao.SyCckTypeButtonDaoImpl;
import com.founder.sipbus.syweb.cck.dao.SyCckTypeDaoImpl;
import com.founder.sipbus.syweb.cck.po.SyCckType;
import com.founder.sipbus.syweb.cck.po.SyCckTypeButton;
import com.founder.sipbus.syweb.cck.service.SyCckService;

/***
 * 生成默认按钮
 * urls="/syCckTypeButtonCreateDefault/{sctGuid}"
 * @author zjl
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syCckTypeButtonCreateDefault/{sctGuid}")
public class SyCckTypeButtonsCreateDefaultResource extends SyBaseResource {
	
	private SyCckTypeButtonDaoImpl syCckTypeButtonDao;
	private SyCckTypeDaoImpl	syCckTypeDao;
	private SyCckService syCckService;
	public void setSyCckTypeButtonDao(SyCckTypeButtonDaoImpl syCckTypeButtonDao) {
		this.syCckTypeButtonDao = syCckTypeButtonDao;
	}
 
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		String sctGuid = getAttribute("sctGuid");
		createDefaultButton(sctGuid,getLoginUser().getUsername());
		JSON jp = JSONSerializer.toJSON(null,config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	/**
	 *	生成 多个 按钮
	 *  @param sctGuid
	 *  @param userName
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午5:13:42 
	 */
	public void createDefaultButton(String sctGuid, String userName) {
		SyCckType cckType = syCckTypeDao.findById(sctGuid);
		if (cckType!=null) {
			String type =cckType.getShowType();
			Date d=new Date();
				if ("1".equals(type)) {
					

					syCckTypeButtonDao.add(createButton("7","新增子节点",userName,d,sctGuid,0));
					syCckTypeButtonDao.add(createButton("8","新增根节点",userName,d,sctGuid,4));
				}else {
					syCckTypeButtonDao.add(createButton("1","新增",userName,d,sctGuid,0));
					syCckTypeButtonDao.add(createButton("10","导出Excel",userName,d,sctGuid,4));
				}	
				
				syCckTypeButtonDao.add(createButton("2","修改",userName,d,sctGuid,1));
				syCckTypeButtonDao.add(createButton("3","查看",userName,d,sctGuid,2));
				syCckTypeButtonDao.add(createButton("4","删除",userName,d,sctGuid,3));
		}
		syCckService.clearButtonCache(sctGuid); 
	}
	
	/**
	 *填充生成单个按钮的 相同属性
	 *  @param type
	 *  @param label
	 *  @param userName
	 *  @param d
	 *  @param sctGuid
	 *  @param sortNo
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午5:13:46 
	 */
	private SyCckTypeButton createButton(String type, String label,
			String userName, Date d,String sctGuid,int sortNo) {
		SyCckTypeButton button =new SyCckTypeButton();
		button.setButtonSortno(sortNo);
		button.setButtonType(type);
		button.setButtonLabel(label);
		button.setDelFlag(0);
		button.setCreateDt(d);
		button.setCreateBy(userName);
		button.setUpdateBy(userName);
		button.setUpdateDt(d);
		button.setSctGuid(sctGuid);
		return button;
	}

	public SyCckService getSyCckService() {
		return syCckService;
	}

	public void setSyCckService(SyCckService syCckService) {
		this.syCckService = syCckService;
	}

	public SyCckTypeDaoImpl getSyCckTypeDao() {
		return syCckTypeDao;
	}

	public void setSyCckTypeDao(SyCckTypeDaoImpl syCckTypeDao) {
		this.syCckTypeDao = syCckTypeDao;
	}
	
	 
}