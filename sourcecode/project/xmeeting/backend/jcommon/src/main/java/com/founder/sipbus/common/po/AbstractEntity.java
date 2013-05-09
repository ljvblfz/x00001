/*
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 */
package com.founder.sipbus.common.po;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * @author cw peng
 * 
 */
@SuppressWarnings("unchecked")
@MappedSuperclass
public abstract class AbstractEntity implements Comparable, Serializable {

//	private boolean selected;
//	
//
//	@Transient
//	public boolean isSelected() {
//		return selected;
//	}
//
//	public void setSelected(boolean selected) {
//		this.selected = selected;
//	}
	
	@Override
	public String toString() {
//		net.sf.json.JSONObject j=net.sf.json.JSONObject.fromObject(this);
//		return j.toString();
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public int hashCode() {
		return super.hashCode();
	}
 
}
