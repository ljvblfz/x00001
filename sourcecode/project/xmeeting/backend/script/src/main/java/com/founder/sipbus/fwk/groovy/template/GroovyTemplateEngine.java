package com.founder.sipbus.fwk.groovy.template;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class GroovyTemplateEngine {
	private static Configuration cfg;

	private static String clazzTemplatePackageDir = "/template/groovyftl";
	static {
		cfg = new Configuration();
		cfg.setTemplateLoader(new ClassTemplateLoader(
				GroovyTemplateEngine.class, clazzTemplatePackageDir));
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		cfg.setDefaultEncoding("UTF-8");
		cfg.setOutputEncoding("UTF-8");
	}

	public static String genGroovyScript(String name,Map<String, String> context) {
		String scriptContent = "";
		try {
			Template template = cfg.getTemplate(name);
			StringWriter sw = new StringWriter();
			
			template.process(context, sw);
			scriptContent = sw.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return scriptContent;
	}

	private static void initContext(Map<String, String> context) {
		StringBuilder sbManagedImports = getManagerImports();
		context.put("packageName", "com.founder.sipbus.hrs.scriptdemo");
		context.put("managedImports", sbManagedImports.toString());
		context.put("className", "HelloFtl");
		context.put("methodContent", "System.out.println(\"Hello ftl.\");");
	}

	private static StringBuilder getManagerImports() {
		StringBuilder sbManagedImports = new StringBuilder();
		sbManagedImports.append("import java.util.*;\n");
		sbManagedImports.append("import java.util.Map;\n");
		return sbManagedImports;
	}

	public static void main(String[] args) {
		Map<String, String> context = new HashMap<String, String>();
		initContext(context);
//		String fileName="groovy_function_hrsalary.ftl";
		String fileName="groovy_decisiontable_hrsalary.ftl";
		String script=GroovyTemplateEngine.genGroovyScript(fileName,context);
		System.out.println("script------->"+script);
	}

}
