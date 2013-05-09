package com.founder.sipbus.fwk.groovy.groovytmpl.hrs;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.founder.sipbus.hrs.param.dao.CacheParamIDaoImpl;
import com.founder.sipbus.hrs.param.vo.SaEmpKpiDetailIVO;
import com.founder.sipbus.hrs.param.vo.SaEmpKpiIVO;
import com.founder.sipbus.hrs.param.vo.SaLineParamIVO;
import com.founder.sipbus.hrs.param.vo.SaPublicParamIVO;

public class CacheKpiAndParamSupportDummyImpl implements ICacheKpiAndParamSupport {

	private Logger logger = LoggerFactory.getLogger(CacheKpiAndParamSupportDummyImpl.class);
	
	private static ConcurrentHashMap<String,BigDecimal> publicParam=new ConcurrentHashMap<String,BigDecimal>();  
	private static ConcurrentHashMap<String,ConcurrentHashMap<String,BigDecimal>> lineParam=new ConcurrentHashMap<String,ConcurrentHashMap<String,BigDecimal>>(); 
	private static ConcurrentHashMap<String,ConcurrentHashMap<String,String>> empKpi=new ConcurrentHashMap<String,ConcurrentHashMap<String,String>>();
	private static ConcurrentHashMap<String,ConcurrentHashMap<String,ConcurrentHashMap<String,String>>> empKpiDetail=new ConcurrentHashMap<String,ConcurrentHashMap<String,ConcurrentHashMap<String,String>>>();
	
	private void logPrint(String sp)
	{
		if (logger.isTraceEnabled()) {
			logger.trace(sp);
		}
		System.out.println(sp);
	}
	
	public BigDecimal getParamValue(String paramName){
		BigDecimal paramValue=publicParam.get(paramName);
		return paramValue;
	}
	
	public BigDecimal getLineParamValue(String paramName,String lname){
		ConcurrentHashMap<String,BigDecimal> value=lineParam.get(paramName);
		if(null!=value){
			BigDecimal paramValue=value.get(lname);
			return paramValue;
		}else{
			return BigDecimal.valueOf(0);
		}
	}
	
	
	public String getKpiValue(String jobnumber,String kpiName){
		ConcurrentHashMap<String,String> value=empKpi.get(jobnumber);
		if(value==null)
		{
			logPrint("jobnumber:"+jobnumber+" kpiName"+kpiName+" getKpiValue is null!");
			return null;
		}
		String kpiValue=value.get(kpiName);
		return kpiValue;
	}
	
	public String getLineKpiValue(String jobnumber,String kpiName,String lname){
		ConcurrentHashMap<String,ConcurrentHashMap<String,String>> value=empKpiDetail.get(jobnumber);
		ConcurrentHashMap<String,String> kpiValue=value.get(kpiName);
		if(null==kpiValue){
			logPrint("jobnumber:"+jobnumber+" kpiName"+kpiName+" lname="+lname+" getLineKpiValue is null!");
			return "0";
		}
		String lvalue=kpiValue.get(lname);
		return lvalue;
	}
	
	
	public ConcurrentHashMap<String,String> getKpiDetailValue(String jobnumber,String kpiName){
		ConcurrentHashMap<String,ConcurrentHashMap<String,String>> value=empKpiDetail.get(jobnumber);
		if(value==null)
		{
			logPrint("jobnumber:"+jobnumber+" kpiName"+kpiName+" getKpiDetailValue is null!");
			return null;
		}
		ConcurrentHashMap<String,String> kpiValue=value.get(kpiName);
		return kpiValue;
	}

