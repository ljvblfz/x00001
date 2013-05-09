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


public class AuMenuSimpleVO extends BaseVO {

	private static final long serialVersionUID = 1L; 
	//primary key
	

	private java.lang.String id;
	private java.lang.String t;
	private java.lang.String pId;
	private java.lang.String name;
	private JSONObject font;
	private java.lang.String icon;
	
	//

	private java.lang.String authorityId;
	private java.lang.String delFlag;
	private java.lang.String isDisplay;
	/**
	 * UUID，唯一标识用户的id，主关键字，由hibernate自动生成
	 */
	private java.lang.String menuId;
	/**
	 * 该菜单项的上一级菜单ID
	 */
	private java.lang.String parentId;
	/**
	 * 菜单名称
	 */
	private java.lang.String menuName;

	/**
	 * 功能模块名称
	 */
	private java.lang.String domainName;

	/**
	 * 菜单类别
	 */
	private java.lang.Short menuType;
	private java.lang.String menuOrder;

	/**
	 * 菜单标题
	 */
	private java.lang.String menuTitle;
	/**
	 * 菜单对应的URL
	 */
	private String menuAction;
	

	/**
	 * 菜单标题
	 */
	private java.lang.String menuDescription;
	/**
	 * 菜单标题
	 */
	private java.lang.String menuHelp;
	 
 
	public java.lang.String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(java.lang.String menuTitle) {
		this.menuTitle = menuTitle;
	}

	
	public AuMenuSimpleVO(){
	}

	public AuMenuSimpleVO(java.lang.String menuId ){
		this.menuId = menuId;
	}

	public java.lang.String getMenuId() {
		return this.menuId;
	}
	
	public void setMenuId(java.lang.String value) {
		this.menuId = value;
	}
	 
	public java.lang.String getParentId() {
		return parentId;
	}

	public void setParentId(java.lang.String parentId) {
		this.parentId = parentId;
	}

	public java.lang.String getMenuName() {
		return this.menuName;
	}
	
	public void setMenuName(java.lang.String value) {
		this.menuName = value;
	}

	public java.lang.String getDomainName() {
		return domainName;
	}

	public void setDomainName(java.lang.String domainName) {
		this.domainName = domainName;
	}

	public java.lang.Short getMenuType() {
		return menuType;
	}

	public void setMenuType(java.lang.Short menuType) {
		this.menuType = menuType;
	}

	public String getMenuAction() {
		return menuAction;
	}

	public void setMenuAction(String menuAction) {
		this.menuAction = menuAction;
	}

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
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

	public java.lang.String getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(java.lang.String authorityId) {
		this.authorityId = authorityId;
	}

	public java.lang.String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(java.lang.String delFlag) {
		this.delFlag = delFlag;
	}

	public java.lang.String getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(java.lang.String isDisplay) {
		this.isDisplay = isDisplay;
	}

	public java.lang.String getT() {
		return t;
	}

	public void setT(java.lang.String t) {
		this.t = t;
	} 

	public java.lang.String getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(java.lang.String menuOrder) {
		this.menuOrder = menuOrder;
	}

	public java.lang.String getMenuDescription() {
		return menuDescription;
	}

	public void setMenuDescription(java.lang.String menuDescription) {
		this.menuDescription = menuDescription;
	}

	public java.lang.String getMenuHelp() {
		return menuHelp;
	}

	public void setMenuHelp(java.lang.String menuHelp) {
		this.menuHelp = menuHelp;
	}

	public java.lang.String getIcon() {
		String delicon="styles/zTreev3.3/css/zTreeStyle/img/diy/del.png";
		String normalicon="styles/zTreev3.3/css/zTreeStyle/img/diy/normal.png";
		if("1".equals(this.isDisplay)&&"0".equals(this.delFlag)){  
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
//		if(this.isDisplay.equals("1")&&this.delFlag.equals("0")){  
		if("1".equals(this.isDisplay)&&"0".equals(this.delFlag)){  
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
			AuMenuSimpleVO castObj = (AuMenuSimpleVO) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getMenuId(), castObj.getMenuId());
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
			AuMenuSimpleVO castObj = (AuMenuSimpleVO) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getMenuId(), castObj.getMenuId());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

