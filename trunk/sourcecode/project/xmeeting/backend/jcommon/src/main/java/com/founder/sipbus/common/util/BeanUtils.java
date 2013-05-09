package com.founder.sipbus.common.util;

import java.util.ArrayList;
import java.util.List;

public class BeanUtils {

	public static List copyList(List source , Class clazz){
		List returnList=new ArrayList();
		try {
			Object o ;
			for(Object sourceObject:source){
				o = clazz.newInstance();
				org.springframework.beans.BeanUtils.copyProperties(sourceObject, o );
				returnList.add(o);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
}