	public void reloadDriverEmpKpi(String yearmonth){ 
		HashMap<String,String> param=new HashMap<String,String>();
		param.put("yearmonth", yearmonth);
		List<SaEmpKpiIVO> listOfSaEmpKpiIVO=cacheParamIDao.findAllDriverEmpKpi(param);
		for(SaEmpKpiIVO saEmpKpiIVO:listOfSaEmpKpiIVO){
			String jobnumber=saEmpKpiIVO.getJobnumber();
			String kpiname=saEmpKpiIVO.getName();
			String value=saEmpKpiIVO.getValue(); 
			ConcurrentHashMap<String,String> mapvalue=new ConcurrentHashMap<String,String>(); 
			ConcurrentHashMap<String,String> mapvaluex=empKpi.putIfAbsent(jobnumber, mapvalue); 
			if(null!=mapvaluex){
				mapvalue=mapvaluex;
			}  
			if(null!=value){
				mapvalue.putIfAbsent(kpiname, value);  
			}else{
				mapvalue.putIfAbsent(kpiname, "");  
				
			}
		}//end of for 
		
	}
	
	
	public void reloadDriverEmpKpiDetail(String yearmonth){
		HashMap<String,String> param=new HashMap<String,String>();
		param.put("yearmonth", yearmonth);
		List<SaEmpKpiDetailIVO> listOfSaEmpKpiDetailIVO=cacheParamIDao.findAllDriverEmpKpiDetail(param);
		for(SaEmpKpiDetailIVO saEmpKpiDetailIVO:listOfSaEmpKpiDetailIVO){
			String jobnumber=saEmpKpiDetailIVO.getJobnumber();
			String kpiname=saEmpKpiDetailIVO.getName();
			String key=saEmpKpiDetailIVO.getKey();
			String value=saEmpKpiDetailIVO.getValue(); 
			ConcurrentHashMap<String,ConcurrentHashMap<String,String>> jobnumbervalue=new ConcurrentHashMap<String,ConcurrentHashMap<String,String>>(); 
			ConcurrentHashMap<String,ConcurrentHashMap<String,String>> jobnumbervaluex=empKpiDetail.putIfAbsent(jobnumber, jobnumbervalue);
			if(null!=jobnumbervaluex){
				jobnumbervalue=jobnumbervaluex;
			}
			ConcurrentHashMap<String,String> kpinamevalue=new ConcurrentHashMap<String,String>(); 
			ConcurrentHashMap<String,String> kpinamevaluex=jobnumbervalue.putIfAbsent(kpiname, kpinamevalue); 
			if(null!=kpinamevaluex){
				kpinamevalue=kpinamevaluex;
			}
			kpinamevalue.put(key, value);
		}//end of for  
	}//end of reloadDriverEmpKpiDetail
	
	

	public void reloadDriverPublicParam(){ 
		List<SaPublicParamIVO> listOfSaPublicParamIVO=cacheParamIDao.findAllDriverPublicParam(null); 
		for(SaPublicParamIVO saPublicParamIVO:listOfSaPublicParamIVO){
			String name=saPublicParamIVO.getName();
			BigDecimal value=saPublicParamIVO.getValue(); 
			publicParam.putIfAbsent(name, value);
		} 
	}
	
	public void reloadDriverLineParam(){ 
		List<SaLineParamIVO> listOfSaLineParamIVO=cacheParamIDao.findAllDriverLineParam(null);
		for(SaLineParamIVO saLineParamIVO:listOfSaLineParamIVO){
			String name=saLineParamIVO.getName();
			String lname=saLineParamIVO.getLname();
			BigDecimal value=saLineParamIVO.getValue(); 
			ConcurrentHashMap<String,BigDecimal> mapvalue=new ConcurrentHashMap<String,BigDecimal>(); 
			ConcurrentHashMap<String,BigDecimal> mapvaluex=lineParam.putIfAbsent(name, mapvalue);
			if(null!=mapvaluex){
				mapvalue=mapvaluex;
			}
			mapvalue.putIfAbsent(lname, value);  
		}//end of for 
//		System.out.println("lineParam------->"+lineParam);
	}//end of reloadDriverLineParam
	

	public void initLoad(String yearmonth){
		if(logger.isTraceEnabled()){
			logger.trace("initLoad begin");
		}
		if(enable){ 
			reloadDriverEmpKpi(yearmonth);
			reloadDriverEmpKpiDetail(yearmonth);
			reloadDriverLineParam();
			reloadDriverPublicParam();
		}
		if(logger.isTraceEnabled()){
			logger.trace("initLoad end");
		}
	}
	

	public void destroyLoad(){
		if(logger.isTraceEnabled()){
			logger.trace("destroyLoad begin");
		}
		if(enable){
			publicParam.clear();
			lineParam.clear();
			empKpi.clear();
			empKpiDetail.clear();
		}
		if(logger.isTraceEnabled()){
			logger.trace("destroyLoad end");
		}
	}
	 

	@Resource(name="cacheParamIDao")
	private CacheParamIDaoImpl cacheParamIDao;  
	public void setCacheParamIDao(CacheParamIDaoImpl cacheParamIDao) {
		this.cacheParamIDao = cacheParamIDao;
	}
	

	
	private boolean enable=true; 
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
}
