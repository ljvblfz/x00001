package com.broadsoft.xmcommon.androidsqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class BaseSQLiteOpenHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	// Database Name
	public static final String DATABASE_NAME = "xmeeting";
	
	
	public BaseSQLiteOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version); 
	}
	 

	 
}
