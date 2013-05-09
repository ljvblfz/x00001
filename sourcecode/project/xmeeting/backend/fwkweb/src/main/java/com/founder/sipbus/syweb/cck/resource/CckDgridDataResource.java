package com.founder.sipbus.syweb.cck.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.memcache.util.StdMemCacheUtil;
import com.founder.sipbus.common.util.DateJsonValueProcessor;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.service.SyCodeService;
import com.founder.sipbus.syweb.cck.service.SyCckService;

/**
 * 未使用
 * cck内容数据
 * urls = "/cck/cckContentData/{ccktypeguid}"
 * @author zjl
 *
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/cck/cckContentData/{ccktypeguid}")
public class CckDgridDataResource extends SyBaseResource {

	private String ccktypeguid = "";
	private SyCckService syCckService;
	private SyCodeService syCodeService;

	@Override
	protected void doInit() throws ResourceException {
		ccktypeguid = getAttribute("ccktypeguid");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Get
	public Representation get(Representation entity) {

		Map map = getQueryMap();
		//TODO a 删除缓存
//		StdMemCacheUtil.getMemCachedClient().delete(SyCckService.KEY_GEN_PARAMS + ccktypeguid);

		Map params = (Map) StdMemCacheUtil.getMemCachedClient().get(
				SyCckService.KEY_GEN_PARAMS + ccktypeguid);
		if (null == params) {
			params = new HashMap<String, Object>(); 
			if (!syCckService.createParamsForList(params, ccktypeguid)) {
				setStatus(Status.CLIENT_ERROR_NOT_FOUND);
				return getJsonGzipRepresentation(JsonUtils
						.genFailureReturnJson("未找到记录！", null));
			}
		}
		map.put("sctGuidColumn", params.get("sctGuidColumn"));
		map.put("masterIdColumn", params.get("masterIdColumn"));
		map.put("createByColumn", params.get("createByColumn"));
		map.put("whereSql", params.get("whereSql"));
		map.put("fromSql", params.get("fromSql"));
		map.put("selectFields", params.get("selectFields"));
		map.put("sctGuid", ccktypeguid);
 
		map.put("tableName", params.get("tableName"));
		map.put("defaultOrderBySql", params.get("defaultOrderBySql"));
//	if (false)  	{
//			List<Map<String, String>> maplistList = (List<Map<String, String>>) params
//					.get("searchlist");
//			if (null!=maplistList) {
//				for (Map<String, String> map2 : maplistList) {// 添加查询条件
//					map2.put("value", (String) map.get(map2.get("name")));
//				}
//				map.put("searchlist", maplistList);
//			}
//		}
		
		
		map.put("authority", params.get("authority"));// 权限设置 1时只能查询用户自己创建的数据
		map.put("username", getLoginUser().getUsername());// 当前用户名
 
		map.put("deletemodecolumn", params.get("deletemodecolumn"));
		map.put("selectFields", params.get("selectFields"));
		//TODO del_flag条件
		List<Map> list = syCckService.queryAll(  map,
				ccktypeguid);
//		List<Map> list = p.getList();
		List<JSONObject> codeFields = (List<JSONObject>) params
				.get("codeFields");
		for (Map object : list) {
			String column = "";
			for (JSONObject codefield : codeFields) {// 转换码表字段
				column = (String) codefield.get("fieldColumn");
				Object value= object.get(column);
				if (null==value) {
					value="";
				} 
				object.put(column+"__NAME", syCodeService.getSyCodeName(
						(String) codefield.get("fieldTypeReference"),
						 value.toString()));
				 
			}

		}
		config.registerJsonValueProcessor(java.sql.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));
		JSON jp = JSONSerializer.toJSON(list, config);

		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp) );

	}

	public SyCckService getSyCckService() {
		return syCckService;
	}

	public void setSyCckService(SyCckService syCckService) {
		this.syCckService = syCckService;
	}

	public SyCodeService getSyCodeService() {
		return syCodeService;
	}

	public void setSyCodeService(SyCodeService syCodeService) {
		this.syCodeService = syCodeService;
	}

}
