package com.founder.sipbus.syweb.au.base;

import org.hibernate.SessionFactory;

import com.founder.fix.fixflow.core.ProcessEngine;
import com.founder.fix.fixflow.core.ProcessEngineManagement;
import com.founder.fix.fixflow.core.impl.ExternalContent;
import com.founder.sipbus.syweb.au.util.WorkflowHandle;

public class WorkflowBaseResource extends SyBaseResource {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 获取工作流引擎
	 * 
	 * @author founder 2012-6-26
	 * @return
	 */
	public ProcessEngine getProcessEngine() {
		ProcessEngine engine = ProcessEngineManagement
				.getDefaultProcessEngine();
		ExternalContent externalContent = new ExternalContent();
		externalContent.setConnection(sessionFactory.getCurrentSession().connection());
		externalContent.setAuthenticatedUserId(getLoginUser().getUserid());
		engine.setExternalContent(externalContent);
		return engine;
	}

	private WorkflowHandle workflowHandle;

	public WorkflowHandle getWorkflowHandle() {
		if (workflowHandle == null) {
			workflowHandle = new WorkflowHandle(getProcessEngine());
			workflowHandle.setCurrentUserId(getLoginUser().getUserid());
			workflowHandle.setCurrentUser(getLoginUser());
		}
		return workflowHandle;
	}

}
