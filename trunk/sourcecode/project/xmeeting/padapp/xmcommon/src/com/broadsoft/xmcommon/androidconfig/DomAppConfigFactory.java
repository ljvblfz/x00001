package com.broadsoft.xmcommon.androidconfig;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import android.content.res.AssetManager;

/*
 * DOM解析
 */
public class DomAppConfigFactory {
 

	private DomAppConfigFactory() {
	}

	
	private  static AppConfig appConfig; 
	
	public static AppConfig buildAppConfig(AssetManager assetManager){
		if(null!=appConfig){
			return appConfig;
		}else{
			parse(assetManager);
			return appConfig;
		}
	}
	
	/**
	 * In activity,  AssetManager assetManager = getAssets();
	 * @param assetManager
	 * @return
	 */
	public static void parse(AssetManager assetManager) { 
		String assetConfig = "config/appconfig.xml"; 
		try {
			InputStream istr = assetManager.open(assetConfig);
			appConfig=doParse(istr);
		}catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * 
	 * @param in
	 * @return
	 */
	private static AppConfig doParse(InputStream in) {
		// 获取构建器
		DocumentBuilder builder = createDocumentBuilder();
		//解析xml
		try {
			Document doc = builder.parse(in);
			Element eleappconfig = doc.getElementById("appconfig"); 
			AppConfig appConfig = xmlMapping(eleappconfig); 
			
			return appConfig;
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static AppConfig xmlMapping(Element eleappconfig) {
		AppConfig appConfig = new AppConfig();
		appConfig.appname = eleappconfig.getAttribute("appname");
		appConfig.sdcarddir = eleappconfig.getAttribute("sdcarddir");  
		appConfig.databasename = eleappconfig.getAttribute("databasename");
		appConfig.databaseversion = eleappconfig.getAttribute("databaseversion");
		appConfig.serveripport = eleappconfig.getAttribute("serveripport");
		return appConfig;
	}


	/**
	 * 
	 * @return
	 */
	private static DocumentBuilder createDocumentBuilder() {
		DocumentBuilder builder=null;
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance(); 
		try {
			builder = f.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return builder;
	}

}
