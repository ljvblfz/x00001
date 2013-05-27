package com.broadsoft.xmcommon.appsupport;

import com.broadsoft.xmcommon.androiddao.DaoHolder;
import com.broadsoft.xmcommon.androiddao.DownloadInfoEntity;

public class DemoDataInit {
	
	
	
	
	public static void init(String jsonData){

		DownloadInfoEntity downloadInfoEntityParam=new DownloadInfoEntity();
		downloadInfoEntityParam.setMeetingId("000000000XMMEETINGINFO13041820484043");
		downloadInfoEntityParam.setMeetingName("电力公司技术交流会");
		downloadInfoEntityParam.setMemberId("0000000XMPERSONNELINFO13052120484283");
		downloadInfoEntityParam.setMemberDisplayName("测试人员");
		downloadInfoEntityParam.setSeatno("A9001_01");
		downloadInfoEntityParam.setServiceMemberId("0000000XMPERSONNELINFO13041820484041");
		downloadInfoEntityParam.setServiceMemberDisplayName("李天一");
		downloadInfoEntityParam.setStatus("1"); 
		downloadInfoEntityParam.setJsonData(jsonData); 
		saveDBForDownloadInfo(downloadInfoEntityParam);
		
		
		
	}
	
	public static void saveDBForDownloadInfo(DownloadInfoEntity downloadInfoEntityParam){
		String meetingId=downloadInfoEntityParam.getMeetingId();
		DownloadInfoEntity downloadInfoEntity=DaoHolder.getInstance().getDownloadInfoDao().findByMeetingId(meetingId);
		if(null==downloadInfoEntity){//insert
			DaoHolder.getInstance().getDownloadInfoDao().add(downloadInfoEntityParam);
		}else{//update
			downloadInfoEntityParam.setGuid(downloadInfoEntity.getGuid());
		}
	}

}
