/**
 * project:OdysseyGenerator
 *
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 * 
 */
package com.founder.odyssey.generator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import com.founder.odyssey.generator.model.db.Table;
import com.founder.odyssey.generator.provider.DbModelHelper;
import com.founder.odyssey.generator.provider.DbTableProvider;
import com.founder.odyssey.generator.provider.JavaClassProvider;
import com.founder.odyssey.generator.provider.JavaMethodProvider;

/**
 * @author cw peng
 * @email peng_chuanwei@founder.com
 */
public class OdysseyGenerator {
//	private String templateBaseDir="E:/03-FounderWorking/SVN_HOME/FounderIPTS/02 Products/05 Tool/codegeneration/src/main/configuration/template";

	private String templateBaseDir=Constant.templateBaseDir;
	
	Generator g = new Generator();
	public void clean() throws IOException{
		g.clean();
	}
	private String subpackage="";
	private String thirdpackage="";
	
	public void generateCRUDByTable(String tableName,String subpackage,String thirdpackage) throws Exception {
		
		g.templateRootDir = new File(templateBaseDir+"/crud").getAbsoluteFile();
		g.modelProvider = new DbTableProvider(tableName);
		g.tableName=tableName;
		g.generateByModelProvider(subpackage,thirdpackage);
	}

	public void generateCRUDByTable(String tableName,String subpackage,String thirdpackage,String domain) throws Exception {
		
		g.templateRootDir = new File(templateBaseDir+"/crud").getAbsoluteFile();
		g.modelProvider = new DbTableProvider(tableName);
		g.tableName=tableName;
		g.generateByModelProvider(subpackage,thirdpackage,domain);
	}
	
	
	public void generateCRUDByAllTable() throws Exception {
		g.templateRootDir = new File(templateBaseDir+"/crud").getAbsoluteFile();
		List<Table> allTable = DbModelHelper.getInstance().getAllTables();
		for(Table t : allTable) {
			g.modelProvider = new DbTableProvider(t.getSqlName());
			g.generateByModelProvider();
		}
	}

	public void generateFlexDtoFormGridByJavaClass(Class clazz)
			throws Exception {
		generateFlexDtoByJavaClass(clazz);
		generateFlexFormByJavaClass(clazz);
		generateFlexGridByJavaClass(clazz);
	}

	public void generateFlexDtoByJavaClass(Class clazz) throws Exception {
		g.templateRootDir = new File(templateBaseDir+"/flexDto").getAbsoluteFile();
		g.modelProvider = new JavaClassProvider(clazz);
		g.generateByModelProvider();
	}

	public void generateFlexFormByJavaClass(Class clazz) throws Exception {
		g.templateRootDir = new File(templateBaseDir+"/flexForm").getAbsoluteFile();
		g.modelProvider = new JavaClassProvider(clazz);
		g.generateByModelProvider();
	}

	public void generateFlexGridByJavaClass(Class clazz) throws Exception {
		g.templateRootDir = new File(templateBaseDir+"/flexGrid").getAbsoluteFile();
		g.modelProvider = new JavaClassProvider(clazz);
		g.generateByModelProvider();
	}
	
	public void generateCairngormDelegateByJavaClass(Class clazz) throws Exception {
		g.templateRootDir = new File(templateBaseDir+"/cairngormDelegate").getAbsoluteFile();
		g.modelProvider = new JavaClassProvider(clazz);
		g.generateByModelProvider();
	}

	public void generateCairngormCommandAndEventByJavaMethod(Class clazz, String methodName)
			throws Exception {
		g.templateRootDir = new File("template/cairngormCommandAndEvent").getAbsoluteFile();
		g.modelProvider = new JavaMethodProvider(clazz,methodName);
		g.generateByModelProvider();
	}

	public void generateCairngormByAllJavaMethod(Class clazz) throws Exception {
		generateCairngormDelegateByJavaClass(clazz);
		for (Method method : clazz.getDeclaredMethods()) {
			generateCairngormCommandAndEventByJavaMethod(clazz,method.getName());
		}
	}
}
