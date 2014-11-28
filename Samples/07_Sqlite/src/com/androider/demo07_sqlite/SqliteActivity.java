package com.androider.demo07_sqlite;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SqliteActivity extends Activity {
	CititesDataSQLHelper citiesData;
	TextView citiesListing;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		citiesListing = (TextView) findViewById(R.id.citiesListing);

		citiesData = new CititesDataSQLHelper(this);
		showCities();
	}

	/**
	 * showCities
	 * display the records from cities table
	 */
	private void showCities() {
		Log.d("Demo8", "before getWritableDatabase");
		int testVariableDebug = 1;
		Log.d("Demo8", String.valueOf(testVariableDebug));
		
		
		SQLiteDatabase db = citiesData.getWritableDatabase();
		Log.d("Demo8", "after getWritableDatabase");

		
		
		Cursor cursor = db.query(CititesDataSQLHelper.TABLE, null, null, null, null, null, null);
		startManagingCursor(cursor);
		
		StringBuilder result = new StringBuilder("Orase si coordonatele lor:\n\n");
		
		while (cursor.moveToNext()) {
			long id = cursor.getLong(0);
			String city = cursor.getString(1);
			double lat = cursor.getDouble(2);
			double longitude = cursor.getDouble(3);
			result.append(id + "-" + city + "-" + lat + "-" + longitude+"\n");
		}
		citiesListing.setText(result);
		
		if ( cursor!= null && !cursor.isClosed())
			cursor.close();
	}
  
	@Override
	public void onDestroy() {
		super.onDestroy();
		citiesData.close();
	}
}