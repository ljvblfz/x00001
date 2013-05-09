package com.founder.sipbus.syweb.bean.resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.ApplicationContextUtil;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.fwk.groovy.execution.IScriptExecution;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value = "prototype")
@RestletResource(urls = "/bean/beanmgmtlist")
public class BeanManagementListResource extends SyBaseResource {

	@Override
	protected void doInit() throws ResourceException { 
		
	}

	@Get
	public Representation get(Representation entity) throws Exception {
		ApplicationContext applicationContext=ApplicationContextUtil.getCurrentWebApplicationContext();
		int beanDefinitionCount=applicationContext.getBeanDefinitionCount();
//		String[] beanDefinitionNames=applicationContext.getBeanDefinitionNames();  
		String[] beanDefinitionNames=applicationContext.getBeanNamesForType(IScriptExecution.class);
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("beanDefinitionCount", beanDefinitionCount);
		JSONArray jsonArray=new JSONArray();
		for(String beanName:beanDefinitionNames){
			jsonArray.add(beanName);
		}
		jsonObject.put("beanDefinitionNameList", jsonArray);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jsonObject));
	}

}
