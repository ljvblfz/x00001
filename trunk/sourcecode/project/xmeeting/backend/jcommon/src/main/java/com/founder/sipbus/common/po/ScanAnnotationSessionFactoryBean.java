/*
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 */
package com.founder.sipbus.common.po;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 
 * 自动扫描Entiy，不需要在hibernate里配置
 * 
 * @author cw peng
 */
@SuppressWarnings("unchecked")
public class ScanAnnotationSessionFactoryBean
		extends
		org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean {
	public ScanAnnotationSessionFactoryBean() {
	}

	private List scanAnnotatedClasses = new ArrayList();

	public void setAnnotatedScanPackages(String[] packages) {
		for (String packageName : packages) {
			scanAnnotatedClasses.addAll(getClasses(packageName));
		}
	}

	@Override
	protected void postProcessAnnotationConfiguration(
			AnnotationConfiguration config) {
		for (int i = 0; i < this.scanAnnotatedClasses.size(); i++) {
			config.addAnnotatedClass((Class) scanAnnotatedClasses.get(i));
		}
	}

	private List getClasses(String packageName) {
		ResourcePatternResolver rl = new PathMatchingResourcePatternResolver();
		List result = new ArrayList();
		try {
			String packagePart = packageName.replace('.', '/');
			String classPattern = "classpath*:/" + packagePart + "/**/*.class";
			Resource[] resources = rl.getResources(classPattern);
			for (int i = 0; i < resources.length; i++) {
				Resource resource = resources[i];
				String fileName = resource.getURL().toString();
				String className = fileName.substring(
						fileName.indexOf(packagePart.substring(0, packagePart
								.indexOf('*'))),
						fileName.length() - ".class".length())
						.replace('/', '.');
				Class type = Class.forName(className);
				if (isEntityClass(type))
					result.add(type);
			}
		} catch (IOException e) {
			fatal(e);
			return null;
		} catch (ClassNotFoundException e) {
			fatal(e);
			return null;
		}
		return result;
	}

	private boolean isEntityClass(Class type) {
		if (type.isAnnotationPresent(Entity.class) && !type.isInterface()
				&& !isAbstract(type) && !type.isAnonymousClass()
				&& !type.isMemberClass()) {
			return true;
		}
		return false;
	}

	boolean isAbstract(Class type) {
		return (type.getModifiers() ^ Modifier.ABSTRACT) == 0;
	}

	private void fatal(Throwable e) {
		throw new IllegalStateException("scan hibernate annotation error", e);
	}

}
