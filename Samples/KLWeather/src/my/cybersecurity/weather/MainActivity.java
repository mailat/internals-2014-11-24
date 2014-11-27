package my.cybersecurity.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	String TAG = "CyberSecurityWeather";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(TAG, "Inside of the onCreate");
	}

	public void showWeatherClick (View v)
	{
		Log.d(TAG, "Inside of the showWeatherClick");

		//get the input city
		EditText edittext = (EditText) findViewById(R.id.inputCity);
		Toast.makeText(this, "Click on the show weather for city: " + edittext.getText().toString(), Toast.LENGTH_LONG).show();
		
		//start the second activity
		Intent intent = new Intent(this, WeatherActivity.class);
		intent.putExtra("city", edittext.getText().toString().replaceAll(" ", "%20"));
		startActivity(intent);
	}
}
