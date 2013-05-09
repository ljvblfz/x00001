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
@RestletResource(urls = "/aumenu/saveinfo")
public class SaveAuMenuResource extends SyBaseResource {

	private AuMenuDaoImpl auMenuDao;
	private AuAuthoritiesDaoImpl auAuthoritiesDao;

	private static String OPER_ADD = "add";
	private static String OPER_CLONE = "clone";
	private static String OPER_MODIFY = "modify";
	private static String OPER_MODIFYDESC = "modifydesc";
	private static String OPER_MODIFYHELP = "modifyhelp";
	private static String OPER_DELETE = "delete";
	private static String OPER_PHYSICAL_DELETE = "physicaldelete";
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
		} else if (oper.equals(OPER_MODIFYDESC)) {
			doModifyDesc();
		}else if (oper.equals(OPER_MODIFYHELP)) {
			doModifyHelp();
		}else if (oper.equals(OPER_DELETE)) {
			doRemove();
		}else if (oper.equals(OPER_PHYSICAL_DELETE)) {
			doPhysicalRemove();
		} else if (oper.equals(OPER_GENERATE)) {
			doGenerate();
		}
		return getJsonGzipRepresentation(getDefaultReturnJson());
	}

	private void doModify() {
		String menuId = form.getFirstValue("menuId");
		AuMenu auMenu = auMenuDao.findById(menuId);
		PMGridCopyUtil.copyGridToDto(auMenu, form.getValuesMap());
	}
	
	

	private void doModifyDesc() {
		String menuId = form.getFirstValue("menuId");
		String menuDescription = form.getFirstValue("menuDescription");
		AuMenu auMenu = auMenuDao.findById(menuId); 
		auMenu.setMenuDescription(menuDescription);
		
	}
	
	
	private void doModifyHelp() {
		String menuId = form.getFirstValue("menuId");
		String menuHelp = form.getFirstValue("menuHelp");
		AuMenu auMenu = auMenuDao.findById(menuId); 
		auMenu.setMenuHelp(menuHelp);
		
	}

	private void doRemove() {
		String menuId = form.getFirstValue("menuId");
		AuMenu auMenu = auMenuDao.findById(menuId);
		auMenu.setDelFlag(1);
	}
	
	private void doPhysicalRemove() {
		String menuId = form.getFirstValue("menuId");
		AuMenu auMenu = auMenuDao.findById(menuId);
		if(auMenu.getDelFlag()==1){
			auMenuDao.physicalRemove(menuId);  
		}else{
			throw new RuntimeException("不能删除本节点");
		}
	}

	private void doGenerate() {
		String menuId = form.getFirstValue("menuId");
		AuMenu auMenu = auMenuDao.findById(menuId);
//		String authorityId=auMenu.getAuthorityId();
		
		AuAuthorities auAuthorities=new AuAuthorities();
		auAuthorities.setAuthorityId(auMenu.getAuthorityId());
		auAuthorities.setAuthorityName(auMenu.getMenuName());
		auAuthorities.setAutOrder(auMenu.getMenuOrder());
		auAuthorities.setDelFlag(0);
		auAuthorities.setEnabled("1");

		String parentId=auMenu.getParentid();
		if("0".equals(parentId)){  
			auAuthorities.setParentId("0"); 
		} else{ 
			AuMenu parentAuMenu = auMenuDao.findById(parentId);
			String parentAuthorityId=parentAuMenu.getAuthorityId();
			auAuthorities.setParentId(parentAuthorityId); 
		}
//		auAuthoritiesDao.delete(authorityId);
		auAuthoritiesDao.add(auAuthorities);
		
		
	}

	private void doAdd() {
		AuMenu auMenu = new AuMenu(); 
		PMGridCopyUtil.copyGridToDto(auMenu, form.getValuesMap());  
		String menuId=auMenu.getMenuId();
		auMenu.setMenuTitle("dwztabid-"+menuId);
		auMenuDao.add(auMenu);
	}
	//==============IOC=======================================
	public void setAuMenuDao(AuMenuDaoImpl auMenuDao) {
		this.auMenuDao = auMenuDao;
	}

	public void setAuAuthoritiesDao(AuAuthoritiesDaoImpl auAuthoritiesDao) {
		this.auAuthoritiesDao = auAuthoritiesDao;
	}

}
