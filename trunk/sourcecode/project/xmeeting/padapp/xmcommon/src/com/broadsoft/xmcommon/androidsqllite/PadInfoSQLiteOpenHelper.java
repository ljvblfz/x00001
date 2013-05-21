package com.broadsoft.xmcommon.androidsqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


/**
 * http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
 * http://www.vogella.com/articles/AndroidSQLite/article.html
 * @author lu.zhen
 *
 */
public class PadInfoSQLiteOpenHelper extends BaseSQLiteOpenHelper{
	private static final String TAG = "PadInfoSQLiteOpenHelper";


    // Contacts table name
	public final static  String TABLE_PADINFO = "PADINFO";

	public final static String COLUMN_GUID="GUID";
	public final static String COLUMN_ANDROID_ID="ANDROID_ID";
	public final static String COLUMN_ASSET_CODE="ASSET_CODE";

	public final static String CREATE_PADINFO_TABLE = "CREATE TABLE " + TABLE_PADINFO + "("
               + COLUMN_GUID + " TEXT PRIMARY KEY," + COLUMN_ANDROID_ID + " TEXT,"
               + COLUMN_ASSET_CODE + " TEXT" + ")";
    
	/**
	 * 
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public PadInfoSQLiteOpenHelper(Context context ) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, CREATE_PADINFO_TABLE); 
	    db.execSQL(CREATE_PADINFO_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) { 
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PADINFO); 
		// Create tables again
		onCreate(db);
		
	} 

}
