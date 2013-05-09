package com.founder.sipbus.syweb.cck.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.StringUtils;

/**
 * cck工具类
 * @author zjl
 *
 */
public class SyCckUtil {
	/**勾√*/
	public static final String TRUE = "\u221A";//勾√
	/**叉×*/
	public static final String FALSE = "\u00d7";//叉×
	public static final String TYPE_TEXT = "1";
	public static final String TYPE_NUMBER = "2";
	public static final String TYPE_DATE = "3";
	public static final String TYPE_CODE = "4";
	public static final String TYPE_REFERENCE = "5";
	public static final String TYPE_DIGITS = "6";
	public static final String TYPE_PK = "7";
	public static final String TYPE_SCT_GUID = "8";
	public static final String TYPE_CREATE_BY = "9";
	public static final String TYPE_CREATE_DT = "10";
	public static final String TYPE_UPDATE_BY = "11";
	public static final String TYPE_UPDATE_DT = "12";
	public static final String TYPE_DEL_FLAG = "13";
	public static final String TYPE_PARENT_ID = "14";
	public static final String TYPE_GUID = "15";
	public static final String TYPE_TEXTAREA = "16";
	public static final String TYPE_MASTERID = "17";
	public static final String TYPE_RICHTEXT = "18";
//	public static final String TYPE_NAME = "18";
	public static final int TYPE_INT_TEXT = 1;
	public static final int TYPE_INT_NUMBER = 2;
	public static final int TYPE_INT_DATE = 3;
	public static final int TYPE_INT_CODE = 4;
	public static final int TYPE_INT_REFERENCE=5;
	public static final int TYPE_INT_DIGITS = 6;
	public static final int TYPE_INT_PK = 7;
	public static final int TYPE_INT_SCT_GUID = 8;
	public static final int TYPE_INT_CREATE_BY = 9;
	public static final int TYPE_INT_CREATE_DT = 10;
	public static final int TYPE_INT_UPDATE_BY = 11;
	public static final int TYPE_INT_UPDATE_DT = 12;
	public static final int TYPE_INT_DEL_FLAG = 13;
	public static final int TYPE_INT_PARENT_ID = 14;
	public static final int TYPE_INT_GUID = 15;
	public static final int TYPE_INT_TEXTAREA = 16;
	public static final int TYPE_INT_MASTERID = 17;
	public static final int TYPE_INT_RICHTEXT = 18;
//	public static final int TYPE_INT_NAME = 18;
	public static final String domain_1 = "syWeb";
 
	public static HashMap<String, String> fieldTypeMap = new HashMap<String, String>();

	static {
		fieldTypeMap.put(TYPE_TEXT, "文本");
		fieldTypeMap.put(TYPE_NUMBER, "小数");
		fieldTypeMap.put(TYPE_DATE, "日期");
		fieldTypeMap.put(TYPE_CODE, "码表");
		fieldTypeMap.put(TYPE_REFERENCE, "引用");
		fieldTypeMap.put(TYPE_DIGITS, "整数");
		fieldTypeMap.put(TYPE_PK, "主键");
		fieldTypeMap.put(TYPE_SCT_GUID, "CCKTYPEID");
		fieldTypeMap.put(TYPE_CREATE_BY, "创建人");
		fieldTypeMap.put(TYPE_CREATE_DT, "创建日期");
		fieldTypeMap.put(TYPE_UPDATE_BY, "更新人");
		fieldTypeMap.put(TYPE_UPDATE_DT, "更新日期");
		fieldTypeMap.put(TYPE_DEL_FLAG, "删除标志");
		fieldTypeMap.put(TYPE_PARENT_ID, "上层ID");
		fieldTypeMap.put(TYPE_GUID, "GUID");
		fieldTypeMap.put(TYPE_TEXTAREA, "大文本");
		fieldTypeMap.put(TYPE_MASTERID, "MASTERID");
		fieldTypeMap.put(TYPE_RICHTEXT, "富文本");
//		fieldTypeMap.put(TYPE_NAME, "Tree显示名");
	}

	/**{ "fieldIsrequired",
		"fieldIslistdisplay", "fieldIsformdisplay","fieldIssearchfield","fieldIsunique" }*/
	public static String[] isFields = { "fieldIsrequired",
		"fieldIslistdisplay", "fieldIsformdisplay","fieldIssearchfield","fieldIsunique" };
	/**99999999l*/
	private static long MAX= 99999999l;
	public static  AtomicLong atomicLong= new AtomicLong(0);
	/**
	 *getString 方法
	 *	若为1则返回  TRUE 其他均为 FALSE
	 *  @param b
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2012-12-21 下午3:50:03 
	 */
	public static String getString(String b) {
 
		if (StringUtils.equals(b, "1")) {
			return TRUE;
		} else {
			return FALSE;
		}
	}
	/**
	 *changeCheckBox 方法
	 *	将checkbox 的选中 未选中 转换为 1 ，0
	 *  @param map
	 *  
	 * @author zjl 
	 * @date : 2012-12-21 下午3:49:15 
	 */
	public static void changeCheckBox(Map<String, String> map) {

		for (String field : isFields) {
			if (StringUtils.isNotBlank((String) map.get(field))) {
				map.put(field, "1");
			} else {
				map.put(field, "0");
			}

		}
	}
	public static String generateID(String tableName,Serializable seqID) {
		String id8 = seqID.toString();
		while (id8.length() < 8) {
			id8 = "0" + id8;
		}
		if (id8.length() > 8) {
			id8 = id8.substring(id8.length() - 8, id8.length());
		}

		String id = getTableStr(tableName) + getDateStr() + id8;
		while (id.length() < 36)
			id = "0" + id;
		return id;
	}
	/**
	 *generateID 方法
	 *生成主键 36位 表名+ yyMMddHHmmss + 8位序列
	 *  @param tableName
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2012-12-21 下午3:46:45 
	 */
	public static String generateID(String tableName ) {
		return generateID(  tableName ,getID() );
	}
	/**
	 *getID 方法
	 *返回序列 支持多线程
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2012-12-21 下午3:47:59 
	 */
	private static long getID() {
	    while (true) {
            long current = atomicLong.get();
            if (current<MAX) {//是否小于最大值
                long next = current + 1;
				if ( atomicLong.compareAndSet(current, next)) {//更新成功 返回新值
					return next;
				}
			}else {
				if ( atomicLong.compareAndSet(current, 0l)) {
            		return 0l;
				}
			}
     
        }
	    
		
		 
	}
	private static String getTableStr(String table_name) {
		table_name = table_name.replaceAll("_", "");
		if (table_name.length() > 16) {
			table_name = table_name.substring(0, 16);
		}
		return table_name;
	}

	/**
	 *getDateStr 方法
	 * 获取yyMMddHHmmss格式的时间String
	 *  @return
	 *  
	 * @author zjl 
	 * @date : 2012-12-21 下午3:47:40 
	 */
	private static String getDateStr() {
		return new SimpleDateFormat("yyMMddHHmmss").format(new Date());
	}
}
