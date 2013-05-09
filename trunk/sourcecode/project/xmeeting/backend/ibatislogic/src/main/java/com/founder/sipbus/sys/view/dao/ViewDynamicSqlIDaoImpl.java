package com.founder.sipbus.sys.view.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.BaseIDao;
import com.founder.sipbus.common.page.PageRequest;
import com.founder.sipbus.common.page.PageResponse;

@Component
public class ViewDynamicSqlIDaoImpl extends BaseIDao {
	private Logger logger = LoggerFactory.getLogger(ViewDynamicSqlIDaoImpl.class);

	 
	
	public PageResponse queryDataGridByPage(PageRequest pageRequest, Map<String,String> params) { 
		String queryKey="sys.view.queryViewDataGrid";
		String queryCountKey="sys.view.queryViewDataGridCount";
		return this.query(pageRequest, params, queryKey,queryCountKey);
	}
	
	public PageResponse queryDataGridByPage(PageRequest pageRequest, Map<String,String> params,String queryKey) {  
		return this.queryPageAuto(pageRequest, params, queryKey); 
	}

	public List queryDataGridAll(Map<String, String> param) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList("sys.view.queryViewDataGrid", param);
	}

	public List queryDataGridAll(Map<String, String> param,
			String originalsqltext) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList(originalsqltext, param);

	}
	
	
}
