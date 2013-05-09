package com.founder.sipbus.syweb.menu.resource;

import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.dao.AuMenuDaoImpl;
import com.founder.sipbus.syweb.au.po.AuMenu;

@Component
@Scope(value = "prototype")
@RestletResource(urls = "/aumenu/previewdesc/{menuId}")
public class AuMenuDescPreviewResource extends SyBaseResource {

	private Logger logger = LoggerFactory.getLogger(AuMenuDescPreviewResource.class);
	private String menuId;
	
	
	@Override
	protected void doInit() throws ResourceException {
	}
	
	@Get
	public Representation get(Representation entity) {
		menuId=getAttribute("menuId");
		if(logger.isDebugEnabled()){
			logger.debug("menuId is {0}",menuId);
		}
		AuMenu auMenu =  auMenuDao.findById(menuId);
//		String desc=auMenu.getMenuDescription();
		if(null==auMenu){
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);   
			return null;
		}
		
		
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(auMenu));
	}

	private AuMenuDaoImpl auMenuDao; 
	public void setAuMenuDao(AuMenuDaoImpl auMenuDao) {
		this.auMenuDao = auMenuDao;
	}
	 

}
