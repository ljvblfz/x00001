/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
 package com.founder.sipbus.syweb.cck.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

/**
 * SY_CCK_TYPE_BUTTON  cck按钮 对应实体类
 * @author zjl
 *
 */
@Entity
@Table(name = "SY_CCK_TYPE_BUTTON")

public class SyCckTypeButton extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String SCTB_GUID= "sctbGuid";
	public static String SCT_GUID= "sctGuid";
	public static String BUTTON_NAME= "buttonName";
	public static String BUTTON_LABEL= "buttonLabel";
	public static String BUTTON_ICON= "buttonIcon";
	public static String BUTTON_TYPE= "buttonType";
	public static String BUTTON_DETAIL_TYPE_CODE= "buttonDetailTypeCode";
	public static String BUTTON_LINK= "buttonLink";
	public static String BUTTON_LINK_NAVTAB_ID= "buttonLinkNavtabId";
	public static String BUTTON_LINK_TARGET= "buttonLinkTarget";
	public static String BUTTON_IS_MULTIPLE= "buttonIsMultiple";
	public static String BUTTON_SORTNO= "buttonSortno";
	public static String BUTTON_IS_READONLY= "buttonIsReadonly";
	public static String MASTER_ID_COLUMN= "masterIdColumn";
	public static String ICON_URL="iconUrl";
	public static String FUNCTION_NAME="functionName";
	public static String DIALOG_WIDTH="dialogWidth";
	public static String DIALOG_HEIGHT="dialogHeight";
	private java.lang.String iconUrl;
	private java.lang.String functionName;
	private java.lang.String dialogWidth;
	private java.lang.String dialogHeight;
	//primary key
	/**
	 * PID
	 */
	private java.lang.String sctbGuid;

	/**
	 * SY_CCK_TYPE
	 */
	private java.lang.String sctGuid;
	/**
	 * button name
	 */
	private java.lang.String buttonName;
	/**
	 * 按钮显示名称
	 */
	private java.lang.String buttonLabel;
	/**
	 * 按钮图标
	 */
	private java.lang.String buttonIcon;
	/**
	 * 按钮类型
	 */
	private java.lang.String buttonType;
	private java.lang.String buttonTypeString;
	/**
	 * 子表的CCK type编码
	 */
	private java.lang.String buttonDetailTypeCode;
	/**
	 * 按钮连接
	 */
	private java.lang.String buttonLink;
	/**
	 * 按钮DWZ TAB ID
	 */
	private java.lang.String buttonLinkNavtabId;
	/**
	 * 按钮DWZ Target
	 */
	private java.lang.String buttonLinkTarget;
	/**
	 * 是否可多选
	 */
	private java.lang.String buttonIsMultiple;
	/**
	 * 顺序
	 */
	private java.lang.Integer buttonSortno;
	/**
	 * 删除标志
	 */
	/**
	 * 子表是否只显示查看按钮
	 */
	private java.lang.String buttonIsReadonly;
	private java.lang.String childrenSctGuid;

	private java.lang.String	masterIdColumn;
	

	public SyCckTypeButton(){
	}

	public SyCckTypeButton(java.lang.String sctbGuid ){
		this.sctbGuid = sctbGuid;
	}


	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getSctbGuid() {
		return this.sctbGuid;
	}
	
	public void setSctbGuid(java.lang.String value) {
		this.sctbGuid = value;
	}
	public java.lang.String getSctGuid() {
		return this.sctGuid;
	}
	
	public void setSctGuid(java.lang.String value) {
		this.sctGuid = value;
	}
	public java.lang.String getButtonName() {
		return this.buttonName;
	}
	
	public void setButtonName(java.lang.String value) {
		this.buttonName = value;
	}
	public java.lang.String getButtonLabel() {
		return this.buttonLabel;
	}
	
	public void setButtonLabel(java.lang.String value) {
		this.buttonLabel = value;
	}
	public java.lang.String getButtonIcon() {
		return this.buttonIcon;
	}
	
	public void setButtonIcon(java.lang.String value) {
		this.buttonIcon = value;
	}
	public java.lang.String getButtonType() {
		return this.buttonType;
	}
	
	public void setButtonType(java.lang.String value) {
		this.buttonType = value;
	}
	public java.lang.String getButtonDetailTypeCode() {
		return this.buttonDetailTypeCode;
	}
	
	public void setButtonDetailTypeCode(java.lang.String value) {
		this.buttonDetailTypeCode = value;
	}
	public java.lang.String getButtonLink() {
		return this.buttonLink;
	}
	
	public void setButtonLink(java.lang.String value) {
		this.buttonLink = value;
	}
	public java.lang.String getButtonLinkNavtabId() {
		return this.buttonLinkNavtabId;
	}
	
	public void setButtonLinkNavtabId(java.lang.String value) {
		this.buttonLinkNavtabId = value;
	}
	public java.lang.String getButtonLinkTarget() {
		return this.buttonLinkTarget;
	}
	
	public void setButtonLinkTarget(java.lang.String value) {
		this.buttonLinkTarget = value;
	}
	public java.lang.String getButtonIsMultiple() {
		return this.buttonIsMultiple;
	}
	
	public void setButtonIsMultiple(java.lang.String value) {
		this.buttonIsMultiple = value;
	}
	public java.lang.Integer getButtonSortno() {
		return this.buttonSortno;
	}
	
	public void setButtonSortno(java.lang.Integer value) {
		this.buttonSortno = value;
	}
 
	public java.lang.String getButtonIsReadonly() {
		return this.buttonIsReadonly;
	}
	
	public void setButtonIsReadonly(java.lang.String value) {
		this.buttonIsReadonly = value;
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
			SyCckTypeButton castObj = (SyCckTypeButton) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getSctbGuid(), castObj.getSctbGuid());
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
			SyCckTypeButton castObj = (SyCckTypeButton) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getSctbGuid(), castObj.getSctbGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
	@Transient
	public java.lang.String getButtonTypeString() {
		return buttonTypeString;
	}

	public void setButtonTypeString(java.lang.String buttonTypeString) {
		this.buttonTypeString = buttonTypeString;
	}
@Transient
	public java.lang.String getChildrenSctGuid() {
		return childrenSctGuid;
	}

	public void setChildrenSctGuid(java.lang.String childrenSctGuid) {
		this.childrenSctGuid = childrenSctGuid;
	}

	public java.lang.String getMasterIdColumn() {
		return masterIdColumn;
	}

	public void setMasterIdColumn(java.lang.String masterIdColumn) {
		this.masterIdColumn = masterIdColumn;
	}

	public java.lang.String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(java.lang.String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public java.lang.String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(java.lang.String functionName) {
		this.functionName = functionName;
	}

	public java.lang.String getDialogWidth() {
		return dialogWidth;
	}

	public void setDialogWidth(java.lang.String dialogWidth) {
		this.dialogWidth = dialogWidth;
	}

	public java.lang.String getDialogHeight() {
		return dialogHeight;
	}

	public void setDialogHeight(java.lang.String dialogHeight) {
		this.dialogHeight = dialogHeight;
	}
}

