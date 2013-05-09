/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */

package com.founder.sipbus.syweb.cck.resource;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.restlet.data.Form;
import org.restlet.data.Status;
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
import com.founder.sipbus.syweb.cck.dao.SyCckTypeFieldDaoImpl;
import com.founder.sipbus.syweb.cck.po.SyCckTypeField;
import com.founder.sipbus.syweb.cck.service.SyCckService;
import com.founder.sipbus.syweb.cck.util.SyCckUtil;
import com.founder.sipbus.syweb.cck.vo.SyCckTypeFieldSearchPVO;

/***
 * 多条syCckTypeField 记录resource
 * urls = "/syCckType/{sctGuid}/syCckTypeField"
 * @author zjl
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/syCckType/{sctGuid}/syCckTypeField")
public class SyCckTypeFieldsResource extends SyBaseResource {

	private SyCckTypeFieldDaoImpl syCckTypeFieldDao;
	private SyCckService syCckService;
// 	private static final String TRUE = "\u221A";
//	private static final String FALSE = "×";
	private static HashSet<String> orderFieldSet = new HashSet<String>();
//	private static HashMap<String, String> fieldTypeMap = new HashMap<String, String>();

	static {

		orderFieldSet.add("fieldLabel");
		orderFieldSet.add("fieldColumn");
		orderFieldSet.add("fieldType");
		orderFieldSet.add("fieldIsrequired");
		orderFieldSet.add("fieldMaxlength");
		orderFieldSet.add("fieldIslistdisplay");
		orderFieldSet.add("fieldIsformdisplay");
		orderFieldSet.add("fieldListsortno");
		orderFieldSet.add("fieldFormposition");
		orderFieldSet.add("fieldDescription");
//TODO 更新 这些字段
//		fieldTypeMap.put("0", "请选择");
//		fieldTypeMap.put("1", "文本");
//		fieldTypeMap.put("2", "小数");
//		fieldTypeMap.put("3", "日期");
//		fieldTypeMap.put("4", "码表");
//		fieldTypeMap.put("5", "引用");
//		fieldTypeMap.put("6", "整数");
//		fieldTypeMap.put("7", "主键");
//		fieldTypeMap.put("8", "SCT_GUID");
//		fieldTypeMap.put("9", "CREATE_BY");
//		fieldTypeMap.put("10", "CREATE_DT");
//		fieldTypeMap.put("11", "UPDATE_BY");
//		fieldTypeMap.put("12", "UPDATE_DT");
//		fieldTypeMap.put("13", "删除标志");
//		fieldTypeMap.put("14", "上层ID");
	}
//	private static String[] isFields = { "fieldIsrequired",
//			"fieldIslistdisplay", "fieldIsformdisplay","fieldIssearchfield" };
//
//	private void changeCheckBox(Map map) {
//
//		for (String field : isFields) {
//			if (StringUtils.isNotBlank((String) map.get(field))) {
//				map.put(field, "1");
//			} else {
//				map.put(field, "0");
//			}
//
//		}
//	}

	public void setSyCckTypeFieldDao(SyCckTypeFieldDaoImpl syCckTypeFieldDao) {
		this.syCckTypeFieldDao = syCckTypeFieldDao;
	}

	@Get
	public Representation get(Representation entity) throws Exception {

		form = new Form(entity);
		String sctGuid = getAttribute("sctGuid");
		Map map = getQueryMap();
		 
		map.remove("sctGuid");
		SyCckTypeFieldSearchPVO sVO = new SyCckTypeFieldSearchPVO();
		PMGridCopyUtil.copyGridToDto(sVO, map);
		PageResponse<SyCckTypeField> p = syCckTypeFieldDao.findPage(
				getPageRequest(),
				fillDetachedCriteria(SyCckTypeField.class, sVO).add(
						Restrictions.eq("sctGuid", sctGuid)).addOrder(Order.asc("fieldListsortno"))); 
		List<SyCckTypeField> l = p.getList();
		for (SyCckTypeField syCckTypeField : l) {
			syCckTypeField.setFieldIsformdisplayString(SyCckUtil.getString(syCckTypeField
					.getFieldIsformdisplay()));
			syCckTypeField.setFieldIslistdisplayString(SyCckUtil.getString(syCckTypeField
					.getFieldIslistdisplay()));
			syCckTypeField.setFieldIsrequiredString(SyCckUtil.getString(syCckTypeField
					.getFieldIsrequired()));
			syCckTypeField.setFieldTypeString(SyCckUtil.fieldTypeMap.get(syCckTypeField.getFieldType()));
			syCckTypeField.setFieldIsuniqueString(SyCckUtil.getString(syCckTypeField.getFieldIsunique()));
			syCckTypeField.setFieldIssearchfieldString(SyCckUtil.getString(syCckTypeField.getFieldIssearchfield()));
			if ("0".equals(syCckTypeField.getOrderType())) {
				syCckTypeField.setOrderTypeString("ASC");
			} else if ("1".equals(syCckTypeField.getOrderType())) {
				syCckTypeField.setOrderTypeString("DESC");
			} 
		}
		JSON jp = JSONSerializer.toJSON(getPageResponse(p), config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));
	}

//	private String getString(String b) {
		// if (StringUtils.equals(b, "1")) {
		// return TRUE;
		// } else if (StringUtils.equals(b, "0")) {
		// return FALSE;
		// } else {
		// return null;
		// }
//		if (StringUtils.equals(b, "1")) {
//			return TRUE;
//		} else {
//			return FALSE;
//		}
//
//	}

	@Delete
	public Representation delete(Representation entity) {
		form = new Form(entity);
		String idsStr = form.getFirstValue("sctfGuids");
		String[] ids = idsStr.split(",");
		if (ids.length>0) {
			syCckService.clearCacheBySctfGuids(ids);
			for (String id : ids) {
				syCckTypeFieldDao.delete(id);
			}
		}
	
		return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
	}

	@Post
	public Representation post(Representation entity) throws ResourceException {

		form = new Form(entity);
		String sctGuid = getAttribute("sctGuid");
		if (StringUtils.isBlank(sctGuid)) {
			setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			return getJsonGzipRepresentation(getErrorMessageReturnJson("缺少参数"));
		}
		SyCckTypeField syCckTypeField = new SyCckTypeField();
		Map map = form.getValuesMap();
		SyCckUtil.changeCheckBox(map);
		map.put("sctGuid", sctGuid);
		map.put("fieldColumn", StringUtils.upperCase((String) map.get("fieldColumn")));
		map.put("fieldAlias", StringUtils.upperCase((String) map.get("fieldAlias")));
		
		String isUnique= map.get("fieldIsunique").toString();
		if ("1".equals(isUnique)) {
			map.put("fieldIsrequired", "1");
		}
		
		PMGridCopyUtil.copyGridToDto(syCckTypeField, map);
//TODO 检查alias等 fieldColumn
		syCckTypeFieldDao.add(syCckTypeField);
		syCckService.clearCache(sctGuid);
		return getJsonGzipRepresentation(getDefaultAddReturnJson().accumulate(
				"callBackMethod", getCallbackMethod()));
	}

	public SyCckService getSyCckService() {
		return syCckService;
	}

	public void setSyCckService(SyCckService syCckService) {
		this.syCckService = syCckService;
	}
}