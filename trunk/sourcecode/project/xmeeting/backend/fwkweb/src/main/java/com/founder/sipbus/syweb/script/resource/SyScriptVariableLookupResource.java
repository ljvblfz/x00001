package com.founder.sipbus.syweb.script.resource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

/***
 * 
 * @author zk
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/scScriptVariableLookup/{configvariableclazz}")
public class SyScriptVariableLookupResource extends SyBaseResource {
	String configvariableclazz;
	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity);
		configvariableclazz = getAttribute("configvariableclazz");
		int i=0;
		Class configvariableclass = null;
		configvariableclass = Class.forName(configvariableclazz);
		List variablesList = new ArrayList();
		Field[] fileds=configvariableclass.getDeclaredFields();
		for(Field field:fileds){
			variablesList.add(field);
		}
		JSON jp = JSONSerializer.toJSON(variablesList, config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));
	}
}

