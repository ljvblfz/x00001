package com.founder.sipbus.syweb.workflow.resource;

import java.sql.Connection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Put;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.WorkflowBaseResource;



@Component
@Scope(value = "prototype")
@RestletResource(urls = "/workflow/task/{taskId}")
public class TaskInstanceResource extends WorkflowBaseResource{
	
	private Log logger = LogFactory.getLog(TaskInstanceResource.class);


	private String taskId;
	
	Connection connection=null;

	@Override
	protected void doInit() {
		taskId=getAttribute("taskId");
	}
	
	@Put
	public Representation startProcess(Representation entity) { 
		form=new Form(entity);
		Map vMap=form.getValuesMap();
		String commandId=(String)vMap.get("commandId");
		String commandType=(String)vMap.get("commandType");
		String processDesc=(String)vMap.get("processDesc");
		
		getWorkflowHandle().processTask(taskId, commandId, commandType, processDesc, vMap, getLoginUser().getUserid());//(processKey, businessKey, getLoginUser().getUserid());
		
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(null)); 
	} 
	
}
