package com.founder.sipbus.syweb.au.resource;

import net.sf.json.JSONArray;

import org.restlet.data.Encoding;
import org.restlet.data.Status;
import org.restlet.engine.application.EncodeRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.represenation.JsonRepresentation;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.dao.AuMenuDaoImpl;

@Component
@Scope(value="prototype")
@RestletResource(urls="/usermainmenu")
public class UserMainMenuResource extends SyBaseResource{
	
	private AuMenuDaoImpl auMenuDao;
	
	public void setAuMenuDao(AuMenuDaoImpl auMenuDao) {
		this.auMenuDao = auMenuDao;
	}

	@Override
    protected void doInit() throws ResourceException {
	}
	
	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		JSONArray menuJson = auMenuDao.findMainMenuListByUserName(getLoginUser().getUserid());
		getResponse().setStatus(Status.SUCCESS_OK);   
		return new EncodeRepresentation(Encoding.GZIP,new JsonRepresentation(menuJson));   
	}
}
