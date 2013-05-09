package com.founder.sipbus.syweb.au.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.founder.sipbus.common.framework.repository.NetSettingRepository;
import com.founder.sipbus.common.util.ApplicationContextUtil;
import com.founder.sipbus.common.util.NumUtil;
import com.founder.sipbus.common.util.StringUtil;
import com.founder.sipbus.syweb.au.dao.SysUserDaoImpl;
import com.founder.sipbus.syweb.au.po.SysUser;

/**
 * [����]<br>
 * MemcachedUtil<br>
 * <br>
 * [���ܸ�Ҫ]<br>
 * l��memcached������Ĺ�����<br>
 * <br>
 * [�������]<br>
 * 2009/7/1 ver1.00 �½� weifeng<br>
 * <br>
 * 
 * @author FOUNDER CORPORATION
 */
public final class MemcachedUtil {

    private static boolean IS_INIT = false;
    
    private MemcachedUtil() {}

    /**
     * ��ʼ��
     */
    public static void init() {
        
        if(IS_INIT){
            return;
        }
        
        Map<String, String> memcachedServer = NetSettingRepository.getMap(NetSettingRepository.MEMCACHED_SERVER_CONFIG);

        // server list and weights
        String[] servers = StringUtil.str2Array(memcachedServer.get("servers"));
        // Memcached��ΪSSO������ʱ��ͬһʱ��ֻ�ܷ�������
        // Integer[] weights = NumUtil.strArr2IntArr(StringUtil.str2Array(memcachedServer.get("weights")));

        for (int i = 0; i < servers.length; i++) {

            // grab an instance of our connection pool
            SockIOPool pool = SockIOPool.getInstance(String.valueOf(i));

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
            pool.setNagle(false);
            pool.setSocketTO(3000);
            pool.setSocketConnectTO(0);

            // initialize the connection pool
            pool.initialize();
        }
        
        IS_INIT = true;
    }

    /**
     * ���ط���memCached��API
     * 
     * @return ����memCached��API
     */
    public static MemCachedClient getMemCachedClient() {
        // server list and weights
        Map<String, String> memcachedServer = NetSettingRepository.getMap(NetSettingRepository.MEMCACHED_SERVER_CONFIG);
        String[] servers = StringUtil.str2Array(memcachedServer.get("servers"));

        for (int i = 0; i < servers.length; i++) {
            try {

                MemCachedClient mcc = new MemCachedClient(String.valueOf(i));
                // lets set some compression on for the client
                // compress anything larger than 64k
                mcc.setCompressEnable(true);
                mcc.setCompressThreshold(64 * 1024);
                if (mcc.stats().containsKey(servers[i])) {// �4��ж�memcached�������Ƿ��ܹ�lͨ
                    return mcc;
                } else {
                    continue;
                }

            } catch (Exception e) {
                continue;
            }
        }

        // ����memcached������l����
        return null;
    }

    /**
     * ��memcachedServerȡ��SSOManger����
     * 
     * @return SSO�������
     */
    public static ISSOManager getSSOManager(HttpServletRequest request) {

        String sSOId = SsoUtil.getSsoIdFromCookie(request);

        MemCachedClient mcc = getMemCachedClient();

        if (!mcc.keyExists(sSOId)) {
            return null;
        }

//        String userName = (String) mcc.get(sSOId);
        
        ISSOManager sSOManager=(ISSOManager) mcc.get(sSOId);
//        System.out.println("=================================start============"+System.currentTimeMillis());
//        // ��Ҫͨ�� loginId ��ѯ�� UserEntity�����ݡ�
//        SysUserDaoImpl userDao = (SysUserDaoImpl) ApplicationContextUtil.getBean(request,"sysUserDao");
//        System.out.println("=================================end0============"+System.currentTimeMillis());
//        SysUser sysUser =  userDao.getUserByName(userName);
//        System.out.println("=================================end1============"+System.currentTimeMillis());
//        sysUser.setAuthorities(userDao.getAuListByUserId(sysUser.getUserid()));
//        System.out.println("=================================end2============"+System.currentTimeMillis());
        
//        RoleDao roleDao = (RoleDao) SpringConfigLoadHelper.getBean("au_roleDao");
//        sysUser = userDao.getUserDetail(loginId);
//        List<RoleInfoTo> roleList = roleDao.getRolesByLoginId(loginId);
//        if(userInfoTO==null)
//        	return null;
//        sysUser.setRoleList(roleList);
//        ISSOManager sSOManager = new SSOManager();
//        sSOManager.setSsoId(sSOId);
//        sSOManager.setSysUser(sysUser);
        sSOManager.refresh();
//	    SsoUtil.refreshSSOManager((SSOManager)sSOManager);
        return sSOManager;
    }
}
