package com.founder.sipbus.syweb.script.resource;

import java.util.List;

import net.sf.json.JSONSerializer;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.script.dao.SyScriptDaoImpl;
import com.founder.sipbus.syweb.script.po.SyScript;

@Component
@Scope(value = "prototype")
@RestletResource(urls = "/scriptValidateSpringBeanName/{beanname}")
public class SyScriptValidationBeanNameResource extends SyBaseResource {
	private SyScriptDaoImpl syScriptDao;

	private String beanname;
	private String newSpringBeanName;

	@Get
	public Representation get(Representation entity) {

		beanname = ((String) this.getQueryMap().get("beanName")).trim();
		newSpringBeanName = ((String) this.getQueryMap().get("newSpringBeanName")).trim();
		SyScript scScript = new SyScript();
		scScript.setBeanName(beanname);
		scScript.setDelFlag(0);
		List<SyScript> al = syScriptDao.findByExample(scScript);
		if(newSpringBeanName.equalsIgnoreCase(beanname)){
			return getJsonGzipRepresentation(JSONSerializer.toJSON(
					"{result:true}", config));
		}
		if (al.size() == 0) {

			return getJsonGzipRepresentation(JSONSerializer.toJSON(
					"{result:true}", config));
		}

		return getJsonGzipRepresentation(JSONSerializer.toJSON(
				"{result:false,message:\"Spring beanName " + beanname + " 已被使用。\"}", config));
	}


	public void setSyScriptDao(SyScriptDaoImpl syScriptDao) {
		this.syScriptDao = syScriptDao;
	}


}

