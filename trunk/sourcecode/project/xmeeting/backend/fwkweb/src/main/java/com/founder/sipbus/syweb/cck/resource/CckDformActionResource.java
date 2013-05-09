package com.founder.sipbus.syweb.cck.resource;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.engine.application.EncodeRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.cck.dao.SyCckContentDaoImpl;
import com.founder.sipbus.syweb.cck.service.SyCckService;

/**
 * 处理cckform的action
 * @author zjl
 *
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/cck/dform/formaction/{action}")
public class CckDformActionResource extends SyBaseResource { 
	  
	private String action=""; 
//	private String dbtablename="";  
	private SyCckService syCckService;
	private SyCckContentDaoImpl syCckContentDao;
	private static String ACTION_ADD="add";
	private static String ACTION_EDIT="edit";
	private static String ACTION_DELETE="delete";
	private static String ACTION_EDIT_PARENT_ID="editParentId";

	@Override
	protected void doInit() throws ResourceException {
		action=getAttribute("action"); 
	}
 
	/**
	 *	 根据action 参数 调用不同方法
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:39:15 
	 */
	@Post
	public Representation post(Representation entity) {   
		form = new Form(entity); 
		if (action.equals(ACTION_ADD)) {
		return	doAdd();
		} else if (action.equals(ACTION_EDIT)) {
			return		doModify();
		} else if (action.equals(ACTION_DELETE)) {
			return		doRemove();
		} else if (action.equals(ACTION_EDIT_PARENT_ID)) {
				return		doUpdateParentId();
			}
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(null));
		  
	}
	
	
	/**
	 *	更新父id
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:39:15 
	 */
	private Representation doUpdateParentId() {
		Map map = form.getValuesMap();
		map.put("userName", getLoginUser().getUsername());
		syCckService.updateParentId(map);
		return	getJsonGzipRepresentation(getDefaultEditReturnJson()); 
	}

	/**
	 *新增
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:40:23 
	 */
	@SuppressWarnings("unchecked")
	public EncodeRepresentation doAdd(){ 
	 
		Map map = form.getValuesMap();
		map.put("userName", getLoginUser().getUsername());
		syCckService.insert(map);
	return	getJsonGzipRepresentation(getDefaultAddReturnJson().accumulate("callBackMethod", getCallbackMethod())); 
	}
	 
	/**
	 *	删除
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:40:32 
	 */
	public EncodeRepresentation doRemove(){ 
		Map map = form.getValuesMap();
		map.put("userName", getLoginUser().getUsername());
		
		String sccGuids=(String) map.get("sccGuids");
		if (StringUtils.isNotBlank(sccGuids)) {
			String ids[]= sccGuids.split(",");
			if (ids.length>0) {
				map.put("sccGuids", ids);
				int count=syCckService.delete(map);
				if (count<0) {
					setStatus(Status.CLIENT_ERROR_NOT_FOUND);
					return	getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(null)); 
				}
				return	getJsonGzipRepresentation(getDefaultDeleteReturnJson()); 
			}
		}
	setStatus(Status.CLIENT_ERROR_NOT_FOUND);
		return	getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(null)); 
		
	}
	
	/**
	 *	修改
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:40:42 
	 */
	public EncodeRepresentation doModify(){ 
		Map map = form.getValuesMap();
		map.put("userName", getLoginUser().getUsername());
		syCckService.update(map);
		return	getJsonGzipRepresentation(getDefaultEditReturnJson()); 
	}

	public SyCckContentDaoImpl getSyCckContentDao() {
		return syCckContentDao;
	}

	public void setSyCckContentDao(SyCckContentDaoImpl syCckContentDao) {
		this.syCckContentDao = syCckContentDao;
	}

	public SyCckService getSyCckService() {
		return syCckService;
	}

	public void setSyCckService(SyCckService syCckService) {
		this.syCckService = syCckService;
	}

 
}
