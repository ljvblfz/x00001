package com.founder.common.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PhotoGeter {

	public static Bitmap getPhoto(String sUrl)
	{
		 Bitmap bitmap = null;
		 try{
	         URL  url = new URL(sUrl);
	         HttpURLConnection conn  = (HttpURLConnection)url.openConnection();
	         conn.setDoInput(true);
	         conn.connect();
	         InputStream inputStream=conn.getInputStream();
	         bitmap =  BitmapFactory.decodeStream(inputStream);
	
	     } catch (MalformedURLException e1) {
	         e1.printStackTrace();
	     } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	     }  
		 return bitmap;
		 
	}
}
