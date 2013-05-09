<%@page import="com.founder.fix.fixflow.core.impl.persistence.deployer.DeploymentCache"%>
<%@page import="com.founder.fix.fixflow.core.impl.ProcessEngineConfigurationImpl"%>
<%@page import="com.founder.fix.fixflow.core.ProcessEngineManagement"%>
<%@page import="com.founder.fix.fixflow.core.ProcessEngine"%>
<%
	
	ProcessEngine defaultProcessEngine = ProcessEngineManagement.getDefaultProcessEngine();
	ProcessEngineConfigurationImpl processEngineConfiguration = defaultProcessEngine.getProcessEngineConfiguration();
	DeploymentCache deploymentCache = processEngineConfiguration.getDeploymentCache();
	deploymentCache.cleanProcessDefinitionCache();
	//清空流程其他缓存
	processEngineConfiguration.getCacheHandler().cleanCacheData();
%>