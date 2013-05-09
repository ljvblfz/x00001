/*
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.au.po;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

@Entity
@Table(name = "AU_FUNCTION")
public class AuFunction extends BaseEntity {
	

	private static final long serialVersionUID = 1L;
	public static String FUNCID= "funcId";
	public static String AUTHORITY_ID= "authorityId";
	public static String FUNCTION_NAME= "functionName";
	public static String MODULE_KEY= "moduleKey";
	public static String IS_TOOLBAR= "isToolbar";
	public static String CALL_BACK= "callBack";
	public static String COMPONENT_ID= "componentId";
	public static String ACTION_VALUE= "actionValue";
	
	//primary key
	/**
	 * UUID，唯一标识用户的id，主关键字，由hibernate自动生成
	 */
	private java.lang.String functionId;

	/**
	 * 权限的外键引用ID
	 */
	//private java.lang.String authorityId;
	/**
	 * java中被拦截的业务方法名称
	 */
	private java.lang.String functionname;
	
	private java.lang.String url;
	
	private java.lang.String descpt;
	private java.lang.String isToolbar;
	private java.lang.String domainName;
	private java.lang.String urlMethod;

	private AuAuthorities auAuthorities;
	

	public AuFunction(){
	}

	public AuFunction(java.lang.String functionId ){
		this.functionId = functionId;
	}

	public java.lang.String getFunctionname() {
		return functionname;
	}

	public void setFunctionname(java.lang.String functionname) {
		this.functionname = functionname;
	}

	public java.lang.String getUrl() {
		return url;
	}

	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	public java.lang.String getDescpt() {
		return descpt;
	}

	public void setDescpt(java.lang.String descpt) {
		this.descpt = descpt;
	}

	public java.lang.String getDomainName() {
		return domainName;
	}

	public void setDomainName(java.lang.String domainName) {
		this.domainName = domainName;
	}

	public java.lang.String getUrlMethod() {
		return urlMethod;
	}

	public void setUrlMethod(java.lang.String urlMethod) {
		this.urlMethod = urlMethod;
	}

	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")    
	public java.lang.String getFunctionId() {
		return this.functionId;
	}
	
	public void setFunctionId(java.lang.String value) {
		this.functionId = value;
	}
	/*@Column(name = "AUTHORITY_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getAuthorityId() {
		return this.authorityId;
	}
	
	public void setAuthorityId(java.lang.String value) {
		this.authorityId = value;
	}*/
	public java.lang.String getIsToolbar() {
		return this.isToolbar;
	}
	
	public void setIsToolbar(java.lang.String value) {
		this.isToolbar = value;
	}
	
	public void setAuAuthorities(AuAuthorities auAuthorities){
		this.auAuthorities = auAuthorities;
	}
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY )
	@JoinColumn(name = "AUTHORITY_ID")
	public AuAuthorities getAuAuthorities() {
		return auAuthorities;
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
			AuFunction castObj = (AuFunction) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getFunctionId(), castObj.getFunctionId());
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
			AuFunction castObj = (AuFunction) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getFunctionId(), castObj.getFunctionId());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

