package com.broadsoft.xmcommon.androidnetwork;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * ConnectivityManager connMgr = (ConnectivityManager)
 * getSystemService(Context.CONNECTIVITY_SERVICE);
 * 
 * @author lu.zhen
 * 
 */
public class NetworkSupport {

	/**
	 * 
	 * @param connMgr
	 * @return
	 */
	public static boolean isConnected(ConnectivityManager connMgr) {
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}// end of isConnected

}// end of NetworkSupport
