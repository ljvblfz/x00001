package com.founder.sipbus.syweb.codeprinciple.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.restlet.data.Form;

public class ItemsDetailUtil {
/**
 * 获取详细信息 itemname[n].porpertyname 的形式
 * map的key为itemname 获取对应list
 * */
	@SuppressWarnings("rawtypes")
	public static HashMap<String, ArrayList<Map>> getDetails(Form form) {
		 
		HashMap<String, ArrayList<Map>> mapmap = new HashMap<String, ArrayList<Map>>();
	 
		for (String name : form.getNames()) {
			 
			if (StringUtils.contains(name, "[")) {
				String[] str = StringUtils.split(name, "[");
				if (StringUtils.isBlank(str[0]) || StringUtils.isBlank(str[1])) {
					continue;
				}
				String[] str2 = StringUtils.split(str[1], "].");
				if (StringUtils.isBlank(str2[0])
						|| StringUtils.isBlank(str2[1])) {
					continue;
				}
				if (!StringUtils.isNumeric(str2[0])) {
					continue;
				}

				String itemName = str[0];
				int index = Integer.parseInt(str2[0]);
				if (index<0) {
					continue;
				}
				String propertName = str2[1];

				ArrayList<Map> maplist = mapmap.get(itemName);
				if (maplist == null) {				//没有itemName的 list 则新建
					maplist = new ArrayList<Map>();
					mapmap.put(itemName, maplist);
				}
				if(maplist.size()<=index){			//List size比index小，插入新node
					for (int i = maplist.size()-1; i < index; i++) {
						 maplist.add(new HashMap<String, String>());
						
					}
				}
				@SuppressWarnings("unchecked")
				Map<String, String> itemMap = maplist.get(index);
				if (itemMap == null) {
					itemMap = new HashMap<String, String>();
					maplist.add(index, itemMap);
				}
				itemMap.put(propertName, form.getFirstValue(name));
			}
		}
		return mapmap;
	}
}
