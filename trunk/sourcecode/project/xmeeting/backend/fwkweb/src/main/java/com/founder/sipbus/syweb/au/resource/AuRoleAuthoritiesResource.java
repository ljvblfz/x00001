package com.founder.sipbus.syweb.au.resource;

import net.sf.json.JSONArray;

import org.restlet.data.Encoding;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.engine.application.EncodeRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.represenation.JsonRepresentation;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.dao.AuAuthoritiesDaoImpl;
import com.founder.sipbus.syweb.au.dao.AuRoleAuthoritiesDaoImpl;

@Component
@Scope(value = "prototype")
@RestletResource(urls = "/auRole/{guid}/auAuthority")
public class AuRoleAuthoritiesResource extends SyBaseResource {

	private String guid;

 

	private AuRoleAuthoritiesDaoImpl auRoleAuthoritiesDao;

	private AuAuthoritiesDaoImpl auAuthoritiesDao;

	public AuAuthoritiesDaoImpl getAuAuthoritiesDao() {
		return auAuthoritiesDao;
	}

	public void setAuAuthoritiesDao(AuAuthoritiesDaoImpl auAuthoritiesDao) {
		this.auAuthoritiesDao = auAuthoritiesDao;
	}

	@Override
	protected void doInit() throws ResourceException {
	}

	@Put
	public Representation put(Representation entity) {
		form = new Form(entity);

		String idsStr = form.getFirstValue("authorityIds");
		guid = (String) getRequestAttributes().get("guid");
		
		if (guid != null) {
		
			if (idsStr != null) {

			} else{idsStr="";}
			
			String[] ids= idsStr.split(",");
			 
			auRoleAuthoritiesDao.updateRoleAuthorities(guid, ids);
		}

		return getJsonGzipRepresentation(this.getDefaultEditReturnJson());

	}

	@Get
	public Representation get(Representation entity) throws Exception {
 
		guid = (String) getRequestAttributes().get("guid");
		JSONArray menuJson = auAuthoritiesDao.findAuthorities(guid);
		getResponse().setStatus(Status.SUCCESS_OK);
		return new EncodeRepresentation(Encoding.GZIP, new JsonRepresentation(
				menuJson));
	}


	
	public void setAuRoleAuthoritiesDao(AuRoleAuthoritiesDaoImpl aad) {
		this.auRoleAuthoritiesDao = aad;
	}

	public AuRoleAuthoritiesDaoImpl getAuRoleAuthoritiesDao() {
		return auRoleAuthoritiesDao;
	}

}
