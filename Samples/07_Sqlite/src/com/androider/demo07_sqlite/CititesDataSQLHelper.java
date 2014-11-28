package com.androider.demo07_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * CititesDataSQLHelper
 */
public class CititesDataSQLHelper extends SQLiteOpenHelper {
	//date legate de baza de date
	private static final String DATABASE_NAME = "cities.db";
	private static final int DATABASE_VERSION = 6;

	//date legate de tabele
	public static final String TABLE = "cities";

	//campurile din tabele
	public static final String CITY = "city";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";

	public CititesDataSQLHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE + "( " + BaseColumns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + CITY + " TEXT NOT NULL, "
				+ LATITUDE + " REAL, " 
				+ LONGITUDE + " REAL);";
		
		Log.d("Demo8", "onCreate database: " + sql);
		db.execSQL(sql);
		
	    insertInitialValues(db);
	}

	public void insertInitialValues(SQLiteDatabase db) {
		//importa coordonatele pentru orase prin ContentValues
		ContentValues cv=new ContentValues();
        
		cv.put(CititesDataSQLHelper.CITY, "Sibiu");
		cv.put(CititesDataSQLHelper.LATITUDE, 24.15);
		cv.put(CititesDataSQLHelper.LONGITUDE, 45.79);
		db.insert(CititesDataSQLHelper.TABLE, null, cv); 
		
		cv.put(CititesDataSQLHelper.CITY, "Paltinis");
		cv.put(CititesDataSQLHelper.LATITUDE, 23.93);
		cv.put(CititesDataSQLHelper.LONGITUDE, 45.6);
		db.insert(CititesDataSQLHelper.TABLE, null, cv); 

		//importa coordonatele pentru orase prin SQL
		db.execSQL("INSERT INTO " + TABLE + " values ( null, 'Medias',24.36,46.16);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("Demo8", "onUpgrade: distrugem datele vechi");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE + ";");
	    onCreate(db);
	}
}
