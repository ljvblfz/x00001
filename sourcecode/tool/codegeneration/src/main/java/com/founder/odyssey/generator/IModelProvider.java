/**
 * project:odyssey
 * 
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 * 
 * 
 */
package com.founder.odyssey.generator;

import com.founder.odyssey.generator.model.IModel;

/**
 * 提供template数据模型
 * 
 * @author cw peng
 * @email peng_chuanwei@founder.com
 */
public interface IModelProvider {

	public IModel getModel() throws Exception;

}
