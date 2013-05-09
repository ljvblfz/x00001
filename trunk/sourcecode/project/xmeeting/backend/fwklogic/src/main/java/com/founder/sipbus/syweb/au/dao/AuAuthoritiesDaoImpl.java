package com.founder.sipbus.syweb.au.dao;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.founder.sipbus.common.dao.DefaultBaseDaoImpl;
import com.founder.sipbus.common.po.BaseEntity;
import com.founder.sipbus.syweb.au.po.AuAuthorities;

@Component
public class AuAuthoritiesDaoImpl extends
		DefaultBaseDaoImpl<AuAuthorities, String> {

	/**
	 * 查找角色拥有权限 拼装好子权限 返回JSONArray对象
	 * 
	 * @param guid
	 *            角色guidid
	 * */
	public JSONArray findAuthoritiesByRoleGuid(String guid) {

		List<AuAuthorities> list = new java.util.ArrayList<AuAuthorities>();
		List<AuAuthorities> list2 = new java.util.ArrayList<AuAuthorities>();

		try {

			list = findAuthoritiesByRoleGuidList(guid);

			// 把权限项转换为JSON文本
			if (list != null && list.size() > 0) {

				int index = 0;
				while (!list.isEmpty() && index < list.size()) {

					AuAuthorities tAuthorities = list.get(index);
					if (tAuthorities.getParentId() == null) {
						list2.add(tAuthorities);

					}
					index++;
				}
				for (AuAuthorities ttAuthorities : list2) {
					addSonAuthorities(ttAuthorities, list);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONArray.fromObject(list2);
	}

	/**
	 * 查找角色拥有权限
	 * */
	public List<AuAuthorities> findAuthoritiesByRoleGuidList(String guid) {

		List<AuAuthorities> list = new java.util.ArrayList<AuAuthorities>();

		try {

			String hql = "from AuAuthorities t where 1=1 and t.delFlag=0   and "
					+ " exists"
					+ " ( "
					+ "	select ara.authorityId from AuRolesAuthorities ara where "
					+ " 1=1 and ara.authorityId =t.authorityId"
					+ "	 and exists "
					+ "("
					+ " select sr.guid from AuRoles sr, AuRolesAuthorities "
					+ " ara where  sr.guid=? and ara.roleId = "
					+ " sr.guid and  ara.authorityId=t.authorityId"
					+ ") "
					+ ")" + " order by t.parentId,  t.autOrder asc";
			list = findByHql(hql, guid);

			// 把权限项转换为JSON文本

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 查找角色拥有权限以及所以权限，拼装好子权限，用于修改权限
	 * */
	public JSONArray findAuthorities(String guid) {

		List<AuAuthorities> list = new java.util.ArrayList<AuAuthorities>();
		List<AuAuthorities> list2 = new java.util.ArrayList<AuAuthorities>();
		List<AuAuthorities> list3 = new java.util.ArrayList<AuAuthorities>();

		try {
			//list = this.findAll();
			list = this.findByHql("from AuAuthorities where enabled=1 and delFlag=0 order by parentId desc , autOrder asc");
			list3 = findAuthoritiesByRoleGuidList(guid);

			// 把菜单项转换为JSON文本
			if (list != null && list.size() > 0) {
				if (list3 != null && list3.size() > 0) {
					for (AuAuthorities ta : list) {

						if (list3.indexOf(ta) >= 0) {
							list3.remove(ta);
							ta.setIsHave("true");
						}
					}
				}
				AuAuthorities root=new AuAuthorities("0");
				
				addSonAuthorities(root, list);
				
				//level - 2
				for(int i=0;i<root.getSonAuthorities().size();i++){
					AuAuthorities a=root.getSonAuthorities().get(i);
					addSonAuthorities(a, list);
					//level - 3
					if(a!=null&&a.getSonAuthorities()!=null){
						for(int j=0;j<a.getSonAuthorities().size();j++){
							AuAuthorities b=a.getSonAuthorities().get(j);
							addSonAuthorities(b, list);
							//level - 4
							if(b!=null&&b.getSonAuthorities()!=null){
								for(int k=0;k<b.getSonAuthorities().size();k++){
									AuAuthorities c=b.getSonAuthorities().get(k);
									addSonAuthorities(c, list);
									//level - 5
									if(c!=null&&c.getSonAuthorities()!=null){
										for(int l=0;l<c.getSonAuthorities().size();l++){
											AuAuthorities d=c.getSonAuthorities().get(l);
											addSonAuthorities(d, list);
										}
									}
								}
							}
						}
					}
				}
				
				list2.addAll(root.getSonAuthorities());
				
//				int index = 0;
//				while (!list.isEmpty() && index < list.size()) {
//					AuAuthorities tAuthorities = list.get(index);
//
//					if ("0".equals(tAuthorities.getParentId())) {
//						index=index-addSonAuthorities(tAuthorities, list);
//						list2.add(tAuthorities);
//					}
//					index++;
//				}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSONArray.fromObject(list2);
	}

	private int addSonAuthorities(AuAuthorities authorities,
			List<AuAuthorities> authoritiesList) {

		int j=0;
		AuAuthorities sonAuthorities;
		for (int i = 0; i < authoritiesList.size(); i++) {
			sonAuthorities = (AuAuthorities) authoritiesList.get(i);
			if (sonAuthorities.getParentId().equals(
				authorities.getAuthorityId())) {
				authorities.addSonAuthorities(sonAuthorities);
				authoritiesList.remove(sonAuthorities);
				i--;
				j++;
//				i=i-addSonAuthorities(sonAuthorities, authoritiesList);
				
			}
		}
		
		
		
//		AuAuthorities sonAuthorities;
//		for (int i = 0; i < authoritiesList.size(); i++) {
//			sonAuthorities = (AuAuthorities) authoritiesList.get(i);
//			if (!sonAuthorities.getAuthorityId().equals(authorities.getAuthorityId())) {
//				if (sonAuthorities.getParentId().equals(
//						authorities.getAuthorityId())) {
//					j++;
//					authorities.addSonAuthorities(sonAuthorities);
////	        		i--;
////					i=i-
//					addSonAuthorities(sonAuthorities, authoritiesList);
//					i=authoritiesList.indexOf(sonAuthorities)+1;
//					authoritiesList.remove(sonAuthorities);
//					i--;
//				}
//			}
//		}
		return j;
	}

}
