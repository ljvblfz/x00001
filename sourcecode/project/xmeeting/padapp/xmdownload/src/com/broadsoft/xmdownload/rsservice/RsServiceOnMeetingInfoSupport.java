package com.broadsoft.xmdownload.rsservice;

import java.io.File;
import java.text.MessageFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.broadsoft.xmcommon.androidconfig.DomAppConfigFactory;
import com.broadsoft.xmcommon.androiddao.DaoHolder;
import com.broadsoft.xmcommon.androiddao.DownloadInfoEntity;
import com.broadsoft.xmcommon.androidhttp.HttpDownloadSupport;
import com.broadsoft.xmcommon.androidhttp.HttpRestSupport;
import com.broadsoft.xmcommon.androidsdcard.SDCardSupport;
import com.broadsoft.xmcommon.androidutil.AndroidIdSupport;

public class RsServiceOnMeetingInfoSupport {
	private static final String TAG="RsServiceOnMeetingInfoSupport"; 
	
	public static void download(String meetingId){
		Log.d(TAG, String.format("download begin, meetingId:  %s", meetingId));
		// 
		String serveriport=DomAppConfigFactory.getAppConfig().getServeripport();
		String[] arguments={serveriport,meetingId};
		
		String rspathMeetingInfoResult = MessageFormat.format(rspathMeetingInfo,arguments);
		String rspathMeetingPersonnelResult = MessageFormat.format(rspathMeetingPersonnel,arguments);
		Log.d(TAG, "rspathMeetingInfoResult is : "+rspathMeetingInfoResult);
		Log.d(TAG, "rspathMeetingPersonnelResult  is : "+rspathMeetingPersonnelResult);
		
		Thread thread=new Thread(new DownloadMeetingInfoRunnable(meetingId,rspathMeetingInfoResult,rspathMeetingPersonnelResult));
		thread.start();
		
		Log.d(TAG, "download end"); 
	}//end of download
	
	
	
	
//	public static String getServeripport(){
//		String serveriport="172.29.135.151:8080";
//		return serveriport;
//	}
	
	


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
			
			JSONObject jsonDataMeetingInfo=jsonMeetingInfo.getJSONObject("jsonData");
			JSONObject jsonDataPersonnelInfo=jsonMeetingPersonnel.getJSONObject("jsonData");
			Log.d(TAG, "[run]jsonDataMeetingInfo--->"+jsonDataMeetingInfo);
			Log.d(TAG, "[run]jsonDataPersonnelInfo--->"+jsonDataPersonnelInfo);
			DownloadInfoEntity downloadInfoEntityParam=this.createDownloadInfoEntity(jsonDataMeetingInfo,jsonDataPersonnelInfo );
			downloadInfoEntityParam.setStatus("0");
			this.saveDBForDownloadInfo(downloadInfoEntityParam); 
			//下载文件
			cleanFileForDownloadInfo(jsonDataMeetingInfo);
			saveFileForDownloadInfo(jsonDataMeetingInfo); 
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
		
		JSONObject jsoninfo=new JSONObject();
		jsoninfo.put("meetingInfo", jsonMeetingInfo);
		jsoninfo.put("personnelInfo", jsonMeetingPersonnel);
		downloadInfoEntityParam.setJsonData(jsoninfo.toString());
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
			DaoHolder.getInstance().getDownloadInfoDao().update(downloadInfoEntityParam);
		}
	}
	
	
	
	
	
	
	
	public void cleanFileForDownloadInfo(JSONObject jsonMeetingInfo) throws JSONException{
		JSONObject jsonObjectMeetingInfo=jsonMeetingInfo.getJSONObject("xmMeetingInfo");
		String meetingId=jsonObjectMeetingInfo.getString("xmmiGuid"); 
		String sdcardDir = SDCardSupport.getSDCardDirectory(); 
		String localDir=sdcardDir+"/upload/xmeeting/"+meetingId;  
		Log.d(TAG, "clean the  localDir is : "+localDir);
		File fileDir=new File(localDir);
		deleteDirectory(fileDir); 
	}

	
	public void deleteDirectory(File dir) { 
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				File child = new File(dir, children[i]);
				if (child.isDirectory()) {
					deleteDirectory(child);
					child.delete();
				} else {
					child.delete(); 
				}
			}//end of for
			dir.delete();
		}//end of if
	}
	
	public void saveFileForDownloadInfo(JSONObject jsonMeetingInfo) throws JSONException{
		String serveriport=DomAppConfigFactory.getAppConfig().getServeripport();
		//会议文稿
		JSONArray listOfXmMeetingDocument=jsonMeetingInfo.getJSONArray("listOfXmMeetingDocument"); 
		for(int i=0;i<listOfXmMeetingDocument.length();i++){
			JSONObject docJson=listOfXmMeetingDocument.getJSONObject(i);
			String docFile=docJson.getString("xmmdFile");
			HttpDownloadSupport.downloadFile(serveriport, docFile);
		}
		
		//会议图片
		JSONArray listOfXmMeetingPicture=jsonMeetingInfo.getJSONArray("listOfXmMeetingPicture"); 
		for(int i=0;i<listOfXmMeetingPicture.length();i++){
			JSONObject picJSONObject=listOfXmMeetingPicture.getJSONObject(i);
			JSONArray listOfXmMeetingPictureDetail=picJSONObject.getJSONArray("listOfXmMeetingPictureDetail");
			for(int j=0;j<listOfXmMeetingPictureDetail.length();j++){
				JSONObject picJson=listOfXmMeetingPictureDetail.getJSONObject(j);
				String picFile=picJson.getString("xmmpicImageFile");
				HttpDownloadSupport.downloadFile(serveriport, picFile); 
			}//end of for j 
		}
		
		//会议视频
		JSONArray listOfXmMeetingVideo=jsonMeetingInfo.getJSONArray("listOfXmMeetingVideo"); 
		for(int i=0;i<listOfXmMeetingVideo.length();i++){
			JSONObject videoJson=listOfXmMeetingVideo.getJSONObject(i);
			String videoFile=videoJson.getString("xmmvFile");
			HttpDownloadSupport.downloadFile(serveriport, videoFile);  
		}
		
	}
	
}//end of DownloadMeetingInfoRunnable
