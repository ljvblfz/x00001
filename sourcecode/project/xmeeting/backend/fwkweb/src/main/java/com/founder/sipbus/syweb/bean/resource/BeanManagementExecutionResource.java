package com.founder.sipbus.syweb.bean.resource;

import java.lang.reflect.Method;

import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.ApplicationContextUtil;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

@Component
@Scope(value = "prototype")
@RestletResource(urls = "/bean/beanmgmtexecution/{beanname}/{methodname}")
public class BeanManagementExecutionResource extends SyBaseResource {
	
	private String beanname="";
	private String methodname="";

	@Override
	protected void doInit() throws ResourceException { 
		this.beanname=this.getAttribute("beanname");
		this.methodname=this.getAttribute("methodname");
		
	}

	@Post
	public Representation post(Representation entity)  {
		ApplicationContext applicationContext=ApplicationContextUtil.getCurrentWebApplicationContext();
 
		Object object=applicationContext.getBean(beanname); 
		Class<?>[] parameterTypes={com.founder.sipbus.fwk.groovy.execution.ScriptExecutionContext.class};
		invokeMethod(object, methodname,parameterTypes,null);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(null));
	}
	
	public static Object invokeMethod(final Object object, final String methodName, final Class<?>[] parameterTypes,
			final Object[] parameters) {
		Method method = getDeclaredMethod(object, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
		}

		method.setAccessible(true);

		try {
			return method.invoke(object, parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
		Assert.notNull(object, "object不能为空"); 
		for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredMethod(methodName, parameterTypes);
			} catch (NoSuchMethodException e) {//NOSONAR
				// Method不在当前类定义,继续向上转型
			}
		}
		return null;
	}

}
