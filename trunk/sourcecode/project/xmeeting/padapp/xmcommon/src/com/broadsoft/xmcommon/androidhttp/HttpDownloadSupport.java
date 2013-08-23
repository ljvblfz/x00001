package com.broadsoft.xmcommon.androidhttp;

import java.io.BufferedInputStream; 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;
import org.apache.commons.io.output.ByteArrayOutputStream;

import com.broadsoft.xmcommon.androidsdcard.SDCardSupport;

/**
 * http://dampce032.iteye.com/blog/975642
 * http://www.eoeandroid.com/thread-108676-1-1.html
 * http://blog.csdn.net/ameyume/article/details/6528205
 * 
 * 
 * 通过URL取得HttpURLConnection 要网络连接成功，需在AndroidMainfest.xml中进行权限配置
 * <uses-permission android:name="android.permission.INTERNET" /> 写前准备
 * 1.在AndroidMainfest.xml中进行权限配置 <uses-permission
 * android:name="android.permission.WRITE_EXTERNAL_STORAGE" /> 取得写入SDCard的权限
 * 2.取得SDCard的路径： Environment.getExternalStorageDirectory() 3.检查要保存的文件上是否已经存在
 * 4.不存在，新建文件夹，新建文件 5.将input流中的信息写入SDCard 6.关闭流
 * 
 * @author lu.zhen
 * 
 */
public class HttpDownloadSupport {
	
	private final static String TAG="HttpDownloadSupport";

	/**
	 * 
	 * @param serveriport
	 * @param docPathFile
	 * @return  0--下载失败,1--下载成功,2--已经存在,4 --文件不存在
	 */
	public static int downloadFile(String serveriport,String docPathFile,HttpDownloadListener httpDownloadListener) { 
		Log.d(TAG, "downloadFile begin, docPathFile is: "+docPathFile); 
		if(null==docPathFile||"".equals(docPathFile.trim())){
			return 4;
		}
		String urlStr = "http://" + serveriport + docPathFile;  
		Log.d(TAG, "Download the file url ---->"+urlStr);
		OutputStream output = null;
		InputStream input =null;
//		InputStream byteArrayOutputStream=null;
		HttpURLConnection conn=null;
		int retFlag=0;
 
		String sdcardDir = SDCardSupport.getSDCardDirectory(); 
		
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();  

			conn.setConnectTimeout(60*1000);//ms
			conn.setReadTimeout(1000*60*1000);//ms==>100minute
			conn.setRequestMethod("GET"); 
//			conn.connect();
//			System.out.println("--------set timeout----------"); 
			System.out.println("--------change3----------"); 
			if(conn.getResponseCode()!=200){
				System.out.println("--------ResponseCode----------"+conn.getResponseCode());  
//				throw new RuntimeException("请求url失败-->ResponseCode:"+conn.getResponseCode());
				if(null!=conn){
					conn.disconnect();
				}
				return 0;
			}
			//
			String localDir = sdcardDir + docPathFile;// 文件存储路径 
			File file = new File(localDir);
			input = conn.getInputStream();
			if (file.exists()) { 
				Log.d(TAG, "File exists: "+localDir);
				retFlag= 2;
			} else {  
				String[] dirArray=docPathFile.split("/");
				String newLocalDir=sdcardDir;
				for(int i=0;i<dirArray.length-1;i++){
					String dir=dirArray[i];
					if(i==0){
						newLocalDir+=dir; 
					}else{ 
						newLocalDir+="/"+dir;
					}
					File fileDir=new File(newLocalDir);
					if(!fileDir.exists()){
						Log.d(TAG, "File dir created: "+newLocalDir);
						fileDir.mkdir();
					} 
				}//end of for 
				Log.d(TAG, "localDir is : "+localDir); 
				file.createNewFile();// 新建文件
				output = new FileOutputStream(file);  
				long totalRead=0; 
				int bytesRead = 0;
				byte[] dataRead = new byte[1024*10];

//				Log.d(TAG, "下载中.");
				int index=0;
				// 
//				byteArrayOutputStream=ByteArrayOutputStream.toBufferedInputStream(input);
				//写入文件
//				while((bytesRead = byteArrayOutputStream.read(dataRead))!=-1){
				while((bytesRead = input.read(dataRead))!=-1){
					index++;
					output.write(dataRead, 0, bytesRead);
					totalRead += bytesRead;
					if(index%1000==0){
						Log.d(TAG,System.currentTimeMillis()+ "Download-"+(totalRead/(1024))+" KB");
						httpDownloadListener.notifyDownloadSize(totalRead/1024);
					}
				}//end of while
				httpDownloadListener.notifyDownloadSize(totalRead/1024);
				int totalReadInKB = (int) (totalRead / 1024);
				Log.d(TAG, "[File("+docPathFile+") Download] totalReadInKB--->"+totalReadInKB+"  KB"); 
				retFlag= 1;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			retFlag= 0;
			return retFlag;
		} catch (IOException e) {
			e.printStackTrace();
			retFlag= 0;
			return retFlag;
		} finally {
			try {
				if(null!=output){
					output.flush();
					output.close();
				}
				if(null!=input){
					input.close();
				}
				if(null!=conn){
					conn.disconnect();
				}
			} catch (IOException e) { 
				e.printStackTrace();
			}  
		}
		Log.d(TAG, "downloadFile end");
		retFlag= 1;
		return retFlag;
	}//end of downloadFile 
	

 
	 

}//end of HttpDownloadSupport
