package com.broadsoft.xmcommon.androiddao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.broadsoft.xmcommon.androidsqllite.CompanyInfoConstant;
import com.broadsoft.xmcommon.androidsqllite.SQLiteOpenHelperSupport;

/**
 * 
 * @author lu.zhen
 * 
 */
public class CompanyInfoDao extends CompanyInfoConstant implements
		IDao<CompanyInfoEntity> {

	public final static String RAW_SELECT_ALL_QUERY = "SELECT  "+ COLUMN_GUID+ " , "+ COLUMN_COMPANY_NAME+ " , "+COLUMN_COMPANY_CODE+" , "+ COLUMN_JSON_DATA+ " FROM " + TABLE_COMPANYINFO;
	public final static String[] COLUMNS={ COLUMN_GUID, COLUMN_COMPANY_NAME, COLUMN_COMPANY_CODE,COLUMN_JSON_DATA };
	private SQLiteOpenHelperSupport sqliteOpenHelperSupport;
	public CompanyInfoDao(Context context) {
		this.sqliteOpenHelperSupport=new SQLiteOpenHelperSupport(context);	 
	}

	@Override
	public boolean add(CompanyInfoEntity entity) {
		SQLiteDatabase db = sqliteOpenHelperSupport.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(COLUMN_GUID, GuidGeneration.generateGuid(TABLE_COMPANYINFO));
		values.put(COLUMN_COMPANY_NAME, entity.getCompanyName());
		values.put(COLUMN_COMPANY_CODE, entity.getCompanyCode());
		values.put(COLUMN_JSON_DATA, entity.getJsonData());
		db.insert(TABLE_COMPANYINFO, null, values);
		db.close();
		return true;
	}

	@Override
	public boolean update(CompanyInfoEntity entity) {
		SQLiteDatabase db = sqliteOpenHelperSupport.getWritableDatabase();

		ContentValues values = new ContentValues();
		// values.put(COLUMN_GUID, entity.getGuid());
		values.put(COLUMN_COMPANY_NAME, entity.getCompanyName());
		values.put(COLUMN_COMPANY_CODE, entity.getCompanyCode());
		values.put(COLUMN_JSON_DATA, entity.getJsonData());
		// updating row
		int count = db.update(TABLE_COMPANYINFO, values, COLUMN_GUID + " = ?",
				new String[] { String.valueOf(entity.getGuid()) });
		db.close();
		return (count > 0 ? true : false);

	}

	@Override
	public boolean delete(String guid) {
		SQLiteDatabase db = sqliteOpenHelperSupport.getWritableDatabase();
		// deleting row
		int count = db.delete(TABLE_COMPANYINFO, COLUMN_GUID + " = ?", new String[] { String.valueOf(guid) });
		db.close();
		return (count>0?true:false);
	}

	
	@Override
	public CompanyInfoEntity findByPK(String guid) {
		SQLiteDatabase db = sqliteOpenHelperSupport.getReadableDatabase();
		Cursor cursor = db.query(TABLE_COMPANYINFO,COLUMNS , COLUMN_GUID + "=?", new String[] { String.valueOf(guid) }, null, null, null, null);
		boolean flag=cursor.moveToFirst(); 
		if (cursor != null&&flag){ 
			CompanyInfoEntity entity = new CompanyInfoEntity();
			entity.setGuid(cursor.getString(0));
			entity.setCompanyName(cursor.getString(1));
			entity.setCompanyCode(cursor.getString(2));
			entity.setJsonData(cursor.getString(3));
			cursor.close();
			db.close();
			return entity;
		} 
		db.close();
		return null;
	}

	
	
	
	@Override
	public List<CompanyInfoEntity> findAll() {
		List<CompanyInfoEntity> entityList = new ArrayList<CompanyInfoEntity>();
	    // Select All Query 
	    SQLiteDatabase db = sqliteOpenHelperSupport.getWritableDatabase();
	    Cursor cursor = db.rawQuery(RAW_SELECT_ALL_QUERY, null);
	   
	    // looping through all rows and adding to list
		boolean flag=cursor.moveToFirst(); 
		if (cursor != null&&flag){
	        do {
	        	CompanyInfoEntity entity = new CompanyInfoEntity();
				entity.setGuid(cursor.getString(0));
				entity.setCompanyName(cursor.getString(1));
				entity.setCompanyCode(cursor.getString(2));
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
	
	 
	public CompanyInfoEntity uniqueOne() {
		List<CompanyInfoEntity> entityList =findAll(); 
		if(null!=entityList&&!entityList.isEmpty()){
			return entityList.get(0);
		}
		return null;
	}

}
