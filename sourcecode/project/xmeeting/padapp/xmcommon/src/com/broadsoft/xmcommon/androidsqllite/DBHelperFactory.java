package com.broadsoft.xmcommon.androidsqllite;

import android.content.Context;

public class DBHelperFactory {
	
	
	public static PadInfoSQLiteOpenHelper createPadInfoDbHelper(Context context){
		PadInfoSQLiteOpenHelper dbHelper=new PadInfoSQLiteOpenHelper(context);
		return dbHelper;
	}
	
	
	public static DownloadInfoSQLiteOpenHelper createDownloadInfoDbHelper(Context context){
		DownloadInfoSQLiteOpenHelper dbHelper=new DownloadInfoSQLiteOpenHelper(context);
		return dbHelper;
	}

}
