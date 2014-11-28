package org.app.face;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PointF;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailsDialog  extends Dialog implements OnClickListener 
{
	LinearLayout layoutDialog;
	Button cancelButton;
	TextView txtNoOfFaces, txtConfidence, txtEyeDistance, txtMidPoint;
	Activity currentActivity;
	 
	public DetailsDialog(Context context, Activity a, String faces, String confidance, String eyeDist, ArrayList<String> midPoints) 
	{ 
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.details_dialog);
		
		currentActivity = a;
		
		layoutDialog = (LinearLayout)findViewById(R.id.layoutDialog);
		setWidthOfDialog(a);
		
		cancelButton = (Button) findViewById(R.id.btnCancel);
		cancelButton.setOnClickListener(this);
		
		txtConfidence = (TextView) findViewById(R.id.txtConfidence);
		txtConfidence.setText(confidance + "");
		
		txtNoOfFaces = (TextView) findViewById(R.id.txtNoOfFaces);
		txtNoOfFaces.setText(faces + "");
		
		txtEyeDistance = (TextView) findViewById(R.id.txtEyeDistance);
		txtEyeDistance.setText(eyeDist + "");
		
		txtMidPoint = (TextView) findViewById(R.id.txtMidPoint);
		txtMidPoint.setText(midPoints.toString());
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