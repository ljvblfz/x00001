package com.founder.sipbus.syweb.cck.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StreamRepresentation;
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
import com.founder.sipbus.syweb.cck.util.CckExcelUtil;

/**
 * 导出excel 查询数据部分同 {@link CckDgridBuilderResource}
 * urls = "/cck/dgrid/export/{ccktypeguid}/{filename}"
 * @author zjl
 *
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/cck/dgrid/export/{ccktypeguid}/{filename}")
public class CckDgridExportResource extends SyBaseResource {

	private String ccktypeguid = "";
	private SyCckService syCckService;
	private SyCodeService syCodeService;
	private List<Map> list;
	private StringBuilder sBuilder;
	private String cckName;
	public static String[] format = { "yyyy-MM-dd" };
	@Override
	protected void doInit() throws ResourceException {
		ccktypeguid = getAttribute("ccktypeguid");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Get
	public Representation get(Representation entity) {

		Map map = getQueryMap();
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
		List<Map<String, Object>> maplistList = (List<Map<String, Object>>) params
				.get("searchlist");
		if (null!=maplistList) {
			for (Map<String, Object> map2 : maplistList) {// 添加查询条件
				String value =(String) map.get(map2.get("name"));
				map2.put("value", value);
				 Object type= map2.get("isDate");
				if( StringUtils.isNotBlank(value)&& "true".equals(type) ){
				 
						try {
							Date date = DateUtils.parseDate(value,
									format);
							map2.put("value", date);
						} catch (ParseException e) {
							e.printStackTrace();
							map2.put("value", null);
							continue;
						}
				}
			}
			map.put("searchlist", maplistList);
		}
		List<Map<String, Object>> maplistList2 = (List<Map<String, Object>>) params
				.get("searchlist2");
		if (null!=maplistList) {
			for (Map<String, Object> map2 : maplistList2) {// 添加查询条件
				String value =(String) map.get(map2.get("name"));
				map2.put("value", value);
				 Object type= map2.get("isDate");
				if( StringUtils.isNotBlank(value)&& "true".equals(type) ){
				 
						try {
							Date date = DateUtils.parseDate(value,
									format);
							map2.put("value", date);
						} catch (ParseException e) {
							e.printStackTrace();
							map2.put("value", null);
							continue;
						}
				}
			}
			map.put("searchlist2", maplistList2);
		}
		
		
		map.put("authority", params.get("authority"));// 权限设置 1时只能查询用户自己创建的数据
		map.put("username", getLoginUser().getUsername());// 当前用户名
 
		map.put("deletemodecolumn", params.get("deletemodecolumn"));
		map.put("selectFields", params.get("selectFields"));
			list = syCckService.queryAll(map,  
				ccktypeguid);
	 
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
 
	String fields = (String) params.get("fieldsToShow");
	config.registerJsonValueProcessor(java.sql.Date.class,
			new DateJsonValueProcessor("yyyy-MM-dd"));
	JSONArray jp = (JSONArray) JSONSerializer.toJSON(fields, config);
	  sBuilder=new StringBuilder();
	for (  Object object : jp) {
		JSONObject jsonObject=(JSONObject) object;
		sBuilder.append(jsonObject.get("fieldLabel")).append(":").append(jsonObject.get("fieldColumn"));
		Object type=  jsonObject.get("fieldType");
		if ("4".equals(type)||"5".equals(type)) {
			sBuilder.append("__NAME");
		}
		sBuilder.append(",");
	}
	  cckName=(String) params.get("cckName");	
	  if (null==cckName) {
		  cckName=ccktypeguid;
	}
		return 	new StreamRepresentation(MediaType.APPLICATION_EXCEL) {
			
			@Override
			public void write(OutputStream outputstream) throws IOException {
				try {
				 
					CckExcelUtil.export(cckName, getTitleColumns(sBuilder.toString()), list,  outputstream);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			@Override
			public InputStream getStream() throws IOException {
				return null;
			}
		};

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
