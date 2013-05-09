package com.founder.sipbus.syweb.workflow.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.fix.fixflow.core.task.TaskInstance;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.DateJsonValueProcessor;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.WorkflowBaseResource;



@Component
@Scope(value = "prototype")
@RestletResource(urls = "/wfinst/{instId}/task/end")
public class WorkflowInstanceTaskEndResource extends WorkflowBaseResource{
	
	private Log logger = LogFactory.getLog(WorkflowInstanceTaskEndResource.class);



	private String instId=null;
	@Override
	protected void doInit() {
		instId=getAttribute("instId");
	}
	
	@SuppressWarnings("unchecked")
	@Get
	public Representation getSvgStr(Representation entity) {
		form = new Form(entity);
		
		// String isStartProcess=vMap.get("isStartProcess");

		//ProcessInstance processInstance = getWorkflowHandle().getProcessInstance(instId);
		
//		getWorkflowHandle().getProcessEngine().getTaskService().createTaskQuery().
		
		List<TaskInstance> list = getWorkflowHandle().getProcessEngine().getTaskService().createTaskQuery().orderByEndTime().asc().processInstanceId(instId).taskIsEnd().list();
		@SuppressWarnings("rawtypes")
		List returnList=new ArrayList();
		if(null!=list){
			for(TaskInstance token : list){
				returnList.add(token.getPersistentState());
			}
		}
		config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss")); 
		JSON jp = JSONSerializer.toJSON(returnList,config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp)); 
	} 
	
}
