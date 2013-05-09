package com.founder.sipbus.common.util;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.po.AbstractEntity;

public class JsonUtils {
	
	private static String STATUS_CODE_OK = "200";
	
	private static String STATUS_CODE_ERROR = "300";
	
	private static String STATUS_CODE_TIMEOUT = "301";
	
	/**  
	 * 新增或修改时，后台返回给前台指定json格式数据，DWZ框架做出相应操作
	 * 
	 * @param statusCode String
	 *        状态码  必须输入 200 表示操作成功 300表示操作失败 301表示session超时
	 * @param message    String
	 * 		  提示信息 可为空
	 * @param navTabId
	 *        tab的id值 把指定navTab页面标记为需要"重新载入"reloadFlag=1，下次切换到那个navTab时会重新载入内容。
	 *        注意navTabId 不能是当前navTab页面的 
	 * @param callbackType
	 * 		  回调操作类型 callbackType="closeCurrent"关闭当前tab callbackType="forward"需要forwardUrl值。
	 * @param forwordUrl
	 * 		  DWZ框架重载forwordUrl
	 * 
	 */ 
	
	public static JSONObject genReturnJson(String statusCode,String message,String navTabId,
			String callbackType,String forwordUrl) {
		return genReturnJson(statusCode,message,navTabId,
			callbackType,forwordUrl,null);
	}
	
	public static JSONObject genReturnJson(String statusCode,String message,String navTabId,
			String callbackType,String forwordUrl,String callbackmethod) {
		JSONObject jo = new JSONObject();
		jo.accumulate("statusCode", statusCode);
		if(StringUtils.isBlank(message)) {
			if(STATUS_CODE_OK.equals(statusCode)) {
				jo.accumulate("message", "操作成功");
			}else if(STATUS_CODE_ERROR.equals(statusCode)) {
				jo.accumulate("message", "操作失败");
			}else if(STATUS_CODE_TIMEOUT.equals(statusCode)) {
				jo.accumulate("message", "会话超时");
			}
		}else {
			jo.accumulate("message", message);
		}
		jo.accumulate("navTabId", navTabId);
		jo.accumulate("forwordUrl", forwordUrl);
		jo.accumulate("callbackType", callbackType);
		if(null!=callbackmethod)
			jo.accumulate("callBackMethod", callbackmethod);
		return jo;
	}
	
	public static JSONObject genReturnJson(AbstractEntity entity){
		
		return null;
	}

	
	public static JSONObject genReturnJson(PageResponse pageResponse){
		
		return null;
	}
	
	/**
	 * 通用操作，后台返回给前台指定json格式数据，DWZ框架做出相应操作
	 * @param statusCode
	 * 			状态码  必须输入 200 表示操作成功 300表示操作失败 301表示session超时
	 * @param message
	 * 			提示信息 可为空
	 * @param navTabId
	 * 			tab的id值 把指定navTab页面标记为需要"重新载入"reloadFlag=1，下次切换到那个navTab时会重新载入内容。
	 *          注意navTabId 不能是当前navTab页面的
	 * @param callbackType
	 * 			回调操作类型 callbackType="closeCurrent"关闭当前tab callbackType="forward"需要forwardUrl值。
	 * @param forwordUrl
	 * 			DWZ框架重载forwordUrl
	 * @param jsonData
	 * 			操作返回数据
	 * @return
	 */
	public static JSONObject genReturnJson(String statusCode,String message,String navTabId,
			String callbackType,String forwordUrl,Object jsonData) {
		JSONObject jo = genReturnJson(statusCode,message,navTabId,callbackType,forwordUrl);
		jo.accumulate("jsonData", jsonData);
		
		return jo;
	}
	
	public static JSONObject genEditReturnJson(String navTabId,
			String callbackType,String forwordUrl,String callBackMethod) {
		JSONObject jo = genReturnJson("200","",navTabId,callbackType,forwordUrl);
		jo.accumulate("callBackMethod", callBackMethod);
		
		return jo;
	}
	
	
	public static JSONObject genSuccessReturnJson(Object jsonData) {
		return genReturnJson("200", "", "", "", "", jsonData);
	}
	
	public static JSONObject genSuccessReturnJson(String successMessage,Object jsonData) {
		return genReturnJson("200", successMessage, "", "", "", jsonData);
	}
	
	public static JSONObject genFailureReturnJson(String failMessage,Object jsonData) {
		return genReturnJson("300", failMessage, "", "", "", jsonData);
	}
	
	/**
	 * 回调方法操作，后台返回给前台指定json格式数据，DWZ框架做出相应操作
	 * @param statusCode
	 * 			状态码  必须输入 200 表示操作成功 300表示操作失败 301表示session超时
	 * @param message
	 * 			提示信息 可为空
	 * @param navTabId
	 * 			tab的id值 把指定navTab页面标记为需要"重新载入"reloadFlag=1，下次切换到那个navTab时会重新载入内容。
	 *          注意navTabId 不能是当前navTab页面的
	 * @param callbackType
	 * 			回调操作类型 callbackType="closeCurrent"关闭当前tab callbackType="forward"需要forwardUrl值。
	 * @param forwordUrl
	 * 			DWZ框架重载forwordUrl
	 * @param callBackMethod
	 * 			回调方法
	 * @return
	 */
	public static JSONObject genDeleteReturnJson(String navTabId,
			String callbackType,String forwordUrl,String callBackMethod) {
		JSONObject jo = genReturnJson("200","删除成功",navTabId,callbackType,forwordUrl);
		jo.accumulate("callBackMethod", callBackMethod);
		
		return jo;
	}
	

	public static JSONObject genDeleteReturnJson(String callBackMethod) {
		
		return genDeleteReturnJson("","","",callBackMethod);
	}
}
