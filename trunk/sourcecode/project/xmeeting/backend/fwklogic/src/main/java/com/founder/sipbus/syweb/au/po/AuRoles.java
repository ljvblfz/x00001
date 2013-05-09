/*
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.au.po;
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
@Table(name = "SYS_ROLE")
public class AuRoles extends  AbstractEntity{

	private static final long serialVersionUID = 1L;
	public static String GUID= "guid";
	public static String NAME= "name";
	//primary key
	/**
	 * UUID，唯一标识角色的id，主关键字，由hibernate自动生成
	 */
	private java.lang.String guid;

	/**
	 * 系统角色的名称
	 */
	private java.lang.String name;
	
	private String memo;
	
	/**
     * 角色编号
     */
    private java.lang.String rolecode;
	

    public AuRoles(){
	}

	public AuRoles(java.lang.String guid ){
		this.guid = guid;
	}


	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getGuid() {
		return this.guid;
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
			AuRoles castObj = (AuRoles) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getGuid(), castObj.getGuid());
			compare = builder.toComparison();
		}
		return compare;
	}
		
	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
 
	 
	public java.lang.String getRolecode() {
		return rolecode;
	}

	public void setRolecode(java.lang.String rolecode) {
		this.rolecode = rolecode;
	}

	public void setGuid(java.lang.String guid) {
		this.guid = guid;
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
			AuRoles castObj = (AuRoles) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getGuid(), castObj.getGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
 
}

