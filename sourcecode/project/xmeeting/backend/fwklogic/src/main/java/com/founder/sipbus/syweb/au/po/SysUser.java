/*
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.au.po;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.AbstractEntity;
import com.founder.sipbus.common.po.BaseEntity;

@Entity
@Table(name = "SYS_USER")

public class SysUser extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	public static String USERNAME= "username";
	public static String FULLNAME= "fullname";
	public static String PASSWORD= "password";
	public static String CONF_PASSWORD= "confPassword";
	public static String USERID= "userid";
	public static String EMAIL= "email";
	public static String TELEPHONE= "telephone";
	public static String MOBILEPHONE= "mobilephone";
	public static String MPHONE= "mphone";
	public static String FAX= "fax";
	public static String WORKED_TIME= "workedTime";
	public static String FEE_CENTER= "feeCenter";
	public static String REG_NUM= "regNum";
	public static String TITLEOFPOST= "titleofpost";
	public static String ISENABLE= "isenable";
	public static String ISADMIN= "isadmin";
	public static String MEMO= "memo";
	public static String MAILSIZE= "mailsize";
	public static String GUID= "guid";
	public static String IMG= "img";
	public static String ISON= "ison";
	public static String ORDERID= "orderid";
	public static String ISFIRSTLOGIN= "isfirstlogin";

	//primary key
	/**
	 * 
	 */
	private java.lang.String username;

	/**
	 * 
	 */
	private java.lang.String fullname;
	/**
	 * 
	 */
	private java.lang.String password;
	private java.lang.String orgId;
	@Transient
	public java.lang.String getOrgId() {
		return orgId;
	}

	public void setOrgId(java.lang.String orgId) {
		this.orgId = orgId;
	}

	/**
	 * 
	 */
	private java.lang.String confPassword;
	/**
	 * 
	 */
	private java.lang.String userid;
	/**
	 * 
	 */
	private java.lang.String email;
	private java.lang.String loginIpAddr;

	@Transient
	public java.lang.String getLoginIpAddr() {
		return loginIpAddr;
	}

	public void setLoginIpAddr(java.lang.String loginIpAddr) {
		this.loginIpAddr = loginIpAddr;
	}

	/**
	 * 
	 */
	private java.lang.String telephone;
	/**
	 * 
	 */
	private java.lang.String mobilephone;
	/**
	 * 
	 */
	private java.lang.String mphone;
	/**
	 * 
	 */
	private java.lang.String fax;
	/**
	 * 
	 */
	private java.lang.String workedTime;
	/**
	 * 
	 */
	private java.lang.String feeCenter;
	/**
	 * 
	 */
	private java.lang.String regNum;
	/**
	 * 
	 */
	private java.lang.String titleofpost;
	/**
	 * 
	 */
	private java.lang.Integer isenable;
	/**
	 * 
	 */
	private java.lang.Integer isadmin;
	/**
	 * 
	 */
	private java.lang.String memo;
	/**
	 * 
	 */
	private java.lang.Integer mailsize;
	/**
	 * 
	 */
	private java.lang.String guid;
	/**
	 * 
	 */
	private java.lang.String img;
	/**
	 * 
	 */
	private java.lang.Long ison;
	/**
	 * 
	 */
	private java.lang.String orderid;
	/**
	 * 
	 */
	private java.lang.Boolean isfirstlogin;

	private List<AuAuthorities> authorities;

	@Transient
	public List<AuAuthorities> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<AuAuthorities> authorities) {
		this.authorities = authorities;
	}

	public SysUser(){
	}

	public SysUser(java.lang.String username ){
		this.username = username;
	}


	@Id
	public java.lang.String getUsername() {
		return this.username;
	}
	
	public void setUsername(java.lang.String value) {
		this.username = value;
	}
	public java.lang.String getFullname() {
		return this.fullname;
	}
	
	public void setFullname(java.lang.String value) {
		this.fullname = value;
	}
	public java.lang.String getPassword() {
		return this.password;
	}
	
	public void setPassword(java.lang.String value) {
		this.password = value;
	}
	public java.lang.String getConfPassword() {
		return this.confPassword;
	}
	
	public void setConfPassword(java.lang.String value) {
		this.confPassword = value;
	}
	public java.lang.String getUserid() {
		return this.userid;
	}
	
	public void setUserid(java.lang.String value) {
		this.userid = value;
	}
	public java.lang.String getEmail() {
		return this.email;
	}
	
	public void setEmail(java.lang.String value) {
		this.email = value;
	}
	public java.lang.String getTelephone() {
		return this.telephone;
	}
	
	public void setTelephone(java.lang.String value) {
		this.telephone = value;
	}
	public java.lang.String getMobilephone() {
		return this.mobilephone;
	}
	
	public void setMobilephone(java.lang.String value) {
		this.mobilephone = value;
	}
	public java.lang.String getMphone() {
		return this.mphone;
	}
	
	public void setMphone(java.lang.String value) {
		this.mphone = value;
	}
	public java.lang.String getFax() {
		return this.fax;
	}
	
	public void setFax(java.lang.String value) {
		this.fax = value;
	}
	public java.lang.String getWorkedTime() {
		return this.workedTime;
	}
	
	public void setWorkedTime(java.lang.String value) {
		this.workedTime = value;
	}
	public java.lang.String getFeeCenter() {
		return this.feeCenter;
	}
	
	public void setFeeCenter(java.lang.String value) {
		this.feeCenter = value;
	}
	public java.lang.String getRegNum() {
		return this.regNum;
	}
	
	public void setRegNum(java.lang.String value) {
		this.regNum = value;
	}
	public java.lang.String getTitleofpost() {
		return this.titleofpost;
	}
	
	public void setTitleofpost(java.lang.String value) {
		this.titleofpost = value;
	}
	public java.lang.Integer getIsenable() {
		return this.isenable;
	}
	
	public void setIsenable(java.lang.Integer value) {
		this.isenable = value;
	}
	public java.lang.Integer getIsadmin() {
		return this.isadmin;
	}
	
	public void setIsadmin(java.lang.Integer value) {
		this.isadmin = value;
	}
	public java.lang.String getMemo() {
		return this.memo;
	}
	
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}
	public java.lang.Integer getMailsize() {
		return this.mailsize;
	}
	
	public void setMailsize(java.lang.Integer value) {
		this.mailsize = value;
	}
	public java.lang.String getGuid() {
		return this.guid;
	}
	
	public void setGuid(java.lang.String value) {
		this.guid = value;
	}
	public java.lang.String getImg() {
		return this.img;
	}
	
	public void setImg(java.lang.String value) {
		this.img = value;
	}
	public java.lang.Long getIson() {
		return this.ison;
	}
	
	public void setIson(java.lang.Long value) {
		this.ison = value;
	}
	public java.lang.String getOrderid() {
		return this.orderid;
	}
	
	public void setOrderid(java.lang.String value) {
		this.orderid = value;
	}
	public java.lang.Boolean getIsfirstlogin() {
		return this.isfirstlogin;
	}
	
	public void setIsfirstlogin(java.lang.Boolean value) {
		this.isfirstlogin = value;
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
			SysUser castObj = (SysUser) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getUsername(), castObj.getUsername());
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
			SysUser castObj = (SysUser) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getUsername(), castObj.getUsername());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

