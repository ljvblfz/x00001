package com.founder.sipbus.syweb.script.resource;

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
import com.founder.sipbus.syweb.cck.dao.SyCckTypeDaoImpl;
import com.founder.sipbus.syweb.cck.po.SyCckType;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/syScriptCckTypeList")
public class SyScriptCckTypeListResource extends SyBaseResource {

	private SyCckTypeDaoImpl syCckTypeDao;

	public void setSyCckTypeDao(SyCckTypeDaoImpl syCckTypeDao) {
		this.syCckTypeDao = syCckTypeDao;
	}

	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity);
		List<SyCckType> list = syCckTypeDao
				.searchCckTypeByTypetable("SY_SCRIPT_DECISIONTABLE");
		JSON jp = JSONSerializer.toJSON(list, config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));
	}
}
