package com.broadsoft.xmcommon.androidsdcard;

import android.os.Environment;
import android.util.Log;

public class SDCardSupport {
	

	private final static String TAG="SDCardSupport";
	
	public static String getSDCardDirectory(){

		String sdcardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
		Log.d(TAG, "sdcardDir is : "+sdcardDir); 
		return sdcardDir;
	}

}
