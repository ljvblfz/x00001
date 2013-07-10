/**
 * project:odyssey
 * 
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 * 
 */
package com.founder.odyssey.generator.model.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.founder.odyssey.generator.model.IModel;

/**
 * 
 * @author cw peng
 * @email peng_chuanwei@founder.com
 */
public class JavaClass implements IModel{

	private String className;
	private String packageName;
	private String superclassName;

	
	

	private List<JavaClassField> fields = new ArrayList<JavaClassField>();
	private List<JavaMethod> methods = new ArrayList<JavaMethod>();

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<JavaClassField> getFields() {
		return fields;
	}

	public void setFields(List<JavaClassField> fields) {
		this.fields = fields;
	}

	public String getSuperclassName() {
		return superclassName;
	}

	public void setSuperclassName(String superclassName) {
		this.superclassName = superclassName;
	}

	public List<JavaMethod> getMethods() {
		return methods;
	}

	public void setMethods(List<JavaMethod> methods) {
		this.methods = methods;
	}
	
	public String getPackagePath(){
		return getPackageName().replace(".", "/");
	}

	public String getDelegateClassName() {
		return className+"Delegate";
	}

	public String getDisplayDescription() {
		return "javaclass:"+getClassName();
	}

	public String getTemplateModelName() {
		return "clazz";
	}

	public void mergeFilePathModel(Map fileModel) throws Exception {
		fileModel.putAll(BeanUtils.describe(this));
	}

	public String getParentPackageName() {
		return packageName.substring(0,packageName.lastIndexOf("."));
	}

	public String getParentPackagePath() {
		return getParentPackageName().replace(".", "/");
	}
	
}
