package com.founder.sipbus.syweb.au.resource;

import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.dispatch.gis.dao.HrStaffrecordsDaoImpl;
import com.founder.sipbus.dispatch.gis.po.HrStaffrecords;
import com.founder.sipbus.dispatch.workplan.dao.PbDisperWorkplanDetailDaoImpl;
import com.founder.sipbus.dispatch.workplan.po.PbDisperWorkplanDetail;
import com.founder.sipbus.sys.au.dao.HrAttendancerecordIDaoImpl;
import com.founder.sipbus.sys.au.vo.WorkPlanIVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;
import com.founder.sipbus.syweb.au.dao.HrattendancelogDaoImpl;
import com.founder.sipbus.syweb.au.po.Hrattendancelog;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/system/au/hrattendanceWorkPlan")
public class HrWorkPlanResource extends SyBaseResource {
	
	private  HrAttendancerecordIDaoImpl hrAttendancerecordIDao;
	private PbDisperWorkplanDetailDaoImpl pbDisperWorkplanDetailDao;
	private HrStaffrecordsDaoImpl hrStaffrecordsDao;
	private HrattendancelogDaoImpl hrattendancelogDao;
	
	
	
	
	public void setHrattendancelogDao(HrattendancelogDaoImpl hrattendancelogDao) {
		this.hrattendancelogDao = hrattendancelogDao;
	}




	public void setHrStaffrecordsDao(HrStaffrecordsDaoImpl hrStaffrecordsDao) {
		this.hrStaffrecordsDao = hrStaffrecordsDao;
	}




	public void setPbDisperWorkplanDetailDao(
			PbDisperWorkplanDetailDaoImpl pbDisperWorkplanDetailDao) {
		this.pbDisperWorkplanDetailDao = pbDisperWorkplanDetailDao;
	}




	public void setHrAttendancerecordIDao(
			HrAttendancerecordIDaoImpl hrAttendancerecordIDao) {
		this.hrAttendancerecordIDao = hrAttendancerecordIDao;
	}




	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		 Map<String, String> map = getQueryMap();
			
			PageResponse<WorkPlanIVO> p = hrAttendancerecordIDao.queryWorkPlanInfoByPage(getPageRequest(),map);
		/*	List q = p.getList();
			for(int i=0;i<q.size();i++){
				LineLinebasicinfoSearchIVO ivo = (LineLinebasicinfoSearchIVO) q.get(i);
				System.out.println(ivo.toString());
			}*/
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	@Delete
	public Representation delete(Representation entity) { 
		form = new Form(entity); 
		String idsStr=form.getFirstValue("dwdguids");
		String jobnum = form.getFirstValue("jobnum");
			PbDisperWorkplanDetail pdd = pbDisperWorkplanDetailDao.findById(idsStr);
			List list = hrStaffrecordsDao.search(getLoginUser().getUserid());
			HrStaffrecords hr = (HrStaffrecords) list.get(0);
			pdd.setUserid(hr.getUserid());
		Hrattendancelog hrlog = new Hrattendancelog();
		hrlog.setCreateBy(getLoginUser().getUserid());
		hrlog.setCreateDt(new java.util.Date());
		hrlog.setOlduserid(jobnum);
		hrlog.setNewuserid(getLoginUser().getUserid());
		hrattendancelogDao.add(hrlog);
		//return getJsonGzipRepresentation(getDefaultDeleteReturnJson());
		JSONObject jo = new JSONObject();
		jo.put("ok", "true");
		return getJsonGzipRepresentation(jo);
	}
	
}
