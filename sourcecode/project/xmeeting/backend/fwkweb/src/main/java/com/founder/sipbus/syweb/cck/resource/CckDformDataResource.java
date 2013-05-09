package com.founder.sipbus.syweb.cck.resource;

import net.sf.json.JSON;

import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.cck.dao.SyCckContentDaoImpl;
import com.founder.sipbus.syweb.cck.service.SyCckService;

/**
 * 获取一条cck数据记录
 * urls = "/cck/dform/data/{sctGuid}/{sccGuid}"
 * @author zjl
 *
 */
@Component
@Scope(value = "prototype")
@RestletResource(urls = "/cck/dform/data/{sctGuid}/{sccGuid}")
public class CckDformDataResource extends SyBaseResource {

	private String sccGuid = "";
	private String sctGuid = "";
	private SyCckService syCckService;
	private SyCckContentDaoImpl syCckContentDao;

	@Override
	protected void doInit() throws ResourceException {
		sccGuid = getAttribute("sccGuid");
		sctGuid = getAttribute("sctGuid");
	}

 
	/**
	 *获取一条cck数据记录
	 *通过 sctGuid,sccGuid 
	 *  @param entity
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:45:21 
	 */
	@Get
	public Representation get(Representation entity) {
		form = new Form(entity);
		JSON json = syCckService.findById(sctGuid,sccGuid );
		if (null == json) {
			getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
			return null;
		}
		return getJsonGzipRepresentation(json);
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
