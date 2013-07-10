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
public class DbTableProvider implements IModelProvider {

	private String dbTableName;

	public DbTableProvider(String dbTableName) {
		super();
		this.dbTableName = dbTableName;
	}

	public IModel getModel() throws Exception {
		return DbModelHelper.getInstance().getTable(dbTableName);
	}

}
