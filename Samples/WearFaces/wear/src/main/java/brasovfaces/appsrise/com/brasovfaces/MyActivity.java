package brasovfaces.appsrise.com.brasovfaces;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyActivity extends Activity {

    private TextView mTime, mBattery;
     WatchViewStub stub = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        mTime = (TextView) findViewById(R.id.watch_time);
        mBattery = (TextView) findViewById(R.id.watch_battery);

        stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {


                //BR time
                mTimeInfoReceiver.onReceive(MyActivity.this, registerReceiver(null, INTENT_FILTER));    //  Here, we're just calling our onReceive() so it can set the current time.
                registerReceiver(mTimeInfoReceiver, INTENT_FILTER);

                //BR battery
                registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            }
        });

    }

    private final static IntentFilter INTENT_FILTER;
    static {
        INTENT_FILTER = new IntentFilter();
        INTENT_FILTER.addAction(Intent.ACTION_TIME_TICK);
        INTENT_FILTER.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        INTENT_FILTER.addAction(Intent.ACTION_TIME_CHANGED);
    }

    private final String TIME_FORMAT_DISPLAYED = "kk:mm a";

    private BroadcastReceiver mTimeInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context arg0, Intent intent) {
            mTime.setText(
                    new SimpleDateFormat(TIME_FORMAT_DISPLAYED)
                            .format(Calendar.getInstance().getTime()));
        }
    };

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context arg0, Intent intent) {
            mBattery.setText(String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0) + "%"));
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mTimeInfoReceiver);
        unregisterReceiver(mBatInfoReceiver);
    }

    @Override
    protected void onPause() {
        //  onPause();
        Log.v("WatchFace", "onPause();");
        super.onPause();
        mTime.setTextColor(Color.WHITE);
        mBattery.setTextColor(Color.WHITE);
    }

    @Override
    protected void onResume() {
        //  onResume();
        Log.v("WatchFace", "onResume();");
        super.onResume();
        //mTime.setTextColor(Color.RED);
        //mBattery.setTextColor(Color.RED);
    }

/*
    @Override
    public void onScreenDim() {
        mTime.setTextColor(Color.WHITE);
        mBattery.setTextColor(Color.WHITE);
    }

    @Override
    public void onScreenAwake() {
        mTime.setTextColor(Color.RED);
        mBattery.setTextColor(Color.RED);
    }
*/
}
