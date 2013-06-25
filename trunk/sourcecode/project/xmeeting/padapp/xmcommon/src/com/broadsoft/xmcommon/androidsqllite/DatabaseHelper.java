package com.broadsoft.xmcommon.androidsqllite;
import java.io.File;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class DatabaseHelper

{
	private static final String TAG  = "DatabaseHelper";
	public static final String  DATABASE_FILE_PATH = "/sdcard";
	public static final String  DATABASE_NAME = "testDatabase";

	private static final String TRACKS_TABLE_CREATE =
		"create table if not exists casecategory (_id INTEGER primary key autoincrement, "
		+ "category_name TEXT not null);";
	
	private SQLiteDatabase database;
	
	public DatabaseHelper()
	{  
		try
		{
//			database = SQLiteDatabase.openDatabase(DATABASE_FILE_PATH + File.separator + DATABASE_NAME, null,SQLiteDatabase.OPEN_READWRITE + SQLiteDatabase.CREATE_IF_NECESSARY);
			database = SQLiteDatabase.openOrCreateDatabase(DATABASE_FILE_PATH + File.separator + DATABASE_NAME, null);
			createTables();
		}

		catch (SQLiteException ex)
		{
//			createTables();
			Log.e(TAG, "error -- " + ex.getMessage(), ex);
		}
		finally
		{
			DatabaseUtil.closeDataBase(database);
		}
	}

	private void createTables()
	{
		try {
			database.execSQL(TRACKS_TABLE_CREATE);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close()
	{
		DatabaseUtil.closeDataBase(database);
	}

	public SQLiteDatabase getReadableDatabase()
	{
		database = SQLiteDatabase.openDatabase(DATABASE_FILE_PATH

				+ File.separator + DATABASE_NAME, null,

				SQLiteDatabase.OPEN_READONLY);
		return database;
	}

	public SQLiteDatabase getWritableDatabase()
	{
		database = SQLiteDatabase.openDatabase(DATABASE_FILE_PATH

				+ File.separator + DATABASE_NAME, null,

				SQLiteDatabase.OPEN_READWRITE);

		return database;
	}
}