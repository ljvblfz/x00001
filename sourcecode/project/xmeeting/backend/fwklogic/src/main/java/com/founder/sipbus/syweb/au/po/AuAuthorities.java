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
@Table(name = "AU_AUTHORITIES")
public class AuAuthorities extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String AUTHORITYID= "authorityId";
	public static String PARENT_ID= "parentId";
	public static String AUT_ORDER= "autOrder";
	public static String AUTHORITY_NAME= "authorityName";
	public static String ENABLED= "enabled";

	//primary key
	/**
	 * UUID，唯一标识权限的id，主关键字，由hibernate自动生成
	 */
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
	/**
	 * 某角色是否有该权限，true：有；fales：无。
	 */
	private java.lang.String isHave;
	/**
	 * 子权限列表
	 */
	private  List<AuAuthorities>  sonAuthotities;
	
//	private List<AuFunction> auFunctionList= new ArrayList<AuFunction>();
	@Transient
	public List<AuAuthorities> getSonAuthorities() {
		return sonAuthotities;
	}

	public void setSonAuthorities(List<AuAuthorities> sonMenu) {
		this.sonAuthotities = sonMenu;
	}

	public AuAuthorities(){
	}

	public AuAuthorities(java.lang.String id ){
		this.authorityId = id;
	}

	@Id
	@GeneratedValue(generator = "paymentableGenerator") 
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	public java.lang.String getAuthorityId() {
		return this.authorityId;
	}
	
	public void setAuthorityId(java.lang.String value) {
		this.authorityId = value;
	}
	public java.lang.String getParentId() {
		return this.parentId;
	}
	
	public void setParentId(java.lang.String value) {
		this.parentId = value;
	}
	public java.lang.Short getAutOrder() {
		return this.autOrder;
	}
	
	public void setAutOrder(java.lang.Short value) {
		this.autOrder = value;
	}
	public java.lang.String getAuthorityName() {
		return this.authorityName;
	}
	
	public void setAuthorityName(java.lang.String value) {
		this.authorityName = value;
	}
	public java.lang.String getEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(java.lang.String value) {
		this.enabled = value;
	}
	
//	@OneToMany(mappedBy = "auAuthorities", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	public List<AuFunction> getAuFunctionList(){
//		return this.auFunctionList;
//	}
//	
//	public void setAuFunctionList(List<AuFunction> auFunctionList){
//		this.auFunctionList= auFunctionList;
//	}

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
			AuAuthorities castObj = (AuAuthorities) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getAuthorityId(), castObj.getAuthorityId());
			compare = builder.toComparison();
		}
		return compare;
	}
		
	@Override
	public String toString() {
		return "AuAuthorities [authorityId=" + authorityId + ", parentId="
				+ parentId + ", autOrder=" + autOrder + ", authorityName="
				+ authorityName + ", enabled=" + enabled + ", isHave=" + isHave
				+ "]";
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
			AuAuthorities castObj = (AuAuthorities) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getAuthorityId(), castObj.getAuthorityId());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
	public void setIsHave(java.lang.String isHave) {
		this.isHave = isHave;
	}
	@Transient
	public java.lang.String getIsHave() {
		return isHave;
	}
		/**
		*增加子权限
		*/
	public void addSonAuthorities(AuAuthorities auAuthorities) {
		if(this.sonAuthotities==null){
			this.sonAuthotities=new ArrayList<AuAuthorities>();
		}
		 this.sonAuthotities.add(auAuthorities);
	}

	

}

