package com.founder.sipbus.syweb.menu.resource;

import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONArray;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.sys.au.dao.AuAuthorityIDaoImpl;

@Component
@Scope(value = "prototype")
@RestletResource(urls = "/auauthority/treedynamicload")
public class AuAuthorityTreeDynamicLoadResource extends SyBaseResource { 
	
	private AuAuthorityIDaoImpl auAuthorityIDao; 

	@Override
	protected void doInit() throws ResourceException {
	}

	@Get
	public Representation get(Representation entity) { 
		List<com.founder.sipbus.sys.au.vo.AuAuthoritySimpleVO>  listOfAuMenu=auAuthorityIDao.findAll();
		JSON jp=JSONArray.fromObject(listOfAuMenu, config); 
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp)); 
	}

	public void setAuAuthorityIDao(AuAuthorityIDaoImpl auAuthorityIDao) {
		this.auAuthorityIDao = auAuthorityIDao;
	}

 

 
}
