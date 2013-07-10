<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.${subpackage}.${thirdpackage}.po;

import javax.persistence.*;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.GenericGenerator;

import ${commonpackage}.po.BaseEntity;

/** ${table.remarks}对应实体类 */
@Entity
@Table(name = "${table.realSqlName}")
public class ${className} extends BaseEntity {

	private static final long serialVersionUID = 1L;
	<@generateStaticColumns/>
	<@generateFields/>
	<@generateConstructor className/>
	<@generatePkProperties/>
	<@generateNotPkProperties/>
	<@generateJavaManyToOne/>
	<@genEquals/>
}

<#macro generateStaticColumns>
<#if !table.compositeId>
<#list table.columns as column>
	<#if !column.isCreateOrUpdate >
	public static String ${column.sqlName}= "${column.columnNameLower}";
	</#if>
	</#list>
</#if>
</#macro>

<#macro generateFields>

<#if !table.compositeId>
<#list table.columns as column>
	<#if column.pk>
	//primary key
	/** ${column.comments} */
	private ${column.javaType} ${column.columnNameLower};
	</#if>
</#list>
</#if>
<#if table.compositeId>
	private ${className}Id id;
	<#list table.columns as column>
		<#if !column.pk>
	/** ${column.comments} */
	private ${column.javaType} ${column.columnNameLower};
		</#if>
	</#list>
<#else>
	<#list table.columns as column>
		<#if !column.pk>
			<#if !column.fk>
			<#if !column.isCreateOrUpdate >
	/** ${column.comments} */
	private ${column.javaType} ${column.columnNameLower};
			</#if>
			</#if>
		</#if>
	</#list>

	<#list table.importedKeys.associatedTables?values as foreignKey>
		<#assign fkSqlTable = foreignKey.sqlTable>
		<#assign fkTable    = fkSqlTable.className>
		<#assign fkPojoClass = fkSqlTable.className>
		<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	private ${fkPojoClass} ${fkPojoClassVar};
	</#list>
	
</#if>

</#macro>

<#macro generatePkProperties>
	<#if !table.compositeId>
		<#list table.columns as column>
			<#if column.pk>

	/**
	 * 取得${column.comments}
	 * return ${column.comments}
	 */
	@Id
	@GeneratedValue(generator = "trustIdGenerator")      
	@GenericGenerator(name = "trustIdGenerator", strategy = "com.founder.sipbus.common.util.FounderHibernateIDGenerator")     
	public ${column.javaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	
	/**
	 * 设置${column.comments}
	 * @param ${column.columnNameLower} ${column.comments}
	 */
	public void set${column.columnName}(${column.javaType} ${column.columnNameLower}) {
		this.${column.columnNameLower} = ${column.columnNameLower};
	}
			</#if>
		</#list>
	</#if>
</#macro>


<#macro generateNotPkProperties>
	<#list table.columns as column>
		<#if !column.pk >
		<#if !column.isCreateOrUpdate >
		/**
		 * 取得${column.comments}
		 * return ${column.comments}
		 */
	public ${column.javaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	
	/**
	 * 设置${column.comments}
	 * @param ${column.columnNameLower} ${column.comments}
	 */
	public void set${column.columnName}(${column.javaType} value) {
		this.${column.columnNameLower} = value;
	}
		</#if>
		</#if>
	</#list>
</#macro>

<#macro generateJavaManyToOne>
	<#list table.importedKeys.associatedTables?values as foreignKey>
	<#assign fkSqlTable = foreignKey.sqlTable>
	<#assign fkTable    = fkSqlTable.className>
	<#assign fkPojoClass = fkSqlTable.className>
	<#assign fkPojoClassVar = fkPojoClass?uncap_first>
	
	
	public void set${fkPojoClass}(${fkPojoClass} ${fkPojoClassVar}){
		this.${fkPojoClassVar} = ${fkPojoClassVar};
	}
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	<#list foreignKey.parentColumns?values as fkColumn>
	@JoinColumn(name = "${fkColumn}",nullable = false, insertable = false, updatable = false)
    </#list>
	public ${fkPojoClass} get${fkPojoClass}() {
		return ${fkPojoClassVar};
	}
	</#list>
</#macro>


<#macro genEquals>
<#if !table.compositeId>

<#list table.columns as column>
	<#if column.pk>
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
			${className} castObj = (${className}) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.get${column.columnName}(), castObj.get${column.columnName}());
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
			${className} castObj = (${className}) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.get${column.columnName}(), castObj.get${column.columnName}());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}
	</#if>
	</#list>
</#if>
</#macro>