/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
package com.founder.sipbus.syweb.refmgmt.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

/** no table comments对应实体类 */
@Entity
@Table(name = "SY_WIDGET_REFERENCE")
public class SyWidgetReference extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String SWR_GUID = "swrGuid";
	public static String SWR_CODE = "swrCode";
	public static String SWR_NAME = "swrName";
	public static String DESCRIPTION = "description";
	public static String WIDGET_TYPE = "widgetType";
	public static String REFERENCE_VALUE = "referenceValue";
	public static String AUTOCOMPLETE_CONFIG = "autocompleteConfig";
	public static String GROUPNAME = "groupname";

	// primary key
	/** PID */
	private java.lang.String swrGuid;
	/** CODE */
	private java.lang.String swrCode;
	/** 名称 */
	private java.lang.String swrName;
	/** 描述 */
	private java.lang.String description;
	/** 控件类型 */
	private java.lang.Integer widgetType;
	private java.lang.Integer autocompleteConfig;
	private java.lang.String groupname;
	//

	/** 控件类型 */
	private java.lang.String widgetTypeLabel;
	private java.lang.String autocompleteConfigLabel;
	private java.lang.String groupnameLabel;
	/** 数据reference */
	private java.lang.String referenceValue;

	public SyWidgetReference() {
	}

	public SyWidgetReference(java.lang.String swrGuid) {
		this.swrGuid = swrGuid;
	}

	/**
	 * 取得PID return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")
	public java.lang.String getSwrGuid() {
		return this.swrGuid;
	}

	/**
	 * 设置PID
	 * 
	 * @param swrGuid
	 *            PID
	 */
	public void setSwrGuid(java.lang.String swrGuid) {
		this.swrGuid = swrGuid;
	}

	/**
	 * 取得CODE return CODE
	 */
	public java.lang.String getSwrCode() {
		return this.swrCode;
	}

	/**
	 * 设置CODE
	 * 
	 * @param swrCode
	 *            CODE
	 */
	public void setSwrCode(java.lang.String value) {
		this.swrCode = value;
	}

	/**
	 * 取得名称 return 名称
	 */
	public java.lang.String getSwrName() {
		return this.swrName;
	}

	/**
	 * 设置名称
	 * 
	 * @param swrName
	 *            名称
	 */
	public void setSwrName(java.lang.String value) {
		this.swrName = value;
	}

	/**
	 * 取得描述 return 描述
	 */
	public java.lang.String getDescription() {
		return this.description;
	}

	/**
	 * 设置描述
	 * 
	 * @param description
	 *            描述
	 */
	public void setDescription(java.lang.String value) {
		this.description = value;
	}

	/**
	 * 取得控件类型 return 控件类型
	 */
	public java.lang.Integer getWidgetType() {
		return this.widgetType;
	}

	/**
	 * 设置控件类型
	 * 
	 * @param widgetType
	 *            控件类型
	 */
	public void setWidgetType(java.lang.Integer value) {
		this.widgetType = value;
	}

	public java.lang.Integer getAutocompleteConfig() {
		return autocompleteConfig;
	}

	public void setAutocompleteConfig(java.lang.Integer autocompleteConfig) {
		this.autocompleteConfig = autocompleteConfig;
	}

	/**
	 * 取得数据reference return 数据reference
	 */
	public java.lang.String getReferenceValue() {
		return this.referenceValue;
	}

	/**
	 * 设置数据reference
	 * 
	 * @param referenceValue
	 *            数据reference
	 */
	public void setReferenceValue(java.lang.String value) {
		this.referenceValue = value;
	}

	public java.lang.String getGroupname() {
		return groupname;
	}

	public void setGroupname(java.lang.String groupname) {
		this.groupname = groupname;
	}

	@Transient
	public java.lang.String getWidgetTypeLabel() {
		return widgetTypeLabel;
	}

	public void setWidgetTypeLabel(java.lang.String widgetTypeLabel) {
		this.widgetTypeLabel = widgetTypeLabel;
	}

	@Transient
	public java.lang.String getAutocompleteConfigLabel() {
		return autocompleteConfigLabel;
	}

	public void setAutocompleteConfigLabel(java.lang.String autocompleteConfigLabel) {
		this.autocompleteConfigLabel = autocompleteConfigLabel;
	}

	@Transient
	public java.lang.String getGroupnameLabel() {
		return groupnameLabel;
	}

	public void setGroupnameLabel(java.lang.String groupnameLabel) {
		this.groupnameLabel = groupnameLabel;
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
			SyWidgetReference castObj = (SyWidgetReference) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getSwrGuid(), castObj.getSwrGuid());
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
			SyWidgetReference castObj = (SyWidgetReference) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getSwrGuid(), castObj.getSwrGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}
