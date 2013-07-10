/**
 * project:odyssey
 * 
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 * 
 */
package com.founder.odyssey.generator.model;

import java.util.Map;

/**
 * 
 * template数据模型
 * 
 * @author cw peng
 * @email peng_chuanwei@founder.com
 */
public interface IModel {

	/**
	 * model描述
	 * 
	 * @return
	 */
	public String getDisplayDescription();

	/**
	 * 为模板路径提供数据
	 * 
	 * @param fileModel
	 * @throws Exception
	 */
	public void mergeFilePathModel(Map fileModel) throws Exception;

	/**
	 * 数据模型描述
	 * 
	 * @return
	 */
	public String getTemplateModelName();

}
