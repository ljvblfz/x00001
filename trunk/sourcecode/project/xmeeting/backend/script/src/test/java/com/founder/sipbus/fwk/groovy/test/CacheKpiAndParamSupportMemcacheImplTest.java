package com.founder.sipbus.fwk.groovy.test;

import static org.testng.AssertJUnit.assertNotNull;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.founder.sipbus.common.memcache.repository.MemCacheRepository;
import com.founder.sipbus.common.memcache.util.StdMemCacheUtil;
import com.founder.sipbus.fwk.groovy.groovytmpl.hrs.CacheKpiAndParamSupportMemcacheImpl;

public class CacheKpiAndParamSupportMemcacheImplTest extends BaseGroovyTest{

	@Autowired
	private CacheKpiAndParamSupportMemcacheImpl cacheKpiAndParamSupport;

	
	 

	@BeforeClass
	public void setup() { 
		assertNotNull(cacheKpiAndParamSupport);
		try {
			MemCacheRepository.init();
		} catch (Exception e) { 
			e.printStackTrace();
		}
		StdMemCacheUtil.init(); 
		

		cacheKpiAndParamSupport.initLoad("2013-02");
	}
 
 

	@Test
	public void testReload(){  
	}
	
	
	
	

	@Test
	public void testGetPublicParam(){
		BigDecimal paramValue=cacheKpiAndParamSupport.getParamValue("员工法定月工作天数");
		System.out.println("paramValue--->"+paramValue);
	}
	
	
	@Test
	public void testGetLineParam(){
		BigDecimal lineparamValue=cacheKpiAndParamSupport.getLineParamValue("线路圈奖基数", "177");
		System.out.println("lineparamValue--->"+lineparamValue);
	}
	
	
	@Test
	public void testGetEmpKpi(){
//		String kpiValue=cacheKpiAndParamSupport.getKpiValue("660028", "员工是否信息员");
		String kpiValue=cacheKpiAndParamSupport.getKpiValue("660028", "员工带徒天数");
		System.out.println("kpiValue--->"+kpiValue);
	}
	
	
	@Test
	public void testGetEmpKpiDetail(){
		ConcurrentHashMap<String,String> kpiValue=cacheKpiAndParamSupport.getKpiDetailValue("660028", "员工线路圈数");
		System.out.println("kpiValue--->"+kpiValue);
	}
	
	
	@Test
	public void testGetEmpLineKpi(){
		String kpiValue=cacheKpiAndParamSupport.getLineKpiValue("660028",  "员工线路圈数", "228");
		System.out.println("kpiValue--->"+kpiValue);
	}
	
	

	
	
}
