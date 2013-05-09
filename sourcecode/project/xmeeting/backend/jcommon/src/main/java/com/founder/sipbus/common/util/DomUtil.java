/**
 * Copyright c FOUNDER CORPORATION 2006 All Rights Reserved.
 * DomUtil.java
 */
package com.founder.sipbus.common.util;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.SystemException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;



/**
 * [クラス名]<br>
 * Dom Parser関連Utilityクラス<br>
 * <br>
 * [機能概要]<br>
 * Dom Parser関連Utility関数群<br>
 * <br>
 * [変更履歴]<br>
 * 2003/11/06 ver1.00 新規作成 zhanjc<br>
 * 
 * @author zhanjc
 * @version 1.00
 */
public class DomUtil {
    /**
     * XML文書のファクトリーを格納する
     */
    private static DocumentBuilderFactory factory = null;

    /**
     * ログオブジェクト
     */
    private static Log debugLog = LogFactory.getLog(DomUtil.class);
    // 変量の初期化
    static {
        factory = DocumentBuilderFactory.newInstance();
    }

    /**
     * XMLファイルから指定されたエレメントの指定された属性を取得する
     * 
     * @param xmlFile
     *            XMLファイル
     * @param tagName
     *            エレメント名
     * @param attrName
     *            エレメントの属性名
     * @return String 取得した属性値
     */
    public static String getAttributeValue(File xmlFile, String tagName, String attrName) {
        String attrValue = null;// 属性値
        // check input condition
        if (xmlFile == null || tagName == null || attrName == null) {
            return attrValue;
        }
        // get an attribute value
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            NodeList list = doc.getElementsByTagName(tagName);
            Node node = list.item(0);

            if (node == null) {
                return attrValue;
            }
            NamedNodeMap attrs = node.getAttributes();
            for (int i = 0; i < attrs.getLength(); i++) {
                // 属性名が一致
                if (attrs.item(i).getNodeName().equals(attrName)) {
                    attrValue = attrs.item(i).getNodeValue();
                    break;
                }
            }
        } catch (Exception e) {
            debugLog.error(e.getMessage());
        }

