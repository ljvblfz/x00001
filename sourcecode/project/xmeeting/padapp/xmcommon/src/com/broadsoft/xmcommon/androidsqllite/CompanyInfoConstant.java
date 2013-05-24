package com.broadsoft.xmcommon.androidsqllite;



/**
 * http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/
 * http://www.vogella.com/articles/AndroidSQLite/article.html
 * @author lu.zhen
 *
 */
public class CompanyInfoConstant  {
	private static final String TAG = "CompanyInfoConstant";


    // Contacts table name
	public final static  String TABLE_COMPANYINFO = "COMPANYINFO";

	public final static String COLUMN_GUID="GUID";
	public final static String COLUMN_COMPANY_NAME="COMPANY_NAME";
	public final static String COLUMN_COMPANY_CODE="COMPANY_CODE";
	public final static String COLUMN_JSON_DATA="JSON_DATA";

	public final static String CREATE_COMPANYINFO_TABLE = "CREATE TABLE " + TABLE_COMPANYINFO + "("
               + COLUMN_GUID + " TEXT PRIMARY KEY," + COLUMN_COMPANY_NAME + " TEXT, "
               + COLUMN_COMPANY_CODE + " TEXT, " +COLUMN_JSON_DATA + " TEXT " + ")";
	
	
	public final static String UPGRADE_COMPANYINFO_TABLE = "DROP TABLE IF EXISTS " + TABLE_COMPANYINFO;
    
 

}
