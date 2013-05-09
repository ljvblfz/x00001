package com.founder.sipbus.syweb.workflow.resource;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.fix.fixflow.core.TaskService;
import com.founder.fix.fixflow.core.task.TaskInstance;
import com.founder.fix.fixflow.core.task.TaskQuery;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.DateJsonValueProcessor;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.WorkflowBaseResource;



@Component
@Scope(value = "prototype")
@RestletResource(urls = "/workflowtasks")
public class MyTasksResource extends WorkflowBaseResource{
	
	private Log logger = LogFactory.getLog(MyTasksResource.class);



	Connection connection=null;

	@Override
	protected void doInit() {
	}
	
	@Get
	public Representation getTasks(Representation entity) { 
//		form=new Form(entity);
//		Map<String,String> vMap=form.getValuesMap();
//		String businessKey=vMap.get("businessKey");
//		String processKey=vMap.get("processKey");
		
		TaskService ts=getWorkflowHandle().getTaskService();
		
		
		TaskQuery taskQuery = ts.createTaskQuery();
		String loginUserId = getLoginUser().getUserid();
		List<TaskInstance>  taskList = taskQuery.taskCandidateUser(loginUserId).taskAssignee(loginUserId).taskNotEnd().orderByTaskCreateTime().desc().listPagination(1 , 12);
		
//		taskQuery.task
		
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
		
		List aList=new ArrayList();
		for(TaskInstance taskInstance : taskList){
			aList.add(taskInstance.getPersistentState());
		}
		config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm")); 
		JSON jp = JSONSerializer.toJSON(aList,config);
		
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp)); 
	} 
	
}
