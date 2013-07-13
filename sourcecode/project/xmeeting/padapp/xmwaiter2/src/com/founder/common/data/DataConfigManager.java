package com.founder.common.data;
import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.res.AssetManager;


public class DataConfigManager {
	
	private final String sDataConfigFilePath = "DataSourceConfig.xml";
	private AssetManager assetManager;
	public DataConfigManager(AssetManager assetManager)
	{
		this.assetManager = assetManager;
	}
	
	public String getSocketUrl(String sRequestName)
	{
		try {
			InputStream inputStream = assetManager.open(sDataConfigFilePath);
			DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();  
			DocumentBuilder db=dbf.newDocumentBuilder(); 
			Document doc =db.parse(inputStream);
			Element root = doc.getDocumentElement(); 
			NodeList lNetDataSource = root.getElementsByTagName("Socket");
			String sServerIp = ((Element)lNetDataSource.item(0)).getAttribute("ServerIp");
			
			NodeList nodeList = doc.getElementsByTagName("RequestUrl");
			for (int i = 0; i < nodeList.getLength(); i++)
			{				
				if ( ( (Element)nodeList.item(i) ).getAttribute("name").equals(sRequestName) )
				{
					String sPort = ( (Element) nodeList.item(i) ).getAttribute("Port");;
					return sServerIp + ":" + sPort;
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public String getDataUrl(String sRequestName, String sArg)
	{
		try {
			InputStream inputStream = assetManager.open(sDataConfigFilePath);
			DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();  
			DocumentBuilder db=dbf.newDocumentBuilder(); 
			Document doc =db.parse(inputStream);
			Element root = doc.getDocumentElement(); 
			NodeList lNetDataSource = root.getElementsByTagName("NetDataSource");
			String sServerIp = ((Element)lNetDataSource.item(0)).getAttribute("ServerIp");
			
			NodeList nodeList = doc.getElementsByTagName("RequestUrl");
			for (int i = 0; i < nodeList.getLength(); i++)
			{				
				if ( ( (Element)nodeList.item(i) ).getAttribute("name").equals(sRequestName) )
				{
					String sPrefixUrl = ( (Element) nodeList.item(i) ).getAttribute("prefixUrl");
					String suffixUrl = ( (Element) nodeList.item(i) ).getAttribute("suffixUrl");
					return sServerIp + sPrefixUrl + sArg + suffixUrl;
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public String getAuthenticationUrl(String sRequestName, String sArg)
	{
		try {
			InputStream inputStream = assetManager.open(sDataConfigFilePath);
			DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();  
			DocumentBuilder db=dbf.newDocumentBuilder(); 
			Document doc =db.parse(inputStream);
			Element root = doc.getDocumentElement(); 
			NodeList lNetDataSource = root.getElementsByTagName("Authentication");
			String sServerIp = ((Element)lNetDataSource.item(0)).getAttribute("ServerIp");
			
			NodeList nodeList = doc.getElementsByTagName("RequestUrl");
			for (int i = 0; i < nodeList.getLength(); i++)
			{				
				if ( ( (Element)nodeList.item(i) ).getAttribute("name").equals(sRequestName) )
				{
					String sPrefixUrl = ( (Element) nodeList.item(i) ).getAttribute("prefixUrl");
					String suffixUrl = ( (Element) nodeList.item(i) ).getAttribute("suffixUrl");
					return sServerIp + sPrefixUrl + sArg + suffixUrl;
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public class Parse
	{  
		private Document doc=null;  
		    
		public void init(String xmlFile) throws Exception
		{  
			DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();  
			DocumentBuilder db=dbf.newDocumentBuilder();    
			doc=db.parse(new File(xmlFile));  
		} 
		
		 public void viewXML(String xmlFile) throws Exception
		 {  
			 this.init(xmlFile);  
			 Element element=doc.getDocumentElement();  
			 System.out.println("根元素为:"+element.getTagName());  
		    
			 NodeList nodeList=doc.getElementsByTagName("dbstore");  
		  	 System.out.println("dbstore节点链的长度:"+nodeList.getLength());  
		    
		  	 Node fatherNode=nodeList.item(0);  
		  	 System.out.println("父节点为:"+fatherNode.getNodeName());  
		    
		  	 NamedNodeMap attributes=fatherNode.getAttributes();  
		    
		   	 for(int i=0;i<attributes.getLength();i++)
		  	 {  
		  	 	 Node attribute=attributes.item(i);  
		  		 System.out.println("dbstore的属性名为:"+attribute.getNodeName()+" 相对应的属性值为:"+attribute.getNodeValue());  
		  	 }  
		  
		  	 NodeList childNodes = fatherNode.getChildNodes();  
		  	 System.out.println(childNodes.getLength());  
		  	 for(int j=0;j<childNodes.getLength();j++)
		  	 {  
		  		 Node childNode=childNodes.item(j);  
		  		if(childNode instanceof Element)
		  		 {  
		  			 System.out.println("子节点名为:"+childNode.getNodeName()+"相对应的值为"+childNode.getFirstChild().getNodeValue());  
		  		 }  
		  	 }  
		    
		 }  
	}
}
