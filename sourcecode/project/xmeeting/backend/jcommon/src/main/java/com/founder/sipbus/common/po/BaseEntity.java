/*
 * Copyright 2011 [founder], Inc. All rights reserved.
 * 
 */
package com.founder.sipbus.common.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * 
 * @author cw peng
 * 
 */
@SuppressWarnings("unchecked")
@MappedSuperclass
public abstract class BaseEntity extends AbstractEntity implements Comparable, Serializable {

	
	public static String CREATE_BY="createBy";
	
	public static String UPDATE_BY="updateBy";
	
	public static String CREATE_DT="createDt";
	
	public static String UPDATE_DT="updateDt";
	
	public static String DEL_FLAG="delFlag";
	
	private String createBy;
	
	private String updateBy;
	
	private Date createDt;
	
	private Date updateDt;
	

	/**
	 *  0正常 1删除
	 */
	private Integer delFlag = new Integer(0);
	
	public Integer getDelFlag() {
		return delFlag;
	}


	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}


	@Column(name = "create_by", unique = false, nullable = true, insertable = true, updatable = false, length = 50)
	public String getCreateBy() {
		return createBy;
	}

	
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}



	@Column(name = "update_by", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public String getUpdateBy() {
		return updateBy;
	}

	
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@Column(name = "create_dt", unique = false, nullable = true, insertable = true, updatable = false )
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateDt() {
		return createDt;
	}

	
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	@Column(name = "update_dt", unique = false, nullable = true, insertable = true, updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateDt() {
		return updateDt;
	}

	
	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}
 
}
