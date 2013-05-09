package com.founder.sipbus.fwk.groovy.groovytmpl.hrs;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

public interface ICacheKpiAndParamSupport {
 
	public BigDecimal getParamValue(String paramName);
	
	public BigDecimal getLineParamValue(String name,String lname);
	
	
	public String getKpiValue(String jobnumber,String kpiName);
	
	public String getLineKpiValue(String jobnumber,String kpiName,String lname);
	
	
	public ConcurrentHashMap<String,String> getKpiDetailValue(String jobnumber,String kpiName);
 

	public void initLoad(String yearmonth);

	public void destroyLoad() ;
	 
 
	
}
