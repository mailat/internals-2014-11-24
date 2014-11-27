package my.cybersecurity.weather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompletedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("Weather", "We are in onReceive");
		context.startService(new Intent(context, WeatherIntentService.class));
	}

}
