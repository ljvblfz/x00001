package com.founder.sipbus.syweb.au.base;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.ListOrderedMap;
import org.restlet.data.Encoding;
import org.restlet.data.Form;
import org.restlet.engine.application.EncodeRepresentation;
import org.restlet.representation.Representation;

import com.founder.sipbus.common.constant.SessionAttrConstant;
import com.founder.sipbus.common.resource.BaseResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.po.SysUser;
import com.founder.sipbus.syweb.au.util.ISSOManager;
import com.founder.sipbus.syweb.au.util.MemcachedUtil;
import com.founder.sipbus.syweb.au.util.SsoUtil;

public class SyBaseResource extends BaseResource {


	/**
	 * 获取登录者SysUser
	 * 
	 * @author founder 2012-3-12
	 * @return
	 */
	public SysUser getLoginUser() {
		return getSSOManager().getSysUser();
	}

	protected ISSOManager getSSOManager() {
		ISSOManager ssoManager = (ISSOManager) MemcachedUtil
				.getMemCachedClient().get(
						SsoUtil.getSSOManagerFromSession(getHttpRequest()));
		return ssoManager;
	}

	/**
	 * 获取当前所在域
	 * 
	 * @author founder 2012-3-12
	 * @return
	 */
	public String getCurrentDomain() {
		return (String) getHttpSession().getAttribute(
				SessionAttrConstant.DOMAIN);
	}

	protected Form form;

	public String getNavTabId() {
		return form.getFirstValue("navTabId");
	}

	public String getCallbackType() {
		return form.getFirstValue("callbackType");
	}

	public String getCallbackMethod() {
		return form.getFirstValue("callBackMethod");
	}

	protected EncodeRepresentation getJsonGzipRepresentation(JSON jo) {
		return new EncodeRepresentation(Encoding.GZIP,
				new com.founder.sipbus.common.represenation.JsonRepresentation(
						jo));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Map getQueryMap() {
		Map m = getHttpRequest().getParameterMap();
		Map vM = new HashMap();
		if (null != m) {
			Set s = m.keySet();
			Iterator i = s.iterator();
			String key;
			String value;
			while (i.hasNext()) {
				key = (String) i.next();
				value = getHttpRequest().getParameter(key);
				//如果包含% 则将%替换为%25进行转义
				if(value.indexOf("%")>=0)
				{
					value=value.replaceAll("%", "%25");
				}
				//替换结束
				try {
					//vM.put(key,
					//		value == null ? "" : new String(value
					//				.getBytes("ISO-8859-1"), "UTF-8"));
					
					
					vM.put(key, value == null ? "" : new String(URLDecoder.decode(value, "UTF8")));
				} catch (UnsupportedEncodingException e) {
				}
				//return URLDecoder.decode(val, "UTF8");
			}
		}
		return vM;

	}

	protected JSONObject getDefaultEditReturnJson() {
		return JsonUtils.genEditReturnJson(getNavTabId(), getCallbackType(),
				"", getCallbackMethod());
	}

	protected JSONObject getDefaultAddReturnJson() {
		
		return getDefaultEditReturnJson();
//		return JsonUtils.genReturnJson("200", "", getNavTabId(),
//				getCallbackType(), "");
	}

	protected JSONObject getDefaultReturnJson() {
		return JsonUtils.genReturnJson("200", "", "", "", "");
	}

	protected JSONObject getDefaultDeleteReturnJson() {
		return JsonUtils.genDeleteReturnJson(getCallbackMethod());
	}

	protected JSONObject getMessageReturnJson(String returnString) {
		return JsonUtils.genReturnJson("200", returnString, getNavTabId(),
				getCallbackType(), "", form.getFirstValue("callBackMethod"));
	}
	
	protected JSONObject getErrorMessageReturnJson(String returnString) {
		return JsonUtils.genReturnJson("300", returnString, getNavTabId(),
				getCallbackType(), "", form.getFirstValue("callBackMethod"));
	}

	public String getAttribute(String name) {
		try {
			String val = (String) getRequestAttributes().get(name);
			if (null != val)
				return URLDecoder.decode(val, "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public EncodeRepresentation getNotAllowedRepresentation(String str) {
		StringBuilder sb = new StringBuilder("不允许");
		sb.append(str);
		sb.append("操作");
		return getJsonGzipRepresentation(JsonUtils.genReturnJson("300",
				sb.toString(), "", "", ""));
	}

	public EncodeRepresentation getSuccessAndCloseCurrentRepresentation() {
		String callbackType = getCallbackType();
		if (callbackType == null) {
			callbackType = "closeCurrent";
		}
		return getJsonGzipRepresentation(JsonUtils.genReturnJson("200", "操作成功",
				"", getCallbackType(), "", getCallbackMethod()));
	}

	public EncodeRepresentation getSuccessRepresentation() {
		return getJsonGzipRepresentation(JsonUtils.genReturnJson("200", "操作成功",
				"", "", ""));
	}

	protected Representation getFailedRepresentation() {
		// TODO Auto-generated method stub
		return getJsonGzipRepresentation(JsonUtils.genReturnJson("300", "操作失败",
				"", "", ""));
	}

	public EncodeRepresentation getErrorRepresentation() {
		return getJsonGzipRepresentation(JsonUtils.genReturnJson("300", "操作失败",
				"", "", ""));
	}


	public ListOrderedMap getTitleColumns(String columns) {
		ListOrderedMap titleColumns = new ListOrderedMap();
		String[] column = columns.split(",");
		Map<String, String> m = new LinkedHashMap<String, String>();
		for (int i = 0; i < column.length; i++) {
			String[] names = column[i].split(":");
			m.put(names[0], names[1]);
		}
		titleColumns.putAll(m);
		return titleColumns;
	}
}
