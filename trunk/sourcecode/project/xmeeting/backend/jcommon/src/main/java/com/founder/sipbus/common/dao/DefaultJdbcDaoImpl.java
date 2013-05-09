package com.founder.sipbus.common.dao;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * 
 * @author cw peng
 *
 * @param <E>
 */

@SuppressWarnings("unchecked")
public class DefaultJdbcDaoImpl extends JdbcDaoSupport{

	/**
	 * @author Tracychen
	 * 
	 * @version 2012-4-11
	 * 
	 */
    @SuppressWarnings("rawtypes")
	public List queryBySql(String sql,Class clazz) {
//            final String sql = "Select * from " + TABLE_NAME;
            return getJdbcTemplate().query(sql, new BeanPropertyRowMapper(clazz));
    }

    
}
