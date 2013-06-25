package com.broadsoft.xmdownload;

import android.widget.TextView;

public class ViewHolder {

	
	private ViewHolder(){}
	public static ViewHolder viewHolder=new ViewHolder();
	
	public static ViewHolder getInstance(){
		return viewHolder;
	}
	
	
	//
	private TextView textViewDownloadStatus;

	public TextView getTextViewDownloadStatus() {
		return textViewDownloadStatus;
	}

	public void setTextViewDownloadStatus(TextView textViewDownloadStatus) {
		this.textViewDownloadStatus = textViewDownloadStatus;
	}
	
	
}
