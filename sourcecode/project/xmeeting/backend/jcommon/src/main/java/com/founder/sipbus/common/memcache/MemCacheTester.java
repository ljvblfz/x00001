package com.founder.sipbus.common.memcache;

import com.founder.sipbus.common.memcache.engine.PaginationMemCacheEngine;
import com.founder.sipbus.common.memcache.engine.StdMemCacheEngine;
import com.founder.sipbus.common.memcache.param.StdCacheParam;
import com.founder.sipbus.common.memcache.repository.MemCacheRepository;
import com.founder.sipbus.common.memcache.util.StdMemCacheUtil;

public class MemCacheTester {
	public static void main(String[] args){
		try {
			MemCacheRepository.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
////		StdCacheParam scp = new StdCacheParam("default.timeout","test");
		StdMemCacheUtil.getMemCachedClient().add("ffff1", "asdfasdfasf");
		System.out.println(StdMemCacheUtil.getMemCachedClient().get("ffff1"));
//		StdMemCacheEngine.put(scp, "1234555");
//		System.out.println("1:"+StdMemCacheEngine.get(scp));
		StdMemCacheUtil.getMemCachedClient().set("ffff1", "sdasdasd33");
		System.out.println(StdMemCacheUtil.getMemCachedClient().get("ffff1"));
//		try{
//			Thread.sleep(70*1000);
//		}catch(Exception e){}
//		System.out.println("2:"+StdMemCacheEngine.get(scp));
//		String key = PaginationMemCacheEngine.storeSearchCriteria("1234567890");
		PaginationMemCacheEngine.storeSearchCriteria("mmmmm","dddd");
		System.out.println(PaginationMemCacheEngine.takeAndCleanSearchCriteria("mmmmm"));
//		System.out.println("1:"+PaginationMemCacheEngine.takeAndCleanSearchCriteria(key));
//		System.out.println("1:"+PaginationMemCacheEngine.takeAndCleanSearchCriteria(key));
	}
}
