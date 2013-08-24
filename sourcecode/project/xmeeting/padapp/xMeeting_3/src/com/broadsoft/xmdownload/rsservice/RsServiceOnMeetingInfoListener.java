package com.broadsoft.xmdownload.rsservice;

import android.util.Log;

import com.broadsoft.xmcommon.androidhttp.HttpDownloadListener;
import com.broadsoft.xmeeting.uihandler.DownloadByHandUIHandler;
import com.broadsoft.xmeeting.uihandler.DownloadByWsUIHandler;

public class RsServiceOnMeetingInfoListener implements HttpDownloadListener {

	private static final String TAG = "RsServiceOnMeetingInfoListener";
	private long sizekb = 0;
	private int type;

	public RsServiceOnMeetingInfoListener(int type) {
		this.type = type;
	}

	@Override
	public void notifyDownloadSize(long kb) {
		sizekb += kb;
		Log.d(TAG, "[notifyDownloadSize]sizekb=" + sizekb);
		updateProgress(sizekb);

	}

	private void updateProgress(long kb) {
		if (type == RsServiceOnMeetingInfoSupport.WS_TYPE_DEFAULT) {
			DownloadByWsUIHandler.getInstance()
					.sendDownloadMeetingMessageOnProgress(kb);
		} else {
			DownloadByHandUIHandler.getInstance()
					.sendDownloadMessageOnProgress(kb);
		} 
	}//end of updateProgress
}//end of RsServiceOnMeetingInfoListener
