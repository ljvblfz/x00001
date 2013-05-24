package com.broadsoft.xmcommon.androidsqllite;



/**
 * http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
 * http://www.vogella.com/articles/AndroidSQLite/article.html
 * @author lu.zhen
 *
 */
public class PadInfoConstant  {
	private static final String TAG = "PadInfoConstant";


    // Contacts table name
	public final static  String TABLE_PADINFO = "PADINFO";

	public final static String COLUMN_GUID="GUID";
	public final static String COLUMN_ANDROID_ID="ANDROID_ID";
	public final static String COLUMN_ASSET_CODE="ASSET_CODE";
	public final static String COLUMN_JSON_DATA="JSON_DATA";

	public final static String CREATE_PADINFO_TABLE = "CREATE TABLE " + TABLE_PADINFO + "("
               + COLUMN_GUID + " TEXT PRIMARY KEY," + COLUMN_ANDROID_ID + " TEXT, "
               + COLUMN_ASSET_CODE + " TEXT, " +COLUMN_JSON_DATA + " TEXT " + ")";
	
	
	public final static String UPGRADE_PADINFO_TABLE = "DROP TABLE IF EXISTS " + TABLE_PADINFO;
    
 

}
