/**
 * Copyright c FOUNDER CORPORATION 2006 All Rights Reserved.
 * NumberUtil.java
 */
package com.founder.sipbus.common.util;

import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




/**
 * [クラス名]<br>
 * 数値処理Utilityクラス<br>
 * <br>
 * [機能概要]<br>
 * 数値変換、チェック関数群<br> 
 * <br>
 * [変更履歴]<br>
 * 2006/2/24 ver1.00 新規作成 zhanjc<br>
 * 
 * @author zhanjc
 * @version 1.00  
 */
public class NumUtil {

    /**
     * ログオブジェクト
     */
    private static Log debugLog = LogFactory.getLog(NumUtil.class);

    /**
     * 妥当性数字を判定し（０-９）、true又はfalseを返す
     * 
     * @param number
     *            変換する文字列
     * @return boolean 有効な時、trueを返し、他のはfalseを返す。 2003/11/06 新規作成 zhanjc
     */
    public static boolean checkNumberValid(String number) {
        number = StringUtil.nvl(number);
        if ("".equals(number)) {
            return false;
        }
        boolean bDot = false; // 単一点の判断フラグ
        int nChar;
        for (int i = 0; i < number.length(); i++) {
            nChar = number.charAt(i);
            if (nChar == '-' && i == 0) {
                continue;
            } else if (nChar == '-' && i != 0) {
                return false;
            }
            if (nChar > '9') {
                return false;
            }
            if ((nChar < '0') && (nChar != ',') && (nChar != '.')) {
                return false;
            }
            // 一つ点のみがある、trueを返し、他のはfalseを返す
            if (nChar == '.') {
                // 単一点がある、trueを返し、他のはfalseを返す
                if (!bDot) {
                    bDot = true;
                    continue;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 数字のチェック
     * 
     * @param s
     *            数字の文字列
     * @return boolean 2003/11/06 新規作成 zhanjc
     */
    public static boolean checkIntNumberValid(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        for (int i = 0, j = s.length(); i < j; i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * StringタイプをIntタイプに変換する
     * 
     * @param str
     *            文字列
     * @return int intの文字列 2003/11/07 新規作成 zhanjc
     */
    public static int convertToInt(String str) {
        int result = 0;
        try {
            str = str.trim();
            result = Integer.parseInt(str);
        } catch (Exception ex) {
            debugLog.error(ex);
        }
        return result;
    }

    /**
     * StringタイプをLongタイプに変換する
     * 
     * @param str
     *            文字列
     * @return Long Longの文字列 2003/11/07 新規作成 zhanjc
     */
    public static long convertToLong(String str) {
        long result = 0;
        try {
            str = str.trim();
            result = Long.parseLong(str);
        } catch (Exception ex) {
            debugLog.error(ex);
        }
        return result;
    }

    /**
     * StringタイプをFloatタイプに変換する
     * 
     * @param str
     *            文字列
     * @return Float Floatの文字列 2003/11/07 新規作成 zhanjc
     */
    public static float convertToFloat(String str) {
        float result = 0;
        try {
            str = str.trim();
            result = Float.parseFloat(str);
        } catch (Exception ex) {
            debugLog.error(ex);
        }
        return result;
    }

    /**
     * StringタイプをIntタイプに変換する
     * 
     * @param value
     *            文字列
     * @param flag
     *            変換フラグ： true : valueはNULL又は空文字列の場合、Exceptionを投げる false : valueはNULL又は空文字列の場合、0を返す
     * @return int 変換した文字列
     * @author JiangJusheng
     */
    public static int parseInt(String value, boolean flag) throws Exception {
        if (flag) {
            if (value == null || "".equals(value.trim())) {
                throw new Exception("FW077");
            }
        } else {
            if (value == null || "".equals(value.trim())) {
                return 0;
            }
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException ex) {
            if (flag) {
                throw new Exception("FW077");
            } else {
                return 0;
            }
        }
    }

    /**
     * String類型からlong類型に変換する
     * 
     * @param value
     *            文字列
     * @param flag
     *            変換のフラグ： true : valueはNULL又は空文字列の場合、WarningException例外を呼び出す false : valueはNULL又は空文字列の場合、0を返す
     * @return long 変換後文字列
     * @author JiangJusheng
     */
    public static long parseLong(String value, boolean flag) throws Exception {
        if (flag) {
            if (value == null || "".equals(value.trim())) {
                throw new Exception("FW077");
            }
        } else {
            if (value == null || "".equals(value.trim())) {
                return 0;
            }
        }
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException ex) {
            if (flag) {
                throw new Exception("FW077");
            } else {
                return 0;
            }
        }
    }

    public static Integer[] strArr2IntArr(String[] strArr) {
        Integer[] intArr = new Integer[strArr.length];

        for (int i = 0; i < strArr.length; i++) {
            intArr[i] = convertToInt(strArr[i]);
        }

        return intArr;
    }

    /**
     * l为null时返回0L
     * 
     * @param l
     *            长整型对象
     * @return 长整型值
     */
    public static long NVL(Long l) {
        if (l == null) {
            return 0L;
        }

        return l.longValue();
    }

    /**
     * i为null时返回0
     * 
     * @param i
     *            整形对象
     * @return 整形值
     */
    public static int NVL(Integer i) {
        if (i == null) {
            return 0;
        }

        return i.intValue();
    }
    
    public static String formatCurrency(Double value){
    	String result = "0.00";
    	if (value != null)
    		result = new DecimalFormat(",##0.00").format(value);
    	return result;
    }
    
    public static String formatInteger(Double value){
    	String result = "0";
    	if (value != null)
    		result = new DecimalFormat(",##0").format(value);
    	return result;
    }
}
