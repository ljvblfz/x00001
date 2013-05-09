package com.founder.sipbus.common.memcache.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.founder.sipbus.common.memcache.repository.MemCacheRepository;
import com.founder.sipbus.common.util.NumUtil;
import com.founder.sipbus.common.util.StringUtil;

public final class StdMemCacheUtil {
	private static boolean IS_INIT = false;
	private static String SOCKIO_POOL_KEY = "STD_MEM_CACHE";
    private StdMemCacheUtil() {}
    
    static{
    	init();
    }
    /**
     * 初始化
     */
    public static void init() {
        
        if(IS_INIT){
            return;
        }
        
        Map<String, String> memcachedServer = MemCacheRepository.getMap(MemCacheRepository.MEMCACHED_SERVER_CONFIG);

        // server list and weights
        String[] servers = StringUtil.str2Array(memcachedServer.get("servers"));

        for (int i = 0; i < servers.length; i++) {

            // grab an instance of our connection pool
            SockIOPool pool = SockIOPool.getInstance(String.valueOf(SOCKIO_POOL_KEY+i));

            // set the servers and the weights
            pool.setServers(new String[] { servers[i] });
            pool.setWeights(new Integer[] { 1 });

            // set some basic pool settings
            // 5 initial, 5 min, and 1024 max conns
            // and set the max idle time for a conn
            // to 6 hours
            pool.setInitConn(NumUtil.convertToInt(memcachedServer.get("initConn")));
            pool.setMinConn(NumUtil.convertToInt(memcachedServer.get("minConn")));
            pool.setMaxConn(NumUtil.convertToInt(memcachedServer.get("maxConn")));
            pool.setMaxIdle(1000 * 60 * NumUtil.convertToInt(memcachedServer.get("maxIdle")));

            // set the sleep for the maint thread
            // it will wake up every x seconds and
            // maintain the pool size
            pool.setMaintSleep(1000*NumUtil.convertToInt(memcachedServer.get("maintSleep")));

            // set some TCP settings
            // disable nagle
            // set the read timeout to 3 secs
            // and don't set a connect timeout
            pool.setAliveCheck(true);
            pool.setNagle(false);
            pool.setSocketTO(3000);
            pool.setSocketConnectTO(0);

            // initialize the connection pool
            pool.initialize();
        }
        
        IS_INIT = true;
    }
    
    /**
     * 返回访问memCached的API
     * 
     * @return 访问memCached的API
     */
    public static MemCachedClient getMemCachedClient() {
        // server list and weights
        Map<String, String> memcachedServer = MemCacheRepository.getMap(MemCacheRepository.MEMCACHED_SERVER_CONFIG);
        String[] servers = StringUtil.str2Array(memcachedServer.get("servers"));
        for (int i = 0; i < servers.length; i++) {
            try {
                MemCachedClient mcc = new MemCachedClient(String.valueOf(SOCKIO_POOL_KEY+i));
                // lets set some compression on for the client
                // compress anything larger than 64k
                mcc.setCompressEnable(true);
                mcc.setCompressThreshold(64 * 1024);
                if (mcc.stats().containsKey(servers[i])) {// 依次判断memcached服务器是否能够连通
                    return mcc;
                } else {
                    continue;
                }
            } catch (Exception e) {
                continue;
            }
        }
        // 所有memcached服务器都连不上
        return null;
    }
    
    
    /**
     * 设置组缓存值
     * @author Tracy 2013-2-28
     * @param key
     * @param value
     * @param groupName
     */
    public static void putToGroup(String key , Object value , String groupName){
    	getMemCachedClient().set(groupName.toUpperCase()+"_"+key, value);
    	addGroupKey(key,groupName);
    }

    
    /**
     * 从组中获取缓存
     * @author Tracy 2013-2-28
     * @param key
     * @param value
     * @param groupName
     */
    public static void getFromGroup(String key , String groupName){
    	getMemCachedClient().get(groupName.toUpperCase()+"_"+key);
    }
    
    /**
     * 清除组数据
     * @author Tracy 2013-2-28
     * @param groupName
     */
    public static void clearGroup(String groupName){
    	List<String> keyList = (List<String>)getMemCachedClient().get("MEMCACHE_GROUPKEYS_"+groupName.toUpperCase());
    	if(null!=keyList){
	    	for(String key : keyList){
	        	getMemCachedClient().delete(groupName.toUpperCase()+"_"+key);
	    	}
    	}
		getMemCachedClient().set("MEMCACHE_GROUPKEYS_"+groupName.toUpperCase(),null);
    }
    
    /**
     * 从组中删除缓存key
     * @author Tracy 2013-2-28
     * @param key
     * @param groupName
     */
    public static void deleteFromGroup(String key , String groupName){
    	getMemCachedClient().delete(groupName.toUpperCase()+"_"+key);
    	removeGroupKey(key,groupName);
    }

    
    private static void addGroupKey(String key , String groupName){
    	List keyList = (List)getMemCachedClient().get("MEMCACHE_GROUPKEYS_"+groupName.toUpperCase());
    	if(null==keyList){
    		keyList=new ArrayList();
    	}
		keyList.add(key);
		getMemCachedClient().set("MEMCACHE_GROUPKEYS_"+groupName.toUpperCase(),keyList);
    	
    }
    private static void removeGroupKey(String key , String groupName){
    	List keyList = (List)getMemCachedClient().get("MEMCACHE_GROUPKEYS_"+groupName.toUpperCase());
    	if(null!=keyList){
    		keyList.remove(key);
    	}
		getMemCachedClient().set("MEMCACHE_GROUPKEYS_"+groupName.toUpperCase(),keyList);
    	
    }
}
