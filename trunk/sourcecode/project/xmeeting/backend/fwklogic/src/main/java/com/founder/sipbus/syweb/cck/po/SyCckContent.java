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

/** SY_CCK_CONTENT 对应实体类 */
@Entity
@Table(name = "SY_CCK_CONTENT")
public class SyCckContent extends BaseEntity {

	private static final long serialVersionUID = 1L;
	public static String SCC_GUID= "sccGuid";
	public static String SCT_GUID= "sctGuid";
	public static String FIELD_DATA1= "fieldData1";
	public static String FIELD_DATA2= "fieldData2";
	public static String FIELD_DATA3= "fieldData3";
	public static String FIELD_DATA4= "fieldData4";
	public static String FIELD_DATA5= "fieldData5";
	public static String FIELD_DATA6= "fieldData6";
	public static String FIELD_DATA7= "fieldData7";
	public static String FIELD_DATA8= "fieldData8";
	public static String FIELD_DATA9= "fieldData9";
	public static String FIELD_DATA10= "fieldData10";
	public static String FIELD_DATA11= "fieldData11";
	public static String FIELD_DATA12= "fieldData12";
	public static String FIELD_DATA13= "fieldData13";
	public static String FIELD_DATA14= "fieldData14";
	public static String FIELD_DATA15= "fieldData15";
	public static String FIELD_DATA16= "fieldData16";
	public static String FIELD_DATA17= "fieldData17";
	public static String FIELD_DATA18= "fieldData18";
	public static String FIELD_DATA19= "fieldData19";
	public static String FIELD_DATA20= "fieldData20"; 

	//primary key
	/** PID */
	private java.lang.String sccGuid;
	/** SY_CCK_TYPE */
	private java.lang.String sctGuid;
	/** 数据字段1 */
	private java.lang.String fieldData1;
	/** 数据字段2 */
	private java.lang.String fieldData2;
	/** 数据字段3 */
	private java.lang.String fieldData3;
	/** 数据字段4 */
	private java.lang.String fieldData4;
	/** 数据字段5 */
	private java.lang.String fieldData5;
	/** 数据字段6 */
	private java.lang.String fieldData6;
	/** 数据字段7 */
	private java.lang.String fieldData7;
	/** 数据字段8 */
	private java.lang.String fieldData8;
	/** 数据字段9 */
	private java.lang.String fieldData9;
	/** 数据字段10 */
	private java.lang.String fieldData10;
	/** 数据字段11 */
	private java.lang.String fieldData11;
	/** 数据字段12 */
	private java.lang.String fieldData12;
	/** 数据字段13 */
	private java.lang.String fieldData13;
	/** 数据字段14 */
	private java.lang.String fieldData14;
	/** 数据字段15 */
	private java.lang.String fieldData15;
	/** 数据字段16 */
	private java.lang.String fieldData16;
	/** 数据字段17 */
	private java.lang.String fieldData17;
	/** 数据字段18 */
	private java.lang.String fieldData18;
	/** 数据字段19 */
	private java.lang.String fieldData19;
	/** 数据字段20 */
	private java.lang.String fieldData20; 

	

	public SyCckContent(){
	}

	public SyCckContent(java.lang.String sccGuid ){
		this.sccGuid = sccGuid;
	}


	/**
	 * 取得PID
	 * return PID
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public java.lang.String getSccGuid() {
		return this.sccGuid;
	}
	
	/**
	 * 设置PID
	 * @param sccGuid PID
	 */
	public void setSccGuid(java.lang.String sccGuid) {
		this.sccGuid = sccGuid;
	}
		/**
		 * 取得SY_CCK_TYPE
		 * return SY_CCK_TYPE
		 */
	public java.lang.String getSctGuid() {
		return this.sctGuid;
	}
	
	/**
	 * 设置SY_CCK_TYPE
	 * @param sctGuid SY_CCK_TYPE
	 */
	public void setSctGuid(java.lang.String value) {
		this.sctGuid = value;
	}
		/**
		 * 取得数据字段1
		 * return 数据字段1
		 */
	public java.lang.String getFieldData1() {
		return this.fieldData1;
	}
	
	/**
	 * 设置数据字段1
	 * @param fieldData1 数据字段1
	 */
	public void setFieldData1(java.lang.String value) {
		this.fieldData1 = value;
	}
		/**
		 * 取得数据字段2
		 * return 数据字段2
		 */
	public java.lang.String getFieldData2() {
		return this.fieldData2;
	}
	
	/**
	 * 设置数据字段2
	 * @param fieldData2 数据字段2
	 */
	public void setFieldData2(java.lang.String value) {
		this.fieldData2 = value;
	}
		/**
		 * 取得数据字段3
		 * return 数据字段3
		 */
	public java.lang.String getFieldData3() {
		return this.fieldData3;
	}
	
	/**
	 * 设置数据字段3
	 * @param fieldData3 数据字段3
	 */
	public void setFieldData3(java.lang.String value) {
		this.fieldData3 = value;
	}
		/**
		 * 取得数据字段4
		 * return 数据字段4
		 */
	public java.lang.String getFieldData4() {
		return this.fieldData4;
	}
	
	/**
	 * 设置数据字段4
	 * @param fieldData4 数据字段4
	 */
	public void setFieldData4(java.lang.String value) {
		this.fieldData4 = value;
	}
		/**
		 * 取得数据字段5
		 * return 数据字段5
		 */
	public java.lang.String getFieldData5() {
		return this.fieldData5;
	}
	
