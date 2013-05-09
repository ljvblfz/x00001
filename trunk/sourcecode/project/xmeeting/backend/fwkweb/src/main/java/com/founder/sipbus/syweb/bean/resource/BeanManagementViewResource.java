package com.founder.sipbus.syweb.bean.resource;

import java.lang.reflect.Method;

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
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value = "prototype")
@RestletResource(urls = "/bean/beanmgmtview/{beanname}")
public class BeanManagementViewResource extends SyBaseResource {
	
	private String beanname="";

	@Override
	protected void doInit() throws ResourceException { 
		this.beanname=this.getAttribute("beanname");
		
	}

	@Get
	public Representation get(Representation entity) throws Exception {
		ApplicationContext applicationContext=ApplicationContextUtil.getCurrentWebApplicationContext();
 
		Object object=applicationContext.getBean(beanname); 
		
		System.out.println("script object class name:  "+object.getClass());
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("clazzName", object.getClass().getName()); 
		
		Method[] methods=object.getClass().getDeclaredMethods();
		
		JSONArray methodNameArray=new JSONArray();
		for(Method method:methods){
			String methodName=method.getName();
			methodNameArray.add(methodName);
		}
		jsonObject.put("methodName", methodNameArray);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jsonObject));
	}

}
