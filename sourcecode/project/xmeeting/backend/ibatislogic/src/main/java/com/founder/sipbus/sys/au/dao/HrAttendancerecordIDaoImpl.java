package com.founder.sipbus.sys.au.dao;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.BaseIDao;
import com.founder.sipbus.common.page.PageRequest;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.sys.au.vo.HrAttendancerecordIVO;
import com.founder.sipbus.sys.au.vo.WorkPlanIVO;

@Component
public class HrAttendancerecordIDaoImpl extends BaseIDao {

	public List<HrAttendancerecordIVO> findAll(String userid) { 
		List<HrAttendancerecordIVO> list = (List<HrAttendancerecordIVO>)getSqlMapClientTemplate().queryForList( "sys.hrattendancerecord.isDispatcher",userid); 
		return list;
	}
	
	public List<HrAttendancerecordIVO> isindispatchplan(String userid) { 
		List<HrAttendancerecordIVO> list = (List<HrAttendancerecordIVO>)getSqlMapClientTemplate().queryForList( "sys.hrattendancerecord.isindispatchplan",userid); 
		return list;
	}
	
	public PageResponse<WorkPlanIVO> queryWorkPlanInfoByPage(PageRequest pageRequest,
			Map<String, String> map) {

		return this.queryPageAuto(pageRequest, map,
				"sys.hrattendancerecord.diaplayplan");
	}
	
	public PageResponse<WorkPlanIVO> queryhrattendanceCheckWorkmanInfoByPage(PageRequest pageRequest,
			Map<String, String> map) {

		return this.queryPageAuto(pageRequest, map,
				"sys.hrattendancerecord.checkworkman");
	}
}
