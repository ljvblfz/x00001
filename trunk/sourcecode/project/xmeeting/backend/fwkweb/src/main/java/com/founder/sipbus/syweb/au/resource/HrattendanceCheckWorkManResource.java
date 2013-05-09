package com.founder.sipbus.syweb.au.resource;

import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.sys.au.dao.HrAttendancerecordIDaoImpl;
import com.founder.sipbus.sys.au.vo.WorkPlanIVO;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/system/au/hrattendanceCheckWorkMan")
public class HrattendanceCheckWorkManResource extends SyBaseResource {
	
	private  HrAttendancerecordIDaoImpl hrAttendancerecordIDao;




	public void setHrAttendancerecordIDao(
			HrAttendancerecordIDaoImpl hrAttendancerecordIDao) {
		this.hrAttendancerecordIDao = hrAttendancerecordIDao;
	}




	@SuppressWarnings("unchecked")
	@Get
	public Representation get(Representation entity) throws Exception {
		form = new Form(entity); 
		 Map<String, String> map = getQueryMap();
			
			PageResponse<WorkPlanIVO> p = hrAttendancerecordIDao.queryhrattendanceCheckWorkmanInfoByPage(getPageRequest(),map);
		/*	List q = p.getList();
			for(int i=0;i<q.size();i++){
				LineLinebasicinfoSearchIVO ivo = (LineLinebasicinfoSearchIVO) q.get(i);
				System.out.println(ivo.toString());
			}*/
		JSON jp = JSONSerializer.toJSON(getPageResponse(p),config);
		return getJsonGzipRepresentation(JsonUtils.genSuccessReturnJson(jp));   
	}
	
	
}

