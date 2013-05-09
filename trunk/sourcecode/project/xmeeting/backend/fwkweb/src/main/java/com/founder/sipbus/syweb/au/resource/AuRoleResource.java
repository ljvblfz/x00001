package com.founder.sipbus.syweb.au.resource;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.memcache.util.StdMemCacheUtil;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.dao.AuRoleDaoImpl;
import com.founder.sipbus.syweb.au.dao.SysMemberofroleDaoImpl;
import com.founder.sipbus.syweb.au.po.AuRoles;
@Component
@Scope(value="prototype")
@RestletResource(urls="/auRole/{guid}")
public class AuRoleResource extends SyBaseResource{
private AuRoleDaoImpl auRoleDao;
private SysMemberofroleDaoImpl sysMemberofroleDao;
private static String KEY_GEN="SYS_ROLE_";
private String guid;
@Override
protected void doInit() throws ResourceException {
}

@Get
public Representation get(Representation entity) {
	guid =  (String) getRequestAttributes().get("guid");
//	AuRoles auRole =(AuRoles)StdMemCacheUtil.getMemCachedClient().get(KEY_GEN+guid);
//	if(null==auRole){
		AuRoles auRole = auRoleDao.findById(guid);
		if(null==auRole){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
//		auRole=auRoleDao.findById(guid)  ;
//		StdMemCacheUtil.getMemCachedClient().set(KEY_GEN+guid, auRole);
//	}
	return getJsonGzipRepresentation(JSONSerializer.toJSON(auRole,config));
}
//@Delete
//public Representation delete(Representation entity) {
//	form = new Form(entity);
//	guid =  (String) getRequestAttributes().get("guid");
//	AuRoles auRole = auRoleDao.findById(guid);
//	BigDecimal l=sysMemberofroleDao.countMember( auRole.getName()) ;
//	if(l!=null&&l.longValue()==0){
//		
//		 
//	auRoleDao.delete(guid);
//	}else{
//  this.setStatus(Status.SUCCESS_OK, "该角色在使用中，无法删除！");
//	}
//	
//	return  getJsonGzipRepresentation(this.getDefaultEditReturnJson());
//}

@Put
public Representation put(Representation entity)
		throws ResourceException {
	form = new Form(entity);
	guid =  form.getFirstValue("guid");
	AuRoles b= auRoleDao.findById(guid);
	PMGridCopyUtil.copyGridToDto(b, form.getValuesMap());
	JSONObject jo = getDefaultEditReturnJson();
	StdMemCacheUtil.getMemCachedClient().delete(KEY_GEN+guid);
	return getJsonGzipRepresentation(jo);
}

	public void setAuRoleDao(AuRoleDaoImpl auRoleDao) {
		this.auRoleDao = auRoleDao;
	}

	public AuRoleDaoImpl getAuRoleDao() {
		return auRoleDao;
	}

	public void setSysMemberofroleDao(SysMemberofroleDaoImpl sysMemberofroleDao) {
		this.sysMemberofroleDao = sysMemberofroleDao;
	}

	public SysMemberofroleDaoImpl getSysMemberofroleDao() {
		return sysMemberofroleDao;
	}

}
