package com.founder.sipbus.syweb.au.resource;

import net.sf.json.JSONObject;

import org.restlet.data.Encoding;
import org.restlet.data.Status;
import org.restlet.engine.application.EncodeRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.constant.SessionAttrConstant;
import com.founder.sipbus.common.represenation.JsonRepresentation;
import com.founder.sipbus.sys.au.dao.AuMenuIDaoImpl;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.dao.AuMenuDaoImpl;


/***
 * 
 * @author Steven
 * http://www.lifeba.org
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/usermenu/{domain}")
public class UserMenuResource extends SyBaseResource{
	
	private String domain;
	
//	private AuMenuDaoImpl auMenuDao;
	
	private AuMenuIDaoImpl auMenuIDao;
	
	public void setAuMenuIDao(AuMenuIDaoImpl auMenuIDao) {
		this.auMenuIDao = auMenuIDao;
	}

//	public void setAuMenuDao(AuMenuDaoImpl auMenuDao) {
//		this.auMenuDao = auMenuDao;
//	}

	@Override
    protected void doInit() throws ResourceException {
		if(getRequestAttributes()!=null){
			domain =  (String) getRequestAttributes().get("domain");
		}
	}
	
	@Get
	public Representation get(Representation entity) throws Exception {
		JSONObject menuJson=(JSONObject)getHttpSession().getAttribute(SessionAttrConstant.LOGINUSER_MENU+domain);
		if(null==menuJson){
			menuJson = auMenuIDao.findByCondition(getLoginUser().getUserid(),domain);
			getHttpSession().setAttribute(SessionAttrConstant.LOGINUSER_MENU+domain,menuJson);
		}
		getResponse().setStatus(Status.SUCCESS_OK);   
		return new EncodeRepresentation(Encoding.GZIP,new JsonRepresentation(menuJson));   
	}
}
