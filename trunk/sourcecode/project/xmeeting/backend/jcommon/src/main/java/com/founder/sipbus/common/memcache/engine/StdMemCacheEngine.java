package com.founder.sipbus.common.memcache.engine;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.danga.MemCached.MemCachedClient;
import com.founder.sipbus.common.memcache.param.StdCacheParam;
import com.founder.sipbus.common.memcache.repository.MemCacheRepository;
import com.founder.sipbus.common.memcache.util.StdMemCacheUtil;

public class StdMemCacheEngine {
	private static Log log = LogFactory.getLog(StdMemCacheEngine.class);
	 public static void put(StdCacheParam param, Object value) {
        if(param.validation()){
        	MemCachedClient mcc = getCacheAdapter();
        	if(mcc!=null)
        		mcc.set(param.generateKey(), value,getExpiredDate(param));
        	else{
        		logError();
        	}
        }
	 }
	 
	 public static Object get(StdCacheParam param) {
        if(param.validation()){
        	MemCachedClient mcc = getCacheAdapter();
        	if(mcc!=null){
	            Object object = mcc.get(param.generateKey());
	            return object;
        	}else
        		logError();
        }
        return null;
	 }
	 
	 public static void delete(StdCacheParam param){
		 MemCachedClient mcc = getCacheAdapter();
		 if(mcc!=null)
			 mcc.delete(param.generateKey());
		 else
			 logError();
	 }
	 
	 public static boolean validate(){
		 if(getCacheAdapter()!= null)
			 return true;
		 else
			 return false;
	 }
	 
	 private static MemCachedClient getCacheAdapter(){
		 return StdMemCacheUtil.getMemCachedClient();
	 }
	 
	 private static void logError(){
		 log.error("MemCache is null, please check the setting of memcache.xml or check the stats of memcache server!!!");
//		 System.out.println("[ERROR]:MemCache is null, please check the setting of memcache.xml or check the stats of memcache server!!!");
	 }

	 private static Date getExpiredDate(StdCacheParam param){
		 String group = param.getGroup();
		 Map<String, String> memcachedTimeout = MemCacheRepository.getMap(MemCacheRepository.MEMCACHED_GROUP_TIMEOUT);
		 if(memcachedTimeout!=null){
			 String timeout = memcachedTimeout.get(group);
			 timeout = StringUtils.isNotBlank(timeout)?timeout:"0";
			 return new Date(new Date().getTime() + Long.valueOf(timeout) * 60 * 1000);
		 }else
			 return new Date(0);
		
	 }
}
