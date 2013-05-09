package com.founder.sipbus.syweb.au.dao.test;

import static org.testng.AssertJUnit.assertNotNull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.founder.sipbus.syweb.au.dao.SyCodeDaoImpl;
import com.founder.sipbus.syweb.au.po.SyCode;
 
public class SyCodeDaoImplTest extends BaseSyTest{

	@Autowired
	private SyCodeDaoImpl syCodeDao;

	@BeforeClass
	public void setup() { 
		assertNotNull(syCodeDao);
	}

	@Test
	public void testFindById() {
		String typeName="1003";//短信模板
		String userId="system";
		List<SyCode> listOfSyCode=syCodeDao.getSyCodeByTypeNameAndUserId(typeName, userId);
		System.out.println("listOfSyCode is : "+listOfSyCode);
		
	}
	
}
