package com.broadsoft.xmcommon.androidsqllite;

import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteOpenHelperSupport extends SQLiteOpenHelper {

	private final static String TAG = "SQLiteOpenHelperSupport";

	public static final int DATABASE_VERSION = 22;
	// Database Name
	public static final String DATABASE_NAME = "xmeeting";

	public SQLiteOpenHelperSupport(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate begin");
		// 

		//
		db.execSQL(PadInfoConstant.CREATE_PADINFO_TABLE);
		db.execSQL(CompanyInfoConstant.CREATE_COMPANYINFO_TABLE);
		db.execSQL(DownloadInfoConstant.CREATE_DOWNLOADINFO_TABLE);
		Log.d(TAG, "onCreate end");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onUpgrade begin");
		// Drop older table if existed
		db.execSQL(PadInfoConstant.UPGRADE_PADINFO_TABLE);
		db.execSQL(CompanyInfoConstant.UPGRADE_COMPANYINFO_TABLE);
		db.execSQL(DownloadInfoConstant.UPGRADE_DOWNLOADINFO_TABLE);
		// Create tables again
		onCreate(db);
		Log.d(TAG, "onUpgrade end");

	}

}
