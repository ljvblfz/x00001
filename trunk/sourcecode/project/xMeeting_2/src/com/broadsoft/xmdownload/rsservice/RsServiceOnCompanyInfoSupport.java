package com.broadsoft.xmdownload.rsservice;

import java.text.MessageFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androiddao.CompanyInfoEntity;
import com.broadsoft.xmcommon.androiddao.DaoHolder;
import com.broadsoft.xmcommon.androidhttp.HttpDownloadSupport;
import com.broadsoft.xmcommon.androidhttp.HttpRestSupport;

public class RsServiceOnCompanyInfoSupport {
	private static final String TAG="RsServiceOnCompanyInfoSupport"; 
	
	public static void download(){
		Log.d(TAG, "download begin");
		// 
		String serveriport=DomAppConfigFactory.getAppConfig().getServeripport(); 
		String[] arguments={serveriport}; 
		String rspathCompanyInfoResult = MessageFormat.format(rspathPadInfo,arguments); 
		Log.d(TAG, "rspathCompanyInfoResult is : "+rspathCompanyInfoResult);  
		Thread thread=new Thread(new DownloadCompanyInfoRunnable(rspathCompanyInfoResult));
		thread.start();
		
		
		
		Log.d(TAG, "download end"); 
	}//end of download 
	
	
	


	private final static String rspathPadInfo="http://{0}/xmeeting/rs/open/companyinfo/download";
 
	
	
 

}



class  DownloadCompanyInfoRunnable implements Runnable{
	private final static String TAG="DownloadCompanyInfoRunnable";
	private String rspathCompanyInfoResult;  
	
	public DownloadCompanyInfoRunnable(String rspathCompanyInfoResult){ 
		this.rspathCompanyInfoResult=rspathCompanyInfoResult; 
	}

	
	
	@Override
	public void run() { 
		try {
			JSONObject jsonCompanyInfo=HttpRestSupport.getByHttpClientWithGzip(rspathCompanyInfoResult); 
			JSONObject jsonData=jsonCompanyInfo.getJSONObject("jsonData"); 
			CompanyInfoEntity companyInfoEntity=createCompanyInfoEntity(jsonData);
			//更新数据库
			saveDBForCompanyInfo(companyInfoEntity);
			//下载附件
			saveFileForDownloadInfo(jsonData);
		} catch (Exception e) { 
			e.printStackTrace();
		} 
	 
	}//end of run
	
	
	
	
	public CompanyInfoEntity createCompanyInfoEntity(JSONObject jsonData) throws JSONException{
		CompanyInfoEntity companyInfoEntity=new CompanyInfoEntity();
		JSONObject companyinfo=jsonData.getJSONObject("companyinfo");
		String xmciCompanyName=companyinfo.getString("xmciCompanyName");
		String xmciGuid=companyinfo.getString("xmciGuid");
		companyInfoEntity.setCompanyCode(xmciGuid);
		companyInfoEntity.setCompanyName(xmciCompanyName);
		companyInfoEntity.setJsonData(jsonData.toString()); 
		return companyInfoEntity;
	}
	
	public void saveDBForCompanyInfo(CompanyInfoEntity companyInfoEntityParam){
		CompanyInfoEntity companyInfoEntity=DaoHolder.getInstance().getCompanyInfoDao().uniqueOne();
		if(null==companyInfoEntity){//insert
			DaoHolder.getInstance().getCompanyInfoDao().add(companyInfoEntityParam);
		}else{//update
			companyInfoEntityParam.setGuid(companyInfoEntity.getGuid());
			DaoHolder.getInstance().getCompanyInfoDao().update(companyInfoEntityParam);
		}
	}
	
	
	public void saveFileForDownloadInfo(JSONObject jsonData) throws JSONException{
		String serveriport=DomAppConfigFactory.getAppConfig().getServeripport();
		//组织架构图片 
		JSONObject jsonOrginfo=jsonData.getJSONObject("orginfo"); 
		String xmciAttachmentOrginfo=jsonOrginfo.getString("xmciAttachment");
		HttpDownloadSupport.downloadFile(serveriport, xmciAttachmentOrginfo); 
		//领导风采图片 
		JSONObject jsonLeaderinfo=jsonData.getJSONObject("leaderinfo");
		String xmciAttachmentLeaderinfo=jsonOrginfo.getString("xmciAttachment");
		HttpDownloadSupport.downloadFile(serveriport, xmciAttachmentLeaderinfo);  
	}//end of saveFileForDownloadInfo
	
	
	
 
	
}//end of DownloadMeetingInfoRunnable
