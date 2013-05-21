package com.broadsoft.xmcommon.androidsqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
 * http://www.vogella.com/articles/AndroidSQLite/article.html
 * 
 * @author lu.zhen
 * 
 */
public class DownloadInfoSQLiteOpenHelper extends BaseSQLiteOpenHelper {
	private static final String TAG = "DownloadInfoSQLiteOpenHelper";

	// Contacts table name
	public static final String TABLE_DOWNLOADINFO = "DOWNLOADINFO";

	public final static String COLUMN_GUID = "GUID";
	public final static String COLUMN_MEETING_ID = "MEETING_ID";
	public final static String COLUMN_MEETING_NAME = "MEETING_NAME";
	public final static String COLUMN_MEMBER_ID = "MEMBER_ID";
	public final static String COLUMN_MEMBER_DISPLAY_NAME = "MEMBER_DISPLAY_NAME";
	public final static String COLUMN_SEATNO = "SEATNO"; 
	public final static String COLUMN_SERVICE_MEMBER_ID = "SERVICE_MEMBER_ID";
	public final static String COLUMN_SERVICE_MEMBER_DISPLAY_NAME = "SERVICE_MEMBER_DISPLAY_NAME";
	public final static String COLUMN_STATUS = "STATUS";
	public final static String COLUMN_JSON_DATA = "JSON_DATA";

	public final static String CREATE_DOWNLOADINFO_TABLE = "CREATE TABLE "
			+ TABLE_DOWNLOADINFO + "(" + COLUMN_GUID + " TEXT PRIMARY KEY,"
			+ COLUMN_MEETING_ID + " TEXT," + COLUMN_MEETING_NAME + " TEXT,"
			+ COLUMN_MEMBER_ID + " TEXT," + COLUMN_MEMBER_DISPLAY_NAME
			+ " TEXT," + COLUMN_SEATNO + " TEXT," + COLUMN_SERVICE_MEMBER_ID
			+ " TEXT," + COLUMN_SERVICE_MEMBER_DISPLAY_NAME + " TEXT,"
			+ COLUMN_STATUS + " TEXT," + COLUMN_JSON_DATA + " TEXT" + ")";

	/**
	 * 
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public DownloadInfoSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, CREATE_DOWNLOADINFO_TABLE); 
		db.execSQL(CREATE_DOWNLOADINFO_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOWNLOADINFO);
		// Create tables again
		onCreate(db);

	}

}
