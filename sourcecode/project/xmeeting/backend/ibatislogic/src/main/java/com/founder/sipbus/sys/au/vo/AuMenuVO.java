/*
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.sys.au.vo;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;
import com.founder.sipbus.common.vo.BaseVO;


public class AuMenuVO extends BaseVO {

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

	/**
	 * 菜单标题
	 */
	private java.lang.String menuTitle;
	/**
	 * 菜单对应的URL
	 */
	private String menuAction;
	private List<AuMenuVO> sonMenu;
	
	@Transient
	public List<AuMenuVO> getSonMenu() {
		return sonMenu;
	}

	public void setSonMenu(List<AuMenuVO> sonMenu) {
		this.sonMenu = sonMenu;
	}

	public void addSonMenu(AuMenuVO auMenu) {
		if(this.sonMenu==null){
			this.sonMenu=new ArrayList<AuMenuVO>();
		}
		this.sonMenu.add(auMenu);
	}
	public java.lang.String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(java.lang.String menuTitle) {
		this.menuTitle = menuTitle;
	}

	
	public AuMenuVO(){
	}

	public AuMenuVO(java.lang.String menuId ){
		this.menuId = menuId;
	}

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
			AuMenuVO castObj = (AuMenuVO) obj;
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
			AuMenuVO castObj = (AuMenuVO) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getMenuId(), castObj.getMenuId());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

