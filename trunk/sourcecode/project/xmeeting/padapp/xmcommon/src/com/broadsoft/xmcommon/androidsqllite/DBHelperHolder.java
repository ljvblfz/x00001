package com.broadsoft.xmcommon.androidsqllite;

import android.content.Context;

public class DBHelperHolder {
	
	
	private PadInfoSQLiteOpenHelper padInfoSQLiteOpenHelper;
	private DownloadInfoSQLiteOpenHelper downloadInfoSQLiteOpenHelper;
	
	
	private static DBHelperHolder dbHelperHolder=new DBHelperHolder();
	private static DBHelperHolder getInstance(){
		return dbHelperHolder;
	}
	
	public void init(Context context){
		this.padInfoSQLiteOpenHelper=new PadInfoSQLiteOpenHelper(context);
		this.downloadInfoSQLiteOpenHelper=new DownloadInfoSQLiteOpenHelper(context);
		
	}
	
	public PadInfoSQLiteOpenHelper getPadInfoSQLiteOpenHelper(){ 
		return padInfoSQLiteOpenHelper;
	}
	
	
	public DownloadInfoSQLiteOpenHelper getDownloadInfoDbHelper(){ 
		return downloadInfoSQLiteOpenHelper;
	}

}
