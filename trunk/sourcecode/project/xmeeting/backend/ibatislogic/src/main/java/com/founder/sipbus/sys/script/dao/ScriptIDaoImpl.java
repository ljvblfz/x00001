package com.founder.sipbus.sys.script.dao;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.BaseIDao;
import com.founder.sipbus.common.page.PageRequest;
import com.founder.sipbus.common.page.PageResponse;
import com.founder.sipbus.sys.script.vo.ScriptIVO;

@Component
public class ScriptIDaoImpl extends BaseIDao {

	public PageResponse<ScriptIVO> queryScriptInfoByPage(
			PageRequest pageRequest, Map<String, String> map) {

		return this.queryPageAuto(pageRequest, map,
				"sys.script.queryScriptbypage");
	}
	
	
	
	public PageResponse<ScriptIVO> queryScriptInfoForHrsByPage(
			PageRequest pageRequest, Map<String, String> map) {

		return this.queryPageAuto(pageRequest, map,
				"sys.script.queryScriptForHrsbypage");
	}
	
	public PageResponse<ScriptIVO> queryScriptLogInfoByPage(
			PageRequest pageRequest, Map<String, String> map) {

		return this.queryPageAuto(pageRequest, map,
				"sys.script.queryScriptLogbypage");
	}

}
