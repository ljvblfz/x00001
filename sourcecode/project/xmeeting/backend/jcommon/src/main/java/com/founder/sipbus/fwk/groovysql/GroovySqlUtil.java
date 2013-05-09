package com.founder.sipbus.fwk.groovysql;

public class GroovySqlUtil {
	
	public boolean isEmpty(String str) {
	     return str == null || str.length() == 0;
	}

	public boolean isNotEmpty(String str) {
	     return !isEmpty(str);
	}
}
