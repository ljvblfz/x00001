package com.founder.common.data;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

public class HttpService {

    public static String getByHttpURLConnection(String path) throws Exception{  
  
       URL url = new URL(path);  
       HttpURLConnection urlConn = (HttpURLConnection) url.openConnection(); 
       InputStreamReader in = new InputStreamReader(urlConn.getInputStream()); 
       BufferedReader buffer = new BufferedReader(in); 
       String inputLine = null;
       String resultData = "";
       while (((inputLine = buffer.readLine()) != null)) 
       { 
           resultData += inputLine + "\n"; 
       }          
       in.close(); 
       urlConn.disconnect();
       return resultData;
    }  
    
    public static String getByHttpClient(String path) throws Exception{  
    	String strResult = "";
    	HttpGet httpRequest = new HttpGet(path); 
    	httpRequest.addHeader("Accept-Encoding", "gzip");
    	
    	HttpParams  httpParameters = new BasicHttpParams();// Set the timeout in milliseconds until a connection is established.  
        HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);// Set the default socket timeout (SO_TIMEOUT) // in milliseconds which is the timeout for waiting for data.  
        HttpConnectionParams.setSoTimeout(httpParameters, 5000);  
        
        HttpClient httpclient = new DefaultHttpClient(httpParameters); 
        HttpResponse httpResponse = httpclient.execute(httpRequest); 
       
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
        { 
        	HttpEntity e = httpResponse.getEntity();
        	InputStream is= e.getContent();

        	if (e.getContentEncoding().getValue().contains("gzip")) {
        	        is= new GZIPInputStream(is);
        	        strResult = inputStream2String(is);
        	}
        }  
		return strResult;
     } 
    
    public static String getByHttpClientUnzip(String path) throws Exception{  
    	String strResult = "";
    	HttpGet httpRequest = new HttpGet(path); 
    	
    	HttpParams  httpParameters = new BasicHttpParams();// Set the timeout in milliseconds until a connection is established.  
        HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);// Set the default socket timeout (SO_TIMEOUT) // in milliseconds which is the timeout for waiting for data.  
        HttpConnectionParams.setSoTimeout(httpParameters, 5000);  
        
        HttpClient httpclient = new DefaultHttpClient(httpParameters); 
        HttpResponse httpResponse = httpclient.execute(httpRequest); 
       
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
        { 
        	HttpEntity e = httpResponse.getEntity();
        	InputStream is= e.getContent();
        	strResult = inputStream2String(is);

        }  
		return strResult;
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
        } 
    	catch (IOException e) {}
    	return outputStream.toString();
    }
    
  
}
