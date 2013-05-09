package com.founder.sipbus.common.dao;

import java.io.Serializable;
import java.sql.Date;

import com.founder.sipbus.common.po.BaseEntity;
import com.founder.sipbus.common.util.LoginInfoUtils;

/**
 * 
 * @author cw peng
 *
 * @param <E>
 */

@SuppressWarnings("unchecked")
public abstract class DefaultBaseDaoImpl<E extends BaseEntity,PK extends Serializable> extends
		AbstractBaseDaoImpl<E,PK>{

//	private Log logger = LogFactory.getLog(DefaultBaseDaoImpl.class);
	
	@Override
	public E insert(E entity) {
		appendAddUserInfo(entity);
		getHibernateTemplate().save(entity);
		getSession().flush();
		return entity;
	}
	
	private void appendAddUserInfo(E entity){
		String userId = LoginInfoUtils.getLoginUserId();
		entity.setCreateBy(userId);
		entity.setUpdateBy(userId);
		entity.setUpdateDt(new Date(System.currentTimeMillis()));
		entity.setCreateDt(new Date(System.currentTimeMillis()));
	}
	

	private void appendUpdateUserInfo(E entity){
		String userId = LoginInfoUtils.getLoginUserId();
		entity.setUpdateBy(userId);
		entity.setUpdateDt(new Date(System.currentTimeMillis()));
	}
	
	@Override
    public E update(E entity) {
        appendUpdateUserInfo(entity);
        getHibernateTemplate().update(entity);
		getSession().flush();
        return entity;
    }
	
	@Override
    public E merge(E entity) {
        appendUpdateUserInfo(entity);
        getHibernateTemplate().merge(entity);
        return entity;
    }
    
}
