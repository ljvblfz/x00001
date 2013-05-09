package com.founder.sipbus.fwk.groovy.test;

import static org.testng.AssertJUnit.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.founder.sipbus.fwk.groovy.groovytmpl.hrs.KpiAndParamSupport;

public class KpiAndParamSupportTest extends BaseGroovyTest{

	@Autowired
	private KpiAndParamSupport kpiAndParamSupport;

	
	 

	@BeforeClass
	public void setup() { 
		assertNotNull(kpiAndParamSupport);
	}
	
	@Test
	public void testOne(){ 
	}
 
}
