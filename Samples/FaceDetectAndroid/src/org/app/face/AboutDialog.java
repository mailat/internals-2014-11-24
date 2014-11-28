package org.app.face;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class AboutDialog extends Dialog implements OnClickListener 
{
	LinearLayout layoutDialog;
	Button cancelButton;
	Activity currentActivity;
	 
	public AboutDialog(Context context, Activity a) 
	{ 
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.about_dialog);
		
		currentActivity = a;
		
		layoutDialog = (LinearLayout)findViewById(R.id.layoutDialog);
		setWidthOfDialog(a);
		
		cancelButton = (Button) findViewById(R.id.btnCancel);
		cancelButton.setOnClickListener(this);
	}
  
	public void onClick(View v) 
	{
		if (v == cancelButton)
		{
			dismiss(); 
		}  
	}
	
	public void setWidthOfDialog(Activity a)
	{
	    Display display = a.getWindowManager().getDefaultDisplay(); 
	    int width = display.getWidth();
	    ViewGroup.LayoutParams params = layoutDialog.getLayoutParams();
	    params.width = width - 20;
	    layoutDialog.setLayoutParams(params);
	}
}