package com.broadsoft.xmeeting.uihandler;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.broadsoft.xmdownload.adapter.MeetingInfoLVButtonAdapter;


/**
 * 
 * @author lu.zhen
 *
 */
public class DownloadByHandUIHandler extends Handler {
	private final String TAG = "DownloadStatusUIHandler";
	private static DownloadByHandUIHandler downloadUIHandler;
	private Activity act;
	private MeetingInfoLVButtonAdapter meetingInfoLVButtonAdapter;

	private DownloadByHandUIHandler(Activity act,MeetingInfoLVButtonAdapter meetingInfoLVButtonAdapter) {
		this.act = act;
		this.meetingInfoLVButtonAdapter=meetingInfoLVButtonAdapter;
	}
	
	
	public static void init(Activity act,MeetingInfoLVButtonAdapter meetingInfoLVButtonAdapter){
		if(null==downloadUIHandler){
			downloadUIHandler=new DownloadByHandUIHandler(act,meetingInfoLVButtonAdapter);
		} 
	}//end of init 
	
	public static DownloadByHandUIHandler getInstance(){
		return downloadUIHandler;
	}

	@Override
	public void handleMessage(Message msg) {
		Log.d(TAG, "handleMessage begin");
		super.handleMessage(msg);  
		meetingInfoLVButtonAdapter.reload();

		Toast.makeText(act, "会议信息更新完成", Toast.LENGTH_LONG).show();  
		Log.d(TAG, "handleMessage end");
	}


	 
}//end of DownloadByHandUIHandler
