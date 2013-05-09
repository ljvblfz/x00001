package com.founder.sipbus.syweb.script.resource;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.fwk.groovy.annotation.Description;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

/***
 * 
 * @author Zk
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/scScriptFunctionLookup/{configfunctionclazz}")
public class SyScriptFunctionLookupResource extends SyBaseResource {
	String configfunctionclazz;
	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		configfunctionclazz = getAttribute("configfunctionclazz");
		form = new Form(entity);
		Class configfunctionclass = null;
		configfunctionclass = Class.forName(configfunctionclazz);
		int i=0;
		List methodsList = new ArrayList();
		//Method[] methods=EmployeeSalaryCalcFunction.class.getDeclaredMethods();
		Method[] methods=configfunctionclass.getDeclaredMethods();
		for(Method method:methods){
			Description  description=method.getAnnotation(Description.class);
			//System.out.println("method desc---->"+description.desc());
			JSONObject jo = new JSONObject();
			String type3="";
			String type4 = "";
			Type[] paramTypeList = method.getGenericParameterTypes();// 方法的参数列表
			 for (Type paramType : paramTypeList) {
			        //System.out.println("  " + paramType);// 参数类型
			        String type = paramType.toString();
			        String type2 = type.replace('.', ',');
			        //System.out.println(type2);
			        String []typeString = type2.split(",");
			        String realtype = typeString[2];
			        type3+= realtype+",";
			       // int laseone = type3.indexOf(type3.length());
			      type4 = type3.substring(0, type3.length()-1);
			 }
			jo.put("functionName", method.getName()+"("+type4+")");
			if(description==null){
				jo.put("description", "");
			}else{
				jo.put("description", description.desc());
			}
			
			methodsList.add(jo);
			//System.out.println(method.toString());
		}
		JSON jp = JSONSerializer.toJSON(methodsList, config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));
	}
}
