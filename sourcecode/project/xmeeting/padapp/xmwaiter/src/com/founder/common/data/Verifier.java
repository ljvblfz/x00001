package com.founder.common.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;

public class Verifier {
 
	public static AuResult Authenticate (Context ctx, String sKey, AssetManager assetManager) throws JSONException
	{
		JSONObject obj = JsonBuilder.getNetAuJsonObject("license", sKey, assetManager);
		String legal = obj.getString("legal");
		String message = obj.getString("message");
		AuResult auResult = new AuResult();
		auResult.setLegal(Boolean.parseBoolean(legal));
		auResult.setMessage(message);
		return auResult;
	}
	public static boolean AuthenticateLocalTime()
	{
		  final Calendar cNow = Calendar.getInstance();
		  Calendar cLimit = (Calendar) cNow.clone();
		  cLimit.set(2012, 10, 11);
		  if (cNow.compareTo(cLimit) == 1)
			  return false;
		  else
			  return true; 
	}
	
	public static String getLocalKey(Context ctx)
	{
		SharedPreferences sharedata = ctx.getSharedPreferences("SP", 0); 
	    String sKey = sharedata.getString("key", null); 
	    return sKey;
	}
	
	public static void setLocalKey(Context ctx, String sKey)
	{
	    Editor sharedata = ctx.getSharedPreferences("SP", 0).edit();  
        sharedata.putString("key", sKey);  
        sharedata.commit();  
	}
	
	public static boolean AuthenticateUseTime(Context ctx) throws ParseException
	{
	    SharedPreferences sharedata = ctx.getSharedPreferences("SP", 0); 
	    String installDate = sharedata.getString("date", null); 
	    if (installDate != null)
	    {			
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
			Date date =sdf.parse(installDate);
			Calendar cInstall = Calendar.getInstance();
			cInstall.setTime(date); 
			
			final Calendar cNow = Calendar.getInstance();
			if (cNow.compareTo(cInstall) > 0)
			{
				long t1 = cNow.getTimeInMillis();
				long t2 = cInstall.getTimeInMillis();
				long day = (t1-t2) / 1000 / 60 /60 / 24 / 7;
				if (day < 1)
					return true;
				else
					return false;
			}
	    }
	    else
	    {	    	
	    	 Calendar cNow = Calendar.getInstance();
	    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
	    	 String dateStr = sdf.format(cNow.getTime()); 
	    	 SharedPreferences sp = ctx.getSharedPreferences("SP", 0);
	         //´æÈëÊý¾Ý
	         Editor editor = sp.edit();
	         editor.putString("date", dateStr);
	         editor.commit();
	         return true;
	    }
		
       return false;
	}
	
}
