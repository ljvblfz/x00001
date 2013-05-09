/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
package com.founder.sipbus.syweb.cck.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

/** SY_CCK_TYPE_FIELD对应实体类 */
@Entity
@Table(name = "SY_CCK_TYPE_FIELD")
public class SyCckTypeField extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String SCTF_GUID = "sctfGuid";
	public static String SCT_GUID = "sctGuid";
	public static String FIELD_LABEL = "fieldLabel";
	public static String FIELD_COLUMN = "fieldColumn";
	public static String FIELD_TYPE = "fieldType";
	public static String FIELD_ISREQUIRED = "fieldIsrequired";
	public static String FIELD_MAXLENGTH = "fieldMaxlength";
	public static String FIELD_ISLISTDISPLAY = "fieldIslistdisplay";
	public static String FIELD_ISFORMDISPLAY = "fieldIsformdisplay";
	public static String FIELD_LISTSORTNO = "fieldListsortno";
	public static String FIELD_FORMPOSITION = "fieldFormposition";
	public static String FIELD_DESCRIPTION = "fieldDescription";
	public static String FIELD_TYPE_REFERENCE = "fieldTypeReference";
	public static String FIELD_ISUNIQUE = "fieldIsunique";

	public static String FIELD_ALIAS = "fieldAlias";
	public static String FIELD_ISSEARCHFIELD = "fieldIssearchfield";
	
	
	public static String FIELD_JOIN_TYPE = "fieldJoinType";
	public static String FIELD_TYPE_REFERENCE_TYPE = "fieldTypeReferenceType";
	public static String FIELD_AUTOCOMPLETE_TYPE = "fieldAutocompleteType";
	
	public static String ORDER_TYPE = "orderType";
	public static String ORDER_ORDER = "orderOrder";
	public static String COLUMN_WIDTH = "columnWidth";
	
	public static String AREA_ROWS = "areaRows";
	public static String AREA_COLS = "areaCols";
	private java.lang.String areaRows;
	private java.lang.String areaCols;
	private java.lang.String columnWidth;
	// primary key
	/** PID */
	private java.lang.String sctfGuid;

	/** SY_CCK_TYPE */
	private java.lang.String sctGuid;
	/** label名称 */
	private java.lang.String fieldLabel;
	/** db column */
	private java.lang.String fieldColumn;
	/** 数据类型 */
	private java.lang.String fieldType;
	private java.lang.String fieldTypeString;
	/** 是否必须 */
	private java.lang.String fieldIsrequired;
	private java.lang.String fieldIsrequiredString;
	/** 长度限制 */
	private java.lang.String fieldMaxlength;
	/** 是否列表显示 */
	private java.lang.String fieldIslistdisplay;
	private java.lang.String fieldIslistdisplayString;
	/** 是否表单显示 */
	private java.lang.String fieldIsformdisplay;
	private java.lang.String fieldIsformdisplayString;
	/** 列表顺序 */
	private java.lang.Integer fieldListsortno;
	/** 表单位置 */
	private java.lang.Integer fieldFormposition;
	/** 描述 */
	private java.lang.String fieldDescription;

	private java.lang.String fieldTypeReference;

	private java.lang.String fieldIsunique;
	private java.lang.String fieldIsuniqueString;

	private java.lang.String fieldAlias;

	private java.lang.String fieldIssearchfield;
	private java.lang.String fieldIssearchfieldString;
	
	
	private java.lang.Integer fieldJoinType;
	private  int fieldTypeReferenceType;
	private  int fieldAutocompleteType;
	private java.lang.String orderType;
	private java.lang.String orderTypeString;
	private java.lang.String orderOrder;
	public java.lang.String getFieldAlias() {
		return fieldAlias;
	}

	public void setFieldAlias(java.lang.String fieldAlias) {
		this.fieldAlias = fieldAlias;
	}

	public java.lang.String getFieldIssearchfield() {
		return fieldIssearchfield;
	}

	public void setFieldIssearchfield(java.lang.String fieldIssearchfield) {
		this.fieldIssearchfield = fieldIssearchfield;
	}

	public SyCckTypeField() {
	}

	public SyCckTypeField(java.lang.String sctfGuid) {
		this.sctfGuid = sctfGuid;
	}

	/**
	 * 取得PID return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")
	public java.lang.String getSctfGuid() {
		return this.sctfGuid;
	}

	/**
	 * 设置PID
	 * 
	 * @param sctfGuid
	 *            PID
	 */
	public void setSctfGuid(java.lang.String sctfGuid) {
		this.sctfGuid = sctfGuid;
	}

	/**
	 * 取得SY_CCK_TYPE return SY_CCK_TYPE
	 */
	public java.lang.String getSctGuid() {
		return this.sctGuid;
	}

	/**
	 * 设置SY_CCK_TYPE
	 * 
	 * @param sctGuid
	 *            SY_CCK_TYPE
	 */
	public void setSctGuid(java.lang.String value) {
		this.sctGuid = value;
	}

	/**
	 * 取得label名称 return label名称
	 */
	public java.lang.String getFieldLabel() {
		return this.fieldLabel;
	}

	/**
	 * 设置label名称
	 * 
	 * @param fieldLabel
	 *            label名称
	 */
	public void setFieldLabel(java.lang.String value) {
		this.fieldLabel = value;
	}

	/**
	 * 取得db column return db column
	 */
	public java.lang.String getFieldColumn() {
		return this.fieldColumn;
	}

	/**
	 * 设置db column
	 * 
	 * @param fieldColumn
	 *            db column
	 */
	public void setFieldColumn(java.lang.String value) {
		this.fieldColumn = value;
	}

	/**
	 * 取得数据类型 return 数据类型
	 */
	public java.lang.String getFieldType() {
		return this.fieldType;
	}

	/**
	 * 设置数据类型
	 * 
	 * @param fieldType
	 *            数据类型
	 */
	public void setFieldType(java.lang.String value) {
		this.fieldType = value;
	}

	/**
	 * 取得是否必须 return 是否必须
	 */
	public java.lang.String getFieldIsrequired() {
		return this.fieldIsrequired;
	}

	/**
	 * 设置是否必须
	 * 
	 * @param fieldIsrequired
	 *            是否必须
	 */
	public void setFieldIsrequired(java.lang.String value) {
		this.fieldIsrequired = value;
	}

	/**
	 * 取得长度限制 return 长度限制
	 */
	public java.lang.String getFieldMaxlength() {
		return this.fieldMaxlength;
	}

	/**
	 * 设置长度限制
	 * 
	 * @param fieldMaxlength
	 *            长度限制
	 */
	public void setFieldMaxlength(java.lang.String value) {
		this.fieldMaxlength = value;
	}

	/**
	 * 取得是否列表显示 return 是否列表显示
	 */
	public java.lang.String getFieldIslistdisplay() {
		return this.fieldIslistdisplay;
	}

	/**
	 * 设置是否列表显示
	 * 
	 * @param fieldIslistdisplay
	 *            是否列表显示
	 */
	public void setFieldIslistdisplay(java.lang.String value) {
		this.fieldIslistdisplay = value;
	}

	/**
	 * 取得是否表单显示 return 是否表单显示
	 */
	public java.lang.String getFieldIsformdisplay() {
		return this.fieldIsformdisplay;
	}

	/**
	 * 设置是否表单显示
	 * 
	 * @param fieldIsformdisplay
	 *            是否表单显示
	 */
	public void setFieldIsformdisplay(java.lang.String value) {
		this.fieldIsformdisplay = value;
	}

	/**
	 * 取得列表顺序 return 列表顺序
	 */
	public java.lang.Integer getFieldListsortno() {
		return this.fieldListsortno;
	}

	/**
	 * 设置列表顺序
	 * 
	 * @param fieldListsortno
	 *            列表顺序
	 */
	public void setFieldListsortno(java.lang.Integer value) {
		this.fieldListsortno = value;
	}

	/**
	 * 取得表单位置 return 表单位置
	 */
	public java.lang.Integer getFieldFormposition() {
		return this.fieldFormposition;
	}

	/**
	 * 设置表单位置
	 * 
	 * @param fieldFormposition
	 *            表单位置
	 */
	public void setFieldFormposition(java.lang.Integer  value) {
		this.fieldFormposition = value;
	}

	/**
	 * 取得描述 return 描述
	 */
	public java.lang.String getFieldDescription() {
		return this.fieldDescription;
	}

	/**
	 * 设置描述
	 * 
	 * @param fieldDescription
	 *            描述
	 */
	public void setFieldDescription(java.lang.String value) {
		this.fieldDescription = value;
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
			SyCckTypeField castObj = (SyCckTypeField) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getSctfGuid(), castObj.getSctfGuid());
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
			SyCckTypeField castObj = (SyCckTypeField) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getSctfGuid(), castObj.getSctfGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}

	@Transient
	public java.lang.String getFieldIsrequiredString() {
		return fieldIsrequiredString;
	}

	public void setFieldIsrequiredString(java.lang.String fieldIsrequiredString) {
		this.fieldIsrequiredString = fieldIsrequiredString;
	}

	@Transient
	public java.lang.String getFieldIslistdisplayString() {
		return fieldIslistdisplayString;
	}

	public void setFieldIslistdisplayString(
			java.lang.String fieldIslistdisplayString) {
		this.fieldIslistdisplayString = fieldIslistdisplayString;
	}

	@Transient
	public java.lang.String getFieldIsformdisplayString() {
		return fieldIsformdisplayString;
	}

	public void setFieldIsformdisplayString(
			java.lang.String fieldIsformdisplayString) {
		this.fieldIsformdisplayString = fieldIsformdisplayString;
	}

	@Transient
	public java.lang.String getFieldTypeString() {
		return fieldTypeString;
	}

	public void setFieldTypeString(java.lang.String fieldTypeString) {
		this.fieldTypeString = fieldTypeString;
	}

	public java.lang.String getFieldTypeReference() {
		return fieldTypeReference;
	}

	public void setFieldTypeReference(java.lang.String fieldTypeReference) {
		this.fieldTypeReference = fieldTypeReference;
	}

	public java.lang.String getFieldIsunique() {
		return fieldIsunique;
	}

	public void setFieldIsunique(java.lang.String fieldIsunique) {
		this.fieldIsunique = fieldIsunique;
	}
	@Transient
	public java.lang.String getFieldIssearchfieldString() {
		return fieldIssearchfieldString;
	}

	public void setFieldIssearchfieldString(java.lang.String fieldIssearchfieldString) {
		this.fieldIssearchfieldString = fieldIssearchfieldString;
	}

	@Transient
	public java.lang.String getFieldIsuniqueString() {
		return fieldIsuniqueString;
	}

	public void setFieldIsuniqueString(java.lang.String fieldIsuniqueString) {
		this.fieldIsuniqueString = fieldIsuniqueString;
	}
	
	public java.lang.Integer getFieldJoinType() {
		return fieldJoinType;
	}

	public void setFieldJoinType(java.lang.Integer fieldJoinType) {
		this.fieldJoinType = fieldJoinType;
	}
	//============================
	@Transient
	public int getFieldTypeReferenceType() {
		return fieldTypeReferenceType;
	}

	public void setFieldTypeReferenceType(int fieldTypeReferenceType) {
		this.fieldTypeReferenceType = fieldTypeReferenceType;
	}
	@Transient
	public int getFieldAutocompleteType() {
		return fieldAutocompleteType;
	}

	public void setFieldAutocompleteType(int fieldAutocompleteType) {
		this.fieldAutocompleteType = fieldAutocompleteType;
	}

	public java.lang.String getOrderType() {
		return orderType;
	}

	public void setOrderType(java.lang.String orderType) {
		this.orderType = orderType;
	}

	public java.lang.String getOrderOrder() {
		return orderOrder;
	}

	public void setOrderOrder(java.lang.String orderOrder) {
		this.orderOrder = orderOrder;
	}

	public java.lang.String getColumnWidth() {
		return columnWidth;
	}

	public void setColumnWidth(java.lang.String columnWidth) {
		this.columnWidth = columnWidth;
	}

	public java.lang.String getAreaRows() {
		return areaRows;
	}

	public void setAreaRows(java.lang.String areaRows) {
		this.areaRows = areaRows;
	}

	public java.lang.String getAreaCols() {
		return areaCols;
	}

	public void setAreaCols(java.lang.String areaCols) {
		this.areaCols = areaCols;
	}

	@Transient
	public java.lang.String getOrderTypeString() {
		return orderTypeString;
	}

	public void setOrderTypeString(java.lang.String orderTypeString) {
		this.orderTypeString = orderTypeString;
	}

}
