package com.founder.sipbus.syweb.cck.spi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CckReferenceContext {
	private Map<String,String> mapOfContext=new ConcurrentHashMap<String,String>();

	public Map<String, String> getMapOfContext() {
		return mapOfContext;
	}

	public void setMapOfContext(Map<String, String> mapOfContext) {
		this.mapOfContext = mapOfContext;
	}

}
