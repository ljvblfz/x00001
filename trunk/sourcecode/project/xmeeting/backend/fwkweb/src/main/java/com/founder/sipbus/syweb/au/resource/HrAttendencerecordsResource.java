/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 
package com.founder.sipbus.syweb.au.resource;

import java.util.Date;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.founder.fix.fixflow.core.impl.util.GuidUtil;
import com.founder.sipbus.common.annotation.RestletResource;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.common.util.JsonUtils;
import com.founder.sipbus.common.util.PMGridCopyUtil;
import com.founder.sipbus.sys.au.dao.HrAttendancerecordIDaoImpl;
import com.founder.sipbus.syweb.au.dao.HrAttendencerecordDaoImpl;
import com.founder.sipbus.syweb.au.po.HrAttendencerecord;
import com.founder.sipbus.syweb.au.base.SyBaseResource;

/***
 * 
 * @author Founder
 */
@Component
@Scope(value="prototype")
@RestletResource(urls="/hrAttendencerecord")
public class HrAttendencerecordsResource extends SyBaseResource {
	
	private HrAttendencerecordDaoImpl hrAttendencerecordDao;
	private HrAttendancerecordIDaoImpl hrAttendancerecordIDao;
	
	
	public void setHrAttendancerecordIDao(
			HrAttendancerecordIDaoImpl hrAttendancerecordIDao) {
		this.hrAttendancerecordIDao = hrAttendancerecordIDao;
	}

	public void setHrAttendencerecordDao(HrAttendencerecordDaoImpl hrAttendencerecordDao) {
		this.hrAttendencerecordDao = hrAttendencerecordDao;
	}
	
	@Post
	public Representation post(Representation entity)
			throws ResourceException {
		form = new Form(entity);
		List list = hrAttendancerecordIDao.findAll(getLoginUser().getUserid());
		System.out.println(getLoginUser().getUserid());
		System.out.println("是否调度员===>"+list.size());
		if(list.size()>0){
			List isindispatchplanlist = hrAttendancerecordIDao.isindispatchplan(getLoginUser().getUserid());
			System.out.println("是否在当天排班中====>"+isindispatchplanlist.size());
			if(isindispatchplanlist.size()>0){
				
				HrAttendencerecord hrAttendencerecord = new HrAttendencerecord();
				hrAttendencerecord.setGuid(GuidUtil.CreateGuid());
				hrAttendencerecord.setSigndate(new Date());
				hrAttendencerecord.setJobnumber(getLoginUser().getUserid());
				hrAttendencerecord.setUsername(getLoginUser().getFullname());
				hrAttendencerecordDao.add(hrAttendencerecord);
				JSONObject jo = new JSONObject();
				jo.put("isin", "true");
				return getJsonGzipRepresentation(jo);
				//return getJsonGzipRepresentation(getDefaultAddReturnJson());  
				
			}else{
				HrAttendencerecord hrAttendencerecord = new HrAttendencerecord();
				hrAttendencerecord.setGuid(GuidUtil.CreateGuid());
				hrAttendencerecord.setSigndate(new Date());
				hrAttendencerecord.setJobnumber(getLoginUser().getUserid());
				hrAttendencerecord.setUsername(getLoginUser().getFullname());
				hrAttendencerecordDao.add(hrAttendencerecord);
				JSONObject jo = new JSONObject();
				jo.put("isin", "false");
				return getJsonGzipRepresentation(jo);
			}
		}else{
			HrAttendencerecord hrAttendencerecord = new HrAttendencerecord();
			hrAttendencerecord.setGuid(GuidUtil.CreateGuid());
			hrAttendencerecord.setSigndate(new Date());
			hrAttendencerecord.setJobnumber(getLoginUser().getUserid());
			hrAttendencerecord.setUsername(getLoginUser().getFullname());
			hrAttendencerecordDao.add(hrAttendencerecord);
			JSONObject jo = new JSONObject();
			jo.put("isin", "noin");
			return getJsonGzipRepresentation(jo);
			//return getJsonGzipRepresentation(getErrorMessageReturnJson("您不是现场调度员！"));
		}
	}
	
	public static void main(String[] args) {
		System.out.println(GuidUtil.CreateGuid());
	}
}