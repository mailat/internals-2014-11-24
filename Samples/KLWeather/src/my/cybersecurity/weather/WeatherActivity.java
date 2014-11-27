package my.cybersecurity.weather;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class WeatherActivity extends Activity {
	String TAG = "CyberSecurityWeather";
	TextView actualTemp;
	TextView minTemp;
	TextView maxTemp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather);
		Log.d(TAG, "Inside of the onCreate in WeatherActivity");

		// get from UI the reference for Views
		actualTemp = (TextView) findViewById(R.id.actualTemp);
		minTemp = (TextView) findViewById(R.id.minTemp);
		maxTemp = (TextView) findViewById(R.id.maxTemp);

		// get from the intent the city
		String city = getIntent().getStringExtra("city");

		// AsyncTask to get the weather
		new WeatherDownloader()
				.execute("http://api.openweathermap.org/data/2.5/weather?q="
						+ city + "&units=metric");
	}

	class WeatherDownloader extends AsyncTask<String, Object, String> {

		@Override
		protected String doInBackground(String... urls) {
			try {
				// transporter for our in->out call
				HttpClient client = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(urls[0]);
				Log.d(TAG, "Parse for: " + urls[0]);
				String response = client.execute(httpget,
						new BasicResponseHandler());
				Log.d(TAG, "Parse data is: " + response);
				return response;

			} catch (Throwable e) {
				Log.d(TAG, e.getMessage());
				return "Error on posting" + e.getMessage();
			}
		}

		protected void onPostExecute(String result) {
			// parse the JSON and get the temperature
			try {
				JSONObject jObj = new JSONObject(result);
				JSONObject jsonObj = jObj.getJSONObject("main");
				actualTemp.setText(new Float(jsonObj.getString("temp"))
						.intValue() + "\u00B0");
				minTemp.setText(new Float(jsonObj.getString("temp_min"))
						.intValue() + "\u00B0");
				maxTemp.setText(new Float(jsonObj.getString("temp_max"))
						.intValue() + "\u00B0");

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	};

}
