package com.broadsoft.xmdownload.rsservice;

import java.text.MessageFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androiddao.DaoHolder;
import com.broadsoft.xmcommon.androiddao.PadInfoEntity;
import com.broadsoft.xmcommon.androidhttp.HttpRestSupport;
import com.broadsoft.xmcommon.androidutil.AndroidIdSupport;
import com.broadsoft.xmdownload.DownloadUIHandler;


/**
 * 
 * @author lu.zhen
 *
 */
public class RsServiceOnPadInfoSupport {
	
	private static final String TAG="RsServiceOnPadInfoSupport"; 
	
	public static void download(){
		Log.d(TAG, "download begin");
		// 
		String serveriport=DomAppConfigFactory.getAppConfig().getServeripport();
		String padId=AndroidIdSupport.getAndroidID();
		String[] arguments={serveriport,padId};
		
		String rspathPadInfoResult = MessageFormat.format(rspathPadInfo,arguments); 
		Log.d(TAG, "rspathPadInfoResult is : "+rspathPadInfoResult);  
		Thread thread=new Thread(new DownloadPadInfoRunnable(rspathPadInfoResult));
		thread.start();
		
		Log.d(TAG, "download end"); 
	}//end of download  


	private final static String rspathPadInfo="http://{0}/xmeeting/rs/open/padinfo/download/xmpdDeviceId/{1}"; 

}//end of RsServiceOnPadInfoSupport



class  DownloadPadInfoRunnable implements Runnable{
	
	
	private final static String TAG="DownloadPadInfoRunnable";
	private String rspathPadInfoResult;  
	
	public DownloadPadInfoRunnable(String rspathPadInfoResult){ 
		this.rspathPadInfoResult=rspathPadInfoResult; 
	}

	
	
	@Override
	public void run() { 
		Log.d(TAG, "[run]begin.");
		DownloadUIHandler.getInstance().sendDownloadPadInfoOnBegin();
		try {
			JSONObject jsonPadInfo=HttpRestSupport.getByHttpClientWithGzip(rspathPadInfoResult);
			PadInfoEntity padInfoEntity=createPadInfoEntity(jsonPadInfo);
			//更新数据库
			saveDBForPadInfo(padInfoEntity);
		} catch (Exception e) { 
			e.printStackTrace();
		} 
		DownloadUIHandler.getInstance().sendDownloadPadInfoOnEnd();
		Log.d(TAG, "[run]end.");
	 
	}
	

	public PadInfoEntity createPadInfoEntity(JSONObject jsonPadInfo) throws JSONException{
		PadInfoEntity padInfoEntity=new PadInfoEntity();
		JSONObject jsonData= jsonPadInfo.getJSONObject("jsonData");
		JSONObject jsonXmPadDevice=jsonData.getJSONObject("xmPadDevice");
		String assetCode=jsonXmPadDevice.getString("xmpdCode");
		String androidId=jsonXmPadDevice.getString("xmpdDeviceId");  
		padInfoEntity.setAndroidId(androidId);
		padInfoEntity.setAssetCode(assetCode);
		padInfoEntity.setJsonData(jsonXmPadDevice.toString()); 
		return padInfoEntity;
	}
	
	public void saveDBForPadInfo(PadInfoEntity padInfoEntityParam){
		PadInfoEntity padInfoEntity=DaoHolder.getInstance().getPadInfoDao().uniqueOne();
		if(null==padInfoEntity){//insert
			DaoHolder.getInstance().getPadInfoDao().add(padInfoEntityParam);
		}else{//update
			padInfoEntityParam.setGuid(padInfoEntity.getGuid());
			DaoHolder.getInstance().getPadInfoDao().update(padInfoEntityParam);
		}
	} 
 
	
}//end of DownloadPadInfoRunnable
