/**
 * Copyright c FOUNDER CORPORATION 2006 All Rights Reserved.
 * NetSettingRepository.java
 */
package com.founder.sipbus.common.framework.repository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.founder.sipbus.common.util.DomUtil;

/**
 * [クラス名]<br>
 * NetSettingRepositoryクラス。<br>
 * <br>
 * [機能概要]<br>
 * Net共通设定方面的情报<br>
 * <br>
 * [変更履歴]<br>
 * 2007/04/23 新規作成 weifeng<br>
 * 
 * @author weifeng
 * @version 1.00
 */
public final class NetSettingRepository {

    /**
     * ログ出力用のインスタンス生成。
     */
    private static Log debugLog = LogFactory.getLog(NetSettingRepository.class);

    /**
     * 是否在Message后显示MessageID
     */
    public static final String SHOW_MESSAGEID = "show.messageId";

    /**
     * Memcached服务器的配置
     */
    public static final String MEMCACHED_SERVER_CONFIG = "memcached.server.config";

    /**
     * WEB服务器的Domain
     */
    public static final String WEB_SERVER_DOMAIN = "web.server.domain";

    /**
     * 登录时允许错误输入的次数
     */
    public static final String LOGIN_ERROR_TIMES = "login.error.times";

    /**
     * DUMMY_USER
     */
    public static final String DUMMY_USER = "dummy.user";

    /**
     * XML文件名
     */
    private static final String XML_FILE_NAME = "NetSetting.xml";

    /**
     * ロック制御用オブジェクト
     */
    private static RepositoryLock lockController = null;

    /**
     * Net共通设定保存的各种情报(1)
     */
    private static Map<String, Object> infos1 = null;

    /**
     * Net共通设定保存的各种情报(2)
     */
    private static Map<String, Object> infos2 = null;

    /**
     * 用于确定正在使用哪个Mapping
     */
    private static volatile boolean standbyFlag = true;

    /**
     * 初期化します。
     */
    static {
        lockController = new RepositoryLock();
    }

    /**
     * Constructor
     */
    private NetSettingRepository() {

    }

    /**
     * Net共通设定方面的情报初始化
     * 
     * @throws Exception
     *             系统异常
     */
    public static void init() throws Exception {
        try {
            lockController.beginSet();

            boolean tempFlag = standbyFlag;

            if (tempFlag) {
                infos1 = getNetSetting();
                if (infos1 != null) {
                    debugLog.debug("NetSettingRepository initialized!(1)");
                }
            } else {
                infos2 = getNetSetting();
                if (infos2 != null) {
                    debugLog.debug("NetSettingRepository initialized!(2)");
                }
            }

            standbyFlag = !tempFlag;

        } finally {
            lockController.endSet();
        }
    }

    /**
     * keyは存在するかどうか
     * 
     * @param key
     *            設定値のkey
     * @return boolean key exist or not
     */
    public static boolean hasProperty(String key) {
        Map<String, Object> infos = standbyFlag ? infos2 : infos1;
        boolean ret = infos.containsKey(key);
        return ret;

    }

    /**
     * 获取指定key的String型情报
     * 
     * @param key
     *            情报的key
     * @return 情报的值
     */
    public static String getString(String key) {
        Map<String, Object> infos = standbyFlag ? infos2 : infos1;
        String value = (String) infos.get(key);
        return value;

    }

    /**
     * 获取指定key的List型情报
     * 
     * @param key
     *            情报的key
     * @return 情报的值
     */
    @SuppressWarnings("unchecked")
    public static List<String> getList(String key) {
        Map<String, Object> infos = standbyFlag ? infos2 : infos1;
        List<String> value = (List<String>) infos.get(key);
        return value;

    }

    /**
     * 获取指定key的Map型情报
     * 
     * @param key
     *            情报的key
     * @return 情报的值
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> getMap(String key) {
        Map<String, Object> infos = standbyFlag ? infos2 : infos1;
        Map<String, String> value = (Map<String, String>) infos.get(key);
        return value;

    }

    /**
     * 解析NetSetting关联的xml文件
     * 
     * @return 解析后生成的Map对象
     */
    private static Map<String, Object> getNetSetting() throws Exception {
        // xml文件在文件系统中的路径
        // String xmlFilePath = XmlConfigPath.getXmlConfigPath() + XML_FILE_NAME;
        //
        // File xmlFile = new File(java.net.URLDecoder.decode(xmlFilePath));
        // if (!xmlFile.exists()) {
        // return null;
        // }

        InputStream xmlInputStream = NetSettingRepository.class.getResourceAsStream("/"+XML_FILE_NAME);

        Map<String, Object> outputMap = new HashMap<String, Object>();

        // xml配置文件对应的Document对象
        Document propDoc = DomUtil.loadXMLDocumentFromInputStream(xmlInputStream);
        propDoc.normalize();

        // 根结点下一级的所有节点
        NodeList nodeList = propDoc.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node subNode = nodeList.item(i);

            String key = null;
            Object value = null;

            // 如果节点的名字是"key",对应一个String型的对象
            if (subNode.getNodeName().equals("key")) {
                key = DomUtil.getAttributeValue(subNode, "name");

                NodeList strNodeList = subNode.getChildNodes();
                for (int m = 0; m < strNodeList.getLength(); m++) {
                    if (strNodeList.item(m).getNodeName().equals("value")) {
                        value = DomUtil.getTextValue(strNodeList.item(m));
                    }
                }
            }
            // 如果节点的名字是"listKey",对应一个List<String, String>型的对象
            else if (subNode.getNodeName().equals("listKey")) {
                key = DomUtil.getAttributeValue(subNode, "name");
                List<String> listValue = new ArrayList<String>();

                NodeList listNodeList = subNode.getChildNodes();
                for (int m = 0; m < listNodeList.getLength(); m++) {
                    if (listNodeList.item(m).getNodeName().equals("value")) {
                        listValue.add(DomUtil.getTextValue(listNodeList.item(m)));
                    }
                }
                value = listValue;
            }
            // 如果节点的名字是"mapKey",对应一个Map<String, String>型的变量
            else if (subNode.getNodeName().equals("mapKey")) {
                key = DomUtil.getAttributeValue(subNode, "name");
                Map<String, String> mapValue = new HashMap<String, String>();

                NodeList mapNodeList = subNode.getChildNodes();
                for (int m = 0; m < mapNodeList.getLength(); m++) {
                    if (mapNodeList.item(m).getNodeName().equals("value")) {
                        String inKey = DomUtil.getAttributeValue(mapNodeList.item(m), "key");
                        String inValue = DomUtil.getTextValue(mapNodeList.item(m));
                        mapValue.put(inKey, inValue);
                    }
                }
                value = mapValue;

            }
            // 将节点对应的对象保存在properties对象里
            if (key != null && value != null) {
                outputMap.put(key, value);
            }
        }

        return outputMap;
    }

}
