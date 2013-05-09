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
@RestletResource(urls = "/scriptValidatescriptName/{scriptname}")
public class SyScriptValidationNameResource extends SyBaseResource {
	private SyScriptDaoImpl syScriptDao;

	private String scriptname;
 

	@Get
	public Representation get(Representation entity) {

		scriptname = ((String) this.getQueryMap().get("scriptName")).trim();
		SyScript scScript = new SyScript();
		scScript.setScriptName(scriptname);
		scScript.setDelFlag(0);
		List<SyScript> al = syScriptDao.findByExample(scScript);
		if (al.size() == 0) {

			return getJsonGzipRepresentation(JSONSerializer.toJSON(
					"{result:true}", config));
		}

		return getJsonGzipRepresentation(JSONSerializer.toJSON(
				"{result:false,message:\"脚本名称 " + scriptname + " 已被使用。\"}", config));
	}


	public SyScriptDaoImpl getSyScriptDao() {
		return syScriptDao;
	}


	public void setSyScriptDao(SyScriptDaoImpl syScriptDao) {
		this.syScriptDao = syScriptDao;
	}


}
