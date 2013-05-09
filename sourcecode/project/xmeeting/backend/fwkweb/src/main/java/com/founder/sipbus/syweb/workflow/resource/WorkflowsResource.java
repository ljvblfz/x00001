package com.founder.sipbus.syweb.workflow.resource;

import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.WorkflowBaseResource;



@Component
@Scope(value = "prototype")
@RestletResource(urls = "/workflow/{processKey}/startandsubmit/{businessKey}")
public class WorkflowsResource extends WorkflowBaseResource{
	
	private Log logger = LogFactory.getLog(WorkflowsResource.class);



	Connection connection=null;

	private String businessKey;
	private String processKey;
	@Override
	protected void doInit() {
		businessKey=getAttribute("businessKey");
		processKey=getAttribute("processKey");
	}
	
	@Post
	public Representation startProcess(Representation entity) { 
		form=new Form(entity);
//		Map<String,String> vMap=form.getValuesMap();
//		String businessKey=vMap.get("businessKey");
//		String processKey=vMap.get("processKey");
		
		
		//create process
//		ProcessInstance processInstance = getWorkflowHandle().startProcess(processKey,businessKey,getLoginUser().getUserid());
//		
//		TaskService ts=getWorkflowHandle().getTaskService();
//		
//		TaskInstance  ti = ts.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
//		//submit process
//		getWorkflowHandle().processTask(ti.getId(), "Advance_1", "startandsubmit", "submit", null);
		
//		getWorkflowHandle().processTask(null,"Advance_1", "startandsubmit","提交流程",null,processKey,businessKey,getLoginUser().getUserid());
		
		getWorkflowHandle().startAndSubmit(processKey, businessKey, getLoginUser().getUserid());
		
//		System.out.println(businessKey+processKey+connection);
		
//		SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
//				.getResource(sessionFactory);
//		sessionHolder.getSession().connection();
//		Map<String ,String>  ip=new HashMap<String ,String> ();
//		String loginIP=getSSOManager().getSysUser().getLoginIpAddr();
//		ip.put("ip", loginIP);
//		if(logger.isTraceEnabled()){
//			logger.trace("loginIP is:  "+loginIP);
//		}
//		List<DeptLinePCIPVO> listOfDeptLinePCIPVO=realtimeMonitorIDao.getDeptLineByIP(ip); 
//		JSON jp = JSONSerializer.toJSON(listOfDeptLinePCIPVO,config);
		
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(null)); 
	} 
	
}
