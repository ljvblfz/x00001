/*
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 */
package com.founder.sipbus;

import org.restlet.ext.spring.SpringFinder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * 
 * 自动布署spring bean时，名字策略
 * @author Tracy chen
 * 
 */
public class SpringBeanNameGenerator extends DefaultBeanNameGenerator {

	@SuppressWarnings("unchecked")
	public String generateBeanName(BeanDefinition definition,
			BeanDefinitionRegistry registry) {
		try {
			Class clazz = Class.forName(definition.getBeanClassName());
			String simpleName = ClassUtils.getShortName(clazz);
			simpleName = StringUtils.uncapitalize(simpleName);

			if (simpleName.endsWith("Impl")) {
				simpleName = simpleName.substring(0, simpleName.length() - 4);
			} 
//			else {
//				simpleName = super.generateBeanName(definition, registry);
//			}
//			System.out.println ("===" + simpleName);
			return simpleName;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return super.generateBeanName(definition, registry);
		}

	}

}
