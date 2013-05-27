package com.broadsoft.xmeeting.dao;

import java.util.ArrayList;
import java.util.List;

import com.broadsoft.common.sqllite.DataBaseOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 
 * @author lu.zhen
 *
 */
public class PersonDAO {
	private String TAG="PersonDAO";
	
	private DataBaseOpenHelper dbOpenHelper;

	public PersonDAO(Context context) {
		dbOpenHelper = new DataBaseOpenHelper(context);
	}

	public void initPersons() {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		database.beginTransaction();
		try {
			database.execSQL("insert into person(name, age) values(?,?)",
					new Object[] { "立马", (short) 32 });
			database.execSQL("insert into person(name, age) values(?,?)",
					new Object[] { "当下", (short) 37 });
			database.setTransactionSuccessful();// 提交事务（做到事务统一）
		} catch (Exception e) {
			Log.d(TAG, "initPersons raise exception",e);
		}
		database.endTransaction(); 
	}

	public void save(Person person) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		database.execSQL("insert into person(name, age) values(?,?)",
				new Object[] { person.getName(), person.getAge() }); 
	}

	public void update(Person person) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		database.execSQL(
				"update person set name=?, age=? where personid=?",
				new Object[] { person.getName(), person.getAge(),
						person.getPersonId() });
	}

	public Person findById(Integer id) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		Cursor cursor = database.rawQuery(
				"select * from person where personid=?",
				new String[] { String.valueOf(id) });
		if (cursor.moveToNext()) {
			return new Person(cursor.getInt(0), cursor.getString(1),
					cursor.getShort(2));
		}
		return null;
	}
	
	

	public Person findByName(String name) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		Cursor cursor = database.rawQuery(
				"select * from person where name=?",
				new String[] {name });
		if (cursor.moveToNext()) {
			return new Person(cursor.getInt(0), cursor.getString(1),
					cursor.getShort(2));
		}
		return null;
	}

	public void delete(Integer... ids) {
		if (ids.length > 0) {
			SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
			StringBuffer sb = new StringBuffer();
			for (Integer id : ids) {
				sb.append('?').append(",");
			}
			sb.deleteCharAt(ids.length - 1);
			database.execSQL("delete person where personid in(" + sb + ")",
					new Object[] { ids });
		}
	}

	public List<Person> getScrollData(int startResult, int maxResult) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		List<Person> persons = new ArrayList<Person>();
		Cursor cursor = database.rawQuery(
				"select * from person limit ?, ?",
				new String[] { String.valueOf(startResult),
						String.valueOf(maxResult) });
		while (cursor.moveToNext()) {
			persons.add(new Person(cursor.getInt(0), cursor.getString(1),
					cursor.getShort(2)));
		}
		return persons;
	}

	public long getCount() {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		Cursor cursor = database.rawQuery("select count(*) from person", null);
		if (cursor.moveToNext()) {
			return cursor.getLong(0);
		}
		return 0;
	}
}