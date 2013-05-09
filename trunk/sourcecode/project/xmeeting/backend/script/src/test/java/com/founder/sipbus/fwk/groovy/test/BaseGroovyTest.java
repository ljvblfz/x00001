package com.founder.sipbus.fwk.groovy.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
 


@ContextConfiguration(locations = {
		"/spring/applicationContext-datasource.xml",
		"/spring/applicationContext-properties.xml",
		"/spring/applicationContext-common.xml",
//		"/spring/applicationContext-resource.xml" ,
		"/spring/applicationContext-ibatis.xml",
		"/spring/applicationContext-scriptcache.xml"})
public class BaseGroovyTest extends AbstractTestNGSpringContextTests {
 
	
	@Test
	public void testOne(){}
}

