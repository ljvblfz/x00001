/*
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.au.po;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

@Entity
@Table(name = "AU_MENU")

public class AuMenu extends BaseEntity {

	private static final long serialVersionUID = 1L; 
	//primary key
	/**
	 * UUID，唯一标识用户的id，主关键字，由hibernate自动生成
	 */
	private java.lang.String menuId;

	/**
	 * 该菜单项的上一级菜单ID
	 */
	private java.lang.String parentid;
	/**
	 * 菜单序号
	 */
	private java.lang.Short menuOrder;
	/**
	 * 菜单名称
	 */
	private java.lang.String menuName;
	/**
	 * 菜单描述
	 */
	private java.lang.String menuDescription;
	private java.lang.String menuHelp;
	
	private List<AuMenu> sonMenu;
	
	@Transient
	public List<AuMenu> getSonMenu() {
		return sonMenu;
	}

	public void setSonMenu(List<AuMenu> sonMenu) {
		this.sonMenu = sonMenu;
	}

	public void addSonMenu(AuMenu auMenu) {
		if(this.sonMenu==null){
			this.sonMenu=new ArrayList<AuMenu>();
		}
		this.sonMenu.add(auMenu);
	}
	/**
	 * 菜单标题
	 */
	private java.lang.String menuTitle;
	public java.lang.String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(java.lang.String menuTitle) {
		this.menuTitle = menuTitle;
	}

	/**
	 * 功能模块名称
	 */
	private java.lang.String domainName;
	/**
	 * 是否显示 1:显示 2:不显示
	 */
	private java.lang.String isDisplay;  
	/**
	 * 该菜单项被分配的权限id
	 */
	private java.lang.String authorityId; 

	/**
	 * 菜单类别
	 */
	private java.lang.Short menuType;
	

	/**
	 * 菜单对应的URL
	 */
	private String menuAction;
	
	public AuMenu(){
	}

	public AuMenu(java.lang.String menuId ){
		this.menuId = menuId;
	}

	//@Id
	//@GeneratedValue(generator = "system-uuid")
	//@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
//	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator" )
	@GenericGenerator(name = "trustIdGenerator", strategy = "assigned" )
	public java.lang.String getMenuId() {
		return this.menuId;
	}
	
	public void setMenuId(java.lang.String value) {
		this.menuId = value;
	}
	public java.lang.String getParentid() {
		return this.parentid;
	}
	
	public void setParentid(java.lang.String value) {
		this.parentid = value;
	}
	public java.lang.Short getMenuOrder() {
		return this.menuOrder;
	}
	
	public void setMenuOrder(java.lang.Short value) {
		this.menuOrder = value;
	}
	public java.lang.String getMenuName() {
		return this.menuName;
	}
	
	public void setMenuName(java.lang.String value) {
		this.menuName = value;
	}
	public java.lang.String getIsDisplay() {
		return this.isDisplay;
	}
	
	public void setIsDisplay(java.lang.String value) {
		this.isDisplay = value;
	}
	public java.lang.String getAuthorityId() {
		return this.authorityId;
	}
	
	public void setAuthorityId(java.lang.String value) {
		this.authorityId = value;
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

	@Override
	public String toString() {
		return "AuMenu [menuId=" + menuId + ", parentid=" + parentid
				+ ", menuOrder=" + menuOrder + ", menuName=" + menuName
				+ ", menuDescription=" + menuDescription + ", menuHelp="
				+ menuHelp + ", sonMenu=" + sonMenu + ", menuTitle="
				+ menuTitle + ", domainName=" + domainName + ", isDisplay="
				+ isDisplay + ", authorityId=" + authorityId + ", menuType="
				+ menuType + ", menuAction=" + menuAction + "]";
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
			AuMenu castObj = (AuMenu) obj;
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
			AuMenu castObj = (AuMenu) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getMenuId(), castObj.getMenuId());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

