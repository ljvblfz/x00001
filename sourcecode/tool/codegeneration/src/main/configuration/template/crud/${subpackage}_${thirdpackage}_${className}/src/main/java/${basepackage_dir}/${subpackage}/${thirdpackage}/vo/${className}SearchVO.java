<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
package ${basepackage}.${subpackage}.${thirdpackage}.vo;

/** ${table.remarks}对应页面查询VO */
public class ${className}SearchVO {

	private static final long serialVersionUID = 1L;
 
	<#if !table.compositeId>
	<#list table.columns as column>
	<#if !column.ccuu>
	<#if column.asType=='Number'>
	/** ${column.comments}_开始 */
	public String ${column.columnNameLower}_start;
	/** ${column.comments}_结束 */
	public String ${column.columnNameLower}_end;
	/** ${column.comments} */
	public String ${column.columnNameLower};
	<#elseif column.asType=='Date'>
	/** ${column.comments}_开始 */
	public String ${column.columnNameLower}_start;
	/** ${column.comments}_结束 */
	public String ${column.columnNameLower}_end;
	/** ${column.comments} */
	public String ${column.columnNameLower};
	<#else>
	/** ${column.comments} */
	public String ${column.columnNameLower};
	</#if>
	</#if>
	</#list>
	</#if>
	
	
	

	<#if !table.compositeId>
	<#list table.columns as column>
	<#if !column.ccuu>
	<#if ((column.asType=='Number')||(column.asType=='Date'))>
	/**
	 * 取得${column.comments}_开始
	 * return ${column.comments}_开始
	 */
	public String get${column.columnName}_start() {
		return this.${column.columnNameLower}_start;
	}
	/**
	 * 设置${column.comments}_开始
	 * @param ${column.columnNameLower}_start ${column.comments}_开始
	 */
	public void set${column.columnName}_start(String ${column.columnNameLower}_start) {
		this.${column.columnNameLower}_start = ${column.columnNameLower}_start;
	}
	/**
	 * 取得${column.comments}_结束
	 * return ${column.comments}_结束
	 */
	public String get${column.columnName}_end() {
		return this.${column.columnNameLower}_end;
	}
	/**
	 * 设置${column.comments}_结束
	 * @param ${column.columnNameLower}_end ${column.comments}_结束
	 */
	public void set${column.columnName}_end(String ${column.columnNameLower}_end) {
		this.${column.columnNameLower}_end = ${column.columnNameLower}_end;
	}
	/**
	 * 取得${column.comments}
	 * return ${column.comments}
	 */
	public String get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	/**
	 * 设置${column.comments}
	 * @param ${column.columnNameLower} ${column.comments}
	 */
	public void set${column.columnName}(String value) {
		this.${column.columnNameLower} = value;
	}
	<#else>
	/**
	 * 取得${column.comments}
	 * return ${column.comments}
	 */
	public String get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	/**
	 * 设置${column.comments}
	 * @param ${column.columnNameLower} ${column.comments}
	 */
	public void set${column.columnName}(String ${column.columnNameLower}) {
		this.${column.columnNameLower} = ${column.columnNameLower};
	}
	</#if>
	</#if>
	</#list>
	</#if>
	
	 
 
}

