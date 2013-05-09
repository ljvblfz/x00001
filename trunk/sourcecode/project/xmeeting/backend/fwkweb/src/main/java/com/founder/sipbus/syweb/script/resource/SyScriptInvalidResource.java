package com.founder.sipbus.syweb.script.resource;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.script.dao.SyScriptDaoImpl;
import com.founder.sipbus.syweb.script.po.SyScript;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syScriptInvalid")
public class SyScriptInvalidResource extends SyBaseResource {
	
	private SyScriptDaoImpl syScriptDao;
	
	
	

	public void setSyScriptDao(SyScriptDaoImpl syScriptDao) {
		this.syScriptDao = syScriptDao;
	}

	@SuppressWarnings("unchecked")
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("gsguids");
		String[] ids=idsStr.split(",");
		for(String id:ids){
			SyScript sg = syScriptDao.findById(id);
			sg.setStatus("0");
		}
		return getJsonGzipRepresentation(getMessageReturnJson("脚本已失效！"));
		//return getNotAllowedRepresentation("选择");
	}
}
