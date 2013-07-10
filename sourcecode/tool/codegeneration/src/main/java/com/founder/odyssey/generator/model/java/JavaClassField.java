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
public class JavaClassField {
	private JavaClass classDto;
	private String javaType;
	private String fieldName;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public JavaClass getClassDto() {
		return classDto;
	}

	public void setClassDto(JavaClass classDto) {
		this.classDto = classDto;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getAsType() {
		return JavaDataTypesUtils.getPreferredAsType(javaType);
	}

	public boolean getIsDateTimeField() {
		return javaType.equalsIgnoreCase("java.sql.Date")
				|| javaType.equalsIgnoreCase("java.util.Date");
	}

}
