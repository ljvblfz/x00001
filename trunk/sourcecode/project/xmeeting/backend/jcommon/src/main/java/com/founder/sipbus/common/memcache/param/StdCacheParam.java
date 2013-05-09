package com.founder.sipbus.common.memcache.param;

import org.apache.commons.lang.StringUtils;

public class StdCacheParam {
	public static final String MULTI_VALUE_SEP = ",";
	private String group;
	private String key;
	public StdCacheParam(){}
	public StdCacheParam(String group, String key) {
        this.group = group;
        this.key = key;
    }
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean validation() {
        if (StringUtils.isBlank(group)) {
            return false;
        }
        if (StringUtils.isBlank(key)) {
            return false;
        }
        return true;
    }
	public boolean validationGroup() {
        if (StringUtils.isBlank(group)) {
            return false;
        }
        return true;
    }
	public String generateKey() {
        return getGroup() + MULTI_VALUE_SEP + getKey();
    }
}
