package com.founder.sipbus.syweb.menu.resource;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.dao.AuAuthoritiesDaoImpl;
import com.founder.sipbus.syweb.au.dao.AuMenuDaoImpl;
import com.founder.sipbus.syweb.au.po.AuAuthorities;
import com.founder.sipbus.syweb.au.po.AuMenu;

@Component
@Scope(value = "prototype")
@RestletResource(urls = "/auauthority/saveinfo")
public class SaveAuAuthorityResource extends SyBaseResource {
 
	private AuAuthoritiesDaoImpl auAuthoritiesDao;

	private static String OPER_ADD = "add";
	private static String OPER_CLONE = "clone";
	private static String OPER_MODIFY = "modify";
	private static String OPER_DELETE = "delete";
	private static String OPER_GENERATE = "generate";

	@Override
	protected void doInit() throws ResourceException {
	}

	@Post
	public Representation post(Representation entity) {
		form = new Form(entity);
		String oper = form.getFirstValue("oper");
		if (oper.equals(OPER_ADD)||OPER_CLONE.equals(oper)) {
			doAdd();
		} else if (oper.equals(OPER_MODIFY)) {
			doModify();
		} else if (oper.equals(OPER_DELETE)) {
			doRemove();
		}  
		return getJsonGzipRepresentation(getDefaultReturnJson());
	}

	private void doModify() {
		String authorityId = form.getFirstValue("authorityId");
		AuAuthorities auAuthorities = auAuthoritiesDao.findById(authorityId); 
		PMGridCopyUtil.copyGridToDto(auAuthorities, form.getValuesMap());
	}

	private void doRemove() {
		String authorityId = form.getFirstValue("authorityId"); 
		AuAuthorities auAuthorities = auAuthoritiesDao.findById(authorityId); 
		auAuthorities.setDelFlag(1);
	}

	 

	private void doAdd() { 
		AuAuthorities auAuthorities =new AuAuthorities();
		PMGridCopyUtil.copyGridToDto(auAuthorities, form.getValuesMap());  
		auAuthoritiesDao.add(auAuthorities);
	}
	//==============IOC=======================================
	 

	public void setAuAuthoritiesDao(AuAuthoritiesDaoImpl auAuthoritiesDao) {
		this.auAuthoritiesDao = auAuthoritiesDao;
	}

}
