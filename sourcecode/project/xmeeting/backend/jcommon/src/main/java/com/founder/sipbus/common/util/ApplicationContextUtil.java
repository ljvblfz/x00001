/*
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Founder. You shall not disclose such Confidential Information
 * and shall use it only in accordance with the terms of the agreements
 * you entered into with Founder.
 */
package com.founder.sipbus.common.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * spring上下文加载工具类
 * 
 *
 * @version 1.0, 2010-12-15
 * @author Shang Junjie
 */
public class ApplicationContextUtil {
	/**
	 * 上下文
	 */
	public static ApplicationContext context;
	
	/**
	 * servlet上下文
	 */
	public static ApplicationContext servletContext;
	/**
	 * 加载配置文件
	 */
	static{
//		context = new ClassPathXmlApplicationContext(
//				new String[]{"spring/applicationContext-common.xml"
//						,"spring/applicationContext-resource.xml"
//				});
	}
	/**
	 * 获得bean
	 *
	 * @param beanId
	 * @return
	 * @author Shang Junjie
	 */
//	public static Object getBean(String beanId){
//		return context.getBean(beanId);
//	}
	/**
	 * 获得servlet上下文
	 *
	 * @param context
	 * @return
	 * @author Shang Junjie
	 */
//    public static ApplicationContext getApplicationContextFromServletContext(ServletContext servletContext) {
////        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
////        return wac;
//    	return context;
//    }
	
	
	public static ApplicationContext getCurrentWebApplicationContext(){
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext(); 
		return wac;
	}
	
    public static Object getBean(HttpServletRequest request,String beanName){
    	context=WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
    	return context.getBean(beanName);
    } 
    

    public static Object getBean(String beanName){
//    	context=WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
    	return getCurrentWebApplicationContext().getBean(beanName);
    } 
}
