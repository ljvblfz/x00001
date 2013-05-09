package com.founder.sipbus.common.application;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class BaseApplicationImpl extends BaseApplication{

	private Set<Class<?>> rrcs;
	
	@Override
	public Set<Class<?>> getClasses() {
		return rrcs;
	}

	
	public void setClasses(Set<Object> set){
		rrcs= new HashSet<Class<?>>();
		Iterator it= set.iterator();
		while(it.hasNext()){
			rrcs.add(it.next().getClass());
		}
		
		
	}
}
