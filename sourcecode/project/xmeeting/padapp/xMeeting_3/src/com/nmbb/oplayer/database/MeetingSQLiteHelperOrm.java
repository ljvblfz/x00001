package com.nmbb.oplayer.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.broadsoft.xmeeting.po.DownloadinfoPO;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.nmbb.oplayer.OPlayerApplication;
import com.nmbb.oplayer.exception.Logger;
import com.nmbb.oplayer.po.POMedia;

public class MeetingSQLiteHelperOrm extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = "meeting.db";
	private static final int DATABASE_VERSION = 1;

	public MeetingSQLiteHelperOrm(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public MeetingSQLiteHelperOrm() {
		super(OPlayerApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, DownloadinfoPO.class);
		} catch (SQLException e) {
			Logger.e(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int arg2, int arg3) {
		try {
			TableUtils.dropTable(connectionSource, DownloadinfoPO.class, true);
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Logger.e(e);
		}
	}
}