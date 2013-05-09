package com.founder.sipbus.syweb.au.dao.test;

import static org.testng.AssertJUnit.assertNotNull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.founder.sipbus.syweb.au.po.SyCode;
import com.founder.sipbus.syweb.script.dao.SyScriptDecisiontableDaoImpl;
import com.founder.sipbus.syweb.script.po.SyScriptDecisiontable;
 
public class SyDecisionTableDaoImplTest extends BaseSyTest{

	@Autowired
	private SyScriptDecisiontableDaoImpl syScriptDecisiontableDao;

	@BeforeClass
	public void setup() { 
		assertNotNull(syScriptDecisiontableDao);
	}

	@Test
	public void testFindById() {
		String scriptName="1003";//短信模板 
		List<SyScriptDecisiontable> listOfSyScriptDecisiontable=syScriptDecisiontableDao.findByScriptName(scriptName);
		System.out.println("listOfSyScriptDecisiontable is : "+listOfSyScriptDecisiontable);
		
	}
	
}
