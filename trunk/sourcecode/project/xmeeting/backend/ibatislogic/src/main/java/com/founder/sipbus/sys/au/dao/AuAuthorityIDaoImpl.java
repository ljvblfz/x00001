package com.founder.sipbus.sys.au.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.founder.sipbus.sys.au.vo.AuAuthoritySimpleVO;
import com.founder.sipbus.sys.au.vo.AuMenuSimpleVO;

@Component
public class AuAuthorityIDaoImpl extends SqlMapClientDaoSupport {

	 
	
	 
	public List<AuAuthoritySimpleVO> findAll() { 
		List<AuAuthoritySimpleVO> list = (List<AuAuthoritySimpleVO>)getSqlMapClientTemplate().queryForList( "sys.authority.findAll"); 
		return list;
	}
}
