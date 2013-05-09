package com.founder.sipbus.common.memcache.engine;

import com.founder.sipbus.common.memcache.param.StdCacheParam;
import com.founder.sipbus.common.util.GuidUtil;

public class PaginationMemCacheEngine {
	private static String GROUP_TIMEOUT_PAGINATION = "pagination.timeout";
	public static String generateCacheKey(){
		return GuidUtil.getRandomGUID(false);
	}
	public static String storeSearchCriteria(Object searchCriteria){
		String key = generateCacheKey();
		return storeSearchCriteria(key,searchCriteria);
	}
	public static String storeSearchCriteria(String key,Object searchCriteria)
	{
		StdCacheParam param = new StdCacheParam(GROUP_TIMEOUT_PAGINATION,key);
		StdMemCacheEngine.put(param, searchCriteria);
		return key;
	}
	public static Object takeAndCleanSearchCriteria(String key){
		StdCacheParam param = new StdCacheParam(GROUP_TIMEOUT_PAGINATION,key);
		Object result = StdMemCacheEngine.get(param);
		StdMemCacheEngine.delete(param);
		return result;
	}
	public static boolean validate(){
		return StdMemCacheEngine.validate();
	}
}
