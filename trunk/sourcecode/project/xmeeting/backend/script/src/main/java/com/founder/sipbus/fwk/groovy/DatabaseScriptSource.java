package com.founder.sipbus.fwk.groovy;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scripting.ScriptSource;
import org.springframework.util.StringUtils;

import com.founder.sipbus.fwk.groovy.template.GroovyTemplateEngine;

public class DatabaseScriptSource implements ScriptSource {

	private Logger logger = LoggerFactory
			.getLogger(CustomScriptFactoryPostProcessor.class);
	private final String scriptName;
	private final JdbcTemplate jdbcTemplate;
	private Timestamp lastKnownUpdate;

	private final Object lastModifiedMonitor = new Object();
	// private final String tableName="sy_groovy_script";
	private final String tableName = "sy_script";

	public final static String SCRIPT_TYPE_FUNCTION = "1";
	public final static String SCRIPT_TYPE_SQLtEXT = "2";
	public final static String SCRIPT_TYPE_DECISIONTABLE = "3";
	public final static String SCRIPT_TYPE_SINGLEVALUE = "5";

	public DatabaseScriptSource(String scriptName, DataSource dataSource) { 
		this.scriptName = scriptName.trim();
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public String getScriptAsString() throws IOException {
//		System.out
//				.println(Thread.currentThread().getId()
//						+ "--->DatabaseScriptSource-------->getScriptAsString *************begin.");
		synchronized (this.lastModifiedMonitor) {
			this.lastKnownUpdate = retrieveLastModifiedTime();
		}

		Map<String, Object> item = jdbcTemplate.queryForMap(
				"select script_source,script_type,bean_name from " + tableName
						+ " where script_name = ? and del_flag=0 and status=1",
				new Object[] { this.scriptName });

		if (item.isEmpty()) {
			throw new RuntimeException("No script in database. script_name= "
					+ scriptName);
		}
		Object objScriptType = item.get("script_type");
		Object objscriptSource = item.get("script_source");
		Object objbeanName = item.get("bean_name");

		if (null == objscriptSource || null == objScriptType) {
			throw new RuntimeException(
					"Initialize the script bean error. objScriptType="
							+ objScriptType);
		}
		if(logger.isTraceEnabled()){ 
			logger.trace("DatabaseScriptSource-------->getScriptAsString   bean_name="+objbeanName+" scriptName="+scriptName+"  script_type="
							+ objScriptType + " scriptSource=" + objscriptSource);
		}
		String scriptSource = (String) objscriptSource;
		String scriptType = (String) objScriptType;
		String beanName = (String) objbeanName;
//		Map<String, String> context = initTemplateContext(this.scriptName,
//				scriptSource);
		Map<String, String> context = initTemplateContext(beanName,scriptSource);

		String scriptClazzContent = "";

		if (SCRIPT_TYPE_FUNCTION.equals(scriptType)) {
			scriptClazzContent = getFunctionTemplate(context);
		} else if (SCRIPT_TYPE_DECISIONTABLE.equals(scriptType)) {
			scriptClazzContent = getDecisionTableTemplate(context);
		} else if (SCRIPT_TYPE_SQLtEXT.equals(scriptType)) {
			scriptClazzContent = getSqlTextTemplate(context);
		} else if (SCRIPT_TYPE_SINGLEVALUE.equals(scriptType)) {
			scriptClazzContent = getSingleValueTemplate(context);
		}
		if(logger.isTraceEnabled()){ 
			logger.trace("scriptClazzContent=" + scriptClazzContent);
		}
//		System.out
//				.println(Thread.currentThread().getId()
//						+ "--->DatabaseScriptSource-------->getScriptAsString *************end. ");
		return scriptClazzContent;
	}

	private String getFunctionTemplate(Map<String, String> context) {
		String fileName = "groovy_function_hrsalary.ftl";
		String script = GroovyTemplateEngine.genGroovyScript(fileName, context);
		return script;
	}

	private String getDecisionTableTemplate(Map<String, String> context) {
		String fileName = "groovy_decisiontable_hrsalary.ftl";
		String script = GroovyTemplateEngine.genGroovyScript(fileName, context);
		return script;
	}

	private String getSqlTextTemplate(Map<String, String> context) {
		String fileName = "groovy_sqltext_hrsalary.ftl";
		String script = GroovyTemplateEngine.genGroovyScript(fileName, context);
		return script;
	}

	private String getSingleValueTemplate(Map<String, String> context) {
		String fileName = "groovy_singlevalue_hrsalary.ftl";
		String script = GroovyTemplateEngine.genGroovyScript(fileName, context);
		return script;
	}

	private static StringBuilder getManagerImports() {
		StringBuilder sbManagedImports = new StringBuilder();
		sbManagedImports.append("import java.util.*;\n");
		sbManagedImports.append("import java.util.Map;\n");
		return sbManagedImports;
	}

	private Map<String, String> initTemplateContext(String clzNameSuffix,
			String script_content) {
		Map<String, String> context = new HashMap<String, String>();
		context.put("packageName", "com.founder.sipbus.hrs.scriptdemo");
		StringBuilder sbManagedImports = getManagerImports();
		context.put("managedImports", sbManagedImports.toString());
		context.put("className", "Clz_" + clzNameSuffix);
		if (null != script_content && !"".equals(script_content.trim())) {
			context.put("methodContent", script_content);
		} else {
			context.put("methodContent", "");
		}
		return context;
	}

	public boolean isModified() {
		synchronized (this.lastModifiedMonitor) {
			Timestamp lastUpdated = retrieveLastModifiedTime();
			boolean ismodified = lastUpdated.after(this.lastKnownUpdate); 
			return ismodified;
		}
	}

	public String suggestedClassName() { 
		return StringUtils.stripFilenameExtension(this.scriptName);
	}

	private Timestamp retrieveLastModifiedTime() { 
		String sql = "select UPDATE_DT from " + tableName + " where script_name = ? and del_flag=0 and   status=1";
		Object objLastTimestamp = this.jdbcTemplate.queryForObject(sql,
				new Object[] { this.scriptName }, Object.class);
 
		Timestamp lastTimestamp = (Timestamp) objLastTimestamp; 
		return lastTimestamp;
	}
}