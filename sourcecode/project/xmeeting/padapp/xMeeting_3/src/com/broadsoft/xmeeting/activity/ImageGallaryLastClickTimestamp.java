package com.broadsoft.xmeeting.activity;

public class ImageGallaryLastClickTimestamp {
	
	private long lastSelectedTimestamp=System.currentTimeMillis();
	
	private ImageGallaryLastClickTimestamp(){};
	
	private static ImageGallaryLastClickTimestamp instance=new ImageGallaryLastClickTimestamp();
	
	public static ImageGallaryLastClickTimestamp getInstance(){
		return instance;
	}

	public long getLastSelectedTimestamp() {
		return lastSelectedTimestamp;
	}

	public void setLastSelectedTimestamp(long lastSelectedTimestamp) {
		this.lastSelectedTimestamp = lastSelectedTimestamp;
	}

	
	
}
