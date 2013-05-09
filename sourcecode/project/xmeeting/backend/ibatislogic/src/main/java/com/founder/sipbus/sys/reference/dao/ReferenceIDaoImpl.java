package com.founder.sipbus.sys.reference.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.BaseIDao;

@Component
public class ReferenceIDaoImpl extends BaseIDao {
	private Logger logger = LoggerFactory.getLogger(ReferenceIDaoImpl.class);
 
 
	/**
	 *获取combox comboxcascade 数据
	 *sys.reference.queryForReference
	 *  @param sql
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:14:04 
	 */
	public List<Map<String, Object>> getReference(String sql) {
		return (List<Map<String, Object>>) this.getSqlMapClientTemplate().queryForList( "sys.reference.queryForReference", sql) ;
		
	}
	/**
	 *	获取Autocomplete 数据
	 *	sys.reference.queryForAutocomplete
	 *  @param map
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:13:01 
	 */
	public List<Map<String, Object>> getAutocomplete(Map map  ) {
		return (List<Map<String, Object>>) this.getSqlMapClientTemplate().queryForList( "sys.reference.queryForAutocomplete", map) ;
		
	}
	/**
	 *	根据引用编码查询 sctGuid 用于删除ccktype 缓存
	 *	sys.reference.querySctGuids
	 *  @param code
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2013-3-14 下午4:13:20 
	 */
	public List<String> querySctGuids(String code) {
		return (List<String>) this.getSqlMapClientTemplate().queryForList( "sys.reference.querySctGuids", code) ;
		
	}
	
	

}
