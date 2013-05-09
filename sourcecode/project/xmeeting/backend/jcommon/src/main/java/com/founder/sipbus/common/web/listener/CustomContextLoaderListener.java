package com.founder.sipbus.common.web.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

public class CustomContextLoaderListener extends ContextLoaderListener{
	public void contextInitialized(ServletContextEvent event) {  
		super.contextInitialized(event);   
		WebApplicationContext wac = getCurrentWebApplicationContext();
		//
		GenericWebApplicationContext genericWac = new GenericWebApplicationContext();
		genericWac.setParent(wac);  
		event.getServletContext().setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, genericWac);
	}
}
