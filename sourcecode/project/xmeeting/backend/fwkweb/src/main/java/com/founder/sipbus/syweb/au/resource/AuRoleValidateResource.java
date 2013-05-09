package com.founder.sipbus.syweb.au.resource;

import java.util.List;

import net.sf.json.JSONSerializer;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.dao.AuRoleDaoImpl;
import com.founder.sipbus.syweb.au.po.AuRoles;

@Component
@Scope(value = "prototype")
@RestletResource(urls = "/auRoleValidate/{name}")
public class AuRoleValidateResource extends SyBaseResource {
	private AuRoleDaoImpl auRoleDao;

	private String name;
 

	@Get
	public Representation get(Representation entity) {

		name = ((String) this.getQueryMap().get("name")).trim();
		AuRoles auRole = new AuRoles();
		auRole.setName(name);
		List<AuRoles> al = auRoleDao.findByExample(auRole);
		if (al.size() == 0) {

			return getJsonGzipRepresentation(JSONSerializer.toJSON(
					"{result:true}", config));
		}

		return getJsonGzipRepresentation(JSONSerializer.toJSON(
				"{result:false,message:\"角色名 " + name + " 已被使用。\"}", config));
	}

	public void setAuRoleDao(AuRoleDaoImpl auRoleDao) {
		this.auRoleDao = auRoleDao;
	}

	public AuRoleDaoImpl getAuRoleDao() {
		return auRoleDao;
	}

}
