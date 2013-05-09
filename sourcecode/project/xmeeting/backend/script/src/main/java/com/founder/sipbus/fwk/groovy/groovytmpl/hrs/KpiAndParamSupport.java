package com.founder.sipbus.fwk.groovy.groovytmpl.hrs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.founder.sipbus.common.util.StringUtil;
import com.founder.sipbus.fwk.groovy.annotation.Description;

/**
 * 
 * @author Administrator
 *
 */
public class KpiAndParamSupport extends EmployeeSalaryCalcVariable{

	private Logger logger = LoggerFactory.getLogger(KpiAndParamSupport.class);
	
	private void logPrint(String sp)
	{
		if (logger.isTraceEnabled()) {
			logger.trace(sp);
		}
		System.out.println(sp);
	}
	
	@Description(desc="获取公共参数")
	public String p(String paraName){ 
		BigDecimal value=cacheKpiAndParamSupport.getParamValue(paraName);
		if(value==null)
		{
			logPrint(" paramName:"+paraName+" is null!");
			return "0";
		}
		return String.valueOf(value);
	}
	
	@Description(desc="获取线路参数")
	public String lp(String lname,String paraName){
		BigDecimal value=cacheKpiAndParamSupport.getLineParamValue(paraName, lname);
		if(value==null)
		{
			logPrint("lname:"+lname+" paramName:"+paraName+"  is null!");
			return "0";
		}
		return String.valueOf(value);
	}

	 
	@Description(desc="取员工KPI")
	public String kpi(String jobNumber,String kpiName){ 
		String value=cacheKpiAndParamSupport.getKpiValue(jobNumber.trim(), kpiName.trim());
		if(StringUtil.isEmpty(value))
		{
			logPrint("jobNumber:"+jobNumber+" kpiName:"+kpiName+"  is null!");
			return "0";
		}
		return value;
	}
	
//	@Description(desc="取员工线路KPI")
//	public Map<String,String> lkpi(String jobNumber,String kpiName,String lname){
////		String value=cacheKpiAndParamSupport.getKpiValue(jobNumber, kpiName);
//		String value=cacheKpiAndParamSupport.getLineKpiValue(jobNumber, kpiName, lname);
//		return null;
//	}
	
	
	@Description(desc="取员工线路KPI")
	public String lkpi(String jobNumber,String kpiName,String lname){
//		String value=cacheKpiAndParamSupport.getKpiValue(jobNumber, kpiName);
		String value=cacheKpiAndParamSupport.getLineKpiValue(jobNumber, kpiName, lname);
		if(StringUtil.isEmpty(value))
		{
			logPrint(" lname:"+lname+" jobNumber:"+jobNumber+" kpiName:"+kpiName+"  is null!");
			return "0";
		}
		return value;
	}
	
	
	
	@Description(desc="取员工线路")
	public List<String> lnames(String jobNumber,String kpiName){ 
		ConcurrentHashMap<String,String> map=cacheKpiAndParamSupport.getKpiDetailValue(jobNumber, kpiName);
		if(map==null)
		{
			logPrint(" jobNumber:"+jobNumber+" kpiName:"+kpiName+"  is null!");
			return  new ArrayList<String>();
		}
		Set<String> set=map.keySet();
		List<String> list = new ArrayList<String>(set); 
		return list; 
	}
	
	@Description(desc="取员工KPI Array")
	public String[] kpi2(String jobNumber,String kpiName){
		return new String[2];
	}
	
	@Description(desc="取员工KPI boolean")
	public boolean kpi3(String jobNumber,String kpiName){
		return true;
	} 
	
	@Description(desc="String-->Boolean")
	public boolean toBoolean(String value){
		if(StringUtil.isNotEmpty(value))
		{
			return Boolean.valueOf(value);
		}
		else
		{
			return Boolean.valueOf("false");
		}
		
	} 
	
	@Description(desc="String-->Boolean")
	public boolean toBoolean(String value,String isOne){
		if(StringUtil.isNotEmpty(value))
		{
			if(isOne.equals("1")&&value.equals("1"))
			{
				return true;
			}
			return Boolean.valueOf(value);
		}
		else
		{
			return Boolean.valueOf("false");
		}
		
	} 
	
	@Description(desc="String-->bigDecimal")
	public BigDecimal toBigDecimal(String value){
		if(StringUtil.isNotEmpty(value)&&StringUtil.isNum(value))
		{
			return new BigDecimal(value);
		}
		else
		{
			return new BigDecimal(0);
		}
	}
	
	@Description(desc="String --->  int")
	public int toInteger(String value){ 
		if(StringUtil.isNotEmpty(value)&&StringUtil.isNum(value))
		{
			return Integer.valueOf(value);
		}
		else
		{
			return Integer.valueOf("0");
		}
		
	}
	
	@Resource(name="cacheKpiAndParamSupportDummyImpl")
	private ICacheKpiAndParamSupport cacheKpiAndParamSupport;  
	public void setCacheKpiAndParamSupport(
			ICacheKpiAndParamSupport cacheKpiAndParamSupport) {
		this.cacheKpiAndParamSupport = cacheKpiAndParamSupport;
	} 


 
	
	
	
}
