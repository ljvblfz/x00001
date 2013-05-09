package com.founder.sipbus.common.util;
import java.io.UnsupportedEncodingException;


public class ESDecode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String a = "704730254C7538392A";
		String dd = Decode2(a);
		System.out.println(dd);
	}
	
	/**
	 * 解密
	 * 
	 * @param a
	 * @return
	 */
	public static String Decode2(String a) 
	{
		
		return ESCode.Decode2(a);
//		Object _retVal = Decode(asctostr(a));
//		return _retVal == null ? "" : _retVal.toString();
	}
	
	  

}
