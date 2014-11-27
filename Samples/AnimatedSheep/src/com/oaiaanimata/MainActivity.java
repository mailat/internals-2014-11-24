package com.oaiaanimata;



import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    
	OaiaSurface oaiaSurface;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oaiaSurface = new OaiaSurface(this); 
        setContentView(oaiaSurface);      
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        oaiaSurface.terminateThread();
        System.gc();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        if (oaiaSurface.surfaceCreated == true)
        {
        	oaiaSurface.createThread();
        }
    }
}