<#include "/java_copyright.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>   
package ${basepackage}.${subpackage}.dao.${thirdpackage};

import java.sql.Timestamp;

import org.junit.Test;

import ${commonpackage}.util.ApplicationContextUtil;
import ${basepackage}.${subpackage}.dao.${thirdpackage}.${className}DaoImpl;
import ${basepackage}.${subpackage}.domain.${thirdpackage}.${className};

public class ${className}DaoTest {
	private ${className}DaoImpl  ${classNameLower}Dao= (${className}DaoImpl)ApplicationContextUtil.getBean("${classNameLower}Dao");

	private static ${className} ${classNameLower};
	
	@Test
	public void initEntity(){
		${classNameLower}= new ${className}();
		//TODO init Entity sysLoginLog
	}
	
	@Test
	public void findAllTest() {
		try {
			${classNameLower}Dao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}
	