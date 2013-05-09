package com.founder.sipbus.fwk.groovy;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.scripting.support.ScriptFactoryPostProcessor;
import org.springframework.scripting.support.StaticScriptSource;


/**
 * http://static.springsource.org/spring/docs/3.1.x/javadoc-api/org/springframework/scripting/support/ScriptFactoryPostProcessor.html
 * http://www.ibm.com/developerworks/java/library/j-groovierspring2/index.html
 * @author lu.zhen
 *
 */
public class CustomScriptFactoryPostProcessor extends
		ScriptFactoryPostProcessor {

	private Logger logger = LoggerFactory
			.getLogger(CustomScriptFactoryPostProcessor.class);
	
	
	public static final String DATABASE_SCRIPT_PREFIX = "database:";

	private DataSource dataSource;

	@Required
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	protected ScriptSource convertToScriptSource(String beanName,
			String scriptSourceLocator, ResourceLoader resourceLoader) {
//		System.out.println("CustomScriptFactoryPostProcessor-------->convertToScriptSource  beanName:  "+beanName);
	 
		if (scriptSourceLocator.startsWith(INLINE_SCRIPT_PREFIX)) {
			return new StaticScriptSource(
					scriptSourceLocator
							.substring(INLINE_SCRIPT_PREFIX.length()),
					beanName);
		} else if (scriptSourceLocator.startsWith(DATABASE_SCRIPT_PREFIX)) {
			return new DatabaseScriptSource(
					scriptSourceLocator.substring(DATABASE_SCRIPT_PREFIX
							.length()), dataSource);
		} else {
			return new ResourceScriptSource(
					resourceLoader.getResource(scriptSourceLocator));
		}
	}//end of convertToScriptSource

}//end of CustomScriptFactoryPostProcessor
