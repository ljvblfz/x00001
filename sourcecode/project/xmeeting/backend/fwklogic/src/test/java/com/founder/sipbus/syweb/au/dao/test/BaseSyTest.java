package com.founder.sipbus.syweb.au.dao.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(locations = {
		"/spring/applicationContext-datasource.xml",
		"/spring/applicationContext-properties.xml",
		"/spring/applicationContext-common.xml",
		"/spring/applicationContext-resource.xml" })
public class BaseSyTest extends AbstractTestNGSpringContextTests{
 
	
}
