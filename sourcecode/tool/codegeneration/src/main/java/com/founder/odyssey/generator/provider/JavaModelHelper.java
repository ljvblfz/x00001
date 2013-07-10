/**
 * project:odyssey
 * 
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 * 
 */
package com.founder.odyssey.generator.provider;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.founder.odyssey.generator.model.java.JavaClass;
import com.founder.odyssey.generator.model.java.JavaClassField;
import com.founder.odyssey.generator.model.java.JavaMethod;
import com.founder.odyssey.generator.model.java.MethodParameter;

/**
 * java反射，读取类的信息
 * @author cw peng
 * @email peng_chuanwei@founder.com
 */
public class JavaModelHelper {
	private static JavaModelHelper instance = new JavaModelHelper();

	private JavaModelHelper() {
	}

	public static JavaModelHelper getInstance() {
		return instance;
	}

	public JavaClass createJaveClass(Class cl) {
		JavaClass javaClass = new JavaClass();
		javaClass.setClassName(cl.getSimpleName());
		javaClass.setPackageName(cl.getPackage().getName());
		if (cl.getSuperclass() != null) {
			javaClass.setSuperclassName(cl.getSuperclass().getName());
		}

		// ����
		List<JavaClassField> javaClassFields = getJaveClassFields(javaClass, cl);
		javaClass.setFields(javaClassFields);

		// ����
		List<JavaMethod> javaMethods = getJaveClassMethod(javaClass, cl);
		javaClass.setMethods(javaMethods);

		return javaClass;
	}

	public JavaMethod createJavaMethod(Class cl, String method) {
		JavaClass clazz = createJaveClass(cl);
		for (JavaMethod m : clazz.getMethods()) {
			if (m.getMethodName().equals(method)) {
				return m;
			}
		}
		throw new RuntimeException("not found method:" + method);
	}

	private List<JavaClassField> getJaveClassFields(JavaClass javaClass,
			Class pojo) {
		List<JavaClassField> javaClassFields = new ArrayList<JavaClassField>();
		Field[] fields = pojo.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			JavaClassField javaClassField = new JavaClassField();
			javaClassField.setClassDto(javaClass);
			javaClassField.setFieldName(f.getName());
			javaClassField.setJavaType(f.getType().getName());

			javaClassFields.add(javaClassField);

		}
		return javaClassFields;
	}

	private List<JavaMethod> getJaveClassMethod(JavaClass javaClass, Class cl) {
		List<JavaMethod> javaMethods = new ArrayList<JavaMethod>();
		Method[] methods = cl.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];

			JavaMethod javaMethod = new JavaMethod();
			javaMethod.setMethodName(method.getName());
			javaMethod.setClazz(javaClass);
			// ����ֵ
			MethodParameter returnValue = new MethodParameter();
			returnValue.setMethod(javaMethod);
			returnValue.setName("returnValue");
			returnValue.setJavaType(method.getReturnType().getName());
			javaMethod.setReturnType(returnValue);

			// ����
			List<MethodParameter> parameters = getJavaClassField(javaMethod,
					method);
			javaMethod.setParameters(parameters);

			javaMethods.add(javaMethod);

		}
		return javaMethods;
	}

	private List<MethodParameter> getJavaClassField(JavaMethod javaMethod,
			Method method) {
		List<MethodParameter> javaClassFields = new ArrayList<MethodParameter>();

		Class[] parameterTypes = method.getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++) {
			Class cl = parameterTypes[i];

			MethodParameter methodParameter = new MethodParameter();
			methodParameter.setMethod(javaMethod);
			methodParameter.setJavaType(cl.getName());
			methodParameter.setName("param" + i);
			javaClassFields.add(methodParameter);

		}
		return javaClassFields;
	}

}
