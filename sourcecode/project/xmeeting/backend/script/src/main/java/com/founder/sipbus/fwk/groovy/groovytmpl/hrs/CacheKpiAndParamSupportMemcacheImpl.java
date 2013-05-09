package com.founder.sipbus.fwk.groovy.groovytmpl.hrs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danga.MemCached.MemCachedClient;
import com.founder.sipbus.common.memcache.util.StdMemCacheUtil;
import com.founder.sipbus.hrs.param.dao.CacheParamIDaoImpl;
import com.founder.sipbus.hrs.param.vo.SaEmpKpiDetailIVO;
import com.founder.sipbus.hrs.param.vo.SaEmpKpiIVO;
import com.founder.sipbus.hrs.param.vo.SaLineParamIVO;
import com.founder.sipbus.hrs.param.vo.SaPublicParamIVO;

public class CacheKpiAndParamSupportMemcacheImpl implements ICacheKpiAndParamSupport {

	private Logger logger = LoggerFactory.getLogger(CacheKpiAndParamSupportMemcacheImpl.class);
	
	//
	
	private static ConcurrentHashMap<String,BigDecimal> publicParam=new ConcurrentHashMap<String,BigDecimal>();  
	private static ConcurrentHashMap<String,ConcurrentHashMap<String,BigDecimal>> lineParam=new ConcurrentHashMap<String,ConcurrentHashMap<String,BigDecimal>>(); 
//	private static ConcurrentHashMap<String,ConcurrentHashMap<String,String>> empKpi=new ConcurrentHashMap<String,ConcurrentHashMap<String,String>>();
//	private static ConcurrentHashMap<String,ConcurrentHashMap<String,ConcurrentHashMap<String,String>>> empKpiDetail=new ConcurrentHashMap<String,ConcurrentHashMap<String,ConcurrentHashMap<String,String>>>();
	//
	
	private static MemCachedClient mc ;
 

	private static String  KPI_DETAIL_PREFIX=" KPI_DETAIL_";
	private static String  KPI_PREFIX=" KPI_";

	
	@Override
	public void initLoad(String yearmonth) {
		// TODO Auto-generated method stub
		if(mc==null){
			mc=StdMemCacheUtil.getMemCachedClient();
		}

		if(logger.isTraceEnabled()){
			logger.trace("initLoad begin");
		}
		if(enable){ 
			reloadDriverLineParam();
			reloadDriverPublicParam();
			//
			reloadDriverEmpKpi(yearmonth,mc);
			reloadDriverEmpKpiDetail(yearmonth,mc); 
		}
		if(logger.isTraceEnabled()){
			logger.trace("initLoad end");
		}
		
	}
	@Override
	public void destroyLoad() {

		if(logger.isTraceEnabled()){
			logger.trace("destroyLoad begin");
		}
		if(enable){ 
			publicParam.clear();
			lineParam.clear(); 
			clearKpiGroup();
			clearKpiDetailGroup();
		}
		if(logger.isTraceEnabled()){
			logger.trace("destroyLoad end");
		}
		
	}
	//==============>
	 

	public BigDecimal getParamValue(String paramName){
		BigDecimal paramValue=publicParam.get(paramName);
		return paramValue;
	}
	
	public BigDecimal getLineParamValue(String name,String lname){
		ConcurrentHashMap<String,BigDecimal> value=lineParam.get(name);
		if(null!=value){
			BigDecimal paramValue=value.get(lname);
			return paramValue;
		}else{
			return BigDecimal.valueOf(0);
		}
	}
	
	
	public String getKpiValue(String jobnumber,String kpiName){
		ConcurrentHashMap<String,String> value=(ConcurrentHashMap<String,String>)mc.get(KPI_PREFIX+jobnumber); 
		String kpiValue=value.get(kpiName);
		return kpiValue;
	}
	
	public String getLineKpiValue(String jobnumber,String kpiName,String lname){
		ConcurrentHashMap<String,ConcurrentHashMap<String,String>> value=(ConcurrentHashMap<String,ConcurrentHashMap<String,String>>)mc.get(KPI_DETAIL_PREFIX+jobnumber);
		ConcurrentHashMap<String,String> kpiValue=value.get(kpiName);
		String lvalue=kpiValue.get(lname);
		return lvalue;
	}
	
	
	public ConcurrentHashMap<String,String> getKpiDetailValue(String jobnumber,String kpiName){ 
		ConcurrentHashMap<String,ConcurrentHashMap<String,String>> value=(ConcurrentHashMap<String,ConcurrentHashMap<String,String>>)mc.get(KPI_DETAIL_PREFIX+jobnumber);
		ConcurrentHashMap<String,String> kpiValue=value.get(kpiName);
		return kpiValue;
	}
	
	
	
