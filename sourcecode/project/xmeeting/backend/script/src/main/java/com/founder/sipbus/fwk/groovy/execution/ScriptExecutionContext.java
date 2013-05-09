package com.founder.sipbus.fwk.groovy.execution;

import java.util.HashMap;
import java.util.Map;

public class ScriptExecutionContext { 
	
	public final static String RETURN_VALUE="RETURN_VALUE";
	public final static String SCRIPT_NAME="SCRIPT_NAME";
	public final static String EMPLOYEE_JOBNUMBER="EMPLOYEE_JOBNUMBER";
 
	private Map<String,String> context=new HashMap<String,String>();

	public Map<String, String> getContext() {
		return context;
	}

 
	
	
	public String getEmployeeJobNumber(){ 
		String obj=context.get(EMPLOYEE_JOBNUMBER);
		if(obj!=null){
			return obj;
		}
		return null;
	}
	
	public void setEmployeeJobNumber(String value){
		context.put(EMPLOYEE_JOBNUMBER, value);
	}
	
	//=====================================================
	public void setReturnValue(String value){
		context.put(RETURN_VALUE, value);
	}
	
	public String getReturnValue(){
		String obj=context.get(RETURN_VALUE);
		if(obj!=null){
			return obj;
		}
		return null;
	}
	
	//=====================================================
	public void setScriptName(String value){
		context.put(SCRIPT_NAME, value);
	}
	
	public String getScriptName(){
		String obj=context.get(SCRIPT_NAME);
		if(obj!=null){
			return obj;
		}
		return null;
	}
		
	
	
}
