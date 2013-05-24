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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HttpRestSupport { 
	private final static String TAG="HttpRestSupport"; 

	/**
	 * 
	 * JSONObject param = new JSONObject(); 
	 * param.put("name", "rarnu");
	 * param.put("password", "123456");
	 * 
	 * @param path
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	public static JSONObject postByHttpClientWithGzip(String path,JSONObject paramJson) throws Exception {
		HttpPost request = new HttpPost(path);
		// 先封装一个 JSON 对象  绑定到请求 Entity
		StringEntity se = new StringEntity(paramJson.toString()); 
		request.setEntity(se);
		// 发送请求
		HttpClient httpclient = creteHttpClient();
		HttpResponse httpResponse = httpclient.execute(request);
		// 得到应答的字符串，这也是一个 JSON 格式保存的数据 
		JSONObject resultJson = null;
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {//
			String strResult = EntityUtils.toString(httpResponse.getEntity());
			// 生成 JSON 对象
			Log.d(TAG,"postByHttpClientWithGzip----->"+ strResult);
			resultJson = new JSONObject( strResult);
		}
		return resultJson;  
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getByHttpClientWithGzip(String path) throws Exception {
		String strResult = "";
		HttpGet httpRequest = new HttpGet(path);
		// httpRequest.addHeader("Accept-Encoding", "gzip");
		httpRequest.addHeader("Accept-Encoding", "utf-8");

		HttpClient httpclient = creteHttpClient();
		HttpResponse httpResponse = httpclient.execute(httpRequest);

		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity e = httpResponse.getEntity();
			InputStream is = e.getContent();

			if (e.getContentEncoding().getValue().contains("gzip")) {
				is = new GZIPInputStream(is);
				strResult = inputStream2String(is);
			}
		}
		Log.d(TAG,"getByHttpClientWithGzip----->"+ strResult);
		JSONObject jsonObject=createJSONObject(strResult);
		return jsonObject;
	}

	
	/**
	 * 
	 * @return
	 */
	private static HttpClient creteHttpClient() {
		HttpParams httpParameters = new BasicHttpParams(); 
		HttpConnectionParams.setConnectionTimeout(httpParameters, 3000); 
		HttpConnectionParams.setSoTimeout(httpParameters, 5000); 
		HttpClient httpclient = new DefaultHttpClient(httpParameters);
		return httpclient;
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getByHttpClientUnzip(String path) throws Exception{  
    	String strResult = "";
    	HttpGet httpRequest = new HttpGet(path); 
    	
    	HttpClient httpclient = creteHttpClient(); 
        HttpResponse httpResponse = httpclient.execute(httpRequest); 
       
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
        { 
        	HttpEntity e = httpResponse.getEntity();
        	InputStream is= e.getContent();
        	strResult = inputStream2String(is);

        }   
		JSONObject jsonObject=createJSONObject(strResult);
		Log.d(TAG, jsonObject.toString());
		return jsonObject;
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
			Log.e(TAG, "[createJSONObject]Error parsing data " + e.toString());
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
			Log.e(TAG,"[inputStream2String]Error parsing data " + e.toString());
		}
		return outputStream.toString();
	}
}
