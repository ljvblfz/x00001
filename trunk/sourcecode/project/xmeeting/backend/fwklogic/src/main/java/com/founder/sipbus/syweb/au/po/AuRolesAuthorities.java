/*
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.au.po;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.AbstractEntity;
import com.founder.sipbus.common.po.BaseEntity;

@Entity
@Table(name = "AU_ROLES_AUTHORITIES")

public class AuRolesAuthorities extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	//primary key
	/**
	 * 
	 */
	private java.lang.String relId;

	
	private java.lang.String authorityId;

	
	private java.lang.String roleId;


	private String createBy;
	
	private String updateBy;
	
	private Date createDt;
	
	private Date updateDt;

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public AuRolesAuthorities(){
	}

	public AuRolesAuthorities(java.lang.String relId ){
		this.relId = relId;
	}
	
	public java.lang.String getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(java.lang.String authorityId) {
		this.authorityId = authorityId;
	}

	//@Id
	//@GeneratedValue(generator = "system-uuid")
	//@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator" )
	public java.lang.String getRelId() {
		return this.relId;
	}
	
	public void setRelId(java.lang.String relId) {
		this.relId = relId;
	} 
	 

	public java.lang.String getRoleId() {
		return roleId;
	}

	public void setRoleId(java.lang.String roleId) {
		this.roleId = roleId;
	}

	public int compareTo(Object obj) {
		int compare = -1;
	
		if (obj == null)
			compare = -1;
		else if (this == obj)
			compare = 0;
		else if (!(obj instanceof AbstractEntity))
			compare = -1;
		else if (!this.getClass().equals(obj.getClass()))
			compare = -1;
		else {
			AuRolesAuthorities castObj = (AuRolesAuthorities) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getRelId(), castObj.getRelId());
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
		} else if (!(obj instanceof AbstractEntity)) {
			isEqual = false;
		} else if (!this.getClass().equals(obj.getClass())) {
			isEqual = false;
		} else {
			AuRolesAuthorities castObj = (AuRolesAuthorities) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getRelId(), castObj.getRelId());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

