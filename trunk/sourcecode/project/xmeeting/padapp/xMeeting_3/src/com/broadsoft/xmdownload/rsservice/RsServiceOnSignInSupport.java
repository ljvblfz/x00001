package com.broadsoft.xmdownload.rsservice;
 

import java.text.MessageFormat;

import org.json.JSONObject;

import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androidhttp.HttpRestSupport;


public class RsServiceOnSignInSupport {
	
	private final static String TAG="RsServiceOnSignInSupport";
	
	private static RsServiceOnSignInSupport rsServiceOnSignInSupport=new RsServiceOnSignInSupport();
	
	private  RsServiceOnSignInSupport(){
		
	}
	
	public static RsServiceOnSignInSupport getInstance(){
		return rsServiceOnSignInSupport;
	} 
	
	
	

	private final static String rspathSignIn="http://{0}/xmeeting/rs/open/signin/xmpiGuid/{1}/xmmiGuid/{2}";
	
	
	
	public void signIn(String memberId,String meetingId){ 
		String serveriport=DomAppConfigFactory.getAppConfig().getServeripport();
		String[] arguments={serveriport,memberId,meetingId};
		
		String rspathSignInResult = MessageFormat.format(rspathSignIn,arguments);
		Log.d(TAG, "rspathSignInResult is : "+rspathSignInResult);
		
		Thread thread=new Thread(new SingInRunnable(rspathSignInResult));
		thread.start(); 
		
	}//end of signIn
	
	
	
	
	
	
	
	
}


class  SingInRunnable implements Runnable{
	private final static String TAG="SingInRunnable";
	private String rspathSignInResult;
	public SingInRunnable(String rspathSignInResult){
		this.rspathSignInResult=rspathSignInResult;
	}

	@Override
	public void run() { 
		try {
			JSONObject json=HttpRestSupport.getByHttpClientWithGzip(rspathSignInResult);
		} catch (Exception e) { 
			e.printStackTrace();
			Log.d(TAG, "[signIn]Raise the error is : "+e.getMessage());
			
		}
	}
	
}
