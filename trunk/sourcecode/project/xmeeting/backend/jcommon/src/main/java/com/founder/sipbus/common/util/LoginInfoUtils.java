package com.founder.sipbus.common.util;


public class LoginInfoUtils {

	private static final ThreadLocal<String> loginUserThreadLocal = new ThreadLocal();
	
	
	public static void setUser(String userId){
		loginUserThreadLocal.set(userId);
	}

//	public static ISSOManager getLoginSSOManager(){
//		return  loginUserThreadLocal.get();
//	}
	
	
	public static String getLoginUserId(){
		return loginUserThreadLocal.get();
	}
	
}
