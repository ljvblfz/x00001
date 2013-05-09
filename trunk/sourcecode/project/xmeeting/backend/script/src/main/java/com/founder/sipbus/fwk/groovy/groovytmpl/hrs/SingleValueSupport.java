package com.founder.sipbus.fwk.groovy.groovytmpl.hrs;

import org.springframework.stereotype.Component;

import com.founder.sipbus.fwk.groovy.execution.ScriptExecutionContext;


/**
 * 薪酬计算公共函数
 * @author lu.zhen
 *
 */
@Component
public class SingleValueSupport  {

	

	protected String singleValue;
	
	//
	 


	
	
	public void process(final ScriptExecutionContext scriptExecutionContext){
		 if(null!=singleValue){
			 scriptExecutionContext.setReturnValue(singleValue);
		 }
	
	}//end of process
 
	 
	
	
	
}
