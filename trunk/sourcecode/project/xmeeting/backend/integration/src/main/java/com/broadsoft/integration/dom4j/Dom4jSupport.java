package com.broadsoft.integration.dom4j;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

public class Dom4jSupport {

	// 字符串转为Document对象
	public static Document stringToXMLDocment(String xmlStr) {
		Document document = null;

		try {
			document = DocumentHelper.parseText(xmlStr);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}

}//end of Dom4jSupport
