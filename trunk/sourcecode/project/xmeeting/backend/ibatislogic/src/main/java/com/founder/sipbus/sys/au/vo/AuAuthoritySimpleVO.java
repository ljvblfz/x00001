/*
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.sys.au.vo;
import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.founder.sipbus.common.po.BaseEntity;
import com.founder.sipbus.common.vo.BaseVO;


public class AuAuthoritySimpleVO extends BaseVO {

	private static final long serialVersionUID = 1L; 
	//primary key
	

	private java.lang.String id;
	private java.lang.String t;
	private java.lang.String pId;
	private java.lang.String name;
	private JSONObject font;
	private java.lang.String icon;
	
	
	private String delFlag ;
	//
	private java.lang.String authorityId; 
	/**
	 * 该权限记录的上一级权限标号
	 */
	private java.lang.String parentId;
	/**
	 * 权限序号,赋权页面排序规则
	 */
	private java.lang.Short autOrder; 
	/**
	 * 权限的名称
	 */
	private java.lang.String authorityName;
	/**
	 * 该权限是否有效，1：有效；0：无效。
	 */
	private java.lang.String enabled;
	 
	

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getT() {
		return t;
	}

	public void setT(java.lang.String t) {
		this.t = t;
	}

	public java.lang.String getpId() {
		return pId;
	}

	public void setpId(java.lang.String pId) {
		this.pId = pId;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public java.lang.String getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(java.lang.String authorityId) {
		this.authorityId = authorityId;
	}

	public java.lang.String getParentId() {
		return parentId;
	}

	public void setParentId(java.lang.String parentId) {
		this.parentId = parentId;
	}

	public java.lang.Short getAutOrder() {
		return autOrder;
	}

	public void setAutOrder(java.lang.Short autOrder) {
		this.autOrder = autOrder;
	}

	public java.lang.String getAuthorityName() {
		return authorityName;
	}

	public void setAuthorityName(java.lang.String authorityName) {
		this.authorityName = authorityName;
	}

	public java.lang.String getEnabled() {
		return enabled;
	}

	public void setEnabled(java.lang.String enabled) {
		this.enabled = enabled;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public java.lang.String getIcon() {
		String delicon="styles/zTreev3.3/css/zTreeStyle/img/diy/del.png";
		String normalicon="styles/zTreev3.3/css/zTreeStyle/img/diy/normal.png";
		if(this.delFlag.equals("0")){  
			return normalicon;
		}else{  
			return delicon;
		}
	}

	public void setIcon(java.lang.String icon) {
		this.icon = icon;
	}

	public JSONObject getFont() {
		JSONObject jsonObject=new JSONObject(); 
		if(this.delFlag.equals("0")){ 
			jsonObject.put("color", "blue");
		}else{ 
			jsonObject.put("color", "red");
		}
		return jsonObject;
	}

	public void setFont(JSONObject font) {
		this.font = font;
	}

	public int compareTo(Object obj) {
		int compare = -1;
	
		if (obj == null)
			compare = -1;
		else if (this == obj)
			compare = 0;
		else if (!(obj instanceof BaseEntity))
			compare = -1;
		else if (!this.getClass().equals(obj.getClass()))
			compare = -1;
		else {
			AuAuthoritySimpleVO castObj = (AuAuthoritySimpleVO) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getAuthorityId(), castObj.getAuthorityId());
			compare = builder.toComparison();
		}
		return compare;
	}
		
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if (obj == null) {
			isEqual = false;
		} else if (this == obj) {
			isEqual = true;
		} else if (!(obj instanceof BaseEntity)) {
			isEqual = false;
		} else if (!this.getClass().equals(obj.getClass())) {
			isEqual = false;
		} else {
			AuAuthoritySimpleVO castObj = (AuAuthoritySimpleVO) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getAuthorityId(), castObj.getAuthorityId());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

