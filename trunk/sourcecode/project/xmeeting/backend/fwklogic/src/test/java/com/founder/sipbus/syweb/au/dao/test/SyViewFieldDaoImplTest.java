package com.founder.sipbus.syweb.au.dao.test;

import static org.testng.AssertJUnit.assertNotNull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.founder.sipbus.syweb.script.po.SyScriptDecisiontable;
import com.founder.sipbus.syweb.view.dao.SyViewFieldDaoImpl;
import com.founder.sipbus.syweb.view.po.SyViewField;
 
public class SyViewFieldDaoImplTest extends BaseSyTest{

	@Autowired
	private SyViewFieldDaoImpl syViewFieldDao;

	@BeforeClass
	public void setup() { 
		assertNotNull(syViewFieldDao);
	}

	private String svGuid="0000000000000000SYVIEW13010721080720";
	@Test
	public void testFindSearchConditionFieldsBySvguid() {
		List<SyViewField>  list=syViewFieldDao.findSearchConditionFieldsBySvguid(svGuid);
		System.out.println("-----testFindSearchConditionFieldsBySvguid----->"+list);
	}
	@Test
	public void testFindSearchResultFieldsBySvguid() {
		List<SyViewField>  list=syViewFieldDao.findSearchResultFieldsBySvguid(svGuid);
		System.out.println("-----testFindSearchResultFieldsBySvguid----->"+list);
	}
	
}