        return attrValue;
    }

    /**
     * XMLファイルから指定されたエレメントの指定された结点を取得する
     * 
     * @param xmlFile
     *            XMLファイル
     * @param tagName
     *            エレメント名(get the first node of this name)
     * @param nodeName
     *            エレメントの结点名
     * @return List 取得结点の値の集合
     */
    public static List getNodeValue(File xmlFile, String tagName, String nodeName) {
        List<String> nodeValue = new ArrayList<String>();// 结点値
        // check input condition
        if (xmlFile == null || tagName == null || nodeName == null) {
            return nodeValue;
        }

        // get node value(s)
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            NodeList list = doc.getElementsByTagName(tagName);
            Node node = list.item(0);
            // parent node not exist
            if (node == null) {
                return nodeValue;
            }
            NodeList nodes = node.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                // 结点名が一致
                if (nodes.item(i).getNodeName().equals(nodeName)) {
                    nodeValue.add(nodes.item(i).getFirstChild().getNodeValue());
                }
            }
        } catch (Exception e) {
            debugLog.error(e.getMessage());
        }

        return nodeValue;
    }

    /**
     * Document対象から指定されたエレメントの指定された属性を設定する
     * 
     * @param doc
     *            Document対象
     * @param tagName
     *            エレメント名(the first node of this name)
     * @param attrName
     *            エレメントの属性名
     * @param nodeValue
     *            エレメントの属性値
     * @return Document 取得したDocument対象
     */
    public static Document setAttributeValue(Document doc, String tagName, String attrName, String nodeValue) {
        // check input condition
        if (doc == null || tagName == null || attrName == null) {
            return doc;
        }
        // set an attribute value
        try {
            NodeList list = doc.getElementsByTagName(tagName);
            Node node = list.item(0);
            if (node == null) {
                return doc;
            }
            NodeList subnodes = node.getChildNodes();
            for (int i = 0; i < subnodes.getLength(); i++) {
                // 属性名が一致
                if (subnodes.item(i).getNodeName().equals(attrName)) {
                    Text text = doc.createTextNode(nodeValue);
                    subnodes.item(i).appendChild(text);
                    break;
                }
            }
        } catch (Exception e) {
            debugLog.error(e.getMessage());
        }

        return doc;
    }

    /**
     * Document対象から指定されたエレメントの指定された属性を設定する
     * 
     * @param doc
     *            Document対象
     * @param tagName
     *            エレメント名(the first node of this name)
     * @param map
     *            エレメントの属性名/属性値
     * @return Document 取得したDocument対象
     */
    public static Document setAttributeValues(Document doc, String tagName, HashMap map) {
        // check input condition
        if (doc == null || tagName == null || map == null) {
            return doc;
        }
        // set attribute values
        try {
            for (Map.Entry<String, String> entry : (Set<Map.Entry<String, String>>) map.entrySet()) {
                doc = setAttributeValue(doc, tagName, entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            debugLog.error(e.getMessage());
        }

        return doc;
    }

    /**
     * Document対象から指定されたエレメントの指定された结点をcloneする
     * 
     * @param doc
     *            Document対象
     * @param tagName
     *            エレメント名
     * @param map
     *            エレメントの属性名/属性値
     * @return Document 取得したDocument対象
     */
    public static Document cloneNode(Document doc, String tagName, HashMap map) {
        // check input condition
        if (doc == null || tagName == null || map == null) {
            return doc;
        }
        // clone a new node by given node
        try {
            NodeList list = doc.getElementsByTagName(tagName);
            Node node = list.item(0);
            if (node == null) {
                return doc;
            }
            Node parentNode = node.getParentNode();
            Node newNode = node.cloneNode(true);
            parentNode.appendChild(newNode);
            for (Map.Entry<String, String> entry : (Set<Map.Entry<String, String>>) map.entrySet()) {
                doc = setNodeValue(doc, tagName, entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            debugLog.error(e.getMessage());
        }

        return doc;
    }

    /**
     * １つの结点値を設定する
     * 
     * @param doc
     *            Document対象
     * @param tagName
     *            父结点の名称(the last node of this name)
     * @param nodeName
     *            结点の名称
     * @param nodeValue
     *            结点値
     * @return Document 取得したDocument対象
     */
    public static Document setNodeValue(Document doc, String tagName, String nodeName, String nodeValue) {
        // check input condition
        if (doc == null || tagName == null || nodeName == null) {
            return doc;
        }
        // set an node value
        NodeList list = doc.getElementsByTagName(tagName);
        Node node = list.item(list.getLength() - 1);
        NodeList subnodes = node.getChildNodes();
        Node valueNode = null;
        for (int i = 0; i < subnodes.getLength(); i++) {
            // 属性名が一致
            if (subnodes.item(i).getNodeName().equals(nodeName)) {
                valueNode = subnodes.item(i);
                Text text = doc.createTextNode(nodeValue);
                valueNode.appendChild(text);
                break;
            }
        }

        return doc;
    }

    /**
     * 多数の结点値を設定する
     * 
     * @param doc
     *            Document対象
     * @param tagName
     *            エレメント名(the last node of this name)
     * @param map
     *            エレメントの结点名/结点値
     * @return Document 取得したDocument対象
     */
    public static Document setNodeValues(Document doc, String tagName, HashMap map) {
        // check input condition
        if (doc == null || tagName == null || map == null) {
            return doc;
        }
        // set node values
        try {
            for (Map.Entry<String, String> entry : (Set<Map.Entry<String, String>>) map.entrySet()) {
                doc = setNodeValue(doc, tagName, entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            debugLog.error(e.getMessage());
        }

        return doc;
    }

    /**
     * 一つorg.w3c.dom.Document対象に変えて戻る。
     * 
     * @param xmlString
     *            一つのXML規範に合う文字列の表現。
     * @return a Document
     */
    public static Document parseXMLDocument(String xmlString) throws Exception {
        if (xmlString == null) {
            throw new IllegalArgumentException();
        }
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xmlString)));
        } catch (Exception e) {
            throw new Exception("FW070");
        }
    }

    /**
     * １つのファイル名を決めますに、このファイルを得てそして解析して１つのorg.になりますw3c.dom.document対象は戻ります
     * 
     * @param fileName
     *            ファイルのファイル名を解析することにじっとしています
     * @return a org.w3c.dom.document
     * @throws SystemException
     *             SystemException
     */
    public static Document loadXMLDocumentFromFile(String fileName) throws Exception {
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new File(fileName));
        } catch (Exception e) {
            throw new Exception("FW071");
        }
    }

    /**
     * １つのInputStreamを決めますに、このファイルを得てそして解析して１つのorg.になりますw3c.dom.document対象は戻ります
     * 
     * @param inputStream
     *            待解析的InputStream
     * @return a org.w3c.dom.document
     * @throws SystemException
     *             SystemException
     */
    public static Document loadXMLDocumentFromInputStream(InputStream inputStream) throws Exception {
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(inputStream);
        } catch (Exception e) {
            throw new Exception( "FW072");
        }
    }

    /**
     * Nodeの指定された属性を取得する
     * 
     * @param node
     *            指定的node
     * @param attrName
     *            指定的属性名
     * @return 指定属性的值
     */
    public static String getAttributeValue(Node node, String attrName) {
        // 获取所有属性的集合
        NamedNodeMap attrs = node.getAttributes();
        return attrs.getNamedItem(attrName).getNodeValue();
    }

    /**
     * 読み取ってnodeの下でTextノードの内容を指定します
     * 
     * @param node
     *            指定的node
     * @return text的值
     */
    public static String getTextValue(Node node) {
        String outputText = "";
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node textNode = nodeList.item(i);
            String text = textNode.getNodeValue();
            if (!"".equals(text.trim())) {
                outputText = text;
                break;
            }
        }
        return outputText.trim();
    }
}