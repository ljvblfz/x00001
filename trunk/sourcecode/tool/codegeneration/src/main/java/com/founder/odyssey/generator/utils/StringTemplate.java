/**
 * project:odyssey
 * 
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 * 
 */
package com.founder.odyssey.generator.utils;

import java.util.Iterator;
import java.util.Map;

/**
 * 生成模板文件路径
 * @author cw peng
 * @email peng_chuanwei@founder.com
 */
public class StringTemplate {

	private String str;
	private Map params;
	
	public StringTemplate(String str, Map params) {
		this.str = str;
		this.params = params;
	}

	public String toString() {
		String  result = str;
		for(Iterator it = params.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry entry = (Map.Entry)it.next();
			String key = (String)entry.getKey();
			Object value = entry.getValue();
			String strValue = value == null ? "" : value.toString();
			result = StringHelper.replace(result, "${"+key+"}", strValue);
		}
		return result;
	}
	
	
}
