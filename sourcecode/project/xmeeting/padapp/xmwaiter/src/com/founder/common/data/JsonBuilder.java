package com.founder.common.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONObject;


import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

public class JsonBuilder {
	
	public static JSONObject getNetJsonObject(String sRequestName, String sArg, AssetManager assetManager)
	{
		try {
			DataConfigManager dcm = new DataConfigManager(assetManager);
			String sUrl = dcm.getDataUrl(sRequestName, sArg);
			System.out.println(sUrl);
			String sJsonData = HttpService.getByHttpClient(sUrl);
	        JSONObject obj = new JSONObject(sJsonData);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
       
    	return null;
	}
	
	public static JSONObject getNetAuJsonObject(String sRequestName, String sArg, AssetManager assetManager)
	{
		try {
			DataConfigManager dcm = new DataConfigManager(assetManager);
			String sUrl = dcm.getAuthenticationUrl(sRequestName, sArg);
			System.out.println(sUrl);
			String sJsonData = HttpService.getByHttpClientUnzip(sUrl);
	        JSONObject obj = new JSONObject(sJsonData);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
       
    	return null;
	}
	
	public static JSONObject getLoaclJsonObject(String sPath ,AssetManager assetManager)
	{
    	try {
    		InputStream inputStream = assetManager.open(sPath);
    		String s = readTextFile(inputStream);
    	    JSONObject obj = new JSONObject(s);
    		return obj;
    	} 
    	catch (Exception e) {
    	   Log.e("tag", e.getMessage());
    	}

    	return null;
	}
	
	private static String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    	byte buf[] = new byte[1024];
    	int len;
    	try {
    		while ((len = inputStream.read(buf)) != -1) {
    	    outputStream.write(buf, 0, len);
    		}
    		outputStream.close();
    		inputStream.close();
        } 
    	catch (IOException e) {}
    	return outputStream.toString();
    }
    
	
	public static boolean isRequestExistByLocal(String sPath, String sName, Activity ac) throws IOException
	{
		String[] fileList = null;
		try {
			fileList = ac.getResources().getAssets().list(sPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < fileList.length; i++)
		{
			if (fileList[i].equalsIgnoreCase(sName))
			{
				return true;
			}
		}
		return false;
	}
	
	public static boolean isRequestExistByNet(String sPath, String sName, Activity ac) throws IOException
	{
		String[] fileList = null;
		try {
			fileList = ac.getResources().getAssets().list(sPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < fileList.length; i++)
		{
			if (fileList[i].equalsIgnoreCase(sName))
			{
				return true;
			}
		}
		return false;
	}
	
}
