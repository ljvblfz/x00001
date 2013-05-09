/**
 * Copyright c FOUNDER CORPORATION 2006 All Rights Reserved.
 * 
 * StringUtil.java
 */
package com.founder.sipbus.common.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




/**
 * [クラス名]<br>
 * String処理Utilityクラス<br>
 * [機能概要]<br>
 * String変換処理Utility関数群<br>
 * [変更履歴]<br>
 * 2003/11/06 ver1.00 新規作成 zhanjc<br>
 * 
 * @author zhanjc
 * @version 1.00
 */
public class StringUtil extends StringUtils{

    /** ログオブジェクト */
    private static Log debugLog = LogFactory.getLog(StringUtil.class);

    /** TEL */
    private static final String TEL = "tel";

    /** ZIP */
    private static final String ZIP = "zip";

    /** 格式化の文字列定数 */
    private static final String FORMAT_TEXT = "#,###.#################################";

    /** エンコードの形式 */
    private static final String ENCODE = "GBK";

    /**
     * 構造関数
     */
    private StringUtil() {}

    /**
     * コーマ区切りで、文字列から文字列を抜き出し、配列にして返す
     * 
     * @param str
     *            文字列
     * @return String[] 変換後文字列アレー
     */
    public static String[] str2Array(String str) {
        return str2Array(str, ",");
    }

    /**
     * 文字列から文字列を抜き出し、配列にして返す
     * 
     * @param str
     *            文字列
     * @param sep
     *            区切り文字(群)
     * @return String[] 変換後文字列 2003/11/06 新規作成 zhanjc
     */
    public static String[] str2Array(String str, String sep) {
        StringTokenizer token = null;
        String[] array = null;

        // check
        if (str == null || sep == null) {
            return null;
        }

        // get string array
        token = new StringTokenizer(str, sep);
        array = new String[token.countTokens()];
        for (int i = 0; token.hasMoreTokens(); i++) {
            array[i] = token.nextToken();
        }

        return array;
    }

    /**
     * 文字配列から文字列を抽出して組合し、文字列にして返す
     * 
     * @param str
     *            文字列
     * @return String 変換後文字列 2003/11/07 新規作成 zhanjc
     */
    public static String array2String(String[] str) {
        int num = 0;
        StringBuffer result = new StringBuffer("");

        // check
        if (str == null) {
            return "";
        }

        // 文字列を組合
        num = str.length;
        for (int i = 0; i < num; i++) {
            if (str[i] != null) {
                result.append(str[i]);
            }
        }

        return result.toString();
    }


    /**
     * 文字列が全角のみのチェック
     * 
     * @param txt
     *            チェックしたいの文字列
     * @return boolean 空文字列の場合は、trueを返し、<br>
     *         文字列はShift_JISのエンコードに変換失敗の場合、falseを返す<br>
     */
    public static final boolean isFull(String txt) {
        byte[] bytes = null;
        try {
            bytes = txt.getBytes("MS932");
        } catch (UnsupportedEncodingException uee) {
            // should no happen
            throw new IllegalStateException(uee);
        }
        int beams = txt.length() * 2;

        for (int i = 0; i < txt.length(); i++) {
            if ('\n' == txt.charAt(i)) {
                beams = beams - 2;
            }
        }

        return beams == bytes.length;
    }

    /**
     * 文字列が半角のみかをチェックする
     * 
     * @param txt
     *            チェック文字列
     * @return boolean 空文字列の場合は、trueを返し、<br>
     *         文字列はShift_JISのエンコードに変換失敗の場合、falseを返す<br>
     */
    public static final boolean isHalf(String txt) {
        if (txt == null) {
            return true;
        }
        try {
            txt = txt.replaceAll("\\?", "");

            byte[] b = txt.getBytes(ENCODE);
            for (int i = 0; i < b.length; i++) {
                if ((b[i] & 0x80) == 0x80) {
                    // 判定：b[i]は0xA0-0xDFの範囲に存在するか。この範囲は半角カナ文字のみ
                    if (!(((byte) 0xA0) <= b[i] && b[i] <= ((byte) 0xDf))) {
                        // 全角文字の１バイト目
                        return false;
                    }
                } else if (b[i] == 0x3F) {
                    return false;
                }
            }
            return true;
        } catch (java.io.UnsupportedEncodingException e) {
            debugLog.debug(e.getMessage());
            return false;
        }
    }


