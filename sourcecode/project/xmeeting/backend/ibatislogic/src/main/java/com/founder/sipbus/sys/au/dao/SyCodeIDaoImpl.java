package com.founder.sipbus.sys.au.dao;

import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

@Component
public class SyCodeIDaoImpl extends SqlMapClientDaoSupport {

	public Map getAllTypeName() {
		Map map = getSqlMapClientTemplate().queryForMap(
				"sycode.selAllTypeName", null, "TYPENAME", "TYPEDESC");
		return map;
	}

	public Map getAllCodeName(String typename) {
		Map map = getSqlMapClientTemplate().queryForMap(
				"sycode.selAllCodeName", typename, "VALUECODE", "VALUENAME");
		return map;
	}
}