	/**
	 * 设置数据字段5
	 * @param fieldData5 数据字段5
	 */
	public void setFieldData5(java.lang.String value) {
		this.fieldData5 = value;
	}
		/**
		 * 取得数据字段6
		 * return 数据字段6
		 */
	public java.lang.String getFieldData6() {
		return this.fieldData6;
	}
	
	/**
	 * 设置数据字段6
	 * @param fieldData6 数据字段6
	 */
	public void setFieldData6(java.lang.String value) {
		this.fieldData6 = value;
	}
		/**
		 * 取得数据字段7
		 * return 数据字段7
		 */
	public java.lang.String getFieldData7() {
		return this.fieldData7;
	}
	
	/**
	 * 设置数据字段7
	 * @param fieldData7 数据字段7
	 */
	public void setFieldData7(java.lang.String value) {
		this.fieldData7 = value;
	}
		/**
		 * 取得数据字段8
		 * return 数据字段8
		 */
	public java.lang.String getFieldData8() {
		return this.fieldData8;
	}
	
	/**
	 * 设置数据字段8
	 * @param fieldData8 数据字段8
	 */
	public void setFieldData8(java.lang.String value) {
		this.fieldData8 = value;
	}
		/**
		 * 取得数据字段9
		 * return 数据字段9
		 */
	public java.lang.String getFieldData9() {
		return this.fieldData9;
	}
	
	/**
	 * 设置数据字段9
	 * @param fieldData9 数据字段9
	 */
	public void setFieldData9(java.lang.String value) {
		this.fieldData9 = value;
	}
		/**
		 * 取得数据字段10
		 * return 数据字段10
		 */
	public java.lang.String getFieldData10() {
		return this.fieldData10;
	}
	
	/**
	 * 设置数据字段10
	 * @param fieldData10 数据字段10
	 */
	public void setFieldData10(java.lang.String value) {
		this.fieldData10 = value;
	}
		/**
		 * 取得数据字段11
		 * return 数据字段11
		 */
	public java.lang.String getFieldData11() {
		return this.fieldData11;
	}
	
	/**
	 * 设置数据字段11
	 * @param fieldData11 数据字段11
	 */
	public void setFieldData11(java.lang.String value) {
		this.fieldData11 = value;
	}
		/**
		 * 取得数据字段12
		 * return 数据字段12
		 */
	public java.lang.String getFieldData12() {
		return this.fieldData12;
	}
	
	/**
	 * 设置数据字段12
	 * @param fieldData12 数据字段12
	 */
	public void setFieldData12(java.lang.String value) {
		this.fieldData12 = value;
	}
		/**
		 * 取得数据字段13
		 * return 数据字段13
		 */
	public java.lang.String getFieldData13() {
		return this.fieldData13;
	}
	
	/**
	 * 设置数据字段13
	 * @param fieldData13 数据字段13
	 */
	public void setFieldData13(java.lang.String value) {
		this.fieldData13 = value;
	}
		/**
		 * 取得数据字段14
		 * return 数据字段14
		 */
	public java.lang.String getFieldData14() {
		return this.fieldData14;
	}
	
	/**
	 * 设置数据字段14
	 * @param fieldData14 数据字段14
	 */
	public void setFieldData14(java.lang.String value) {
		this.fieldData14 = value;
	}
		/**
		 * 取得数据字段15
		 * return 数据字段15
		 */
	public java.lang.String getFieldData15() {
		return this.fieldData15;
	}
	
	/**
	 * 设置数据字段15
	 * @param fieldData15 数据字段15
	 */
	public void setFieldData15(java.lang.String value) {
		this.fieldData15 = value;
	}
		/**
		 * 取得数据字段16
		 * return 数据字段16
		 */
	public java.lang.String getFieldData16() {
		return this.fieldData16;
	}
	
	/**
	 * 设置数据字段16
	 * @param fieldData16 数据字段16
	 */
	public void setFieldData16(java.lang.String value) {
		this.fieldData16 = value;
	}
		/**
		 * 取得数据字段17
		 * return 数据字段17
		 */
	public java.lang.String getFieldData17() {
		return this.fieldData17;
	}
	
	/**
	 * 设置数据字段17
	 * @param fieldData17 数据字段17
	 */
	public void setFieldData17(java.lang.String value) {
		this.fieldData17 = value;
	}
		/**
		 * 取得数据字段18
		 * return 数据字段18
		 */
	public java.lang.String getFieldData18() {
		return this.fieldData18;
	}
	
	/**
	 * 设置数据字段18
	 * @param fieldData18 数据字段18
	 */
	public void setFieldData18(java.lang.String value) {
		this.fieldData18 = value;
	}
		/**
		 * 取得数据字段19
		 * return 数据字段19
		 */
	public java.lang.String getFieldData19() {
		return this.fieldData19;
	}
	
	/**
	 * 设置数据字段19
	 * @param fieldData19 数据字段19
	 */
	public void setFieldData19(java.lang.String value) {
		this.fieldData19 = value;
	}
		/**
		 * 取得数据字段20
		 * return 数据字段20
		 */
	public java.lang.String getFieldData20() {
		return this.fieldData20;
	}
	
	/**
	 * 设置数据字段20
	 * @param fieldData20 数据字段20
	 */
	public void setFieldData20(java.lang.String value) {
		this.fieldData20 = value;
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
			SyCckContent castObj = (SyCckContent) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getSccGuid(), castObj.getSccGuid());
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
			SyCckContent castObj = (SyCckContent) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getSccGuid(), castObj.getSccGuid());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
}

