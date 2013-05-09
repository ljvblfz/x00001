package com.founder.sipbus.syweb.au.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.util.IntrospectorCleanupListener;

import com.founder.sipbus.common.framework.repository.NetSettingRepository;
import com.founder.sipbus.common.memcache.repository.MemCacheRepository;
import com.founder.sipbus.common.memcache.util.StdMemCacheUtil;
import com.founder.sipbus.syweb.au.util.MemcachedUtil;

public class SysListener extends IntrospectorCleanupListener {

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		super.contextDestroyed(arg0);

		try {
			MemCacheRepository.init();
			StdMemCacheUtil.init();
			//SSO 缓存初始化
			NetSettingRepository.init();
			MemcachedUtil.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//初始化用户权限和功能�?
//		ApplicationContextUtil.getAuAuthoritiesDao().initEnableAuth();
//		
//		//初始化用户至缓存
//		ApplicationContextUtil.getAuUsersDao().initUsersCache();
		
	}


}