    /**
     * 文字列が半角カナのみかをチェックする
     * 
     * @param txt
     *            チェック文字列
     * @return boolean 空文字列の場合は、trueを返し、<br>
     *         文字列はShift_JISのエンコードに変換失敗の場合、falseを返す<br>
     */
    public static final boolean isKana(String txt) {
        if (txt == null) {
            return true;
        }
        try {
            byte[] b = txt.getBytes(ENCODE);
            for (int i = 0; i < b.length; i++) {
                // 判定：b[i]は0xA0-0xDFの範囲に存在であるか。この範囲は半角カナ文字のである
                if (!(((byte) 0xA0) <= b[i] && b[i] <= ((byte) 0xDf) || b[i] == 0x20)) {
                    return false;
                }
            }
            return true;
        } catch (java.io.UnsupportedEncodingException e) {
            debugLog.debug(e.getMessage());
            return false;
        }
    }

    /**
     * 二つStringオブジェクトの内容を比べる
     * 
     * @param s1
     *            文字列１
     * @param s2
     *            文字列２
     * @return 二つの文字列の内容は相同の場合、trueを返し、他のはfalseを返す 2003/11/07 新規作成 zhanjc
     */
    public static boolean isEqual(String s1, String s2) {
        if (s1 == null) {
            s1 = "";
        }
        if (s2 == null) {
            s2 = "";
        }

        return (s1.equals(s2));
    }

