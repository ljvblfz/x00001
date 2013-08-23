package com.broadsoft.xmcommon.androiddao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.broadsoft.xmcommon.androidsqllite.PadInfoConstant;
import com.broadsoft.xmcommon.androidsqllite.SQLiteOpenHelperSupport;

/**
 * 
 * @author lu.zhen
 * 
 */
public class PadInfoDao extends PadInfoConstant implements
		IDao<PadInfoEntity> {

	public final static String RAW_SELECT_ALL_QUERY = "SELECT  "+ COLUMN_GUID+ " , "+ COLUMN_ANDROID_ID+ " , "+COLUMN_ASSET_CODE+" , "+ COLUMN_JSON_DATA+" FROM " + TABLE_PADINFO;
	public final static String[] COLUMNS={ COLUMN_GUID, COLUMN_ANDROID_ID, COLUMN_ASSET_CODE,COLUMN_JSON_DATA };
	private SQLiteOpenHelperSupport sqliteOpenHelperSupport;
	public PadInfoDao(Context context) {
		this.sqliteOpenHelperSupport=new SQLiteOpenHelperSupport(context);	 
	}

	@Override
	public boolean add(PadInfoEntity entity) {
		SQLiteDatabase db = sqliteOpenHelperSupport.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_GUID, GuidGeneration.generateGuid(TABLE_PADINFO));
		values.put(COLUMN_ANDROID_ID, entity.getAndroidId());
		values.put(COLUMN_ASSET_CODE, entity.getAssetCode());
		values.put(COLUMN_JSON_DATA, entity.getJsonData());
		db.insert(TABLE_PADINFO, null, values);
//		db.close();
		return true;
	}

	@Override
	public boolean update(PadInfoEntity entity) {
		SQLiteDatabase db = sqliteOpenHelperSupport.getWritableDatabase();

		ContentValues values = new ContentValues();
		// values.put(COLUMN_GUID, entity.getGuid());
		values.put(COLUMN_ANDROID_ID, entity.getAndroidId());
		values.put(COLUMN_ASSET_CODE, entity.getAssetCode());
		values.put(COLUMN_JSON_DATA, entity.getJsonData());
		// updating row
		int count = db.update(TABLE_PADINFO, values, COLUMN_GUID + " = ?",
				new String[] { String.valueOf(entity.getGuid()) });
		db.close();
		return (count > 0 ? true : false);

	}

	@Override
	public boolean delete(String guid) {
		SQLiteDatabase db = sqliteOpenHelperSupport.getWritableDatabase();
		// deleting row
		int count = db.delete(TABLE_PADINFO, COLUMN_GUID + " = ?", new String[] { String.valueOf(guid) });
		db.close();
		return (count>0?true:false);
	}

	
	@Override
	public PadInfoEntity findByPK(String guid) {
		SQLiteDatabase db = sqliteOpenHelperSupport.getReadableDatabase();
		Cursor cursor = db.query(TABLE_PADINFO,COLUMNS , COLUMN_GUID + "=?", new String[] { String.valueOf(guid) }, null, null, null, null);
		boolean flag=cursor.moveToFirst(); 
		if (cursor != null&&flag){ 
			PadInfoEntity entity = new PadInfoEntity();
			entity.setGuid(cursor.getString(0));
			entity.setAndroidId(cursor.getString(1));
			entity.setAssetCode(cursor.getString(2));
			entity.setJsonData(cursor.getString(3));
			cursor.close();
			db.close();
			return entity;
		} 
		db.close();
		return null;
	}

	
	
	
	@Override
	public List<PadInfoEntity> findAll() {
		List<PadInfoEntity> entityList = new ArrayList<PadInfoEntity>();
	    // Select All Query 
	    SQLiteDatabase db = sqliteOpenHelperSupport.getWritableDatabase();
	    Cursor cursor = db.rawQuery(RAW_SELECT_ALL_QUERY, null);
	   
	    // looping through all rows and adding to list	
	    boolean flag=cursor.moveToFirst(); 
		if (cursor != null&&flag){ 
	        do {
	        	PadInfoEntity entity = new PadInfoEntity();
				entity.setGuid(cursor.getString(0));
				entity.setAndroidId(cursor.getString(1));
				entity.setAssetCode(cursor.getString(2));
				entity.setJsonData(cursor.getString(3));
	            // Adding  to list
				entityList.add(entity);
	        } while (cursor.moveToNext());
	        cursor.close();
	    }
	    db.close();
	    // return contact list
	    return entityList; 
	}
	
	
	
	public PadInfoEntity uniqueOne() {
		List<PadInfoEntity> entityList =findAll(); 
		if(null!=entityList&&!entityList.isEmpty()){
			return entityList.get(0);
		}
		return null;
	}

}
