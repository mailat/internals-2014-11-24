package my.cybersecurity.weather;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class WeatherIntentService extends IntentService {


	public WeatherIntentService() {
		super("WeatherIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		try {
			// transporter for our in->out call
			String urlWeather = "http://api.openweathermap.org/data/2.5/weather?q=" + "Kuala%20Lumpur" + "&units=metric";
			
			HttpClient client = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(urlWeather);
			String response = client.execute(httpget, new BasicResponseHandler());

			JSONObject jObj = new JSONObject(response);
			JSONObject jsonObj = jObj.getJSONObject("main");
			
			String weatherString = "";
			weatherString = new Float(jsonObj.getString("temp")).toString();
			Log.d("Weather", "The new weather via IntentService is: "+weatherString);
			
		} catch (Throwable e) {
		}
	}

}
