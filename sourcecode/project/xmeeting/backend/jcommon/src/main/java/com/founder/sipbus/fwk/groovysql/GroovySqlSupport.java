package com.founder.sipbus.fwk.groovysql;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroovySqlSupport {

	private static Logger logger = LoggerFactory.getLogger(GroovySqlSupport.class);
	
	public static String parseGroovySql(String groovySql,Map<String,String> variables){
		ScriptEngineManager factory = new ScriptEngineManager();  
		ScriptEngine scriptEngine = factory.getEngineByName("groovy");//  
		if(null!=variables&&!variables.isEmpty()){
			Set<String> keySet=variables.keySet();
			Iterator<String> iterKey=keySet.iterator();
			while(iterKey.hasNext()){
				String key=iterKey.next();
				String value=variables.get(key);
				scriptEngine.put(key, value);
			} 
		}
		
		scriptEngine.put("util", new GroovySqlUtil());
		scriptEngine.put("param", variables);
		
		
		String sqlText="";
		try {
			Object obj=scriptEngine.eval(groovySql); 
			if(null!=obj){
				sqlText=obj.toString();
			} 
		} catch (ScriptException e) { 
			logger.error("parseGroovySql raise the error: {}",e);
		} 
		return sqlText;
	}
	
	
	
	public static void main(String[] args){ 
		
		String sql = testTwo();
		
		System.out.println("sql------>"+sql);
	}

	private static String testTwo() { 
		
		InputStream is=GroovySqlSupport.class.getResourceAsStream("/com/founder/sipbus/fwk/groovysql/testgroovy.txt");
		Map<String,String> variables=new HashMap<String,String>();
		variables.put("alarmtypeid", "1");
		String groovysql="";
		try {
			groovysql = IOUtils.toString(is);
		} catch (IOException e) { 
			e.printStackTrace();
		} finally{
			IOUtils.closeQuietly(is);
		}
		 
		String sql=parseGroovySql(groovysql, variables);
		return sql;
	}


	private static String testOne() {
		StringBuilder sb=new StringBuilder();
		sb.append(" if(util.isNotEmpty(alarmtypeid) ){");
		sb.append("     return \"select * from dev_alarminfo  where alarmtypeid='\"+alarmtypeid+\"' \" "); 
		sb.append(" }else{");
		sb.append("       ");  
		sb.append("     return \"select * from dev_alarminfo \" ");  
		sb.append(" }");
		Map<String,String> variables=new HashMap<String,String>();
		variables.put("alarmtypeid", "1");
		 
		String sql=parseGroovySql(sb.toString(), variables);
		return sql;
	}
}
