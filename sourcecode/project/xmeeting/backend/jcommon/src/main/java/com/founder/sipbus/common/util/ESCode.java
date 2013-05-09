package com.founder.sipbus.common.util;

public class ESCode {
	/**
	 * 解密
	 * 
	 * @param a
	 * @return
	 */
	public static String Decode2(String a) 
	{
		String EgoFuncKey = "EGOSYSTEMISBESTBYWZFXBG";
		Licensing EgoCom = new Licensing();
		EgoCom.set_ValidateKey(EgoFuncKey);
		Object _retVal = EgoCom.Decode2(a);
		return _retVal == null ? "" : _retVal.toString();
	}

	/**
	 * 加密
	 * 
	 * @param a
	 * @return
	 */
	public static String Encode2(String a) 
	{
		String EgoFuncKey = "EGOSYSTEMISBESTBYWZFXBG";
		Licensing EgoCom = new Licensing();
		EgoCom.set_ValidateKey(EgoFuncKey);
		Object _retVal = EgoCom.Encode2(a);
		return _retVal == null ? "" : _retVal.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(Encode2("2"));
		System.out.println(Decode2("244B70"));
	}
}
