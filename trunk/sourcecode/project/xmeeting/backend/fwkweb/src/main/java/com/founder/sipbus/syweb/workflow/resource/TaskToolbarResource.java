package com.founder.sipbus.syweb.workflow.resource;

import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.WorkflowBaseResource;



@Component
@Scope(value = "prototype")
@RestletResource(urls = "/workflow/task/{taskId}/toolbar")
public class TaskToolbarResource extends WorkflowBaseResource{
	
	private Log logger = LogFactory.getLog(TaskToolbarResource.class);



	private String taskId=null;
	@Override
	protected void doInit() {
		taskId=getAttribute("taskId");
	}
	
	@Get
	public Representation getToolbar(Representation entity) {

		List<Map<String, Object>> commadList = getWorkflowHandle().getProcessCommandByTaskId(taskId);
		//System.out.println(commadList); 
		JSON jp = JSONSerializer.toJSON(commadList,config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp)); 
	} 
	
}
