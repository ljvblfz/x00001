/**
 * project:odyssey
 * 
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 * 
 */
package com.founder.odyssey.generator.provider;

import com.founder.odyssey.generator.IModelProvider;
import com.founder.odyssey.generator.model.IModel;

/**
 * 
 * @author cw peng
 * @email peng_chuanwei@founder.com
 */
public class JavaMethodProvider implements IModelProvider {

	private String methodName;
	private Class clazz;

	public JavaMethodProvider(Class clazz, String methodName) {
		super();
		this.clazz = clazz;
		this.methodName = methodName;
	}

	public IModel getModel() {
		return JavaModelHelper.getInstance()
				.createJavaMethod(clazz, methodName);
	}

}
