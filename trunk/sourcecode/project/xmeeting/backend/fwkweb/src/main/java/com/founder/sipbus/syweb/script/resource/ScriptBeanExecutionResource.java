package com.founder.sipbus.syweb.script.resource;

import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.exception.FounderException;
import com.founder.sipbus.common.util.ApplicationContextUtil;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.fwk.groovy.execution.IScriptExecution;
import com.founder.sipbus.fwk.groovy.execution.ScriptExecutionContext;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value = "prototype")
@RestletResource(urls = "/script/beanexecution/{beanname}")
public class ScriptBeanExecutionResource extends SyBaseResource {
	
	private String beanname=""; 

	@Override
	protected void doInit() throws ResourceException { 
		this.beanname=this.getAttribute("beanname");  
	}

	@Post
	public Representation post(Representation entity)  {
		ApplicationContext applicationContext=ApplicationContextUtil.getCurrentWebApplicationContext(); 
		Object object=applicationContext.getBean(beanname);   
		if(object instanceof IScriptExecution){ 
			IScriptExecution scriptExecution=(IScriptExecution)object;
			ScriptExecutionContext scriptExecutionContext=new ScriptExecutionContext();
			scriptExecution.execute(scriptExecutionContext);
			String retValue=scriptExecutionContext.getReturnValue(); 
			return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(retValue)); 
		} else{
			throw new RuntimeException("脚本["+beanname+"]没有implements 接口IScriptExecution.");
		} 
	}
	 

}
