<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.${subpackage}.${thirdpackage}.dao;


import org.springframework.stereotype.Component;

import ${commonpackage}.dao.DefaultBaseDaoImpl;
import ${basepackage}.${subpackage}.${thirdpackage}.po.*;

/** ${table.remarks}对应DAO */
@Component
public class ${className}DaoImpl extends DefaultBaseDaoImpl<${className},<#if !table.compositeId><#list table.columns as column><#if column.pk>${column.javaType}</#if></#list></#if>> {
}