    /**
     * "0"と"00"と"000"を空白文字列に変換する
     * 
     * @param s
     *            変換する文字列
     * @return String 変換した文字列を返す 2003/11/07 新規作成 zhanjc
     */
    public static String zeroToSpace(String s) {
        boolean allZero = true;

        if (s == null) {
            return "";
        }
        s = s.trim();
        if (s.equals("")) {
            return "";
        }

        // check if the string consist of "0"
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                continue;
            } else {
                allZero = false;
                break;
            }
        }

        // result deal
        if (allZero == true) {
            return "";
        } else {
            return s;
        }
    }

    /**
     * 前ゼロを付加した結果を返する
     * 
     * @param val
     *            ゼロフォーマット値
     * @param length
     *            変換したintからゼロフォーマット用文字数を生成する
     * @return String 前ゼロを付加した文字列
     */
    public static String addPreZero(Object val, int length) {
        if (val == null) {
            return "";
        }
        String result = val.toString();
        int strLen = result.length();
        if (strLen < length) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length - strLen; i++) {
                sb.append('0');
            }
            sb.append(val);
            result = sb.toString();
        }

        return result;
    }

    /**
     * 如果val的长度小于length,右侧填空格，如果大于，则截断
     * 
     * @param val
     * @param length
     * @return
     */
    public static String spacePadding(Object val, int length) {
        String output = "";
        int valLength = 0;

        if (val != null) {
            output = val.toString();
            try {
                valLength = output.getBytes("GBK").length;
            } catch (UnsupportedEncodingException e) {
                // should not happen
            }
        }

        if (valLength < length) {
            for (int i = 0; i < length - valLength; i++) {
                output += " ";
            }
        } else {
            while (valLength > length) {
                output = output.substring(0, output.length() - 1);
                try {
                    valLength = output.getBytes("UTF-8").length;
                } catch (UnsupportedEncodingException e) {
                    // should not happen
                }
            }

            if (valLength < length) {
                for (int i = 0; i < length - valLength; i++) {
                    output += " ";
                }
            }
        }
        try {
            debugLog.debug("output.length = " + output.length() + " valLength = " + output.getBytes("UTF-8").length);
        } catch (UnsupportedEncodingException e) {
            // should not happen
        }
        return output;
    }



    /**
     * 数字を9999999.99 ----> 9,999,999.99円のようなフォーマットに変換する
     * 
     * @param number
     *            数字の文字列
     * @return String 変換した文字列 2003/11/06 新規作成 zhanjc
     * @throws Exception
     */
    public static String formatCurrency(String number) throws Exception {
        // 円
        return formatCurrency(number, 3, false, true, false, false);
    }

    /**
     * 数字を9999999.99 ----> 9,999,999.99円のようなフォーマットに変換する
     * 
     * @param number
     *            数字の文字列
     * @param zeroToNull
     *            ゼロ変換の制御フラグ：trueの場合、０は空文字列に変換する。falseの場合、変換しない
     * @return String 変換した文字列 2003/11/06 新規作成 zhanjc
     * @throws Exception
     */
    public static String formatCurrency(String number, boolean zeroToNull) throws Exception {
        // 円
        return formatCurrency(number, 3, false, true, false, zeroToNull);
    }

    /**
     * 数字を9999999.99 ----> 9,999,999.99千円、あるいは9999999.99 ----> 9,999,999.99万円 のようなフォーマットに変換し、結果の文字列を返す
     * 
     * @param number
     *            文字列
     * @param suffix
     *            接尾辞のフラグ：1は千円、その他は万円
     * @param needProcess
     *            変換処理制御のフラグ：true 1.千円 9999999.99 ----> 9,999.99千円 2.万円 9999999.99 ----> 999.99万円 その他 円 9999999.99 ----> 9,999,999.99(円) false 1.千円 9999999.99 ----> 9,999,999.99千円 2.万円 9999999.99
     *            ----> 9,999,999.99万円 その他 円 9999999.99 ----> 9,999,999.99(円)
     * @return String 変換した文字列 2003/11/06 新規作成 zhanjc
     * @throws Exception
     */
    public static String formatCurrency(String number, int suffix, boolean needProcess) throws Exception {
        // 千円,万円
        return formatCurrency(number, suffix, needProcess, true, false);
    }

    /**
     * 数字を9999999.99 ----> 9,999,999.99(千円)、あるいは9999999.99 ----> 9,999,999.99(万円) のようなフォーマットに変換し、結果の文字列を返す
     * 
     * @param number
     *            数字の文字列
     * @param suffix
     *            接尾辞のフラグ：1は千円、その他は万円
     * @param needProcess
     *            変換処理制御のフラグ： true 1.千円 9999999.99 ----> 9,999.99(千円) 2.万円 9999999.99 ----> 999.99(万円) その他 円 9999999.99 ----> 9,999,999.99(円) false 1.千円 9999999.99 ----> 9,999,999.99(千円) 2.万円
     *            9999999.99 ----> 9,999,999.99(万円) その他 円 9999999.99 ----> 9,999,999.99(円)
     * @param hasSuffix
     *            接尾辞の制御フラグ：trueは有り、falseはない
     * @param formatToLong
     *            格式化の制御フラグ： true 9999999.99 ----> 10,000,000 false 9999999.99 ----> 9,999,999.99
     * @return String 変換した文字列 2003/11/06 新規作成 zhanjc
     * @throws Exception
     */
    public static String formatCurrency(String number, int suffix, boolean needProcess, boolean hasSuffix,
            boolean formatToLong) throws Exception {
        return formatCurrency(number, suffix, needProcess, hasSuffix, formatToLong, false);
    }

    /**
     * 数字を9999999.99 ----> 9,999,999.99(千円)、あるいは9999999.99 ----> 9,999,999.99(万円) のようなフォーマットに変換し、結果の文字列を返す
     * 
     * @param number
     *            変換する文字列 0 --> "" || 0
     * @param suffix
     *            接尾辞のフラグ：1は千円、その他は万円
     * @param needProcess
     *            処理の制御フラグ： true 1.千円 9999999.99 ----> 9,999.99(千円) 2.万円 9999999.99 ----> 999.99(万円) その他 円 9999999.99 ----> 9999999.99(円) false 1.千円 9999999.99 ----> 9,999,999.99(千円) 2.万円 9999999.99
     *            ----> 9,999,999.99(万円) その他 円 9999999.99 ----> 9999999.99(円)
     * @param hasSuffix
     *            接尾辞の制御フラグ：trueは有り、falseは無し
     * @param formatToLong
     *            格式化の制御フラグ： true 9999999.99 ----> 10,000,000 false 9999999.99 ----> 9,999,999.99
     * @param zeroToNull
     *            ゼロ変換の制御フラグ：trueの場合、０は空文字列に変換する。 falseの場合、変換しない
     * @return String 変換後の文字列 2003/11/06 新規作成 zhanjc
     * @throws Exception
     */
    public static String formatCurrency(String number, int suffix, boolean needProcess, boolean hasSuffix,
            boolean formatToLong, boolean zeroToNull) throws Exception {
        // ヌル又は空文字列の場合、""を返す
        if (number == null || number.trim().equals("")) {
            return "";
        }
        if (number.trim().equals("0") || number.trim().equals("-0")) {
            if (zeroToNull == true) {
                return "";
            }
        }
        // 数字値の検査関数を呼び出す
        if (NumUtil.checkNumberValid(number) == false) {
            return number.trim();
        }
        String currencySuffix = "";
        String currency = "";
        int times;
        // suffixによって、"千円"又は"万円"を追加する
        if (suffix == 1) {
            if (hasSuffix == true) {
                currencySuffix = "千円";
            }
            times = 1000;
        } else if (suffix == 2) {
            if (hasSuffix == true) {
                currencySuffix = "万円";
            }
            times = 10000;
        } else {
            if (hasSuffix == true) {
                currencySuffix = "円";
            }
            times = 1;
        }
        // formatToLongのように
        double num;
        NumberFormat defForm = new DecimalFormat(FORMAT_TEXT);
        if (needProcess == true) {
            try {
                // numberをtimesで割って、計算結果を返す
                num = devideTimes(number, times);// throw
                // NumberFormatException
                if (formatToLong == true) {
                    num = Math.round(num);
                }
                currency = defForm.format(num);
            } catch (NumberFormatException numEx) {
                try {
                    // 数字を通貨のフォーマットに変換する
                    Number numNumber = NumberFormat.getNumberInstance().parse(number);
                    num = devideTimes(String.valueOf(numNumber), times);
                    if (formatToLong == true) {
                        num = Math.round(num);
                    }
                    currency = defForm.format(num);
                } catch (ParseException pEx) {
                    throw new Exception( "FW095");
                }
            }
        } else {
            currency = formatNumber(number, formatToLong);
        }

        return convertToValid(currency) + currencySuffix;
    }

    /**
     * 数字文字列を9999999.99 ----> 9,999,999.99のようなフォーマットに変換する
     * 
     * @param number
     *            数字の文字列
     * @return String 変換後の文字列 2003/11/06 新規作成 zhanjc
     * @throws Exception
     */
    public static String formatNumber(String number) throws Exception {
        // 整数処理のフラグはfalseに固定し、関数を呼び出す
        return formatNumber(number, false);
    }

    /**
     * 数字を9999999.99 ----> 9,999,999のようなフォーマットに変換する
     * 
     * @param number
     *            数字の文字列
     * @param formatToLong
     *            書式の制御フラグ
     * @return String 変換した文字列 2003/11/06 新規作成 zhanjc
     * @throws Exception
     */
    public static String formatNumber(String number, boolean formatToLong) throws Exception {
        return formatNumber(number, formatToLong, false);
    }

    /**
     * 数字文字列を9999999.99 ----> 9,999,999.99のようなフォーマットに変換する
     * 
     * @param number
     *            数字文字列
     * @param formatToLong
     *            小数を整数に変換する
     * @param nullToZero
     *            空白文字列がゼロに変換処理のフラグ：trueの場合、変換する、falseの場合、変換しない
     * @return String 変換した文字列 2003/11/07 新規作成 zhanjc
     * @throws Exception
     */
    public static String formatNumber(String number, boolean formatToLong, boolean nullToZero) throws Exception {
        // ゼロが空白文字列に変換処理のフラグはfalseに固定し、関数を呼び出す
        return formatNumber(number, formatToLong, nullToZero, false);
    }

    /**
     * 数字を9999999.99 ----> 9,999,999.99のようなフォーマットに変換する
     * 
     * @param number
     *            数字の文字列
     * @param formatToLong
     *            小数を整数に変換する
     * @param nullToZero
     *            空白文字列がゼロに変換処理のフラグ：trueの場合、変換する、falseの場合、変換しない
     * @param zeroToNull
     *            ゼロが空白文字列に変換処理のフラグ：trueの場合、変換する、falseの場合、変換しない
     * @return String 変換した文字列 2003/11/07 新規作成 zhanjc
     * @throws Exception
     */
    public static String formatNumber(String number, boolean formatToLong, boolean nullToZero, boolean zeroToNull)
            throws Exception {
        if (number == null || number.trim().equals("")) {
            if (nullToZero == true) {
                return "0";
            } else {
                return "";
            }
        }
        // 数字の文字列が０または-0の場合、zeroToNullによって、""或いは"0"を返す
        if (number.trim().equals("0") || number.trim().equals("-0")) {
            if (zeroToNull == true) {
                return "";
            } else {
                return "0";
            }
        }
        // 数字値の検査関数を呼び出す
        if (NumUtil.checkNumberValid(number) == false) {
            return number.trim();
        }
        String sFormatNumber = "";
        double dbNum;
        // 数字の文字列を指定した形式で数値に変換する
        NumberFormat defForm = new DecimalFormat(FORMAT_TEXT);
        try {
            dbNum = Double.parseDouble(number);
            if (formatToLong == true) {
                dbNum = Math.round(dbNum);
            }
            sFormatNumber = defForm.format(dbNum); // 書式化
        } catch (NumberFormatException numEx) {
            try {
                // 99,9999,9 --> 99,999,999
                Number numNumber = NumberFormat.getNumberInstance().parse(number);
                dbNum = Double.parseDouble(String.valueOf(numNumber));
                if (formatToLong == true) {
                    dbNum = Math.round(dbNum);
                }
                sFormatNumber = defForm.format(dbNum);
            } catch (ParseException pEx) {
                throw new Exception("FW096");
            }
        }
        return convertToValid(sFormatNumber);
    }

    /**
     * 数字を906 --> 906%のようなフォーマットに変換する
     * 
     * @param number
     *            文字列
     * @return String 変換した文字列 2003/11/07 新規作成 zhanjc
     */
    public static String formatPercentNumber(String number) {
        // ヌル又は空文字列の場合、""を返す
        if (number == null || number.trim().equals("")) {
            return "";
        }
        // 数字の文字列は0又は-0の場合、0を返す
        if (number.trim().equals("0") || number.trim().equals("-0")) {
            return "0";
            // 数字文字列の末に、%を追加して返す。
        } else if (NumUtil.checkNumberValid(number)) {
            return number + "%";
        } else {
            return number.trim();
        }
    }

    /**
     * 数字をZZ.ZZ ----> Z9.999のようなフォーマットに変換する
     * 
     * @param rate
     *            数字の文字列
     * @return String 変換した文字列 2003/11/07 新規作成 zhanjc
     */
    public static String formatRate(String rate) {
        if (rate == null || "".equals(rate.trim())) {
            return "";
        }
        if (!NumUtil.checkNumberValid(rate)) {
            return rate.trim();
        } else {
            DecimalFormat rateformat = new DecimalFormat("#,##0.000");
            try {
                double numericrate = Double.parseDouble(rate.trim()) + 0.0000000001;
                rate = rateformat.format(numericrate);
                if ("0.000".equals(rate)) {
                    return " ";
                } else if (rate.substring(rate.length() - 3, rate.length()).equals("000")) {
                    rate = rate.substring(0, rate.length() - 3) + "0";
                } else {
                    while (rate.endsWith("0")) {
                        rate = rate.substring(0, rate.length() - 1);
                    }
                }
            } catch (Exception ex) {
                debugLog.debug("formatRate failed. return the original Data");
            }
        }
        if (rate != null && !"".equals(rate.trim())) {
            rate = rate + "%";
        }
        return rate;
    }

    /**
     * 郵便番号をフォーマットに変換する。<br>
     * 
     * @param postNum
     *            入力フォーム
     * @return String 変換後文字列 2003/11/10 新規作成 zhanjc
     */
    public static String formatPostNum(String[] postNum) {
        if (postNum == null) {
            return "";
        }
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < postNum.length; i++) {
            if (postNum[i] == null || postNum[i].trim().equals("")) {
                return "";
            }
            if (i != 0) {
                result.append("-");
            }
            result.append(postNum[i]);
        }
        return result.toString();
    }

    /**
     * 郵便番号をフォーマットに変換する。<br>
     * 
     * @param postNum
     *            入力フォーム
     * @return String 変換した文字列 2003/11/10 新規作成 zhanjc
     */
    public static String formatPostNum(String postNum) {
        if (postNum == null || postNum.length() != 7) {
            return postNum;
        }
        return postNum.trim().substring(0, 3) + "-" + postNum.trim().substring(3, 7);
    }

    /**
     * 郵便番号をフォーマットに変換する。<br>
     * 
     * @param postNum
     *            入力フォーム
     * @return String 変換した文字列 2003/11/10 新規作成 zhanjc
     */
    public static String[] formatPostNumToArray(String postNum) {
        String[] postNumArray = { "", "" };
        if (postNum == null || postNum.length() != 7) {
        } else {
            postNumArray[0] = postNum.trim().substring(0, 3);
            postNumArray[1] = postNum.trim().substring(3, 7);
        }
        return postNumArray;
    }

    /**
     * 左、右スペス(半角と全角)を削除する
     * 
     * @param val
     *            String 変換文字列
     * @return String 変換した文字列 2003/11/07 新規作成 zhanjc
     */
    public static String trimSpc(String val) {
        if (val == null) {
            return "";
        } else {
            val = val.trim();
        }
        // 左スペス(半角と全角)数の変数
        int iHead = 0;
        // 左スペス(半角と全角)数を計算する
        for (int i = 0; i < val.length(); i++) {
            if (val.charAt(i) == ' ' || val.charAt(i) == '　') {
                iHead++;
            } else {
                break;
            }
        }
        // 右スペス(半角と全角)を削除した文字列を返す
        String valUse = val.substring(iHead);
        if (null != valUse) {
            int iEnd = valUse.length();
            for (int i = valUse.length() - 1; i >= 0; i--) {
                if (valUse.charAt(i) == ' ' || valUse.charAt(i) == '　') {
                    iEnd--;
                } else {
                    break;
                }
            }
            valUse = valUse.substring(0, iEnd);
        }
        return valUse;
    }

    /**
     * 全部ゼロ又はnullの場合 空文字列にして返す
     * 
     * @param val
     *            変換する文字列
     * @return 空文字列 valは全部ゼロ又はnull又は空文字列の場合 空文字列にして返す 2003/11/07 新規作成 zhanjc
     */
    public static String filteZero(String val) {
        if (val == null || "".equals(val.trim())) {
            return "";
        }
        if (!NumUtil.checkIntNumberValid(val)) {
            return "";
        }
        val = new Long(val.trim()).toString();

        return val;
    }

    /**
     * @param value
     *            文字列
     * @return String 変換した文字列
     * @author zhanjc
     */
    public static String nvl(String value) {
        if (value == null) {
            return "";
        } else {
            return value.trim();
        }
    }

    /**
     * numberをtimesで割って、計算の結果を返す
     * 
     * @param number
     *            数字の文字列
     * @param times
     *            計算数
     * @return double 変換後の文字列 2003/11/06 新規作成 zhanjc
     */
    private static double devideTimes(String number, int times) {
        if (number == null) {
            return 0D;
        }
        String sign = "";
        if (number.charAt(0) == '-') {
            sign = "-";
            number = number.substring(1);
        }
        int oldDotPosition = number.indexOf(".");
        if (oldDotPosition == -1) {
            number = number + ".";
            oldDotPosition = number.indexOf(".");
        }
        if (oldDotPosition == 0) {
            number = "0" + number;
            oldDotPosition = number.indexOf(".");
        }

        int moveSteps = String.valueOf(times).length();
        int newDotPosition;
        if (oldDotPosition >= moveSteps) {
            newDotPosition = oldDotPosition - moveSteps + 1;
        } else {
            int zeroCount = moveSteps - oldDotPosition;
            for (int i = 0; i < zeroCount; i++) {
                number = "0" + number;
            }
            newDotPosition = 1;
            oldDotPosition = number.indexOf(".");
        }
        String prefix = number.substring(0, newDotPosition);
        String suffix = number.substring(newDotPosition, oldDotPosition);
        if (oldDotPosition < number.length()) {
            suffix = suffix + number.substring(oldDotPosition + 1);
        }
        return Double.parseDouble(sign + prefix + "." + suffix);
    }

    /**
     * 数字を-0 ----> 0のようなフォーマットで変換し、変換した文字列を返す
     * 
     * @param number
     *            変換する文字列
     * @return String 変換した文字列 2003/11/07 新規作成 zhanjc
     */
    private static String convertToValid(String number) {
        if (number.trim().equals("-0")) {
            return "0";
        } else {
            return number;
        }
    }

    /**
     * 尾ベースを付加した結果を返する
     * 
     * @param val
     *            フォーマット値
     * @param length
     *            変換したintからゼロフォーマット用文字数を生成する
     * @return String 前ゼロを付加した文字列
     */
    public static String addBackFullSpace(String val, int length) {
        if (val == null) {
            return "";
        }
        int num = length - val.length();
        if (num <= 0) {
            return val;
        }
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < num; i++) {
            result.append("　");
        }

        return val + result.toString();
    }

    /**
     * 文字の並べた交替した結果を返する
     * 
     * @param sVal
     *            フォーマット値
     * @param oldVal
     *            交替する文字は並べる
     * @param repVal
     *            交替した文字が並べることに用いる
     * @return String 文字の並べた交替した結果を返する
     */
    public static String replace(String sVal, String oldVal, String repVal) {
        if (sVal == null || oldVal == null || repVal == null) {
            return sVal;
        }
        StringBuffer result = new StringBuffer(sVal);
        String str = sVal;

        int preLength = 0;
        int newEndPost = 0;
        while (str.toString().indexOf(oldVal) >= 0) {
            int startPost = preLength + str.toString().indexOf(oldVal);
            int endPost = startPost + oldVal.length();

            result.delete(startPost, endPost);
            result.insert(startPost, repVal);

            newEndPost = startPost + repVal.length();
            str = result.substring(newEndPost, result.length());
            preLength = newEndPost;
        }
        return result.toString();
    }

    /**
     * 文字列の先頭から数字部分を抜き出して戻す
     * 
     * @param str
     *            変換する文字列
     * @return String 変換後数字文字列
     */
    public static String toNumStr(String str) {
        int i = 0;

        if (str == null) {
            return "";
        }
        for (i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch != '.' && (ch < '0' || ch > '9')) {
                break;
            }
        }
        return str.substring(0, i);
    }

    /**
     * 文字列が全角ひらがなのみかをチェックする
     * 
     * @param txt
     *            チェック文字列
     * @return boolean 空文字列の場合は、trueを返し、<br>
     *         文字列はShift_JISのエンコードに変換失敗の場合、falseを返す<br>
     */
    public static final boolean isFullGana(String txt) {
        if (txt == null) {
            return true;
        }
        // 全角カナ
        try {
            byte[] b = txt.getBytes(ENCODE);
            if (b.length < 2 || b.length % 2 == 1) {
                return false;
            }
            for (int i = 0; i < b.length / 2; i++) {
                String str1 = Integer.toHexString(b[2 * i]);
                str1 = str1.substring(str1.length() - 2);
                String str2 = Integer.toHexString(b[2 * i + 1]);
                str2 = str2.substring(str2.length() - 2);
                int num = Integer.parseInt(str1 + str2, 16);
                // 判定：b[i]は$82$9F --- $82$F1の範囲に存在であるか。この範囲は全角ひらがな文字のである
                // 全角space($81$40)
                if (!((0x829F <= num && num <= 0x82F1) || num == 0x8140)) {
                    return false;
                }
            }
            return true;
        } catch (java.io.UnsupportedEncodingException e) {
            debugLog.debug(e.getMessage());
            return false;
        }
    }

    /**
     * 文字列が全角カタカナ のみかをチェックする
     * 
     * @param txt
     *            チェック文字列
     * @return boolean 空文字列の場合は、trueを返し、<br>
     *         文字列はShift_JISのエンコードに変換失敗の場合、falseを返す<br>
     */
    public static final boolean isFullKana(String str) {
        if (str == null) {
            return true;
        }
        // 全角カナ
        try {
            // mondify by tian.jun 20080710:exception No149 对应
            for (int j = 0; j < str.length(); j++) {
                String txt = str.substring(j, j + 1);
                byte[] b = txt.getBytes(ENCODE);
                if (b.length < 2 || b.length % 2 == 1) {
                    return false;
                }
                for (int i = 0; i < b.length / 2; i++) {
                    String str1 = Integer.toHexString(b[2 * i]);
                    str1 = str1.substring(str1.length() - 2);
                    String str2 = Integer.toHexString(b[2 * i + 1]);
                    str2 = str2.substring(str2.length() - 2);
                    int num = Integer.parseInt(str1 + str2, 16);
                    // 判定：b[i]は$83$40 --- $83$96の範囲に存在であるか。この範囲は全角カタカナ文字のである
                    // 全角space($81$40)
                    // "ー"($81$5B)
                    if (!((0x8340 <= num && num <= 0x8396) || num == 0x8140 || num == 0x815B)) {
                        return false;
                    }
                }
            }
            return true;

        } catch (java.io.UnsupportedEncodingException e) {
            debugLog.debug(e.getMessage());
            return false;
        }
    }

    /**
     * List〈String〉から文字列arrayに転換します。 e.g<br>
     * List&lt;String&gt; list=new ArrayList&lt;String&gt();<br>
     * list.add("first");<br>
     * list.add("second");<br>
     * Stirng[] str={"first","second"}<br>
     * 
     * @param inputList
     *            入力のList型
     * @return String
     */
    public static String[] listToStringArray(List<String> inputList) {
        return inputList.toArray(new String[inputList.size()]);
    }

    /**
     * SQL分に使用されるため、List〈String〉から読点で分けた文字列に転換します。 e.g<br>
     * List&lt;String&gt; list=new ArrayList&lt;String&gt();<br>
     * list.add("first");<br>
     * list.add("second");<br>
     * Stirng str="'first','second'"<br>
     * 
     * @param inputList
     *            入力のList型
     * @return String
     */
    public static String listToSQLString(List<String> inputList) {
        StringBuffer output = new StringBuffer();
        for (int i = 0; i < inputList.size(); i++) {
            output.append("'");
            output.append(inputList.get(i));
            output.append("'");
            if (i != inputList.size() - 1) {
                output.append(",");
            }
        }
        return output.toString();
    }

    /**
     * パラメータ.氏名（漢字）<br>
     * パラメータ.氏名（カナ）<br>
     * 上記の項目には、一文字以上の半角スペースを含む（例：ぴあ 一郎）<br>
     * 
     * @param name
     *            氏名
     * @return
     */
    public static String[] separateName(String name) {
        name = trimSpc(name);

        name = name.replaceAll("　+", " ");

        if ("".equals(nvl(name))) {
            return new String[] { "", "" };
        }

        name = replace(name, "　", " ");

        if (name.indexOf(" ") == -1) {
            return new String[] { "", "" };
        }

        String[] separated = name.split(" ");

        if (separated.length != 2) {
            return new String[] { "", "" };
        }
        return separated;
    }

    public static String getHashString(byte[] md5Data) {
        StringBuffer hashString = new StringBuffer();
        for (int i = 0; i < md5Data.length; ++i) {
            String hex = Integer.toHexString(md5Data[i]);
            if (hex.length() == 1) {
                hashString.append('0');
                hashString.append(hex.charAt(0));
            } else {
                hashString.append(hex.substring(hex.length() - 2));
            }
        }
        return hashString.toString();
    }

    public static String toHexString(byte[] in) {
        byte ch = 0x00;
        int i = 0;

        if (in == null || in.length <= 0)
            return null;

        String pseudo[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

        StringBuffer out = new StringBuffer(in.length * 2);

        while (i < in.length) {
            ch = (byte) (in[i] & 0xF0);
            ch = (byte) (ch >>> 4);
            ch = (byte) (ch & 0x0F);
            out.append(pseudo[(int) ch]);
            ch = (byte) (in[i] & 0x0F);
            out.append(pseudo[(int) ch]);
            i++;
        }

        String rslt = new String(out);
        return rslt;

    }
    
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
    
    public static String[] splitByLength(String s, int chunkSize) {
		int arraySize = (int) Math.ceil((double) s.length() / chunkSize);

		String[] returnArray = new String[arraySize];

		int index = 0;
		for (int i = 0; i < s.length(); i = i + chunkSize) {
			if (s.length() - i < chunkSize) {
				returnArray[index++] = s.substring(i);
			} else {
				returnArray[index++] = s.substring(i, i + chunkSize);
			}
		}

		return returnArray;
	}
    
    public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

}