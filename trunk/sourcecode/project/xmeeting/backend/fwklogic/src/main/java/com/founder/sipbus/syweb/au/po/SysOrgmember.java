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
@Table(name = "SYS_ORGMENBER")
public class SysOrgmember extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	//primary key
	/**
	 * UUID，唯一标识权限的id，主关键字，自动生成
	 */
	private java.lang.String guid;

	/**
	 *  部门
	 */
	private java.lang.String org;
	/**
	 * 用户名称 
	 */
	private java.lang.String member;
	private java.lang.String membertype;
	public java.lang.String getOrg() {
		return org;
	}

	public void setOrg(java.lang.String org) {
		this.org = org;
	}

	public java.lang.String getMember() {
		return member;
	}

	public void setMember(java.lang.String member) {
		this.member = member;
	}

	public java.lang.String getMembertype() {
		return membertype;
	}

	public void setMembertype(java.lang.String membertype) {
		this.membertype = membertype;
	}

	public java.lang.String getDuty() {
		return duty;
	}

	public void setDuty(java.lang.String duty) {
		this.duty = duty;
	}
 

	public java.lang.Integer getIspriduty() {
		return ispriduty;
	}

	public void setIspriduty(java.lang.Integer ispriduty) {
		this.ispriduty = ispriduty;
	}

	public java.lang.Integer getIsmngofleader() {
		return ismngofleader;
	}

	public void setIsmngofleader(java.lang.Integer ismngofleader) {
		this.ismngofleader = ismngofleader;
	}

	public java.lang.Integer getIsmngofmember() {
		return ismngofmember;
	}

	public void setIsmngofmember(java.lang.Integer ismngofmember) {
		this.ismngofmember = ismngofmember;
	}

	public java.lang.String getRolename() {
		return rolename;
	}

	public void setRolename(java.lang.String rolename) {
		this.rolename = rolename;
	}

	public void setGuid(java.lang.String guid) {
		this.guid = guid;
	}

	private java.lang.String duty;
	

	private java.lang.Integer ispriduty;
	private java.lang.Integer ismngofleader;
	private java.lang.Integer ismngofmember;
	private java.lang.String rolename;
	 
	@Id
	@GeneratedValue(generator = "paymentableGenerator") 
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	public java.lang.String getGuid() {
		return this.guid;
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
			SysOrgmember castObj = (SysOrgmember) obj;
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
			SysOrgmember castObj = (SysOrgmember) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getGuid(), castObj.getGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

