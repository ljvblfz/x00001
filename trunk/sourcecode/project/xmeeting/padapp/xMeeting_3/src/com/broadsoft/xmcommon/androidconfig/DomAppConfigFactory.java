package com.broadsoft.xmcommon.androidconfig;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.res.AssetManager;
import android.util.Log;

/*
 * DOM解析
 */
public class DomAppConfigFactory {

	private final static String TAG="DomAppConfigFactory";

	private DomAppConfigFactory() {
	}
	
	

	
	private  static AppConfig appConfig; 
	
	public static void init(AssetManager assetManager){
		Log.d(TAG, "init begin");
		parse(assetManager);
		Log.d(TAG, "init end");
	}
	
	
	
	public static AppConfig getAppConfig(){ 
		return appConfig;
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
		NodeList listOfNodes= eleappconfig.getChildNodes();
		
		for(int i=0;i<listOfNodes.getLength();i++){
			Node node=listOfNodes.item(i);
			String nodeName=node.getNodeName();
			String textContent=node.getTextContent(); 
			if("appname".equals(nodeName.toLowerCase())){
				appConfig.appname = textContent;
			}else if("sdcarddir".equals(nodeName.toLowerCase())){
				appConfig.sdcarddir = textContent;
			}else if("databasename".equals(nodeName.toLowerCase())){
				appConfig.databasename = textContent;
			}else if("databaseversion".equals(nodeName.toLowerCase())){
				appConfig.databaseversion = textContent;
			}else if("serveripport".equals(nodeName.toLowerCase())){
				appConfig.serveripport =textContent;
			} else if("serverenable".equals(nodeName.toLowerCase())){
				appConfig.serverenable=textContent;
			} else if("version".equals(nodeName.toLowerCase())){
				appConfig.version=textContent;
			} 
		} //end of for
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
