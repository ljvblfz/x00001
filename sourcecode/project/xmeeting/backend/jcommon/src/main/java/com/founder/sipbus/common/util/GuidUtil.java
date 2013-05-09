package com.founder.sipbus.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.omg.CORBA.SystemException;

/**
 * [クラス名]<br>
 * GuidUtil<br>
 * [機能概要]<br>
 * 唯一なシリアル番号を生成する<br>
 * [変更履歴]<br>
 * 2006/07/18 ver1.00 新規作成 luoyue<br>
 * 
 * @author luoyue
 * @version 1.00
 */
public class GuidUtil {

    private static final Log log = LogFactory.getLog(GuidUtil.class);

    private static Random myRand;

    private static SecureRandom mySecureRand;

    private static String s_id;

    private static final int PAD_BELOW = 0x10;

    private static final int TWO_BYTES = 0xFF;

    /*
     * Static block to take care of one time secureRandom seed. It takes a few seconds to initialize SecureRandom. You might want to consider removing this static block or replacing it with a "time
     * since first loaded" seed to reduce this time. This block will run only once per JVM instance.
     */

    static {
        mySecureRand = new SecureRandom();
        long secureInitializer = mySecureRand.nextLong();
        myRand = new Random(secureInitializer);
        try {
            s_id = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            log.error(e.getMessage(),e);
        }
    }

    /**
     * Method to generate the random GUID Setting secure true enables each random number generated to be cryptographically strong. Secure false defaults to the standard Random function seeded with a
     * single cryptographically strong random number.
     * 
     * @param secure
     * @return
     * @throws SystemException
     */
    public static String getRandomGUID(boolean secure) {
        MessageDigest md5 = null;
        StringBuffer sbValueBeforeMD5 = new StringBuffer(128);
        String valueBeforeMD5 = "";
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // Should not happen
            throw new IllegalStateException(e);
        }

        long time = System.currentTimeMillis();
        long rand = 0;

        if (secure) {
            rand = mySecureRand.nextLong();
        } else {
            rand = myRand.nextLong();
        }
        sbValueBeforeMD5.append(s_id);
        sbValueBeforeMD5.append(":");
        sbValueBeforeMD5.append(Long.toString(time));
        sbValueBeforeMD5.append(":");
        sbValueBeforeMD5.append(Long.toString(rand));

        valueBeforeMD5 = sbValueBeforeMD5.toString();
        md5.update(valueBeforeMD5.getBytes());

        byte[] array = md5.digest();
        StringBuffer sb = new StringBuffer(32);
        for (int j = 0; j < array.length; ++j) {
            int b = array[j] & TWO_BYTES;
            if (b < PAD_BELOW)
                sb.append('0');
            sb.append(Integer.toHexString(b));
        }

        return sb.toString();

    }

    /**
     * 产生指定位数的随即数，包含数字和大小写字母
     * 
     * @param length
     * @return String
     * @throws SystemException
     */
    public static String getRandomStr(int length) {
        StringBuffer ret = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            int itmp = (int) (Math.random() * 62);
            if (itmp <= 9) {
                ret.append(itmp);
            } else if (itmp >= 10 && itmp <= 35) {
                char c = (char) ((itmp - 10) + 'A');
                ret.append(c);
            } else if (itmp >= 36) {
                char c = (char) ((itmp - 36) + 'a');
                ret.append(c);
            }
        }
        return ret.toString();
    }
}
