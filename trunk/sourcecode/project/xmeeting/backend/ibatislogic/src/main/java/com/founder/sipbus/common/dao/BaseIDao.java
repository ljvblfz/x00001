package com.founder.sipbus.common.dao;

import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.founder.sipbus.common.page.PageRequest;
import com.founder.sipbus.common.page.PageResponse;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapException;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.engine.impl.SqlMapExecutorDelegate;

public class BaseIDao extends SqlMapClientDaoSupport {
	public PageResponse query(PageRequest pageRequest, Map params, String key,
			String countKey) {
		PageResponse p = new PageResponse();
		p.setList(getSqlMapClientTemplate().queryForList(key, params,
				pageRequest.getStart(), pageRequest.getPageSize()));
		p.setTotalCount(getCount(params, countKey));
		return p;
	}

	public int getCount(Map params, String key) {
		return (Integer) getSqlMapClientTemplate().queryForObject(key, params);
	}
	
	
	
	
	public PageResponse queryPageAuto(PageRequest pageRequest, Map params, String key) {
		PageResponse p = new PageResponse();
		
		p.setList(getSqlMapClientTemplate().queryForList(key, params,
				pageRequest.getStart(), pageRequest.getPageSize()));
		p.setTotalCount(getObjectTotal(key,params));
		return p;
	}


	public PageResponse queryPageHand(PageRequest pageRequest, Map params, String key) {
		PageResponse p = new PageResponse();
		
		p.setList(getSqlMapClientTemplate().queryForList(key, params,
				pageRequest.getStart(), pageRequest.getPageSize()));
		p.setTotalCount(getCount(params, key+"Count"));
		return p;
	}
	
	
	protected long getObjectTotal(String selectQuery, Object parameterObject) {
		prepareCountQuery(selectQuery);
		// ...
		return (Long) getSqlMapClientTemplate().queryForObject(
				CountStatementUtil.getCountStatementId(selectQuery),
				parameterObject);
	}

	protected void prepareCountQuery(String selectQuery) {

		String countQuery = CountStatementUtil.getCountStatementId(selectQuery);
		if (logger.isDebugEnabled()) {
			logger.debug("Convert " + selectQuery + " to " + countQuery);
		}
		SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
		if (sqlMapClient instanceof ExtendedSqlMapClient) {
			SqlMapExecutorDelegate delegate = ((ExtendedSqlMapClient) sqlMapClient)
					.getDelegate();
			try {
				delegate.getMappedStatement(countQuery);
			} catch (SqlMapException e) {
				delegate.addMappedStatement(CountStatementUtil
						.createCountStatement(delegate
								.getMappedStatement(selectQuery)));
			}

		}
	}

}
