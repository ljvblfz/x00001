package com.broadsoft.xmdownload.rsservice;

import java.text.MessageFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.broadsoft.xmcommon.androiddao.DaoHolder;
import com.broadsoft.xmcommon.androiddao.DownloadInfoEntity;
import com.broadsoft.xmcommon.androidhttp.HttpRestSupport;
import com.broadsoft.xmcommon.androidutil.AndroidIdSupport;

public class RsServiceSupport {
	private static final String TAG="RsServiceSupport";
	
	
	public static void download(String meetingId){
		Log.d(TAG, String.format("download begin, meetingId:  %s", meetingId));
		// 
		String serveriport="172.29.135.151:8080";
		String[] arguments={serveriport,meetingId};
		
		String rspathMeetingInfoResult = MessageFormat.format(rspathMeetingInfo,arguments);
		String rspathMeetingPersonnelResult = MessageFormat.format(rspathMeetingPersonnel,arguments);
		Log.d(TAG, "rspathMeetingInfoResult is : "+rspathMeetingInfoResult);
		Log.d(TAG, "rspathMeetingPersonnelResult  is : "+rspathMeetingPersonnelResult);
		
		Thread thread=new Thread(new DownloadMeetingInfoRunnable(meetingId,rspathMeetingInfoResult,rspathMeetingPersonnelResult));
		thread.start();
		
		Log.d(TAG, "download end"); 
	}//end of download
	
	
	
	


	private final static String rspathMeetingPersonnel="http://{0}/xmeeting/rs/open/meetingpersonnel/download/xmmiGuid/{1}";

	private final static String rspathMeetingInfo="http://{0}/xmeeting/rs/open/meetingallinfo/download/xmmiGuid/{1}";
	
	
 

}



class  DownloadMeetingInfoRunnable implements Runnable{
	private final static String TAG="DownloadMeetingInfoRunnable";
	private String rspathMeetingInfoResult;
	private String rspathMeetingPersonnelResult;
	private String meetingId;
	public DownloadMeetingInfoRunnable(String meetingId,String rspathMeetingInfoResult,String rspathMeetingPersonnelResult){
		this.meetingId=meetingId;
		this.rspathMeetingInfoResult=rspathMeetingInfoResult;
		this.rspathMeetingPersonnelResult=rspathMeetingPersonnelResult;
	}

	
	
	@Override
	public void run() { 
		try {
			JSONObject jsonMeetingInfo=HttpRestSupport.getByHttpClientWithGzip(rspathMeetingInfoResult); 
			JSONObject jsonMeetingPersonnel=HttpRestSupport.getByHttpClientWithGzip(rspathMeetingPersonnelResult); 
			//更新数据库
			Log.d(TAG, "[run]jsonMeetingInfo--->"+jsonMeetingInfo.getJSONObject("jsonData"));
			Log.d(TAG, "[run]jsonMeetingPersonnel--->"+jsonMeetingPersonnel.getJSONObject("jsonData"));
			DownloadInfoEntity downloadInfoEntityParam=this.createDownloadInfoEntity(jsonMeetingInfo.getJSONObject("jsonData"), jsonMeetingPersonnel.getJSONObject("jsonData"));
			downloadInfoEntityParam.setStatus("0");
			this.saveDBForDownloadInfo(downloadInfoEntityParam);
		} catch (Exception e) { 
			e.printStackTrace();
			Log.d(TAG, "[run]Raise the error is : "+e.getMessage());
			
		}
	}
	

	public DownloadInfoEntity createDownloadInfoEntity(JSONObject jsonMeetingInfo,JSONObject jsonMeetingPersonnel) throws JSONException{
		DownloadInfoEntity downloadInfoEntityParam=new DownloadInfoEntity();
		JSONObject basicMeetingInfo=jsonMeetingInfo.getJSONObject("xmMeetingInfo");
		downloadInfoEntityParam.setMeetingId(basicMeetingInfo.getString("xmmiGuid"));
		downloadInfoEntityParam.setMeetingName(basicMeetingInfo.getString("xmmiName")); 
		downloadInfoEntityParam.setJsonData(jsonMeetingInfo.toString());
		//pad人员  
		String androidId=AndroidIdSupport.getAndroidID();
		JSONArray listOfXmMeetingPersonnelSeatPadIVO=jsonMeetingPersonnel.getJSONArray("listOfXmMeetingPersonnelSeatPadIVO");
		for(int i=0;i<listOfXmMeetingPersonnelSeatPadIVO.length();i++){
			JSONObject jsonObject=listOfXmMeetingPersonnelSeatPadIVO.getJSONObject(i);
			String xmpdDeviceId=jsonObject.getString("xmpdDeviceId");
			if(androidId.equals(xmpdDeviceId)){
				downloadInfoEntityParam.setMemberId(jsonObject.getString("xmpiGuid"));
				downloadInfoEntityParam.setMemberDisplayName(jsonObject.getString("xmpiName"));
				downloadInfoEntityParam.setSeatno(jsonObject.getString("xmridSeatno"));
				break;
			} 
		}//end of for
		
		//服务人员
		StringBuilder sbServiceMemberId=new StringBuilder();
		StringBuilder sbServiceMemberDisplayName=new StringBuilder();
		JSONArray listOfXmMeetingServicePersonnelIVO=jsonMeetingPersonnel.getJSONArray("listOfXmMeetingServicePersonnelIVO"); 
		for(int i=0;i<listOfXmMeetingServicePersonnelIVO.length();i++){
			JSONObject jsonObject=listOfXmMeetingPersonnelSeatPadIVO.getJSONObject(i);
			String xmpiGuid=jsonObject.getString("xmpiGuid");
			String xmpiName=jsonObject.getString("xmpiName"); 
			if(i>0){
				sbServiceMemberId.append(",");
				sbServiceMemberDisplayName.append(","); 
			}
			sbServiceMemberId.append(xmpiGuid);
			sbServiceMemberDisplayName.append(xmpiName); 
		}//end of for
		downloadInfoEntityParam.setServiceMemberId(sbServiceMemberId.toString());
		downloadInfoEntityParam.setServiceMemberDisplayName(sbServiceMemberDisplayName.toString());
		
		return downloadInfoEntityParam;
	}
	
	
	
	
	public void saveDBForDownloadInfo(DownloadInfoEntity downloadInfoEntityParam){
		DownloadInfoEntity downloadInfoEntity=DaoHolder.getInstance().getDownloadInfoDao().findByMeetingId(meetingId);
		if(null==downloadInfoEntity){//insert
			DaoHolder.getInstance().getDownloadInfoDao().add(downloadInfoEntityParam);
		}else{//update
			downloadInfoEntityParam.setGuid(downloadInfoEntity.getGuid());
		}
	}
	
}//end of DownloadMeetingInfoRunnable
