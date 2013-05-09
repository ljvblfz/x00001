package com.founder.sipbus.fwk.groovy.groovytmpl.hrs;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.founder.sipbus.fwk.groovy.annotation.Description;

public class ReflectionMethodFieldUtility {

	
	public static void main(String[] args){
		
		Method[] methods=EmployeeSalaryCalcFunction.class.getDeclaredMethods();
		for(Method method:methods){
			System.out.println("method name--->"+method.getName());
			Description  description=method.getAnnotation(Description.class);
			System.out.println("method desc---->"+description.desc());
		}
		

		Field[] fileds=EmployeeSalaryCalcVariable.class.getDeclaredFields();
		for(Field field:fileds){
			System.out.println("field--->"+field.getName());
		}
		
		
		
	}
}
