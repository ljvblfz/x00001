package com.founder.sipbus.fwk.config;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.founder.sipbus.fwk.config.Environment;

@ContextConfiguration(locations = {
		"/spring/applicationContext-properties.xml"  })
public class BaseConfigTest extends AbstractTestNGSpringContextTests {
	
	@Test
	public void testEnvConfig(){
		
		System.out.println(Environment.getInstance().getString("test"));
	}
 
}
