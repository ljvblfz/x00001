package com.broadsoft.xmcommon.androiddao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.broadsoft.xmcommon.androidsqllite.DownloadInfoSQLiteOpenHelper;

/**
 * 
 * @author lu.zhen
 * 
 */
public class DownloadInfoDao extends DownloadInfoSQLiteOpenHelper implements
		IDao<DownloadInfoEntity> {

	public DownloadInfoDao(Context context) {
		super(context);
	}

	public final static String RAW_SELECT_ALL_QUERY = "SELECT  " + COLUMN_GUID + " , "
			+ COLUMN_MEETING_ID + " , " + COLUMN_MEETING_NAME + " , "
			+ COLUMN_MEMBER_ID + " , " + COLUMN_MEMBER_DISPLAY_NAME + " , "
			+ COLUMN_SEATNO + " , " + COLUMN_SERVICE_MEMBER_ID + " , "
			+ COLUMN_SERVICE_MEMBER_DISPLAY_NAME + " , " + COLUMN_STATUS
			+ " , " + COLUMN_JSON_DATA + " FROM " + TABLE_DOWNLOADINFO;
	
	

	@Override
	public boolean add(DownloadInfoEntity entity) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_GUID, GuidGeneration.generateGuid(TABLE_DOWNLOADINFO));
		values.put(COLUMN_MEETING_ID, entity.getMeetingId());
		values.put(COLUMN_MEETING_NAME, entity.getMeetingName());
		values.put(COLUMN_MEMBER_ID, entity.getMemberId());
		values.put(COLUMN_MEMBER_DISPLAY_NAME, entity.getMemberDisplayName());
		values.put(COLUMN_SEATNO, entity.getSeatno());
		values.put(COLUMN_SERVICE_MEMBER_ID, entity.getServiceMemberId());
		values.put(COLUMN_SERVICE_MEMBER_DISPLAY_NAME,
				entity.getServiceMemberDisplayName());
		values.put(COLUMN_STATUS, entity.getStatus());
		values.put(COLUMN_JSON_DATA, entity.getJsonData());
		db.insert(TABLE_DOWNLOADINFO, null, values);
		db.close();
		return true;
	}

	@Override
	public boolean update(DownloadInfoEntity entity) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		// values.put(COLUMN_GUID,
		// GuidGeneration.generateGuid(TABLE_DOWNLOADINFO));
		values.put(COLUMN_MEETING_ID, entity.getMeetingId());
		values.put(COLUMN_MEETING_NAME, entity.getMeetingName());
		values.put(COLUMN_MEMBER_ID, entity.getMemberId());
		values.put(COLUMN_MEMBER_DISPLAY_NAME, entity.getMemberDisplayName());
		values.put(COLUMN_SEATNO, entity.getSeatno());
		values.put(COLUMN_SERVICE_MEMBER_ID, entity.getServiceMemberId());
		values.put(COLUMN_SERVICE_MEMBER_DISPLAY_NAME,
				entity.getServiceMemberDisplayName());
		values.put(COLUMN_STATUS, entity.getStatus());
		values.put(COLUMN_JSON_DATA, entity.getJsonData());
		// updating row
		int count = db.update(TABLE_DOWNLOADINFO, values, COLUMN_GUID + " = ?",
				new String[] { String.valueOf(entity.getGuid()) });
		db.close();
		return (count > 0 ? true : false);
	}

	@Override
	public boolean delete(String guid) {
		SQLiteDatabase db = this.getWritableDatabase();
		// deleting row
		int count = db.delete(TABLE_DOWNLOADINFO, COLUMN_GUID + " = ?",
				new String[] { String.valueOf(guid) });
		db.close();
		return (count > 0 ? true : false);
	}
	

	public final static String[] COLUMNS = { COLUMN_GUID, COLUMN_MEETING_ID,
			COLUMN_MEETING_NAME, COLUMN_MEMBER_ID, COLUMN_MEMBER_DISPLAY_NAME,
			COLUMN_SEATNO, COLUMN_SERVICE_MEMBER_ID,
			COLUMN_SERVICE_MEMBER_DISPLAY_NAME, COLUMN_STATUS, COLUMN_JSON_DATA };

	@Override
	public DownloadInfoEntity findByPK(String guid) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_DOWNLOADINFO, COLUMNS, COLUMN_GUID
				+ "=?", new String[] { String.valueOf(guid) }, null, null,
				null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			DownloadInfoEntity entity = new DownloadInfoEntity();
			entity.setGuid(cursor.getString(0));
			entity.setMeetingId(cursor.getString(1));
			entity.setMeetingName(cursor.getString(2));
			entity.setMemberId(cursor.getString(3));
			entity.setMemberDisplayName(cursor.getString(4));
			entity.setSeatno(cursor.getString(5));
			entity.setServiceMemberId(cursor.getString(6));
			entity.setServiceMemberDisplayName(cursor.getString(7));
			entity.setStatus(cursor.getString(8));
			entity.setJsonData(cursor.getString(9));
			cursor.close();
			db.close();
			return entity;
		}
		db.close();
		return null; 
	}

	@Override
	public List<DownloadInfoEntity> findAll() {
		List<DownloadInfoEntity> entityList = new ArrayList<DownloadInfoEntity>();
	    // Select All Query 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(RAW_SELECT_ALL_QUERY, null);
	   
	    // looping through all rows and adding to list
	    if (null!=cursor&&cursor.moveToFirst()) {
	        do {
	        	DownloadInfoEntity entity = new DownloadInfoEntity(); 
	            // Adding  to list
				entity.setGuid(cursor.getString(0));
				entity.setMeetingId(cursor.getString(1));
				entity.setMeetingName(cursor.getString(2));
				entity.setMemberId(cursor.getString(3));
				entity.setMemberDisplayName(cursor.getString(4));
				entity.setSeatno(cursor.getString(5));
				entity.setServiceMemberId(cursor.getString(6));
				entity.setServiceMemberDisplayName(cursor.getString(7));
				entity.setStatus(cursor.getString(8));
				entity.setJsonData(cursor.getString(9));
				entityList.add(entity);
	        } while (cursor.moveToNext());
	        cursor.close();
	    }
	    db.close();
	    // return contact list
	    return entityList; 
	}
	
	

	public DownloadInfoEntity findByMeetingId(String meetingId) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_DOWNLOADINFO, COLUMNS, COLUMN_MEETING_ID
				+ "=?", new String[] { String.valueOf(meetingId) }, null, null,
				null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			DownloadInfoEntity entity = new DownloadInfoEntity();
			entity.setGuid(cursor.getString(0));
			entity.setMeetingId(cursor.getString(1));
			entity.setMeetingName(cursor.getString(2));
			entity.setMemberId(cursor.getString(3));
			entity.setMemberDisplayName(cursor.getString(4));
			entity.setSeatno(cursor.getString(5));
			entity.setServiceMemberId(cursor.getString(6));
			entity.setServiceMemberDisplayName(cursor.getString(7));
			entity.setStatus(cursor.getString(8));
			entity.setJsonData(cursor.getString(9));
			cursor.close();
			db.close();
			return entity;
		}
		db.close();
		return null; 
	}

}
