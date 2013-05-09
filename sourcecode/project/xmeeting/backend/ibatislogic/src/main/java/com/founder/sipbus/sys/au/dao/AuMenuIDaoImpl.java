package com.founder.sipbus.sys.au.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.founder.sipbus.sys.au.vo.AuMenuSimpleVO;
import com.founder.sipbus.sys.au.vo.AuMenuVO;

@Component
public class AuMenuIDaoImpl extends SqlMapClientDaoSupport {

	@SuppressWarnings("unchecked")
	public JSONObject findByCondition(String loginUserId, String parentid) {
		@SuppressWarnings("rawtypes")
		Map params = new HashMap();
		params.put("parentid", parentid);
		params.put("userid", loginUserId);
		@SuppressWarnings("rawtypes")
		List list = getSqlMapClientTemplate().queryForList(
				"sys.selectByConditionForList", params);
		AuMenuVO menu = null;
		// 把菜单项转换为JSON文本
		if (list != null && list.size() > 0) {
			menu = (AuMenuVO) list.get(0);
			list.remove(0);
			addSonMenu(menu, list);
		}
		return JSONObject.fromObject(menu);
	}

	private int addSonMenu(AuMenuVO menu, List<AuMenuVO> menuList) {
		int j = 0;
		AuMenuVO sonMenu;
		for (int i = 0; i < menuList.size(); i++) {
			sonMenu = (AuMenuVO) menuList.get(i);
			if (sonMenu.getParentid().equals(menu.getMenuId())) {
				menu.addSonMenu(sonMenu);
				menuList.remove(sonMenu);
				j++;
				i--;
				// i=i-
				addSonMenu(sonMenu, menuList);
			}
		}
		return j;
	}
	
	
	 
	public List<AuMenuSimpleVO> findAll() { 
		List<AuMenuSimpleVO> list = (List<AuMenuSimpleVO>)getSqlMapClientTemplate().queryForList( "sys.findAll"); 
		return list;
	}
}
