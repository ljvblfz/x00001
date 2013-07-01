package com.broadsoft.xmcommon.androidhttp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

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
	public static int downloadFile(String serveriport,String docPathFile) { 
		Log.d(TAG, "downloadFile begin, docPathFile is: "+docPathFile); 
		if(null==docPathFile||"".equals(docPathFile.trim())){
			return 4;
		}
		String urlStr = "http://" + serveriport + docPathFile;  
		Log.d(TAG, "Download the file url ---->"+urlStr);
		OutputStream output = null;
		int retFlag=0;
 
		String sdcardDir = SDCardSupport.getSDCardDirectory(); 
		
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
			String localDir = sdcardDir + docPathFile;// 文件存储路径 
			File file = new File(localDir);
			InputStream input = conn.getInputStream();
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
				} 
				Log.d(TAG, "localDir is : "+localDir); 
				file.createNewFile();// 新建文件
				output = new FileOutputStream(file); 
				writeFile(input,output,docPathFile);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			retFlag= 0;
		} catch (IOException e) {
			e.printStackTrace();
			retFlag= 0;
		} finally {
			 
		}
		Log.d(TAG, "downloadFile end");
		retFlag= 1;
		return retFlag;
	}//end of downloadFile 
	
	private static  void writeFile(InputStream input,OutputStream output,String fileName){ 
		long totalRead=0;
		try { 
			int bytesRead = 0;
			byte[] data = new byte[1024*10];

			Log.d(TAG, "下载中.");
			int index=0;
			while((bytesRead = input.read(data))!=-1){
				index++;
				output.write(data, 0, bytesRead);
				totalRead += bytesRead;
				if(index%1000==0){
					Log.d(TAG,System.currentTimeMillis()+ "-"+(bytesRead/(1024*1024))+" M");
				}
			}
			int totalReadInKB = (int) (totalRead / 1024);
			Log.d(TAG, "[File("+fileName+") Download] totalReadInKB--->"+totalReadInKB+"  KB");
		} catch (FileNotFoundException e) { 
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}finally{
			try {
				output.flush();
				output.close();
				input.close();
			} catch (IOException e) { 
				e.printStackTrace();
			} 
		} 
	}

}//end of HttpDownloadSupport
