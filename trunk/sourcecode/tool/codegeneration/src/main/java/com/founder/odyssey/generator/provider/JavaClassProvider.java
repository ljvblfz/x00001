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
public class JavaClassProvider implements IModelProvider {

	private Class clazz;

	public JavaClassProvider(Class clazz) {
		super();
		this.clazz = clazz;
	}

	public IModel getModel() {
		return JavaModelHelper.getInstance().createJaveClass(clazz);
	}

}
