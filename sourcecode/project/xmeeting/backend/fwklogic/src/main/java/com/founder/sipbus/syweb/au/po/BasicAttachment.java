/*
 * Copyright 2012 [founder], Inc. All rights reserved.
 * 
 */
package com.founder.sipbus.syweb.au.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.founder.sipbus.common.po.BaseEntity;

@Entity
@Table(name = "BASIC_ATTACHMENT")
public class BasicAttachment extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String ID = "id";
	public static String SAVE_FILE_NAME = "saveFileName";
	public static String UPLOAD_FILE_NAME = "uploadFileName";
	public static String FILE_SIZE = "fileSize";
	public static String PATH = "path";
	public static String CONTENT_TYPE = "contentType";
	public static String DOMAIN = "domain";
	public static String MEMO = "memo";
;
	// primary key
	/**
	 * 主键
	 */
	private String id;

	/**
	 *  
	 */
	private String saveFileName;
	/**
	 *  
	 */
	private java.lang.String uploadFileName;
	/**
	 *  
	 */
	private java.lang.Long fileSize;
	/**
	 *  
	 */
	private java.lang.String path;
	/**
	 *  
	 */
	private java.lang.String contentType;
	/**
	 *  
	 */
	private java.lang.String domain;
	
	private java.lang.String memo;

	public BasicAttachment() {
	}

	public BasicAttachment(String id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(generator = "trustIdGenerator")
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getSaveFileName()
	{
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName)
	{
		this.saveFileName = saveFileName;
	}

	public java.lang.String getUploadFileName()
	{
		return uploadFileName;
	}

	public void setUploadFileName(java.lang.String uploadFileName)
	{
		this.uploadFileName = uploadFileName;
	}

	public java.lang.Long getFileSize()
	{
		return fileSize;
	}

	public void setFileSize(java.lang.Long fileSize)
	{
		this.fileSize = fileSize;
	}

	public java.lang.String getPath()
	{
		return path;
	}

	public void setPath(java.lang.String path)
	{
		this.path = path;
	}


	public java.lang.String getContentType()
	{
		return contentType;
	}

	public void setContentType(java.lang.String contentType)
	{
		this.contentType = contentType;
	}

	public java.lang.String getDomain()
	{
		return domain;
	}

	public void setDomain(java.lang.String domain)
	{
		this.domain = domain;
	}

	public java.lang.String getMemo()
	{
		return memo;
	}

	public void setMemo(java.lang.String memo)
	{
		this.memo = memo;
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
			BasicAttachment castObj = (BasicAttachment) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getId(), castObj.getId());
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
			BasicAttachment castObj = (BasicAttachment) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getId(), castObj.getId());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}


}
