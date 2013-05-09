package com.founder.sipbus.syweb.au.resource;

import java.util.ArrayList;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.dao.AuRoleDaoImpl;
import com.founder.sipbus.syweb.au.dao.SysMemberofroleDaoImpl;
import com.founder.sipbus.syweb.au.po.AuRoles;

@Component
@Scope(value = "prototype")
@RestletResource(urls = "/auRole")
public class AuRolesResource extends SyBaseResource {
	private AuRoleDaoImpl auRoleDao;
	private SysMemberofroleDaoImpl sysMemberofroleDao;

	public SysMemberofroleDaoImpl getSysMemberofroleDao() {
		return sysMemberofroleDao;
	}

	public void setSysMemberofroleDao(SysMemberofroleDaoImpl sysMemberofroleDao) {
		this.sysMemberofroleDao = sysMemberofroleDao;
	}

	@Override
	protected void doInit() throws ResourceException {
	}

	 
	@Get
	public Representation get(Representation entity) throws Exception {
 
		AuRoles aurole = new AuRoles();
		PMGridCopyUtil.copyGridToDto(aurole, getQueryMap());
		 
		PageResponse<AuRoles> p = auRoleDao.findPage(getPageRequest(),
				fillDetachedCriteria(AuRoles.class, aurole));
		JSON jp = JSONSerializer.toJSON(getPageResponse(p), config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));
	}

	@Post
	public Representation post(Representation entity) throws ResourceException {

		form = new Form(entity);
		AuRoles aurole = new AuRoles();
		PMGridCopyUtil.copyGridToDto(aurole, form.getValuesMap());
		// aurole.setCreateDt(new Date());
		// aurole.setUpdateDt(new Date());
		// aurole.setCreateBy(getLoginUser().getUserid());
		// aurole.setUpdateBy(getLoginUser().getUserid());
		auRoleDao.add(aurole);
		return getJsonGzipRepresentation(getDefaultAddReturnJson());
	}

	@Delete
	public Representation delete(Representation entity) {
		form = new Form(entity);
		StringBuilder sb=new StringBuilder();
		String ids = form.getFirstValue("guids");
		ArrayList<String> sa1=new ArrayList<String>();
	//	ArrayList<String> sa2=new ArrayList<String>();
		if (ids != null && !ids.isEmpty()) {
			String[] id = ids.split(",");
			ArrayList<Long> la = sysMemberofroleDao.countMemberByAuRoleGuid(id);
		 
			if(la.size()>0){
				int size = la.size();
				for(int i=0;i<size;i++){
					if(la.get(i).longValue()==0L){
						sa1.add(id[i]);
					}else{
					//sa2.add(id[i]);	
					}
					
				}
			//ArrayList<AuRoles> ara = new ArrayList<AuRoles>();
				
				
			for (String tid : sa1) {
				AuRoles tar=auRoleDao.findById(tid);
				sb.append(tar.getName());
				auRoleDao.delete(tid);
				 
				sb.append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			this.setStatus(Status.SUCCESS_OK, sb+"成功删除！");
			}else this.setStatus(Status.SUCCESS_OK,  "删除失败！");
		}  else{
			this.setStatus(Status.SUCCESS_OK,  "删除失败！");
		}
		JSONObject jo = this.getDefaultDeleteReturnJson();
	jo.put("message", sb+"成功删除！");
		return getJsonGzipRepresentation(jo);
	}

	public void setAuRoleDao(AuRoleDaoImpl auRoleDao) {
		this.auRoleDao = auRoleDao;
	}

	public AuRoleDaoImpl getAuRoleDao() {
		return auRoleDao;
	}

}
