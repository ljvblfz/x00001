package com.broadsoft.integration.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
 


/**
 * http://www.androidsnippets.com/executing-a-http-post-request-with-httpclient
 * @author lu.zhen
 *
 */
public class HttpPostSupport {  
	


	private static final int REQUEST_TIMEOUT = 10*1000;//设置请求超时10秒钟  
	private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟  
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
	public static String executePostMethodByHttpClient(String path,JSONObject paramJson) throws Exception {
		HttpPost request = new HttpPost(path);  
        // 添加form数据
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        Iterator<String> keys=paramJson.keys();
        while(keys.hasNext()){
        	String key=keys.next();
        	String value=paramJson.getString(key);  
            nameValuePairs.add(new BasicNameValuePair(key,value));  
        } 
        request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        
		// 发送请求
		HttpClient httpclient = creteHttpClient(); 
		request.setHeader("Content-type", "text/html; charset=UTF-8");
		HttpResponse httpResponse = httpclient.execute(request);
		// 得到应答的字符串，这也是一个 JSON 格式保存的数据 
		String strResult = "";
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {//
			HttpEntity entity = httpResponse.getEntity();
			InputStream is = entity.getContent();  
			strResult = inputStream2String(is);  
		} else{
			//TODO: fixme
		}
		return strResult;
	}
	
	
	 
	
	/**
	 * 
	 * @return
	 */
	private static HttpClient creteHttpClient() {
		HttpParams httpParameters = new BasicHttpParams(); 
		HttpConnectionParams.setConnectionTimeout(httpParameters, REQUEST_TIMEOUT); 
		HttpConnectionParams.setSoTimeout(httpParameters, SO_TIMEOUT); 
		HttpClient httpclient = new DefaultHttpClient(httpParameters);
		return httpclient;
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
			e.printStackTrace();
		}
		return outputStream.toString();
	}
}
