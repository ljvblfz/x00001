/**
 * project:odyssey
 * 
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 * 
 */
package com.founder.odyssey.generator.model.java;

import com.founder.odyssey.generator.utils.JavaDataTypesUtils;

/**
 * 
 * @author cw peng
 * @email peng_chuanwei@founder.com
 */
public class MethodParameter {
	private JavaMethod method;
	private String javaType;
	private String name;

	public JavaMethod getMethod() {
		return method;
	}

	public void setMethod(JavaMethod method) {
		this.method = method;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAsType() {
		return JavaDataTypesUtils.getPreferredAsType(javaType);
	}

}
