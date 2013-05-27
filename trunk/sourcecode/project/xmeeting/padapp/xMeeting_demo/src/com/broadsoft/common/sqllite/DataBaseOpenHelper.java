package com.broadsoft.common.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 
 * @author lu.zhen
 *
 */
public class DataBaseOpenHelper extends SQLiteOpenHelper {

	public static final String DBNAME = "xmeetingtest";
	public static final int version = 1;

	public DataBaseOpenHelper(Context context) {
		super(context, DBNAME, null, version);// 数据库版本如有发生变更，就会调用onUpgrade方法
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE person (personid integer primary key autoincrement, name varchar(20), age INTEGER)");
	}

	/**
	 * 
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS person");
		onCreate(db);
	}

}