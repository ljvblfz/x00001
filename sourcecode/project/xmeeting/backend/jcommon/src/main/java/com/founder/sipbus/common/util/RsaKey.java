package com.founder.sipbus.common.util;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

public class RsaKey {

	private static final String KEY_ALGORITHM = "RSA";
	private static final String PUBLIC_KEY = "publicKey";
	private static final String PRIVATE_KEY = "privateKey";

	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PublicKey publicKey = keyFactory.generatePublic(keySpec);

		return publicKey;
	}

	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes = Base64.decodeBase64(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

		return privateKey;
	}

//	public static void main(String[] args) throws Exception {
//
////		Map<String,String> keyMap = genKey();
////		System.out.println(keyMap.get(PUBLIC_KEY));
////		System.out.println(keyMap.get(PRIVATE_KEY));
//		
//		String pkey="pWPoLhBM8BsY4039bT9U41Qxtv7e17EnHOwuG20VQ1piswfedEPrS6QSjQKpXiLmGrQxdfCcA120cLrJCroyaSs9t3/dnGIKCXEFLA0PqVGx9nPHLRHUnTbKK/cDPEkRGXbNFjwqTP450ZpTpY6e2bUTeb99lsr8d8MXmUAYj1k=";
//		
//		System.out.println(RSAEncrypt(pkey, "system"));
//
//		// String ss =
//		// "eqDtbTuMFT74OKMg4Cr534AD9V8VgiagLJ2FeH3sNI5WTgrBYps/rKArJugm2u/zg1Vr+Q055ruEjZGrvcNYzRAYgssHigy6uAbCLhThiEhOkdo1MfLDM5Sp74gFrdQhCzwxIGomOrPdenjT4VDMPr7Zcn+ymlU/v/4cgtBFDgA=";
//		//
////		 String rr = RSADecrypt(URLDecoder.decode("CMsghDEziPilfHzmccLaszz%2fBCb2sqYBZpDrHFJ2Kw2CNMV7oLGmYMcZS0nNSWZ9IsWbxFJvq8qSJJ%2fGr36P7KY9pPCBJUDyy%2bfyBrE5N4K%2bVfdj0QMC7S2kyzxjfOx56hDDqj8cHI4SfzFLUMHHStCgWcETsrlu%2fCBqDwZMwu8%3d","utf-8"));
////		//
////		 System.out.println(rr);
//	}

	
	/**
	 * 根据公钥加密字符串
	 * @author founder 2012-5-21
	 * @param sPublicKey
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	public static String RSAEncrypt(String sPublicKey, String plainText)
			throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		PublicKey publicKey = getPublicKey(sPublicKey);
		cipher.init(1, publicKey);
		byte[] ptBytes = plainText.getBytes();
		byte[] enBytes = cipher.doFinal(ptBytes);

		String s_en = Base64.encodeBase64String(enBytes);
		return s_en;
	}
	
	
	public static String RSADecrypt(String sDecryptString) throws Exception {
		String sPrivateKey = "" +
				"MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKVj6C4QTPAbGONN/W0/VONUMbb+3texJxzsLhttFUNaYrMH3nRD60ukEo0CqV4i5hq0MXXwnANdtHC6yQq6MmkrPbd/3ZxiCglxBSwND6lRsfZzxy0R1J02yiv3AzxJERl2zRY8Kkz+OdGaU6WOntm1E3m/fZbK/HfDF5lAGI9ZAgMBAAECgYBQDd3Sfh1fkx4m2Bc1DBSgvJ4bv55JtPwDqeZfbBuAsH1ZvxNUH5QC1vYLvyAP6sCKaHIQblh14KN4dVDemmXPbby+S7cU8MXkD8r+qumqw7hWfTsh8zqThukZ4KGJSK5CxTg9FSbsiA+xw15GcOy0PgoZVEVza9vadbbxZbNd6QJBAOU3JqaSL8mhAbU2dTsIPnpbZ4Qvw8u3uPP34Y3N6pc8NYlYt0hb/zO7N3M4edeXrObXsN/qJawFEkTa71el84sCQQC4t3Zb6ALk7KF85oiMExbeWafT3Zhy5dCrwpRyxJzXfYs9wpmggZNpdS18/OjN109S0pqczC1esZ6mLDU1gtUrAkAYJKn+i5FbBImAg97vO9wQ2UXbB4jxEUX69JYecJ0QFvhwohaUGZSU51hc24sB6Wj926Q2wZv8NYCKHiFtsv4nAkAlZV8c9DfByPZYpwN1+Fdk5JnJls9KU6SPYXFUWuG17sDF3CPmDtWdQA/ZggpPwgtZJTIQFHYK+cK4ubJ8BuTJAkAYNFZEkars51qGQQUTNotZmXZqWEOfXWjOGtEbjenHQNbH9v9LjF4KbVqRYQfibQ/F6hF0VyUW5hoAhnXebrmM";
//				 MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIoyvG619d2wYZK27oNf3BO+J6ps9GBRDgLDEJpVoj6tkv+p/hnlU2+qQEwCDufJKKPp7w7Y41IgkxskEIADBBW6fGoT4uGH6R5WQNSYnHI+gymmZ46VaKutwujb2IX9BXDsjyRqxjsEFq1uZur1Wo8vUGEG5U/x0OfoVkehFWFHAgMBAAECgYBB15pfROI3FOnn9K/+d5Vq8pYDZJWprfR27GjknxgFtLzsghlGTTRLZ1nq3LAtTnHGZpY3BK6JWmu76prR4p325tJZOzPFe3qhFF2R7A2tigZ+3SQiauX95XHKc8C+vehDkLjJi6G8nrIWGflefSK/saY0OFdFEw0iRBnFdjChAQJBANvJN1StyUFPFbKa4UbYLEIUera3TK5EZcoQ8wzxpBVOvcv6ecurxqUBHQEtQhJZPP8H8bRdpqGqsuAo9eTSZ9ECQQCg+BFholz0jAaMcLR4Zx8orEP+a/s0RdlPEcnywPNc/212RZx7uR25BP76pJGMeyYTX+8KBhNaoCl6VkzylhWXAkEAqMK+W6T9jco97OCD7jP699plx3rDxGP8jMWq+ttolHvbvI5diwghBX3vWYH448Hl1iWxcD8gva4mn3yUItl0oQJAF66nj/+9QVVgmUTzqEszsF432rS0TqqAIDvzxD1TELmbtyziquk/UDtF8EZKC6sKnQseNhFSw6CQuaj8xSwDnQJBAIUGw+TWwsII2F509XIsNosHpwXq9a4INa5RiD9FQM3IQWlmnL30KmbkC/NjwG3Lvm1Y8woFi5Cx3urU5tHaqqs=
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		PrivateKey privateKey = getPrivateKey(sPrivateKey);
		cipher.init(2, privateKey);

		byte[] ary_en = Base64.decodeBase64(sDecryptString);
		byte[] deBytes = cipher.doFinal(ary_en);
		String s_de = new String(deBytes);
		return s_de;
	}

	
	/**
	 * 随机获取1024位  公钥 以及 私钥
	 * @author founder 2012-5-21
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static Map<String, String> genKey() throws NoSuchAlgorithmException {
		Map<String, String> keyMap = new HashMap<String, String>();
		KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		SecureRandom random = new SecureRandom();
		// random.setSeed(keyInfo.getBytes());
		// 初始加密，512位已被破解，用1024位,最好用2048位
		keygen.initialize(1024, random);
		// 取得密钥对
		KeyPair kp = keygen.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
		String privateKeyString = Base64.encodeBase64String(privateKey
				.getEncoded());
		RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
		String publicKeyString = Base64.encodeBase64String(publicKey
				.getEncoded());
		keyMap.put(PUBLIC_KEY, publicKeyString);
		keyMap.put(PRIVATE_KEY, privateKeyString);
		return keyMap;
	}

	
	/**   
	 * 测试公交二期公钥加密 私钥解密
     * @param args   
	 * @throws Exception 
     */   
    public static void main(String[] args) throws Exception {   
        String en = encrypt("system");   
        System.out.println(en);
        System.out.println(RSADecrypt("fKOPC1kgfnEwdENgb0n9B/gdAJTsFApJPg1uL9BBPycQWdc/9rdLL3efm/YiWPMMCi6tDRq8bxFl9qagDqquhM4kS9gHy02b8o0O3fgEAf5JghfJOaoUIAXuBUnCjU1NVsK6ZuvwKYwDGvCXHPg3x++Pcf6lYAcht25Ww3WlR1M="));
//        byte[] enTest = null;   
//        String enTest = URLDecoder.decode("CMsghDEziPilfHzmccLaszz%2fBCb2sqYBZpDrHFJ2Kw2CNMV7oLGmYMcZS0nNSWZ9IsWbxFJvq8qSJJ%2fGr36P7KY9pPCBJUDyy%2bfyBrE5N4K%2bVfdj0QMC7S2kyzxjfOx56hDDqj8cHI4SfzFLUMHHStCgWcETsrlu%2fCBqDwZMwu8%3d","utf-8");   
//        System.out.println(enTest.length);   
//        System.out.println(en.length);   
//        System.out.println(new String(Dencrypt(en)));
//        System.out.println(new String(Dencrypt(enTest)));   
    }   

    
    
    
    
    /**
     * 公交二期RSA public key 参数  module
     */
    private static String module="pWPoLhBM8BsY4039bT9U41Qxtv7e17EnHOwuG20VQ1piswfedEPrS6QSjQKpXiLmGrQxdfCcA120cLrJCroyaSs9t3/dnGIKCXEFLA0PqVGx9nPHLRHUnTbKK/cDPEkRGXbNFjwqTP450ZpTpY6e2bUTeb99lsr8d8MXmUAYj1k=";

    /**
     * 公交二期RSA public key 参数  Exponent
     */
    private static String exponentString="AQAB";   

    /**
     * 公交二期RSA public key 参数  D
     */
    private static String delement="o1vBLIbYMD1bEgn00rDtDWcr3ifBWnz9Tho1h6l9BoCG0QMurOfcxP6FepiFIwmjPVLFFaUjshA+rONvyshIxlIE4Nudu1FjvPp+jLebreOoPHMi3QhhATG5I8M0KSFyL0r7yLUXXZsNYDBE0g1cpOfrZLuVcdTxSYj+QmeQ7hs=";
	
    /**
     * 私钥解密公交二期 SSO 字符串
     * @author founder 2012-5-21
     * @param deCodeStr
     * @return
     */
    public static byte[] Dencrypt(String deCodeStr ) {   
        try {   
        	byte[] encrypted=Base64.decodeBase64(deCodeStr);
            byte[] expBytes = Base64.decodeBase64(delement);   
            byte[] modBytes = Base64.decodeBase64(module);   
            BigInteger modules = new BigInteger(1, modBytes);   
            BigInteger exponent = new BigInteger(1, expBytes);   
            KeyFactory factory = KeyFactory.getInstance("RSA");   
            Cipher cipher = Cipher.getInstance("RSA");   
            RSAPrivateKeySpec privSpec = new RSAPrivateKeySpec(modules, exponent);   
            PrivateKey privKey = factory.generatePrivate(privSpec);   
            cipher.init(Cipher.DECRYPT_MODE, privKey);   
            byte[] decrypted = cipher.doFinal(encrypted);   
            return decrypted;   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
        return null;   
    }   


    /**
     * 公钥加密公交二期 SSO 字符串
     * @author founder 2012-5-21
     * @param deCodeStr
     * @return
     */
	public static String encrypt(String plainText) {   
        try {   
            byte[] modulusBytes = Base64.decodeBase64(module);   
            byte[] exponentBytes = Base64.decodeBase64(exponentString);   
            BigInteger modulus = new BigInteger(1, modulusBytes);   
            BigInteger exponent = new BigInteger(1, exponentBytes);   
  
            RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus, exponent);   
            KeyFactory fact = KeyFactory.getInstance("RSA");   
            PublicKey pubKey = fact.generatePublic(rsaPubKey);   
  
            Cipher cipher = Cipher.getInstance("RSA");   
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);   
  
            byte[] cipherData = cipher.doFinal(plainText.getBytes());   
            return Base64.encodeBase64String(cipherData);   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
        return null;   
  
    }   

	
}
