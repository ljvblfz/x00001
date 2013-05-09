package com.founder.sipbus.syweb.au.resource;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.restlet.data.Encoding;
import org.restlet.engine.application.EncodeRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.represenation.JsonRepresentation;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.po.AuAuthorities;


/***
 * 
 * @author Steven
 * http://www.lifeba.org
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/toolbar/{authId}")
public class UserToolBarResource extends SyBaseResource{
	
	private String authId;
	

	@Override
    protected void doInit() throws ResourceException {
		if(getRequestAttributes()!=null){
			authId =  (String) getRequestAttributes().get("authId");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
//		JSONObject menuJson = auMenuDao.findMenuListByUserName(getLoginUser().getUserid(),domain);
//		getResponse().setStatus(Status.SUCCESS_OK);   
		
		List<AuAuthorities>  authList=getLoginUser().getAuthorities();
		List<String> sonAuthIdList=new ArrayList<String>();
		for(AuAuthorities auth : authList){
			if(auth.getParentId()!=null&&auth.getParentId().equals(authId)){
				sonAuthIdList.add(auth.getAuthorityId());
			}
		}
		return new EncodeRepresentation(Encoding.GZIP,new JsonRepresentation(JSONArray.fromObject(sonAuthIdList)));   
	}
}
