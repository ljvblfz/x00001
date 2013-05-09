/*
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.au.po;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.AbstractEntity;
import com.founder.sipbus.common.po.BaseEntity;

@Entity
@Table(name = "SYS_MEMBEROFROLE")
public class SysMemberofrole extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	//primary key
	/**
	 * UUID，唯一标识权限的id，主关键字，自动生成
	 */
	private java.lang.String guid;

	/**
	 *  角色名称
	 */
	private java.lang.String rolename;
	/**
	 * 用户名称 
	 */
	private java.lang.String member;
	 
	@Id
	@GeneratedValue(generator = "paymentableGenerator") 
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	public java.lang.String getGuid() {
		return this.guid;
	}
	 
	public java.lang.String getRolename() {
		return rolename;
	}

	public void setRolename(java.lang.String rolename) {
		this.rolename = rolename;
	}



	public java.lang.String getMember() {
		return member;
	}

	public void setMember(java.lang.String member) {
		this.member = member;
	}

	public void setGuid(java.lang.String guid) {
		this.guid = guid;
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
			SysMemberofrole castObj = (SysMemberofrole) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getGuid(), castObj.getGuid());
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
			SysMemberofrole castObj = (SysMemberofrole) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getGuid(), castObj.getGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

