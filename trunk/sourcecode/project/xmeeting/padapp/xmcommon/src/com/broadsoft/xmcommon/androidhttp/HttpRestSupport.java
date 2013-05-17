package com.broadsoft.xmcommon.androidhttp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HttpRestSupport {
	
	/**
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String getByHttpClientWithGzip(String path) throws Exception {
		String strResult = "";
		HttpGet httpRequest = new HttpGet(path);
		// httpRequest.addHeader("Accept-Encoding", "gzip");
		httpRequest.addHeader("Accept-Encoding", "utf-8");

		HttpParams httpParameters = new BasicHttpParams(); 
		HttpConnectionParams.setConnectionTimeout(httpParameters, 3000); 
		HttpConnectionParams.setSoTimeout(httpParameters, 5000);

		HttpClient httpclient = new DefaultHttpClient(httpParameters);
		HttpResponse httpResponse = httpclient.execute(httpRequest);

		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity e = httpResponse.getEntity();
			InputStream is = e.getContent();

			if (e.getContentEncoding().getValue().contains("gzip")) {
				is = new GZIPInputStream(is);
				strResult = inputStream2String(is);
			}
		}
		Log.d("MainActivity--strResult->", strResult);
		JSONObject jsonObject=createJSONObject(strResult);
		return strResult;
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String getByHttpClientUnzip(String path) throws Exception{  
    	String strResult = "";
    	HttpGet httpRequest = new HttpGet(path); 
    	
    	HttpParams  httpParameters = new BasicHttpParams(); 
        HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);
        HttpConnectionParams.setSoTimeout(httpParameters, 5000);  
        
        HttpClient httpclient = new DefaultHttpClient(httpParameters); 
        HttpResponse httpResponse = httpclient.execute(httpRequest); 
       
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
        { 
        	HttpEntity e = httpResponse.getEntity();
        	InputStream is= e.getContent();
        	strResult = inputStream2String(is);

        }   
		JSONObject jsonObject=createJSONObject(strResult);
		Log.d("MainActivity--jsonObject->", jsonObject.toString());
		return strResult;
     } 
	
	/**
	 * 
	 * @param strJson
	 * @return
	 */
	private static JSONObject createJSONObject(String strJson) {
		JSONObject jObj = null;
		try {
			jObj = new JSONObject(strJson);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		return jObj;
	}


	private static String inputStream2String(InputStream inputStream) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
		int len;
		try {
			while ((len = inputStream.read(buf)) != -1) {
				outputStream.write(buf, 0, len);
			}
			outputStream.close();
			inputStream.close();
		} catch (IOException e) {
			Log.e("InputStream2String",e.toString());
		}
		return outputStream.toString();
	}
}
