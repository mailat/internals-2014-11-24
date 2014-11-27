package my.cybersecurity.weather;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class WeatherActivity extends Activity {
	String TAG = "CyberSecurityWeather";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(TAG, "Inside of the onCreate in WeatherActivity");
		
		//get from the intent the city
		String city = getIntent().getStringExtra("city");
		
		//TODO Marius will implement the AsyncTask to get the weather
		weatherDownloader.execute("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric");
	}
	
	AsyncTask weatherDownloader = new AsyncTask() {

		@Override
		protected Object doInBackground(Object... params) {
			//TODO get the JSON data
			
			//TODO Parse the JSON
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			//TODO stop the progress dialog
			super.onPostExecute(result);
		}
		
		
		
	};
	

}
