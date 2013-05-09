package com.founder.sipbus.syweb.script.resource;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.script.dao.SyScriptDaoImpl;
import com.founder.sipbus.syweb.script.po.SyScript;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/syScriptDecisiontableScript")
public class SyScriptDecisiontableScriptResource extends SyBaseResource {
	
	private SyScriptDaoImpl syScriptDao;
	

	public void setSyScriptDao(SyScriptDaoImpl syScriptDao) {
		this.syScriptDao = syScriptDao;
	}

	@SuppressWarnings("unchecked")
	@Post
	public Representation post(Representation entity)
			throws ResourceException {

		form = new Form(entity);
		SyScript syScript = new SyScript();
		PMGridCopyUtil.copyGridToDto(syScript, form.getValuesMap());
		
		syScript.setDelFlag(0);
		syScript.setStatus("0");
		syScript.setCreateDt(new java.util.Date());
		syScript.setCreateBy(super.getLoginUser().getUserid());
		syScript.setUpdateDt(new java.util.Date());
		syScript.setUpdateBy(super.getLoginUser().getUserid());

		syScriptDao.add(syScript);
		JSON jp = JSONSerializer.toJSON(syScript,config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));    
	}
}