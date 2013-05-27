package com.broadsoft.xmdownload.appsupport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.util.Log;

public class AssetManagerSupport {
	

	private final static String TAG="AssetManagerSupport";
	public static String readText(AssetManager assetManager) { 
		String fileName = "json/json.txt"; 
		try {
			InputStream istr = assetManager.open(fileName);
			String json=inputStream2String(istr);
			return json;
		}catch (IOException e) {
			e.printStackTrace();
		} 
		return null;  
	}
	
	
	private static String inputStream2String(InputStream inputStream) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
		int len;
		try {
			while ((len = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, len);
			}
		} catch (IOException e) {
			Log.e(TAG,"[inputStream2String]Error parsing data " + e.toString());
		}finally{ 
			try {
				outputStream.close();
				inputStream.close();
			} catch (IOException e) { 
				e.printStackTrace();
			}
		}
		return outputStream.toString();
	}
}
