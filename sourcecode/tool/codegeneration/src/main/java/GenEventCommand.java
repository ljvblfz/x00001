import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.founder.odyssey.generator.model.IModel;
import com.founder.odyssey.generator.utils.FileHelper;
import com.founder.odyssey.generator.utils.IOHelper;
import com.founder.odyssey.generator.utils.PropertiesProvider;
import com.founder.odyssey.generator.utils.StringTemplate;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;



public class GenEventCommand {
	
	public static void main(String[] args) throws IOException {
		

			String a="中国银行学习型阿斯顿发生大幅";
			System.out.println(a.substring(a.indexOf("银行")+2));
			System.out.println(a.substring(0,a.indexOf("银行")+2));
		
		File templateRootDir =  new File("template/eventAndCommand").getAbsoluteFile();
		Configuration config = new Configuration();
		config.setDirectoryForTemplateLoading(templateRootDir);
		config.setNumberFormat("###############");
		config.setBooleanFormat("true,false");
		
		System.out.println(new File("template/eventAndCommand").getAbsoluteFile());
		Map model= new HashMap<String, String>();
		model.put("eventName", "xxxx");
		model.put("serviceMethod", "xxx1x");
		/*
		 * 
		 * subpackage
			thirdpackage
			className
			basepackage_dir
			subpackage
			eventName
			serviceMethod
		 */
		List templateFiles = new ArrayList();
		FileHelper.listFiles(templateRootDir, templateFiles);

		for (int i = 0; i < templateFiles.size(); i++) {
			File templateFile = (File) templateFiles.get(i);
			String templateRelativePath = FileHelper.getRelativePath(
					templateRootDir, templateFile);
			String outputFilePath = templateRelativePath;
			if (templateFile.isDirectory() || templateFile.isHidden())
				continue;
			if (templateRelativePath.trim().equals(""))
				continue;
			if (templateFile.getName().toLowerCase().endsWith(".include")) {
				System.out.println("[skip]\t\t endsWith '.include' template:" + templateRelativePath);
				continue;
			}
			try {
//				System.out.println(getTemplateDataModel(model));
				generateFile(model, config, templateRelativePath,
						outputFilePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	

	private static File getAbsoluteOutputFilePath(String targetFilename) {
		String outRoot = "d:/event";
		File outputFile = new File(outRoot, targetFilename);
		outputFile.getParentFile().mkdirs();
		return outputFile;
	}
	
	private static void generateFile(Map model, Configuration config,
			String templateRelativePath, String outputFilePath)
			throws Exception {
		Template template = config.getTemplate(templateRelativePath);

		String targetFilename = "";//getTargetFilename(model, outputFilePath);

		File absoluteOutputFilePath = getAbsoluteOutputFilePath(targetFilename);
		if (absoluteOutputFilePath.exists()) {
			StringWriter newFileContentCollector = new StringWriter();
			if (isFoundInsertLocation(template, model,
					absoluteOutputFilePath, newFileContentCollector)) {
				System.out.println("[insert]\t generate content into:"
						+ targetFilename);
				IOHelper.saveFile(absoluteOutputFilePath,
						newFileContentCollector.toString());
				return;
			}
		}

		System.out.println("[generate]\t template:" + templateRelativePath
				+ " to " + targetFilename);
		saveNewOutputFileContent(template, model,
				absoluteOutputFilePath);
	}
	
	
	private static String getTargetFilename(Map model, String templateFilepath)
	throws Exception {
		String targetFilename = new StringTemplate(templateFilepath, model).toString();
		return targetFilename;
	}
	
	private static void saveNewOutputFileContent(Template template, Map model,
			File outputFile) throws IOException, TemplateException {
		FileWriter out = new FileWriter(outputFile);
		template.process(model, out);
		out.close();
	}
	private static boolean isFoundInsertLocation(Template template, Map model,
			File outputFile, StringWriter newFileContent) throws IOException,
			TemplateException {
		LineNumberReader reader = new LineNumberReader(new FileReader(
				outputFile));
		String line = null;
		boolean isFoundInsertLocation = false;

		PrintWriter writer = new PrintWriter(newFileContent);
		while ((line = reader.readLine()) != null) {
			writer.println(line);
			// only insert once
			if (!isFoundInsertLocation
					&& line.indexOf("webapp-generator-insert-location") >= 0) {
				template.process(model, writer);
				writer.println();
				isFoundInsertLocation = true;
			}
		}

		writer.close();
		reader.close();
		return isFoundInsertLocation;
	}
}
