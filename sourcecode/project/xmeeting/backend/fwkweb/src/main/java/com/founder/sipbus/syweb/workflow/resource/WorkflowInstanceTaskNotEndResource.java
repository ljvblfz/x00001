package com.founder.sipbus.syweb.workflow.resource;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.fix.fixflow.core.task.TaskInstance;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.WorkflowBaseResource;



@Component
@Scope(value = "prototype")
@RestletResource(urls = "/wfinst/{instId}/task/notend")
public class WorkflowInstanceTaskNotEndResource extends WorkflowBaseResource{
	
	private Log logger = LogFactory.getLog(WorkflowInstanceTaskNotEndResource.class);



	private String instId=null;
	@Override
	protected void doInit() {
		instId=getAttribute("instId");
	}
	
	@SuppressWarnings("unchecked")
	@Get
	public Representation getSvgStr(Representation entity) {
//		String taskId = (String)getQueryMap().get("taskId");
		// String isStartProcess=vMap.get("isStartProcess");-

		//ProcessInstance processInstance = getWorkflowHandle().getProcessInstance(instId);
		
		
		List<TaskInstance> list = getWorkflowHandle().getProcessEngine().getTaskService().createTaskQuery().orderByTaskCreateTime().asc().processInstanceId(instId).taskNotEnd().list();
		@SuppressWarnings("rawtypes")
		List returnList=new ArrayList();
		if(null!=list){
			for(TaskInstance token : list){
				returnList.add(token.getPersistentState());
			}
		}

//		List<Map<String, Object>> commadList = getWorkflowHandle().getProcessCommandByTaskId(taskId);
//		System.out.println(commadList); 
		
		JSON jp = JSONSerializer.toJSON(returnList,config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp)); 
	} 
	
}
