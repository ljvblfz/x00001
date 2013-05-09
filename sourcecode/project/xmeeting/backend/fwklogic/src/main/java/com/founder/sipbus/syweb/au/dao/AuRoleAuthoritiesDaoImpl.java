package com.founder.sipbus.syweb.au.dao;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.AbstractBaseDaoImpl;
import com.founder.sipbus.syweb.au.po.AuRolesAuthorities;

@Component
public class AuRoleAuthoritiesDaoImpl extends
		AbstractBaseDaoImpl<AuRolesAuthorities, String> {
	
	
	/**
	 * 更新角色权限，先删除再插入
	 * 
	 * **/
	public void updateRoleAuthorities(String guid, String[] ids) {

		ArrayList<AuRolesAuthorities> entities = new ArrayList<AuRolesAuthorities>();
		if (ids != null  ) {
			for (String id : ids) {
				if (id != null && !id.trim().isEmpty()) {
					AuRolesAuthorities ara = new AuRolesAuthorities();
					ara.setAuthorityId(id);
					ara.setRoleId(guid);
					ara.setCreateDt(new Date());
//					ara.setCreateBy(new Date());
					entities.add(ara);
				}
			}
		}
		String hql = "DELETE AuRolesAuthorities    WHERE  roleId = :guid ";
//		Transaction transaction = this.getSession().beginTransaction();
		try {

			HibernateTemplate ht = this.getHibernateTemplate();
//			transaction.begin();

			this.getSession().createQuery(hql).setString("guid", guid)
					.executeUpdate();
			if (entities.size() > 0) {
				ht.saveOrUpdateAll(entities);
			}
//			transaction.commit();
		} catch (Exception e) {
//			transaction.rollback();
			e.printStackTrace();

		}
	}

}