	//==========>

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
	}//end of reloadDriverLineParam
	
	

	public void reloadDriverEmpKpi(String yearmonth,MemCachedClient mc){ 
		HashMap<String,String> param=new HashMap<String,String>();
		param.put("yearmonth", yearmonth);
		List<SaEmpKpiIVO> listOfSaEmpKpiIVO=cacheParamIDao.findAllDriverEmpKpi(param);
		for(SaEmpKpiIVO saEmpKpiIVO:listOfSaEmpKpiIVO){
			String jobnumber=saEmpKpiIVO.getJobnumber();
			String kpiname=saEmpKpiIVO.getName();
			String value=saEmpKpiIVO.getValue(); 
			//
			boolean flag=false;
			ConcurrentHashMap<String,String> mapvaluex=(ConcurrentHashMap<String,String>)mc.get(KPI_PREFIX+jobnumber); 
			if(mapvaluex==null){
				mapvaluex=new ConcurrentHashMap<String,String>();  
				flag=true;
			} 

			if(null!=value){
				mapvaluex.putIfAbsent(kpiname, value);  
			}else{
				mapvaluex.putIfAbsent(kpiname, "");   
			}
			if(flag){
				addKpiGroupKey(jobnumber);
			}
			mc.set(KPI_PREFIX+jobnumber, mapvaluex);
		}//end of for 
		
	}
	
	
	
	
	public void reloadDriverEmpKpiDetail(String yearmonth,MemCachedClient mc){
		HashMap<String,String> param=new HashMap<String,String>();
		param.put("yearmonth", yearmonth);
		List<SaEmpKpiDetailIVO> listOfSaEmpKpiDetailIVO=cacheParamIDao.findAllDriverEmpKpiDetail(param);
		for(SaEmpKpiDetailIVO saEmpKpiDetailIVO:listOfSaEmpKpiDetailIVO){
			String jobnumber=saEmpKpiDetailIVO.getJobnumber();
			String kpiname=saEmpKpiDetailIVO.getName();
			String key=saEmpKpiDetailIVO.getKey();
			String value=saEmpKpiDetailIVO.getValue();  
			//
			ConcurrentHashMap<String,ConcurrentHashMap<String,String>>  jobnumbervalue=(ConcurrentHashMap<String,ConcurrentHashMap<String,String>> )mc.get(KPI_DETAIL_PREFIX+jobnumber); 
			
			boolean flag=false;
			if(null==jobnumbervalue){
				jobnumbervalue=new ConcurrentHashMap<String,ConcurrentHashMap<String,String>>(); 
				flag=true;
			}
			ConcurrentHashMap<String,String> kpinamevalue=new ConcurrentHashMap<String,String>();  
			ConcurrentHashMap<String,String> kpinamevaluex=jobnumbervalue.putIfAbsent(kpiname, kpinamevalue); 
			if(null!=kpinamevaluex){
				kpinamevalue=kpinamevaluex;
			}
			kpinamevalue.put(key, value); 
			if(flag){
				addKpiDetailGroupKey(jobnumber);
			}
			mc.set(KPI_DETAIL_PREFIX+jobnumber, jobnumbervalue);
		}//end of for  
	}//end of reloadDriverEmpKpiDetail
	
	
    
    private static void addKpiGroupKey(String key ){
    	List keyList = (List)mc.get("MEMCACHE_GROUPKEYS_"+KPI_PREFIX);
    	if(null==keyList){
    		keyList=new ArrayList();
    	}
		keyList.add(key);
		mc.set("MEMCACHE_GROUPKEYS_"+KPI_PREFIX,keyList);
    	
    }
    
    
    private static void addKpiDetailGroupKey(String key ){
    	List keyList = (List)mc.get("MEMCACHE_GROUPKEYS_"+KPI_DETAIL_PREFIX);
    	if(null==keyList){
    		keyList=new ArrayList();
    	}
		keyList.add(key);
		mc.set("MEMCACHE_GROUPKEYS_"+KPI_DETAIL_PREFIX,keyList);
    	
    }
    
    
    private static void clearKpiGroup(){
    	List<String> keyList = (List<String>)mc.get("MEMCACHE_GROUPKEYS_"+KPI_PREFIX);
    	if(null!=keyList){
	    	for(String key : keyList){
	    		mc.delete(KPI_PREFIX+"_"+key);
	    	}
    	}
    	mc.set("MEMCACHE_GROUPKEYS_"+KPI_PREFIX,null);
    }
    
    
    private static void clearKpiDetailGroup(){
    	List<String> keyList = (List<String>)mc.get("MEMCACHE_GROUPKEYS_"+KPI_DETAIL_PREFIX);
    	if(null!=keyList){
	    	for(String key : keyList){
	    		mc.delete(KPI_DETAIL_PREFIX+"_"+key);
	    	}
    	}
    	mc.set("MEMCACHE_GROUPKEYS_"+KPI_DETAIL_PREFIX,null);
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
