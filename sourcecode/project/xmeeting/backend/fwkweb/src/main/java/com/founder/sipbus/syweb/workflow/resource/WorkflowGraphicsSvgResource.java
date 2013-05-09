package com.founder.sipbus.syweb.workflow.resource;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.restlet.data.Encoding;
import org.restlet.data.Form;
import org.restlet.engine.application.EncodeRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.fix.fixflow.core.ModelService;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.syweb.au.base.WorkflowBaseResource;



@Component
@Scope(value = "prototype")
@RestletResource(urls = "/workflow/{defId}/graphics")
public class WorkflowGraphicsSvgResource extends WorkflowBaseResource{
	
	private Log logger = LogFactory.getLog(WorkflowGraphicsSvgResource.class);



	private String defId=null;
	@Override
	protected void doInit() {
		defId=getAttribute("defId");
	}
	
	@Get
	public Representation getSvgStr(Representation entity) {
		form = new Form(entity);
		Map<String, String> vMap = form.getValuesMap();
		// String isStartProcess=vMap.get("isStartProcess");

		ModelService modelService = getWorkflowHandle().getProcessEngine()
				.getModelService();
		String svgStr=modelService.getFlowGraphicsSvg(defId);

//		String processDefinitionKey = "";
//		ProcessDefinitionBehavior processDefinition;
//		try {
//			processDefinition = modelService
//					.getProcessDefinition(processDefinitionKey);
//			String processDefinitionId = "";
//			if (processDefinition != null) {
//				processDefinitionId = processDefinition
//						.getProcessDefinitionId();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		 
//		JSON jp = JSONSerializer.toJSON(svgStr,config);
		return new EncodeRepresentation(Encoding.GZIP,new StringRepresentation(svgStr));
	} 
	
}
