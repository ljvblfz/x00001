package com.broadsoft.xmcommon.androiddao;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;


/**
 * generateID 方法 生成主键 36位 表名+ yyMMddHHmmss + 8位序列
 * 不需要数据库sequence
 * @author lu.zhen
 *
 */
public class GuidGeneration {
	private static long MAX = 99999999l;
	private static AtomicLong atomicLong = new AtomicLong(0);


	/**
	 * generateID 方法 生成主键 36位 表名+ yyMMddHHmmss + 8位序列
	 * 
	 * @param tableName
	 * @return
	 * 
	 * @author zjl
	 * @date : 2012-12-21 下午3:46:45
	 */
	public static String generateGuid(String tableName) {
		return generateID(tableName, getID());
	}
	
	
	private static String generateID(String tableName, Serializable seqID) {
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
	 * getID 方法 返回序列 支持多线程
	 * 
	 * @return
	 * 
	 * @author zjl
	 * @date : 2012-12-21 下午3:47:59
	 */
	private static long getID() {
		while (true) {
			long current = atomicLong.get();
			if (current < MAX) {// 是否小于最大值
				long next = current + 1;
				if (atomicLong.compareAndSet(current, next)) {// 更新成功 返回新值
					return next;
				}
			} else {
				if (atomicLong.compareAndSet(current, 0l)) {
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
	 * getDateStr 方法 获取yyMMddHHmmss格式的时间String
	 * 
	 * @return
	 * 
	 * @author zjl
	 * @date : 2012-12-21 下午3:47:40
	 */
	private static String getDateStr() {
		return new SimpleDateFormat("yyMMddHHmmss").format(new Date());
	}
}
