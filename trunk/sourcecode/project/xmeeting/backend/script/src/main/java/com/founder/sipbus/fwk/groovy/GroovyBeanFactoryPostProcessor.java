package com.founder.sipbus.fwk.groovy;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.founder.sipbus.fwk.groovy.sample.BeanA;
import com.founder.sipbus.fwk.groovy.sample.BeanB;

public class GroovyBeanFactoryPostProcessor implements BeanFactoryPostProcessor  {

	private Logger logger = LoggerFactory
			.getLogger(GroovyBeanFactoryPostProcessor.class);
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
    private DefaultListableBeanFactory beanFactoryInstance; 
	private final static String GroovyScriptFactoryClazz="org.springframework.scripting.groovy.GroovyScriptFactory";
	private final String tableName="sy_script";
//	private final String tableName="sy_groovy_script";
	
	private String scriptGroup; 
	public void setScriptGroup(String scriptGroup) {
		this.scriptGroup = scriptGroup;
	}


	public final static String SCRIPT_TYPE_FUNCTION="1";
	public final static String SCRIPT_TYPE_SQLtEXT="2";
	public final static String SCRIPT_TYPE_DECISIONTABLE="3";
	public final static String SCRIPT_TYPE_SINGLEVALUE="5";
	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException { 
		beanFactoryInstance = (DefaultListableBeanFactory)beanFactory;  
		if(null==dataSource){
			this.dataSource=(DataSource)beanFactoryInstance.getBean("businessDataSource"); 
			this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
		String sql="select script_name,bean_name,script_type from "+tableName+"  where del_flag=0 and status=1 and group_name='"+scriptGroup+"'   order by bean_name";
		Object[] args={};
		List<Map<String,Object>> listOfItem=(List<Map<String,Object>>)jdbcTemplate.queryForList(sql,args);
		//
//		registerInlineGroovyBean();
		//
		for(Map<String,Object> item:listOfItem){ 
			String scriptName=(String)item.get("script_name");
			String beanName=(String)item.get("bean_name");
			String scriptType=(String)item.get("script_type");
			
//			System.out.println("  script_name--->"+scriptName+"  bean_name-->"+beanName+"  script_type--->"+scriptType);
			if(SCRIPT_TYPE_FUNCTION.equals(scriptType)){ 
				registerDatabaseGroovyBean(scriptName,beanName);
			}else if(SCRIPT_TYPE_DECISIONTABLE.equals(scriptType)){ 
				registerDatabaseDecisionTableBean(scriptName,beanName);
			}else if(SCRIPT_TYPE_SQLtEXT.equals(scriptType)){ 
				registerDatabaseSqlTextBean(scriptName,beanName);
			}else if(SCRIPT_TYPE_SINGLEVALUE.equals(scriptType)){ 
				registerDatabaseSingleValueBean(scriptName,beanName);
			}
		}
		
	}
	
	



	
	
	
	private void registerDatabaseGroovyBean(String scriptname,String beanName) { 
		String location = CustomScriptFactoryPostProcessor.DATABASE_SCRIPT_PREFIX+scriptname ;
	    //define and register the groovy bean
	    BeanDefinitionBuilder bdb2 = BeanDefinitionBuilder.rootBeanDefinition(GroovyScriptFactoryClazz).addConstructorArgValue(location);
//	    bdb2.getBeanDefinition().setAttribute(ScriptFactoryPostProcessor.REFRESH_CHECK_DELAY_ATTRIBUTE,60000);
	    beanFactoryInstance.registerBeanDefinition("database_groovy_"+beanName, bdb2.getBeanDefinition());
	}	
	
	private void registerDatabaseDecisionTableBean(String scriptname,String beanName) { 
		String location = CustomScriptFactoryPostProcessor.DATABASE_SCRIPT_PREFIX+scriptname ;
	    //define and register the decision table bean
	    BeanDefinitionBuilder bdb2 = BeanDefinitionBuilder.rootBeanDefinition(GroovyScriptFactoryClazz).addConstructorArgValue(location);
//	    bdb2.getBeanDefinition().setAttribute(ScriptFactoryPostProcessor.REFRESH_CHECK_DELAY_ATTRIBUTE,60000);
	    beanFactoryInstance.registerBeanDefinition("database_decisiontable_"+beanName, bdb2.getBeanDefinition());
	}	
	

	private void registerDatabaseSqlTextBean(String scriptname,String beanName) { 
		String location = CustomScriptFactoryPostProcessor.DATABASE_SCRIPT_PREFIX+scriptname ;
	    //define and register the single value bean
	    BeanDefinitionBuilder bdb2 = BeanDefinitionBuilder.rootBeanDefinition(GroovyScriptFactoryClazz).addConstructorArgValue(location);
//	    bdb2.getBeanDefinition().setAttribute(ScriptFactoryPostProcessor.REFRESH_CHECK_DELAY_ATTRIBUTE,60000);
	    beanFactoryInstance.registerBeanDefinition("database_sqltext_"+beanName, bdb2.getBeanDefinition());
	}
	
	private void registerDatabaseSingleValueBean(String scriptname,String beanName) { 
		String location = CustomScriptFactoryPostProcessor.DATABASE_SCRIPT_PREFIX+scriptname ;
	    //define and register the single value bean
	    BeanDefinitionBuilder bdb2 = BeanDefinitionBuilder.rootBeanDefinition(GroovyScriptFactoryClazz).addConstructorArgValue(location);
//	    bdb2.getBeanDefinition().setAttribute(ScriptFactoryPostProcessor.REFRESH_CHECK_DELAY_ATTRIBUTE,60000);
	    beanFactoryInstance.registerBeanDefinition("database_singlevalue_"+beanName, bdb2.getBeanDefinition());
	}
	
	
	private void registerGeneralBean() {
		BeanDefinitionBuilder builderA = BeanDefinitionBuilder
		   .rootBeanDefinition(BeanA.class)
		    .addPropertyValue("name", "Joe");
		beanFactoryInstance.registerBeanDefinition("bean-a", builderA.getBeanDefinition()); 
		BeanDefinitionBuilder builderB = BeanDefinitionBuilder.rootBeanDefinition(BeanB.class).addPropertyReference("beanA", "bean-a");
		beanFactoryInstance.registerBeanDefinition("bean-b", builderB.getBeanDefinition());
	}
	
	
	private void registerInlineGroovyBean() {
		String location = CustomScriptFactoryPostProcessor.INLINE_SCRIPT_PREFIX + "package helloy; \n"+
				"import com.founder.sipbus.fwk.groovy.execution.*;    \n"+
				"class hello implements IScriptExecution{   \n "+
				"	public void execute(ScriptExecutionContext scriptExecutionContext){    \n"+
				"		System.out.println(\"helloWorld!\");     \n"+
				"	}    \n"+
				"}     \n";
	    //define and register the+ groovy bean
	    BeanDefinitionBuilder bdb2 = BeanDefinitionBuilder.rootBeanDefinition(GroovyScriptFactoryClazz).addConstructorArgValue(location);
//	    bdb2.getBeanDefinition().setAttribute(ScriptFactoryPostProcessor.REFRESH_CHECK_DELAY_ATTRIBUTE,600);
	    beanFactoryInstance.registerBeanDefinition("inline_script_"+"hellogrovvy", bdb2.getBeanDefinition());
	}
	
	
	
	

}


