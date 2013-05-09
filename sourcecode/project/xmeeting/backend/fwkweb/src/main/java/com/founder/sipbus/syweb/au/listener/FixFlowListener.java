package com.founder.sipbus.syweb.au.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.util.IntrospectorCleanupListener;

import com.founder.fix.fixflow.core.ProcessEngine;
import com.founder.fix.fixflow.core.ProcessEngineManagement;
import com.founder.sipbus.common.framework.repository.NetSettingRepository;
import com.founder.sipbus.common.memcache.repository.MemCacheRepository;
import com.founder.sipbus.common.memcache.util.StdMemCacheUtil;
import com.founder.sipbus.syweb.au.util.MemcachedUtil;

public class FixFlowListener extends IntrospectorCleanupListener {

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		super.contextDestroyed(arg0);
		try {
			//工作流引擎初始化
//			ProcessEngineManagement
//					.getDefaultProcessEngine();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
