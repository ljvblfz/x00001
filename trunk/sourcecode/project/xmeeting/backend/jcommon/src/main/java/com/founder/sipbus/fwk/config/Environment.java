package com.founder.sipbus.fwk.config;

import java.util.Map;

public class Environment {

	public static Environment environment = new Environment();

	private Environment() {
	}

	public static Environment getInstance() {
		return environment;
	}
	
	
	private Map<String, String> configMap;
 

	public void setConfigMap(Map<String, String> configMap) {
		this.configMap = configMap;
	}
	
	public String getString(String key){
		return configMap.get(key);
	}
	

	public int getInt(String key){
		return Integer.valueOf(configMap.get(key)).intValue();
	}

	 
	
	
	
}
